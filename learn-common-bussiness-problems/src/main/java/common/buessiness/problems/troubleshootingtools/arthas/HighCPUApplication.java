package common.buessiness.problems.troubleshootingtools.arthas;

import org.springframework.util.DigestUtils;

import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 
 *  
 * @since 2020/8/14
 * @author : weizc 
 */
public class HighCPUApplication {

    private static byte[] payload= IntStream.rangeClosed(1,10_000)
            .mapToObj(__->"a")
            .collect(Collectors.joining()).getBytes();

    /**
     * 模拟一个high cpu场景
     * @param args
     */
    public static void main(String[] args) {
        task();
    }

    static Random random=new Random();

    private static void task() {
        while (true){
            doTask(random.nextInt(100));
        }
    }

    private static void doTask(int i) {
        if(i==User.ADMIN_ID){
            IntStream.rangeClosed(1,10_000).parallel()
                    .forEach(t-> DigestUtils.md5DigestAsHex(payload));
        }
    }
}
