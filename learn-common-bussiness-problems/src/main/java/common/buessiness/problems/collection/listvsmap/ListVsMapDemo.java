package common.buessiness.problems.collection.listvsmap;

import jdk.nashorn.internal.ir.debug.ObjectSizeCalculator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;
import org.springframework.util.StopWatch;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author : weizc
 * @since 2020/7/28
 */
@Slf4j
public class ListVsMapDemo {


    public static void main(String[] args) {

        int elementCount = 1000000;
        int loopCount = 1000;
        // list  map 查找给定元素
        StopWatch stopWatch = new StopWatch();
        stopWatch.start("listsearch");
        List list = listsearch(elementCount, loopCount);

        stopWatch.stop();

        log.info("list object size:{}", ObjectSizeCalculator.getObjectSize(list));
        stopWatch.start("mapSearch");

        Map map = mapSearch(elementCount, loopCount);
        stopWatch.stop();
        //内存占用大 性能快
        log.info("map object size:{}", ObjectSizeCalculator.getObjectSize(map));

        System.out.println(stopWatch.prettyPrint());
    }

    private static List listsearch(int elementCount, int loopCount) {
        List<Order> list = IntStream.rangeClosed(1, elementCount).mapToObj(Order::new).collect(Collectors.toList());
        IntStream.rangeClosed(1, loopCount)
                .forEach(i -> {
                    int search = ThreadLocalRandom.current().nextInt(elementCount);
                    Order result = list.stream().filter(t -> t.getOrderId() == search)
                            .findFirst().orElse(null);
                    Assert.isTrue(result != null && result.getOrderId() == search,"order not found");
                });

        return list;
    }


    private static Map mapSearch(int elementCount, int loopCount){
        Map<Integer, Order> map = IntStream.rangeClosed(1, elementCount).mapToObj(Order::new).collect(Collectors.toMap(Order::getOrderId, Function.identity()));
       IntStream.rangeClosed(1,loopCount)
       .forEach(i->{
           int search = ThreadLocalRandom.current().nextInt(elementCount);
           Order result = map.get(search);
           Assert.isTrue(result!=null && result.getOrderId()==search,"order not found");
       });
        return map;
    }


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static class Order {
        private int orderId;
    }
}
