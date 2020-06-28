package common.collections;

import org.apache.commons.collections4.ListUtils;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author : weizc
 * @apiNode:
 * @since 2020/6/24
 */
public class ListUtilsDemo {

    public static void main(String[] args) {

        List<Integer> values = Stream.of(1, 2, 3, 4, 5, 6, 7, 8).collect(Collectors.toList());
        List<Integer> values2 = Stream.of(1, 2, 3, 4, 8, 11).collect(Collectors.toList());

        partition(values);

        intersection(values, values2);

        union(values, values2);
    }

    private static void union(List<Integer> values, List<Integer> values2) {
        System.out.println("----------------");

        List<Integer> union = ListUtils.union(values, values2);
        System.out.println(union);

    }

    private static void intersection(List<Integer> values, List<Integer> values2) {
        System.out.println("----------------");
        List<Integer> intersection = ListUtils.intersection(values, values2);
        System.out.println(intersection);

    }

    private static void partition(List<Integer> values) {
        System.out.println("----------------");
        List<List<Integer>> partitionList = ListUtils.partition(values, 3);
        System.out.println(partitionList);

    }

}
