package common.buessiness.problems.current.copyonwritelistmisuse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 
 *  
 * @since 2020/7/10
 * @author : weizc 
 */
@SpringBootApplication
public class CopyOnWriteListApp {

    public static void main(String[] args) {
        SpringApplication.run(CopyOnWriteListApp.class,args);
    }
}
