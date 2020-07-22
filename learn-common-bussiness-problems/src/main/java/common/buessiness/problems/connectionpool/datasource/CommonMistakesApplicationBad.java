package common.buessiness.problems.connectionpool.datasource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@PropertySource("classpath:common/buessiness/problems/connectionpool/datasource/bad.properties")
@SpringBootApplication
public class CommonMistakesApplicationBad {

    public static void main(String[] args) {
        SpringApplication.run(CommonMistakesApplicationBad.class, args);
    }
}

