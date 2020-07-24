package common.buessiness.problems.transation.transactionproxyfailed;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 
 *  
 * @since 2020/7/24
 * @author : weizc 
 */
@Slf4j
@RequestMapping("transactionproxyfailed")
@RestController
public class TransactionProxyFailedController {

    @Autowired
    UserService userService;

    @GetMapping("wrong1")
    public int wrong1(@RequestParam String name){
        return userService.createUserWrong1(name);
    }

    @GetMapping("wrong2")
    public int wrong2(@RequestParam String name){
        return userService.createUserWrong2(name);
    }

    @GetMapping("wrong3")
    public int wrong3(@RequestParam String name){
        return userService.createUserWrong3(name);
    }

    @GetMapping("right1")
    public int right1(@RequestParam String name){
        return userService.createUserRight(name);
    }

    @GetMapping("right2")
    public int right2(@RequestParam String name){
        try {
            userService.createUserPublic(new UserEntity(name));
        }catch (Exception e){
            e.printStackTrace();
        }
        return userService.getUserCount(name);
    }
}
