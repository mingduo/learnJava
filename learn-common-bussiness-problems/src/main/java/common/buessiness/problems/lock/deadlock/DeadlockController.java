package common.buessiness.problems.lock.deadlock;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author : weizc
 * @since 2020/7/20
 */
@RequestMapping("deadlock")
@RestController
@Slf4j
public class DeadlockController {

    private static ConcurrentMap<String, Item> items = new ConcurrentHashMap<>();

    static {
        IntStream.range(0, 9)
                .forEach(i -> items.put("item" + i, new Item("item" + i)));
    }


    private List<Item> createChart() {
        return IntStream.rangeClosed(1, 3)
                .mapToObj(t -> "item" + ThreadLocalRandom.current().nextInt(items.size()))
                .map(t -> items.get(t))
                .collect(Collectors.toList());
    }


    /**
     * jconsole
     * jvisvm
     * @return
     */
    @RequestMapping("/wrong")
    public long wrong() {
        long begin = System.currentTimeMillis();

        //并发进行100次下单操作，统计成功次数
        long success = IntStream.rangeClosed(1, 100)
                .parallel()
                .mapToObj(t -> {
                    //创建库存
                    List<Item> items = createChart();
                    //扣减订单
                    return createOrder(items);
                }).filter(r -> r)
                .count();


        log.info("success:{},totalRemaining:{},took:{}ms,items:{}", success,
                items.values().stream().map(Item::getRemaining).reduce(0, Integer::sum),
                System.currentTimeMillis() - begin, items);

        return success;
    }

    @RequestMapping("/right")
    public long right() {
        long begin = System.currentTimeMillis();


        long success = IntStream.rangeClosed(1, 100)
                .parallel()
                .mapToObj(t -> {
                    //创建库存
                    List<Item> items = createChart();
                  /*  为购物车中的商品排一下序，让所有的线程一定是先获取
                    item1 的锁然后获取 item2 的锁，就不会有问题了
                    */
                    items.sort(Comparator.comparing(Item::getName));
                    return createOrder(items);
                }).filter(r -> r)
                .count();


        log.info("success:{},totalRemaining:{},took:{}ms,items:{}", success,
                items.values().stream().map(Item::getRemaining).reduce(0, Integer::sum),
                System.currentTimeMillis() - begin, items);

        return success;
    }



    private boolean createOrder(List<Item> items) {
        //全部加锁库存成功才开始扣减
        //存放所有获得的锁
        List<ReentrantLock> locks = new ArrayList<>();

        for (Item item : items) {
            try {
                if (item.lock.tryLock(10, TimeUnit.SECONDS)) {
                    locks.add(item.lock);
                } else {
                    locks.forEach(ReentrantLock::unlock);
                    return false;
                }
            } catch (InterruptedException e) {

            }
        }


        try {
            items.forEach(t -> t.remaining--);

        }finally {
          locks.forEach(ReentrantLock::unlock);
        }
        return true;


    }


    @Data
    @RequiredArgsConstructor
    static class Item {
        final String name; //商品名
        int remaining = 1000; //库存剩余
        @ToString.Exclude //ToString不包含这个字段
                ReentrantLock lock = new ReentrantLock();
    }
}
