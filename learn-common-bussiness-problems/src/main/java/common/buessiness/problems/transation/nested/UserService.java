package common.buessiness.problems.transation.nested;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author : weizc
 * @since 2020/7/24
 */
@Slf4j
@Service
public class UserService {

    @Autowired
    UserMapper userMapper;
    @Autowired
    SubUserService subUserService;


    @Transactional
    public void createUser(String name) {
        createMainUser(name, "main");
        try {
            subUserService.createSubUser(name);
        } catch (Exception e) {
            log.error("create sub user error,reason:{}", e.getMessage());
        }
        //如果createSubUser是NESTED模式，这里抛出异常会导致嵌套事务无法『提交』
        throw new RuntimeException("create main user error");
    }

    private void createMainUser(String name, String source) {
        userMapper.insert(name, source);
    }

    public int getUserCount(String name) {
        return userMapper.count(name);
    }
}
