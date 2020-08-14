package common.buessiness.problems.redundantcode.reflection;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : weizc
 * @since 2020/8/13
 */
@RequestMapping("reflection")
@RestController
@Slf4j
@SpringBootApplication
public class ReflectionApp {


    public static void main(String[] args) {

        SpringApplication.run(ReflectionApp.class,"--server.port=4369");

    }

    @PostMapping("/bank/createUser")
    public String createUser(@RequestBody String data) {
        log.info("createUser called with argument {}", data);
        return "1";
    }

    @PostMapping("/bank/pay")
    public String pay(@RequestBody String data) {
        log.info("pay called with argument {}", data);
        return "OK";
    }

}
