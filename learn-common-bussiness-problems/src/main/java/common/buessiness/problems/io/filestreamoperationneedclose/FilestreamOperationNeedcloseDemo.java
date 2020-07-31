package common.buessiness.problems.io.filestreamoperationneedclose;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.LongAdder;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static common.buessiness.problems.utils.PrintlnUtils.println;

/**
 * @author : weizc
 * @since 2020/7/31
 */
@Slf4j
public class FilestreamOperationNeedcloseDemo {

    private final static String DEMO_PATH = "learn-common-bussiness-problems/src/main/java/common/buessiness/problems/io/filestreamoperationneedclose/demo.txt";
    private final static String LARGE_PATH = "learn-common-bussiness-problems/src/main/java/common/buessiness/problems/io/filestreamoperationneedclose/large.txt";


    public static void main(String[] args) throws IOException {
        init();

        println();

        wrong();

        println();

        right();

        println();

        readLargeFileWrong();

        println();

        readLargeFileRight();
    }


    private static void readLargeFileWrong() throws IOException {
        log.info("lines:{}", Files.readAllLines(Paths.get(LARGE_PATH)).size());
    }

    private static void readLargeFileRight() throws IOException {
        AtomicInteger integer = new AtomicInteger();
        // Files.lines(Paths.get(LARGE_PATH)).count()
        Files.lines(Paths.get(LARGE_PATH)).forEach(__->integer.incrementAndGet());
        log.info("lines:{}", integer.get());
    }


    private static void right() {
        //https://docs.oracle.com/javase/8/docs/api/java/nio/file/Files.html
        LongAdder longAdder = new LongAdder();

        try (Stream<String> linestream = Files.lines(Paths.get(DEMO_PATH))) {
            linestream.forEach(__ -> longAdder.increment());
        } catch (IOException e) {
            e.printStackTrace();
        }

        log.info("total:{}", longAdder.longValue());
    }

    private static void wrong() throws IOException {
        //ps aux | grep CommonMistakesApplication
        //lsof -p 63937
        LongAdder longAdder = new LongAdder();

        Files.lines(Paths.get(DEMO_PATH)).forEach(__ -> longAdder.increment());

        log.info("total:{}", longAdder.longValue());

    }

    private static void init() throws IOException {

        String payload = IntStream.rangeClosed(1, 1000)
                .mapToObj(__ -> "a")
                .collect(Collectors.joining()) + UUID.randomUUID().toString();

        IntStream.rangeClosed(1, 10)
                .forEach(__ -> {

                    try {
                        Files.write(Paths.get(LARGE_PATH), IntStream.rangeClosed(1, 50000).mapToObj(i -> payload).collect(Collectors.toList()), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
                    } catch (IOException e) {
                        log.error("Io异常", e);
                    }
                });

        Files.write(Paths.get(DEMO_PATH), IntStream.rangeClosed(1, 10).mapToObj(i -> payload).collect(Collectors.toList()), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);

    }
}
