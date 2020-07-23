package common.buessiness.problems.httpinvoke.feignandribbontimeout;

import common.buessiness.problems.utils.SystemPropertySourceUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;

@SpringBootApplication
public class CommonMistakesApplicationFeign {



    public static void main(String[] args) {

        SystemPropertySourceUtils.loadProperties(new ClassPathResource("/common/buessiness/problems/httpinvoke/feignandribbontimeout/default.properties"));
        SystemPropertySourceUtils.loadProperties(new ClassPathResource("/common/buessiness/problems/httpinvoke/feignandribbontimeout/feign.properties"));

        SpringApplication.run(CommonMistakesApplicationFeign.class, args);
    }
}

