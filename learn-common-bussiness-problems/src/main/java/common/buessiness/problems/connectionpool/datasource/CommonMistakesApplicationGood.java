package common.buessiness.problems.connectionpool.datasource;

import common.buessiness.problems.utils.SystemPropertySourceUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CommonMistakesApplicationGood {
    /**
     * 对类似数据库连接池的重
     * 要资源进行持续检测，并设置一半的使用量作为报警阈值，出现预警后及时扩容
     * @param args
     */
    public static void main(String[] args) {
        SystemPropertySourceUtils.loadProperties("classpath:common/buessiness/problems/connectionpool/datasource/good.properties");
        SpringApplication.run(CommonMistakesApplicationGood.class, args);
    }
}

