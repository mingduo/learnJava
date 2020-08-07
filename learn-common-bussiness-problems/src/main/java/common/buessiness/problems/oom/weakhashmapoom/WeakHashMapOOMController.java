package common.buessiness.problems.oom.weakhashmapoom;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.internal.util.ConcurrentReferenceHashMap;

import java.lang.ref.WeakReference;
import java.util.WeakHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.LongStream;


/**
 * 声明一个 Key 是 User 类型、Value 是 UserProfile 类型的 WeakHashMap，作为用户数
 * 据缓存，往其中添加 200 万个 Entry，然后使用 ScheduledThreadPoolExecutor 发起一
 * 个定时任务，每隔 1 秒输出缓存中的 Entry 个数：
 */
@Slf4j
public class WeakHashMapOOMController {



    /**
     * WeakHashMap 的 Key 虽然是弱引用，但是其 Value 却持有 Key 中对象的强引用，
     * Value 被 Entry 引用，Entry 被 WeakHashMap 引用，最终导致 Key 无法回收。
     *    key->weakrefenerce 不被回收
     *    key value ->entry
     *    entry -> weakhashmap
     */
    public static void wrong(){
        String name="mercy";
        WeakHashMap<User,UserProfile> caches=new WeakHashMap<>();
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(()->{
            log.info("cache size:{}, ",caches.size());
        },1, 1,TimeUnit.SECONDS);

        LongStream.rangeClosed(1,200_0000)
                .forEach(i->{
                    User user = new User(name + i);
                    UserProfile profile = new UserProfile(user, "location" + i);
                    caches.put(user,profile);
                });
    }

    public static void right(){
        String name="mercy";
        WeakHashMap<User,UserProfile> caches=new WeakHashMap<>();
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(()->{
            log.info("cache size:{}, ",caches.size());
        },1, 1,TimeUnit.SECONDS);

        LongStream.rangeClosed(1,200_0000)
                .forEach(i->{
                    User user = new User(name + i);
                    //取消key value之间相互引用
                    UserProfile profile = new UserProfile(new User(name + i), "location" + i);
                    caches.put(user,profile);
                });
    }


    public static void right2(){
        String name="mercy";
        WeakHashMap<User, WeakReference<UserProfile>> caches=new WeakHashMap<>();
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(()->{
            log.info("cache size:{}, ",caches.size());
        },1, 1,TimeUnit.SECONDS);

        LongStream.rangeClosed(1,200_0000)
                .forEach(i->{
                    User user = new User(name + i);
                    UserProfile profile = new UserProfile(user, "location" + i);
                    caches.put(user,new WeakReference<>(profile));
                });
    }


    public static void right3(){
        String name="mercy";

        ConcurrentReferenceHashMap<User, UserProfile> caches = new ConcurrentReferenceHashMap<>();

        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(()->{
            log.info("cache size:{}, ",caches.size());
        },1, 1,TimeUnit.SECONDS);

        LongStream.rangeClosed(1,200_0000)
                .forEach(i->{
                    User user = new User(name + i);
                    UserProfile profile = new UserProfile(user, "location" + i);
                    caches.put(user,profile);
                });
    }


    public static void main(String[] args) throws InterruptedException {

     //   wrong();


        //right();
        //更换容器
        right2();

        //切换spring ConcurrentReferenceHashMap
       // right3();

        Thread.sleep(Long.MAX_VALUE);
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static
    class User {
        private String name;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UserProfile {
        private User user;
        private String location;
    }
}