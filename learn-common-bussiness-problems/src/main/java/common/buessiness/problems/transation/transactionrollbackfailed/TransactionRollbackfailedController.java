package common.buessiness.problems.transation.transactionrollbackfailed;

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
@RequestMapping("transactionrollbackfailed")
@RestController
public class TransactionRollbackfailedController {

    @Autowired
    UserService userService;

    @GetMapping("wrong1")
    public int wrong1(@RequestParam String name){
        try {
            userService.createUserWrong1(name);
        }catch (Exception e){
            log.error("create user failed ",e);
        }
        return userService.getUserCount(name);
    }




    @GetMapping("wrong2")
    public int wrong2(@RequestParam String name){
        try {
            userService.createUserWrong2(name);
        }catch (Exception e){
            log.error("create user failed ",e);
        }
        return userService.getUserCount(name);
    }


    @GetMapping("right")
    public int right(@RequestParam String name){
        try {
            userService.createUserRight(name);
        }catch (Exception e){
            log.error("create user failed ",e);
        }
        return userService.getUserCount(name);
    }

    @GetMapping("right1")
    public int right1(@RequestParam String name){
        try {
            userService.createUserRight1(name);
        }catch (Exception e){
            log.error("create user failed ",e);
        }
        return userService.getUserCount(name);
    }


    @GetMapping("right2")
    public int right2(@RequestParam String name){
        try {
            userService.createUserRight2(name);
        }catch (Exception e){
            log.error("create user failed ",e);
        }
        return userService.getUserCount(name);
    }

}
