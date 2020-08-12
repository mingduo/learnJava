package common.buessiness.problems.connectionpool.datasource;

import common.buessiness.problems.utils.SystemPropertySourceUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@PropertySource("classpath:common/buessiness/problems/connectionpool/datasource/bad.properties")
@SpringBootApplication
public class CommonMistakesApplicationBad {

    public static void main(String[] args) {

        SystemPropertySourceUtils.loadProperties("classpath:common/buessiness/problems/connectionpool/datasource/bad.properties");
        SpringApplication.run(CommonMistakesApplicationBad.class, args);
    }
}

