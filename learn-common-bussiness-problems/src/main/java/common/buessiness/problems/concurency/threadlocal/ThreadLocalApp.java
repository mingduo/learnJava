package common.buessiness.problems.concurency.threadlocal;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * 
 *  
 * @since 2020/7/10
 * @author : weizc 
 */
@SpringBootApplication
public class ThreadLocalApp {
    //设置tomcat 单线程
    public static void main(String[] args) {
        new SpringApplicationBuilder(ThreadLocalApp.class)
                .properties("server.tomcat.max-threads=1")
                .run(args);
    }
}
