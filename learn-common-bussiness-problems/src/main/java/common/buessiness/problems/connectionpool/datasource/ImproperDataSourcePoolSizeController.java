package common.buessiness.problems.connectionpool.datasource;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("improperdatasourcepoolsize")
@Slf4j
public class ImproperDataSourcePoolSizeController {

    @Autowired
    UserService userService;

    @GetMapping("test")
    public User test(){
        return userService.register();
    }
}