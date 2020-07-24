package common.buessiness.problems.transation.transactionproxyfailed;

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
    UserRepository userRepository;
    @Autowired
    UserService self;


    public void mockCreateUser(Runnable r) {
        try {
            r.run();
        } catch (Exception e) {
            log.error("create user failed because:{}", e.getMessage());
        }
    }

    //一个公共方法供Controller调用，内部调用事务性的私有方法
    public int createUserWrong1(String name) {

        mockCreateUser(() -> this.createUserPrivate(new UserEntity(name)));

        return userRepository.findByName(name).size();
    }

    //一个公共方法供Controller调用，this 没有代理
    //自调用
    public int createUserWrong2(String name) {

        mockCreateUser(() -> createUserPublic(new UserEntity(name)));

        return userRepository.findByName(name).size();
    }
    //不出异常
    @Transactional
    public int createUserWrong3(String name) {

        mockCreateUser(() -> createUserPublic(new UserEntity(name)));

        return userRepository.findByName(name).size();
    }

    //标记了@Transactional的private方法
    @Transactional
    private void createUserPrivate(UserEntity userEntity) {
        userRepository.save(userEntity);
        if (userEntity.getName().contains("test")) {
            throw new RuntimeException("invalid username!");
        }
    }


    //可以传播出异常
    @Transactional
    public void createUserPublic(UserEntity userEntity) {
        userRepository.save(userEntity);
        if (userEntity.getName().contains("test")) {
            throw new RuntimeException("invalid username!");
        }
    }

    //重新注入自己
    public int createUserRight(String name) {
        //mockcreateuser-> self#createUserPublic->transation-proxy->right->commit ->mockcreateuser
        // mockcreateuser-> self#createUserPublic->transation-proxy->wrong-> rollback->ex->  mockcreateuser                                                                                       ->wrong
        mockCreateUser(() ->
                self.createUserPublic(new UserEntity(name)));


        return userRepository.findByName(name).size();
    }

    public int getUserCount(String name) {
        return userRepository.findByName(name).size();

    }
}
