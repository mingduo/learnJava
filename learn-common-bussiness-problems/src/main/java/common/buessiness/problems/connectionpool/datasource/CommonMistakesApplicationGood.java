package common.buessiness.problems.connectionpool.datasource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@PropertySource("classpath:common/buessiness/problems/connectionpool/datasource/good.properties")
@SpringBootApplication
public class CommonMistakesApplicationGood {
    /**
     * 对类似数据库连接池的重
     * 要资源进行持续检测，并设置一半的使用量作为报警阈值，出现预警后及时扩容
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(CommonMistakesApplicationGood.class, args);
    }
}

