package common.buessiness.problems.io.badencodingissue;

import com.google.common.base.Charsets;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Hex;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import static common.buessiness.problems.utils.PrintlnUtils.println;

/**
 * 文件读写需要确保字符编码一致
 *
 * @author : weizc
 * @since 2020/7/31
 */
@Slf4j
public class BadEncodingissueDemo {

    private final static String TXT_PATH = "learn-common-bussiness-problems/src/main/java/common/buessiness/problems/io/badencodingissue/hello.txt";
    private final static String TXT_PATH2 = "learn-common-bussiness-problems/src/main/java/common/buessiness/problems/io/badencodingissue/hello2.txt";


    public static void main(String[] args) throws IOException {
        init();

        println();

        wrong();

        println();

        right();

        println();

        right2();

    }

    private static void right() {

        try (
                FileInputStream ins = new FileInputStream(TXT_PATH);
                InputStreamReader reader = new InputStreamReader(ins, Charset.forName("GBK"));
        ) {
            char[] content = new char[8];
            int len;
            StringBuilder value = new StringBuilder();
            while ((len = reader.read(content)) != -1) {
                value.append(new String(content, 0, len));
            }
            log.info("result:{}",value.toString());

        } catch (Exception e) {
            log.error("读取文件失败", e);
        }
    }

    private static void right2() throws IOException {
        log.info("result:{}", Files.readAllLines(Paths.get(TXT_PATH ), Charset.forName("GBK")));
    }

    private static void wrong() {
        //FileReader 是以当前机器的默认字符集来读取文件的
        try (FileReader fileReader = new FileReader(TXT_PATH)) {

            char[] content = new char[8];
            int len;
            StringBuilder value = new StringBuilder();
            while ((len = fileReader.read(content)) != -1) {
                value.append(new String(content, 0, len));
            }
            log.info("result:{}", value.toString());

            Files.write(Paths.get(TXT_PATH2), "你好2".getBytes(Charsets.UTF_8), StandardOpenOption.CREATE);

        } catch (Exception e) {
            log.error("读取文件失败", e);
        }
    }

    private static void init() throws IOException {
        Files.deleteIfExists(Paths.get(TXT_PATH));
        Files.write(Paths.get(TXT_PATH), "你好,hi2".getBytes(Charset.forName("GBK")));
        log.info("bytes:{}", Hex.encodeHexString(Files.readAllBytes(Paths.get(TXT_PATH))));
    }
}
