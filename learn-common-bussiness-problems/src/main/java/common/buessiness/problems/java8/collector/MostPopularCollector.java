package common.buessiness.problems.java8.collector;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author : weizc
 * @since 2020/9/14
 */
public class MostPopularCollector<T> implements Collector<T, Map<T, Integer>, Optional<T>> {


    @Override
    public Supplier<Map<T, Integer>> supplier() {
        return HashMap::new;
    }

    @Override
    public BiConsumer<Map<T, Integer>, T> accumulator() {
        return (a, b) -> {
            Integer value = Optional.ofNullable(a.get(b)).map(v -> v + 1).orElse(1);
            a.put(b, value);
        };
    }

    @Override
    public BinaryOperator<Map<T, Integer>> combiner() {
        return (a, b) -> Stream.concat(a.entrySet().stream(),b.entrySet().stream())
                .collect(Collectors.toMap(Map.Entry::getKey,
                        Map.Entry::getValue,Integer::sum));
    }

    @Override
    public Function<Map<T, Integer>, Optional<T>> finisher() {
        return a -> a.entrySet().stream()
                .sorted(Map.Entry.<T, Integer>comparingByValue().reversed())
                .map(Map.Entry::getKey).findFirst();
    }

    @Override
    public Set<Characteristics> characteristics() {
        return Collections.emptySet();
    }
}
