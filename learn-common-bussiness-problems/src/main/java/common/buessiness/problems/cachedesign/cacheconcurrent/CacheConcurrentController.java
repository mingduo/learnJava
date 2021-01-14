package common.buessiness.problems.cachedesign.cacheconcurrent;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@RequestMapping("cacheconcurrent")
@RestController
public class CacheConcurrentController {


    @Autowired
    StringRedisTemplate stringRedisTemplate;

    private AtomicInteger atomicInteger = new AtomicInteger();

    @Autowired
    private RedissonClient redissonClient;

    @PostConstruct
    public void init() {
        stringRedisTemplate.opsForValue().set("hotsopt", getExpensiveData(), 5, TimeUnit.SECONDS);
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> {
            log.info("DB QPS : {}", atomicInteger.getAndSet(0));
        }, 0, 1, TimeUnit.SECONDS);
    }

    @GetMapping("wrong")
    public String wrong() {
        String data = stringRedisTemplate.opsForValue().get("hotsopt");
        if (StringUtils.isBlank(data)) {
            data = getExpensiveData();
            stringRedisTemplate.opsForValue().set("hotsopt", data, 5, TimeUnit.SECONDS);
        }
        return data;
    }

    @GetMapping("right")
    public String right() {
        String data = stringRedisTemplate.opsForValue().get("hotsopt");
        if (StringUtils.isBlank(data)) {
            RLock locker = redissonClient.getLock("locker");
            if (locker.tryLock()) {
                try {
                    //双重检查，因为可能已经有一个B线程过了第一次判断，在等锁，然后A线程已经把
                    if (StringUtils.isBlank(stringRedisTemplate.opsForValue().get("hotsopt"))) {
                        //回源到数据库查询
                        data = getExpensiveData();
                        stringRedisTemplate.opsForValue().set("hotsopt", data, 5, TimeUnit.SECONDS);

                    }
                } finally {
                    //别忘记释放，另外注意写法，获取锁后整段代码try+finally，确保unlock万无
                    locker.unlock();
                }

            }
        }
        return data;
    }

    private String getExpensiveData() {
        atomicInteger.incrementAndGet();
        return "import data";
    }
}