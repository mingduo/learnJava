package common.buessiness.problems.lock.lockscope;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.IntStream;

/**
 * @author : weizc
 * @since 2020/7/17
 */
@RestController
public class LockScopeController {

    @GetMapping("data/wrong")
    public int wrong(@RequestParam(value = "count", defaultValue = "10000") int count) {
        Data.reset();
        IntStream.rangeClosed(1, count)
                .parallel().forEach(__ -> new Data().wrong());
        return Data.getCounter();
    }

    @GetMapping("data/right")
    public int right(@RequestParam(value = "count", defaultValue = "10000") int count) {
        Data.reset();
        IntStream.rangeClosed(1, count)
                .parallel().forEach(__ -> new Data().right());
        return Data.getCounter();
    }

    @GetMapping("interest/wrong")
    public String wrong2() {
        Interesting interesting = new Interesting();
        new Thread(() -> interesting.add()).start();
        new Thread(() -> interesting.compare()).start();
        return "ok";

    }

    @GetMapping("interest/right")
    public String right2() {
        Interesting interesting = new Interesting();
        new Thread(() -> interesting.add()).start();
        new Thread(() -> interesting.compareRight()).start();
        return "ok";

    }
}
