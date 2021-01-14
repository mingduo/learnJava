package common.buessiness.problems.cachedesign.cachepenetration;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

@Slf4j
@RequestMapping("cachepenetration")
@RestController
public class CachePenetrationController {

    @Autowired
    StringRedisTemplate stringRedisTemplate;
    AtomicInteger atomicInteger = new AtomicInteger();
    BloomFilter<Integer> bloomFilter;

    @PostConstruct
    public void init() {

        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> {
            log.info("db qps:{}", atomicInteger.getAndSet(0));
        }, 0, 1, TimeUnit.SECONDS);
        bloomFilter = BloomFilter.create(Funnels.integerFunnel(), 1000, 0.01);
        IntStream.rangeClosed(1, 10000).forEach(bloomFilter::put);
    }

    @GetMapping("wrong")
    public String wrong(int id) {

        String key = "data" + id;
        String data = stringRedisTemplate.opsForValue().get(key);
        if (StringUtils.isBlank(data)) {
            data = getCityFromDb(id);
            stringRedisTemplate.opsForValue().set(key, data, 30, TimeUnit.SECONDS);
        }
        return data;
    }

    @GetMapping("right")
    public String right(int id) {

        String key = "data" + id;
        String data = stringRedisTemplate.opsForValue().get(key);
        if (StringUtils.isBlank(data)) {
            data = getCityFromDb(id);
            if (StringUtils.isBlank(data)) {
                stringRedisTemplate.opsForValue().set(key, data, 30, TimeUnit.SECONDS);
            } else {
                stringRedisTemplate.opsForValue().set(key, "NODATA", 30, TimeUnit.SECONDS);

            }
        }
        return data;
    }

    @GetMapping("right2")
    public String right2(int id) {
        String data="";
        if (bloomFilter.mightContain(id)) {
            String key = "data" + id;
             data = stringRedisTemplate.opsForValue().get(key);

            if (StringUtils.isBlank(data)) {
                data = getCityFromDb(id);
                stringRedisTemplate.opsForValue().set(key, data, 30, TimeUnit.SECONDS);
            }
        }
        return data;
    }

    private String getCityFromDb(int id) {
        atomicInteger.incrementAndGet();
        if (id > 0 && id <= 10000) {
            return "userdata" + id;
        }
        return "";
    }
}