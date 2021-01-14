package common.buessiness.problems.java8;

import common.buessiness.problems.java8.collector.MostPopularCollector;
import common.buessiness.problems.java8.collector.MostPopularCollector2;
import common.buessiness.problems.utils.PrintlnUtils;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.atomic.LongAdder;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class StreamDetailTest {


    private static Random random = new Random();
    private List<Order> orders;

    @Before
    public void data() {
        orders = Order.getData();

        orders.forEach(System.out::println);
        System.out.println("==========================================");
    }


    @Test
    public void filter() {
        System.out.println("//最近半年的金额大于40的订单");
        orders.stream().filter(t -> t.getTotalPrice() > 40)
                .filter(t -> t.getPlacedAt().isAfter(LocalDateTime.now().minusMonths(6)))
                .forEach(System.out::println);

    }

    @Test
    public void map() {
        //计算所有订单商品数量
        //通过两次遍历实现
        LongAdder longAdder = new LongAdder();
        orders.stream().forEach(t -> {
            t.getOrderItemList().stream().forEach(i -> longAdder.add(i.getProductQuantity()));
        });

        //使用两次mapToLong+sum方法实现
        Assert.assertThat(longAdder.longValue(), CoreMatchers.is(orders.stream()
                .mapToLong(t -> t.getOrderItemList()
                        .stream().mapToLong(OrderItem::getProductQuantity).sum()).sum()));

        //把IntStream通过转换Stream<Project>
        System.out.println(IntStream.rangeClosed(1, 10)
                .mapToObj(i -> new Product((long) i, "product" + i, i * 100.0))
                .collect(toList()));
    }

    @Test
    public void sorted() {
        System.out.println("//大于50的订单,按照订单价格倒序前5");
        orders.stream()
                .filter(t -> t.getTotalPrice() > 50)
                .sorted(Comparator.comparingDouble(Order::getTotalPrice).reversed())
                .limit(5)
                .forEach(System.out::println);
    }

    //直接展开订单商品进行价格统计
    @Test
    public void flatMap() {
        //不依赖订单上的总价格字段
        System.out.println(orders.stream().mapToDouble(Order::getTotalPrice).sum());
        //如果不依赖订单上的总价格,可以直接展开订单商品进行价格统计

        System.out.println(orders.stream()
                .flatMap(t -> t.getOrderItemList().stream())
                .mapToDouble(it -> it.getProductQuantity() * it.getProductPrice()).sum());

        //另一种方式flatMap+mapToDouble=flatMapToDouble

        System.out.println(orders.stream()
                .flatMapToDouble(t -> t.getOrderItemList()
                        .stream()
                        .mapToDouble(it -> it.getProductQuantity() * it.getProductPrice()))
                .sum());

    }

    @Test
    public void maxMin() {
        orders.stream().max(Comparator.comparingDouble(Order::getTotalPrice)).ifPresent(System.out::println);
        orders.stream().min(Comparator.comparingDouble(Order::getTotalPrice)).ifPresent(System.out::println);

    }

    @Test
    public void reduce() {
        System.out.println("//统计花钱最多的人");
        System.out.println(orders.stream()
                .collect(Collectors.groupingBy(Order::getCustomerName, Collectors.summingDouble(Order::getTotalPrice)))
                .entrySet().stream().reduce(BinaryOperator.maxBy(Map.Entry.comparingByValue()))
                .map(Map.Entry::getKey).orElse("N/A"));

    }

    @Test
    public void distinct() {
        System.out.println("//不去重的下单用户");
        System.out.println(orders.stream().map(Order::getCustomerName).collect(Collectors.joining(",")));


        System.out.println("//去重的下单用户");
        System.out.println(orders.stream().map(Order::getCustomerName).distinct().collect(Collectors.joining(",")));

        System.out.println("//所有购买过的商品");
        System.out.println(orders.stream().flatMap(t -> t.getOrderItemList().stream())
                .map(OrderItem::getProductName).distinct()
                .collect(Collectors.joining(",")));

    }

    @Test
    public void skipLimit() {
        System.out.println("按照下单时间排序，查询前 2 个订单的顾客姓名和下单时间");

        orders.stream().sorted(Comparator.comparing(Order::getPlacedAt))
                .map(t -> t.getCustomerName() + "@" + t.getPlacedAt())
                .limit(2)
                .forEach(System.out::println);

        System.out.println("按照下单时间排序，查询第 3 和第 4 个订单的顾客姓名和下单时间。");

        orders.stream().sorted(Comparator.comparing(Order::getPlacedAt))
                .map(t -> t.getCustomerName() + "@" + t.getPlacedAt())
                .skip(2)
                .limit(2)
                .forEach(System.out::println);
    }


    @Test
    public void collect() {
        System.out.println("//生成一定位数的随机字符串");
        System.out.println(random.ints(48, 122)
                .filter(i -> (i < 57 || i > 65) && (i < 90 || i > 97))
                .mapToObj(i -> (char) i)
                .limit(20)
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString());

        System.out.println("//所有下单的用户,使用toSet去重了");
        System.out.println(orders.stream().map(Order::getCustomerName)
                .collect(Collectors.toSet())
                .stream()
                .collect(Collectors.joining(",", "[", "]")));

        System.out.println("//用toCollection收集器指定集合类型");
        System.out.println(orders.stream()
                .limit(2)
                .collect(Collectors.toCollection(LinkedList::new))
                .getClass());

        System.out.println("//使用toMap获取订单ID+下单用户名的Map");
        System.out.println(orders.stream()
                .collect(Collectors.toMap(Order::getId, Order::getCustomerName)));

        System.out.println("//使用toMap获取下单用户名+最近一次下单时间的Map");
        System.out.println(orders.stream()
                .collect(Collectors.toMap(Order::getCustomerName, Order::getPlacedAt, (x, y) -> x.isAfter(y) ? x : y)));


        System.out.println("//订单平均购买的商品数量");

        //getProductQuantity / order size
        System.out.println(orders.stream()
                .collect(Collectors.averagingInt(t -> t.getOrderItemList().stream()
                        .collect(Collectors.summingInt(OrderItem::getProductQuantity)))));


    }

    @Test
    public void groupBy() {
        System.out.println("//按照用户名分组，统计下单数量");

        System.out.println(orders.stream().collect(Collectors
                .groupingBy(Order::getCustomerName, Collectors.counting()))
                .entrySet().stream().sorted(Map.Entry.<String, Long>comparingByValue().reversed()).collect(toList()));


        System.out.println("//按照用户名分组,统计订单总金额");
        System.out.println(orders.stream().collect(Collectors
                .groupingBy(Order::getCustomerName,
                        Collectors.summingDouble(Order::getTotalPrice)))
                .entrySet()
                .stream()
                .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
                .collect(Collectors.toList()));

        System.out.println("//按照用户名分组,统计商品采购数量");
        System.out.println(orders.stream().collect(Collectors.groupingBy(Order::getCustomerName,
                Collectors.summingInt(t -> t.getOrderItemList()
                        .stream().mapToInt(OrderItem::getProductQuantity).sum())))
                .entrySet()
                .stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .collect(Collectors.toList()));

        System.out.println("//统计最受欢迎的商品，倒序后取第一个");

        System.out.println(orders.stream()
                .flatMap(t -> t.getOrderItemList().stream())
                .collect(Collectors.groupingBy(OrderItem::getProductName,
                        Collectors.summingInt(OrderItem::getProductQuantity))
                ).entrySet().stream()
                //min(Map.Entry.comparingByValue())
                // replace
                // .sorted(Map.Entry.comparingByValue()).findFirst()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .findFirst()
                .orElse(null));


        System.out.println("//统计最受欢迎的商品的另一种方式,直接利用maxBy");

        System.out.println(orders.stream().flatMap(t -> t.getOrderItemList()
                .stream())
                .collect(Collectors.groupingBy(OrderItem::getProductName, Collectors.summingInt(OrderItem::getProductQuantity)))
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue()).orElse(null));

        System.out.println("//按照用户名分组，选用户下的总金额最大的订单");

        orders.stream().collect(Collectors.groupingBy(Order::getCustomerName,
                Collectors.maxBy(Comparator.comparingDouble(Order::getTotalPrice))))
                .forEach((k, v) -> System.out.println(k + "#" + v.get().getTotalPrice() + "@" + v.get().getPlacedAt()));

        PrintlnUtils.println();

        orders.stream().collect(Collectors.groupingBy(Order::getCustomerName,
                Collectors.collectingAndThen(Collectors.maxBy(Comparator.comparingDouble(Order::getTotalPrice)), Optional::get)))
                .forEach((k, v) -> System.out.println(k + "#" + v.getTotalPrice() + "@" + v.getPlacedAt()));

        System.out.println("//根据下单年月分组统计订单ID列表");

        System.out.println(orders.stream()
                .collect(Collectors.groupingBy(t -> t.getPlacedAt().format(DateTimeFormatter.ofPattern("yyyy-MM"))
                        , Collectors.mapping(Order::getId, Collectors.toList()))));


        System.out.println("//根据下单年月+用户名两次分组，统计订单ID列表");

        System.out.println(orders.stream()
                .collect(Collectors.groupingBy(t -> t.getPlacedAt().format(DateTimeFormatter.ofPattern("yyyy-MM")),
                        Collectors.groupingBy(Order::getCustomerName,
                                Collectors.mapping(Order::getId, Collectors.toList())))));


    }

    @Test
    public void partitioningBy() {
        //先来看一下所有下单的用户
        orders.stream().map(Order::getCustomerName).forEach(System.out::println);
        //根据是否有下单记录进行分区
        System.out.println(orders.stream()
                .collect(Collectors.partitioningBy(customer -> orders.stream()
                        .mapToLong(Order::getCustomerId)
                        .anyMatch(id -> customer.getId() == id)
                )));
    }

    @Test
    public void peek() {
        IntStream.rangeClosed(1, 10)
                .peek(i -> {
                    System.out.println("第一次peek");
                    System.out.println(i);
                })
                .filter(i -> i > 5)
                .peek(i -> {
                    System.out.println("第二次peek");
                    System.out.println(i);
                })
                .filter(i -> i % 2 == 0)
                .forEach(i -> {
                    System.out.println("最终结果");
                    System.out.println(i);
                });
        //        orders.stream()
//                .filter(order -> order.getTotalPrice() > 40)
//                .peek(order -> System.out.println(order.toString()))
//                .map(Order::getCustomerName)
//                .collect(toList());
    }


    @Test
    public void customCollector() { //自定义收集器
        Integer[] ints=new Integer[]{1, 1, 2, 2, 2, 3, 4, 5, 5};
        System.out.println(Stream.of(ints).collect(Collectors.groupingBy(Function.identity(),
                Collectors.counting())).entrySet()
                .stream()
                .sorted(Map.Entry.<Integer, Long>comparingByValue().reversed())
                .skip(1)
                .map(Map.Entry::getValue)
                .findFirst().get());

        //最受欢迎收集器
        Assert.assertThat(Stream.of(ints).collect(new MostPopularCollector<>()).get(),
                CoreMatchers.is(2));

        Assert.assertThat(Stream.of('a', 'b', 'c', 'c', 'c', 'd')
                .collect(new MostPopularCollector<>()).get(),
                CoreMatchers.is('c'));

        Assert.assertThat(Stream.concat(Stream.concat(IntStream.rangeClosed(1, 1000).boxed(), IntStream.rangeClosed(1, 1000).boxed()), Stream.of(2))
                .parallel().collect(new MostPopularCollector<>()).get(),
                CoreMatchers.is(2));


        Assert.assertThat(Stream.of(ints).collect(new MostPopularCollector2<>()).get(),
                CoreMatchers.is(2));

        Assert.assertThat(Stream.of('a', 'b', 'c', 'c', 'c', 'd')
                .collect(new MostPopularCollector2<>()).get(),
                CoreMatchers.is('c'));

        Assert.assertThat(Stream.concat(Stream.concat(IntStream.rangeClosed(1, 1000).boxed(), IntStream.rangeClosed(1, 1000).boxed()), Stream.of(2))
                .parallel().collect(new MostPopularCollector2<>()).get(),
                CoreMatchers.is(2));


    }
}