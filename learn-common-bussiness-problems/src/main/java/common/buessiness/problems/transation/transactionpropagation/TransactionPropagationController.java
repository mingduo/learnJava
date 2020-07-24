package common.buessiness.problems.transation.transactionpropagation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 一个用户注册的操作，会插入一个主用户到用户表，还会注册一个关联的
 * 子用户。我们希望将子用户注册的数据库操作作为一个独立事务来处理，即使失败也不会影
 * 响主流程，即不影响主用户的注册
 *
 * @author : weizc
 * @since 2020/7/24
 */
@Slf4j
@RequestMapping("transactionpropagation")
@RestController
public class TransactionPropagationController {


    @Autowired
    UserService userService;


    @GetMapping("wrong1")
    public int wrong1(String name) {
        try {
            userService.createUserWrong1(name);
        }catch (Exception e){
            log.error("createUserWrong failed,reason:{}",e.getMessage());
        }
        return userService.getUserCount(name);
    }


    @GetMapping("wrong2")
    public int wrong2(String name) {
        try {
            userService.createUserWrong2(name);
        }catch (Exception e){
            log.error("",e);
        }
        return userService.getUserCount(name);
    }


    @GetMapping("right")
    public int right(String name) {
        try {
            userService.createUserRight(name);
        }catch (Exception e){
            log.error("",e);
        }
        return userService.getUserCount(name);
    }
}
