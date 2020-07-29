package common.buessiness.problems.collection.listremove;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author : weizc
 * @since 2020/7/28
 */
@Slf4j
public class ListremoveDemo {

    public static void main(String[] args) {


        foreachRemoveWrong(new ArrayList<>(Arrays.asList("1", "2", "3")));

        foreachRemoveRight(new ArrayList<>(Arrays.asList("1", "2", "3")));

        foreachRemoveRight2(new ArrayList<>(Arrays.asList("1", "2", "3")));

    }

    private static void removeByIndex(int index) {
        List<Integer> list =
                IntStream.rangeClosed(1, 10).boxed().collect(Collectors.toCollection(ArrayList::new));
        System.out.println(list.remove(index));
        System.out.println(list);
    }

    private static void removeByValue(Integer index) {
        List<Integer> list =
                IntStream.rangeClosed(1, 10).boxed().collect(Collectors.toCollection(ArrayList::new));
        System.out.println(list.remove(index));
        System.out.println(list);
    }

    private static void foreachRemoveRight2(List<String> list) {
        list.removeIf(t -> "2".equals(t));

        log.info("list:{}", list);


    }

    private static void foreachRemoveRight(List<String> list) {
        Iterator<String> iterator = list.iterator();
        for (; iterator.hasNext(); ) {
            String i = iterator.next();
            if ("2".equals(i)) {
                iterator.remove();
            }
        }
        log.info("list:{}", list);

    }

    private static void foreachRemoveWrong(List<String> list) {

        for (String i : list) {
            try {
                if ("2".equals(i)) {
                    list.remove(i);
                }
            } catch (Exception e) {
                log.error("", e);
            }

        }
        log.info("list:{}", list);
    }


}
