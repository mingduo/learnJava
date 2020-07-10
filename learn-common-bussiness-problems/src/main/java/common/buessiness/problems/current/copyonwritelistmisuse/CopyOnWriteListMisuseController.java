package common.buessiness.problems.current.copyonwritelistmisuse;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 01 -CopyOnWriteList 读写性能分析
 *
 * @author : weizc
 * @since 2020/7/10
 */
@RestController
@RequestMapping("copyonwritelistmisuse")
@Slf4j
public class CopyOnWriteListMisuseController {

    int loopCount = 100000;

    /**
     * 写10W条
     */
    @GetMapping("write")
    public Map<String, Integer> write() {
        List<Integer> copyOnWriteArrayList = new CopyOnWriteArrayList<>();
        List<Integer> synchronizedList = Collections.synchronizedList(new ArrayList<>());

        StopWatch stopWatch = new StopWatch();
        stopWatch.start("write:copyOnWriteArrayList");
        IntStream.rangeClosed(1, loopCount).parallel().forEach(__ -> copyOnWriteArrayList.add(ThreadLocalRandom.current().nextInt(loopCount)));
        stopWatch.stop();
        stopWatch.start("write:synchronizedList");
        IntStream.rangeClosed(1, loopCount).parallel().forEach(__ -> synchronizedList.add(ThreadLocalRandom.current().nextInt(loopCount)));
        stopWatch.stop();
        log.info(stopWatch.prettyPrint());

        Map<String, Integer> map = new HashMap<>();
        map.put("copyOnWriteArrayList", copyOnWriteArrayList.size());
        map.put("synchronizedList", synchronizedList.size());

        return map;
    }

    private void addAll(List<Integer> list) {
        list.addAll(IntStream.rangeClosed(1, loopCount).boxed().collect(Collectors.toList()));
    }

    @GetMapping("read")
    public Map<String, Integer> read() {
        List<Integer> copyOnWriteArrayList = new CopyOnWriteArrayList<>();
        List<Integer> synchronizedList = Collections.synchronizedList(new ArrayList<>());
        addAll(copyOnWriteArrayList);
        addAll(synchronizedList);

        StopWatch stopWatch = new StopWatch();
        stopWatch.start("read:copyOnWriteArrayList");
        IntStream.rangeClosed(1, loopCount).parallel().forEach(__ -> copyOnWriteArrayList.get(ThreadLocalRandom.current().nextInt(loopCount)));
        stopWatch.stop();
        stopWatch.start("read:synchronizedList");
        IntStream.rangeClosed(1, loopCount).parallel().forEach(__ -> synchronizedList.get(ThreadLocalRandom.current().nextInt(loopCount)));
        stopWatch.stop();

        log.info(stopWatch.prettyPrint());

        Map<String, Integer> map = new HashMap<>();
        map.put("copyOnWriteArrayList", copyOnWriteArrayList.size());
        map.put("synchronizedList", synchronizedList.size());
        return map;
    }
}
