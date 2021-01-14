package common.buessiness.problems.cachedesign.cacheinvalid;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

@Slf4j
@RequestMapping("cacheinvalid")
@RestController
public class CacheInvalidController {

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    AtomicInteger atomicInteger=new AtomicInteger();

  //  @PostConstruct
    public void wrongInit(){
        //初始化1000个城市数据到Redis，所有缓存数据有效期30秒
        IntStream.rangeClosed(1,1000)
                .forEach(i->stringRedisTemplate.opsForValue().set("city"+i,getCityFromDb(i), 30, TimeUnit.SECONDS ));
        log.info("Cache init finished");
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(()->{
            log.info("db qps :{}",atomicInteger.getAndSet(0));
        },0 ,1 ,TimeUnit.SECONDS );

    }

    //@PostConstruct
    public void rightInit(){
        //Key 不会集中在 30 秒这个时刻过期，而是会分散在 30~40 秒之间过期
        IntStream.rangeClosed(1,1000)
                .forEach(i->stringRedisTemplate.opsForValue().set("city"+i,getCityFromDb(i),
                        30+ThreadLocalRandom.current().nextInt(10), TimeUnit.SECONDS ));
        log.info("Cache init finished");
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(()->{
            log.info("db qps :{}",atomicInteger.getAndSet(0));
        },0 ,1 ,TimeUnit.SECONDS );

    }


    @PostConstruct
    public void rightInit2() throws InterruptedException {
        CountDownLatch latch=new CountDownLatch(1);
        //Key 不会集中在 30 秒这个时刻过期，而是会分散在 30~40 秒之间过期

        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(()->{
            IntStream.rangeClosed(1,1000).forEach(i->{
                String data = getCityFromDb(i);
                try {
                    TimeUnit.MILLISECONDS.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(StringUtils.isNotBlank(data)){
                    //缓存永不过期
                    stringRedisTemplate.opsForValue().set("city"+i, data);
                }
            });

        },0,30,TimeUnit.SECONDS);
        //启动程序的时候需要等待首次更新缓存完成
        latch.countDown();
        log.info("Cache update finished");

        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(()->{
            log.info("db qps :{}",atomicInteger.getAndSet(0));
        },0 ,1 ,TimeUnit.SECONDS );
        latch.await();
    }


    @GetMapping("city")
    public String city(){
        int id = ThreadLocalRandom.current().nextInt(1000);
        String key="city"+id;
        String data = stringRedisTemplate.opsForValue().get(key);
        if(data==null){
            //回源到数据库查询
            data=getCityFromDb(id);
            if(StringUtils.isNotBlank(data)){
                //缓存30秒过期
                stringRedisTemplate.opsForValue().set(key,data,30,TimeUnit.SECONDS);
            }
        }
        return data;
    }

    ////模拟查询数据库，查一次增加计数器加一
    private String getCityFromDb(int cityId) {
        atomicInteger.incrementAndGet();
        return "citydata"+System.currentTimeMillis();
    }


}