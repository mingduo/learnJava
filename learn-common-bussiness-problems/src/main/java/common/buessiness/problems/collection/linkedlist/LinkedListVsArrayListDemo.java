package common.buessiness.problems.collection.linkedlist;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StopWatch;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * linked list vs arr
 * @author : weizc
 * @since 2020/7/28
 */
@Slf4j
public class LinkedListVsArrayListDemo {

    public static void main(String[] args) {
        int elementCount = 10000;
        int loopCount = 10000;

        StopWatch stopWatch = new StopWatch();
        stopWatch.start("linkedListGet");

        linkedListGet(elementCount,loopCount);
        stopWatch.stop();
        stopWatch.start("arrayListGet");
        arrayListGet(elementCount,loopCount);
        stopWatch.stop();

        log.info(stopWatch.prettyPrint());

        StopWatch stopWatch2 = new StopWatch();
        stopWatch2.start("linkedListAdd");

        linkedListAdd(elementCount,loopCount);
        stopWatch2.stop();
        stopWatch2.start("arrayListAdd");
        arrayListAdd(elementCount,loopCount);
        stopWatch2.stop();

        log.info(stopWatch2.prettyPrint());
    }


    private static void linkedListGet(int elementCount, int loopCount) {
        LinkedList<Integer> linkedList = IntStream.rangeClosed(1, elementCount)
                .boxed().collect(Collectors.toCollection(LinkedList::new));
        IntStream.rangeClosed(1, loopCount)
                .forEach(i -> {
                    int search = ThreadLocalRandom.current().nextInt(loopCount);
                    linkedList.get(search);
                });

    }

    private static void arrayListGet(int elementCount, int loopCount) {
        List<Integer> list = IntStream.rangeClosed(1, elementCount)
                .boxed().collect(Collectors.toList());
        IntStream.rangeClosed(1, loopCount)
                .forEach(i -> {
                    int search = ThreadLocalRandom.current().nextInt(loopCount);
                    list.get(search);
                });

    }

    private static void linkedListAdd(int elementCount, int loopCount) {
        List<Integer> list = IntStream.rangeClosed(1, elementCount)
                .boxed().collect(Collectors.toCollection(LinkedList::new));

        IntStream.rangeClosed(1, loopCount)
                .forEach(i -> {
                    int search = ThreadLocalRandom.current().nextInt(loopCount);
                    list.add(search,1);
                });

    }

    private static void arrayListAdd(int elementCount, int loopCount) {
        List<Integer> list = IntStream.rangeClosed(1, elementCount)
                .boxed().collect(Collectors.toList());
        IntStream.rangeClosed(1, loopCount)
                .forEach(i -> {
                    int search = ThreadLocalRandom.current().nextInt(loopCount);
                    list.add(search,1);
                });

    }

}
