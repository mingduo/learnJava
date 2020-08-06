package common.buessiness.problems.connectionpool.datasource;

import common.buessiness.problems.utils.SystemPropertySourceUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;

@SpringBootApplication
public class CommonMistakesApplicationBad {

    public static void main(String[] args) {
        ClassPathResource classPathResource = new ClassPathResource("common/buessiness/problems/connectionpool/datasource/bad.properties");
        SystemPropertySourceUtils.loadProperties(classPathResource);

        SpringApplication.run(CommonMistakesApplicationBad.class, args);
    }
}

