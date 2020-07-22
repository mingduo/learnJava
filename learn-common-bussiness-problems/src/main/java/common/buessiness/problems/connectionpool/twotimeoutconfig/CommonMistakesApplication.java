package common.buessiness.problems.connectionpool.twotimeoutconfig;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@PropertySource("classtpath:/common/buessiness/problems/connectionpool/twotimeoutconfig/hikari.properties")
@SpringBootApplication
public class CommonMistakesApplication {

    public static void main(String[] args) {
        SpringApplication.run(CommonMistakesApplication.class, args);
    }
}

