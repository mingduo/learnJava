package common.buessiness.problems.springpart1.beansingletonandorder;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author : weizc
 * @since 2020/9/9
 */
@Slf4j
public abstract class SayService {

    List<String> data = new ArrayList<>();

    public void say() {
        data.add(IntStream.range(1, 1000_000)
                .mapToObj(__ -> "a")
                .collect(Collectors.joining()) + UUID.randomUUID().toString());
        log.info("i am :{} size:{}", this, data.size());

    }
}
