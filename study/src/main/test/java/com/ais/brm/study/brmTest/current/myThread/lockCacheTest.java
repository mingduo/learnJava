package com.ais.brm.study.brmTest.current.myThread;


import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.LongAdder;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * <table border="1">
 * <tr><th>@Description: 缓存🔒</th></tr>
 * <tr><td>@Date:Created in 2018-9-17</td>
 * </tr>
 * </table>
 * 两个线程通信
 *
 * @author :    weizc
 */
public class lockCacheTest {


    //非公平锁
    public static void main(String[] args) throws InterruptedException {
        Cache cache = new Cache();

        ThreadLocalRandom r = ThreadLocalRandom.current();

        for (int i = 0; i < 50; i++) {
            new Thread(() -> cache.read("a"), "read" + i++).start();

        }
        for (int i = 0; i < 50; i++) {
            new Thread(() -> cache.write("a", "b" + r.nextInt(20)), "write" + i++).start();
        }
        for (int i = 0; i < 50; i++) {
            new Thread(() -> cache.read("a"), "read" + i++).start();

        }
        for (int i = 0; i < 50; i++) {
            new Thread(() -> cache.getData("a"), "getData" + i++).start();
        }
    }

}

class Cache {
    //访问成员变量需要保证原子性
    private LongAdder capacity = new LongAdder();


    ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
    private Map<String, String> cache = new HashMap<>();

    ReentrantLock lock = new ReentrantLock();

    private final static int FULL_CAPCITY = 10;
    private final static int EMPTY_CAPCITY = 0;

    Condition readCondtion = lock.newCondition();
    Condition writeCondtion = lock.newCondition();

    public void read(String key) {
        lock.lock();
        try {
            while (capacity.intValue() == EMPTY_CAPCITY) {
                readCondtion.await();
            }

            String value = cache.get(key);
            // --
            capacity.decrement();
            System.out.println(Thread.currentThread() +
                    "--read value =>" + value +
                    "capacity=>" + capacity.intValue());

            if (capacity.intValue() < FULL_CAPCITY) {
                writeCondtion.signal();
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();

        }
    }

    public void write(String key, String value) {
        try {
            lock.lock();
            while (capacity.intValue() == FULL_CAPCITY) {
                writeCondtion.await();
            }
            cache.put(key, value);
            // ++
            capacity.increment();

            System.out.println(Thread.currentThread() + "--write ok，" +
                    "capacity=>" + capacity.intValue());
            if (capacity.intValue() > EMPTY_CAPCITY) {
                readCondtion.signal();
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();

        }
    }

    //缓存没有 去查数据库
    public void getData(String key) {
        rwl.readLock().lock();
        String val = null;

        val = cache.get(key);
        if (val == null) {
            rwl.readLock().unlock();

            rwl.writeLock().lock();
            try {
                //query db
                val = "abc";
                cache.put(key, val);
            } finally {
                rwl.writeLock().unlock();
            }
        }


        System.out.println(Thread.currentThread() + "--getData ok，" +
                val);
    }
}


