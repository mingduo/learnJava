package common.buessiness.problems.oom.impropermaxheadersize;

import common.buessiness.problems.utils.SystemPropertySourceUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author : weizc
 * @since 2020/8/5
 */
@SpringBootApplication
public class BadApp {

    public static void main(String[] args){
        SystemPropertySourceUtils.loadProperties("classpath:common/buessiness/problems/oom/impropermaxheadersize/bad.properties");
        SpringApplication.run(BadApp.class,args);

    }


}
