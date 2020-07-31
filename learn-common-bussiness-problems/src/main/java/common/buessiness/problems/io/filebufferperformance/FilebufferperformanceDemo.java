package common.buessiness.problems.io.filebufferperformance;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StopWatch;

import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.nio.file.StandardOpenOption.*;

/**
 * @author weizc
 */
@Slf4j
public class FilebufferperformanceDemo {

    private final static String SRC_PATH = "learn-common-bussiness-problems/src/main/java/common/buessiness/problems/io/filebufferperformance/src.txt";
    private final static String DIST_PATH = "learn-common-bussiness-problems/src/main/java/common/buessiness/problems/io/filebufferperformance/dest.txt";

    public static void main(String[] args) throws IOException {

        StopWatch stopWatch = new StopWatch();
        init();
        stopWatch.start("perByteOperation");
        perByteOperation();
        stopWatch.stop();
        stopWatch.start("bufferOperationWith100Buffer");
        bufferOperationWith100Buffer();
        stopWatch.stop();
        stopWatch.start("bufferedStreamByteOperation");
        bufferedStreamByteOperation();
        stopWatch.stop();
        stopWatch.start("bufferedStreamBufferOperation");
        bufferedStreamBufferOperation();
        stopWatch.stop();
        stopWatch.start("largerBufferOperation");
        largerBufferOperation();
        stopWatch.stop();
        stopWatch.start("fileChannelOperation");
        fileChannelOperation();
        stopWatch.stop();
        log.info(stopWatch.prettyPrint());
    }

    private static void init() throws IOException {

        Files.write(Paths.get(SRC_PATH),
                IntStream.rangeClosed(1, 1000000).mapToObj(i -> UUID.randomUUID().toString()).collect(Collectors.toList())
                , UTF_8, CREATE, TRUNCATE_EXISTING);
    }

    private static void perByteOperation() throws IOException {
        Files.deleteIfExists(Paths.get(DIST_PATH));

        try (FileInputStream fileInputStream = new FileInputStream(SRC_PATH);
             FileOutputStream fileOutputStream = new FileOutputStream(DIST_PATH);
        ) {
            int len;
            while ((len = fileInputStream.read()) != -1) {
                fileOutputStream.write(len);
            }
        }

    }

    private static void bufferOperationWith100Buffer() throws IOException {
        Files.deleteIfExists(Paths.get(DIST_PATH));

        try (FileInputStream fileInputStream = new FileInputStream(SRC_PATH);
             FileOutputStream fileOutputStream = new FileOutputStream(DIST_PATH);
        ) {
            byte[] buffer = new byte[100];
            int len;
            while ((len = fileInputStream.read(buffer)) != -1) {
                fileOutputStream.write(buffer, 0, len);
            }
        }
    }

    private static void largerBufferOperation() throws IOException {
        Files.deleteIfExists(Paths.get(DIST_PATH));

        try (FileInputStream fileInputStream = new FileInputStream(SRC_PATH);
             FileOutputStream fileOutputStream = new FileOutputStream(DIST_PATH)) {
            byte[] buffer = new byte[8192];
            int len = 0;
            while ((len = fileInputStream.read(buffer)) != -1) {
                fileOutputStream.write(buffer, 0, len);
            }
        }
    }

    private static void bufferedStreamBufferOperation() throws IOException {
        Files.deleteIfExists(Paths.get(DIST_PATH));

        try (BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(SRC_PATH));
             BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(DIST_PATH))) {
            byte[] buffer = new byte[8192];
            int len = 0;
            while ((len = bufferedInputStream.read(buffer)) != -1) {
                bufferedOutputStream.write(buffer, 0, len);
            }
        }
    }

    private static void bufferedStreamByteOperation() throws IOException {
        Files.deleteIfExists(Paths.get(DIST_PATH));

        try (BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(SRC_PATH));
             BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(DIST_PATH))) {
            int i;
            while ((i = bufferedInputStream.read()) != -1) {
                bufferedOutputStream.write(i);
            }
        }
    }


    private static void fileChannelOperation() throws IOException {
        Path distPath = Paths.get(DIST_PATH);
        Files.deleteIfExists(distPath);

        try(FileChannel in = FileChannel.open(Paths.get(SRC_PATH), READ);
            FileChannel out = FileChannel.open(distPath,CREATE, WRITE);
        ) {
            in.transferTo(0, Files.size(distPath),out);
        }
    }
}

