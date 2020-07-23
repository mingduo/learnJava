package common.buessiness.problems.httpinvoke.ribbonretry;

import common.buessiness.problems.utils.SystemPropertySourceUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;


@SpringBootApplication
public class CommonMistakesApplication {

    public static void main(String[] args) {

        ClassPathResource resource = new ClassPathResource("/common/buessiness/problems/httpinvoke/ribbonretry/default.properties");
        SystemPropertySourceUtils.loadProperties(resource);
        SpringApplication.run(CommonMistakesApplication.class, args);
    }
}

