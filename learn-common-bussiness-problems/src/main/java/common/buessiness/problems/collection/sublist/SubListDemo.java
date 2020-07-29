package common.buessiness.problems.collection.sublist;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author : weizc
 * @since 2020/7/28
 */
@Slf4j
public class SubListDemo {
    public static void main(String[] args) {
        oom();

        wrong();

        right();

        right2();
    }

    private static void oom() {
        List<List<Integer>> data = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            List<Integer> rawList = IntStream.rangeClosed(1, 10000)
                    .boxed().collect(Collectors.toList());
            //强引用 sublist
            data.add(rawList.subList(0, 1));
        }

        //log.info("data:{}",data);
    }

    private static void oomfix() {
        List<List<Integer>> data = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            List<Integer> rawList = IntStream.rangeClosed(1, 10000)
                    .boxed().collect(Collectors.toList());
            //强引用 sublist
            data.add(new ArrayList<>(rawList.subList(0, 1)));
        }

        //log.info("data:{}",data);
    }


    private static void wrong() {
        log.info("wrong");
        subListDemo(list-> list.subList(1, 4));

    }

    private static void right(){
        log.info("right");

        subListDemo(list->new ArrayList<>( list.subList(1, 4)));
    }

    private static void right2(){
        log.info("right2");


        subListDemo(list->list.stream().skip(1).limit(3).collect(Collectors.toList()));
    }

    private static void subListDemo(Function<List<Integer>, List<Integer>> convertSubList) {
        List<Integer> list = IntStream.rangeClosed(1, 10)
                .boxed().collect(Collectors.toList());

        list.add(0);

        List<Integer> subList =convertSubList.apply(list);

        log.info("sublist:{}", subList);

        subList.remove(1);
        //3 没了
        log.info("list:{}", list);


        list.add(0);

        //修改原list 会影响 sublist modcount
        try {
            subList.forEach(System.out::println);


        } catch (Exception e) {
            log.error("", e);
        }
    }

}
