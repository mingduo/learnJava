package common.buessiness.problems.lock.lockgranularity;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@RestController
@RequestMapping("lockgranularity")
@Slf4j
public class LockGranularityController {


    private List<Integer> data = new ArrayList<>();


    //不涉及共享资源的慢方法
    @SneakyThrows
    private void slow() {
        Thread.sleep(10);
    }

    //错误的加锁方法
    @GetMapping("wrong")
    public int wrong() {
        data.clear();
        long begin = System.currentTimeMillis();

        IntStream.rangeClosed(1, 1000)
                .parallel().forEach(t -> {
            //加锁粒度太粗了
            synchronized (data) {
                slow();
                data.add(t);
            }
        });

        log.info("took:{}", System.currentTimeMillis() - begin);
        return data.size();
    }


    @GetMapping("right")
    public int right() {
        data.clear();
        long begin = System.currentTimeMillis();

        IntStream.rangeClosed(1, 1000)
                .parallel().forEach(t -> {
            //只对List加锁
            slow();
            synchronized (data) {

                data.add(t);
            }
        });

        log.info("took:{}", System.currentTimeMillis() - begin);
        return data.size();
    }
}
