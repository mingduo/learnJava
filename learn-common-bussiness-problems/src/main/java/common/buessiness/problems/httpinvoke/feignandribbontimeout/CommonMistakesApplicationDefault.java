package common.buessiness.problems.httpinvoke.feignandribbontimeout;

import common.buessiness.problems.utils.SystemPropertySourceUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.DefaultResourceLoader;


@SpringBootApplication
public class CommonMistakesApplicationDefault {


    public static void main(String[] args) {

        SystemPropertySourceUtils.loadProperties(new DefaultResourceLoader()
                .getResource
                ("classpath:/common/buessiness/problems/httpinvoke/feignandribbontimeout/default.properties"));

        SpringApplication.run(CommonMistakesApplicationDefault.class, args);
    }
}

