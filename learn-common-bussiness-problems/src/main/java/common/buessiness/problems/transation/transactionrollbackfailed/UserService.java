package common.buessiness.problems.transation.transactionrollbackfailed;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * 
 *  
 * @since 2020/7/24
 * @author : weizc 
 */
@Slf4j
@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    private void mockCreateUserFailed(Runnable runnable){
        try {
            runnable.run();
        }catch (Exception e){
          log.error("create user failed ",e);
        }
    }
    //异常无法传播出方法，导致事务无法回滚
    @Transactional
    public void createUserWrong1(String name) {
        mockCreateUserFailed(()->{
            userRepository.save(new UserEntity(name));
            throw new RuntimeException("error");
        });
    }

    //即使出了受检异常也无法让事务回滚
    @Transactional
    public void createUserWrong2(String name ) throws IOException {
        userRepository.save(new UserEntity(name));
        otherTask();
    }
    //因为文件不存在，一定会抛出一个IOException
    private void otherTask() throws IOException {
        Files.readAllLines(Paths.get("file-that-not-exist"));
    }


    public int getUserCount(String name){
        return userRepository.countByName(name);
    }

    @Autowired
    PlatformTransactionManager platformTransactionManager;


    @Transactional
    public void createUserRight(String name) {
        try {
            userRepository.save(new UserEntity(name));
            throw new RuntimeException("error");
        }catch (Exception e){
            log.error("create user failed ",e);
            //自己手动回滚
            platformTransactionManager.rollback(TransactionAspectSupport.currentTransactionStatus());
        }
        log.info("result {} ", userRepository.countByName(name));//为什么这里是1你能想明白吗？

    }

    @Transactional
    public void createUserRight1(String name) {
        try {
            userRepository.save(new UserEntity(name));
            throw new RuntimeException("error");
        }catch (Exception e){
            log.error("create user failed ",e);
            //利用org.springframework.transaction.support.AbstractPlatformTransactionManager.commit
           TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        log.info("result {} ", userRepository.countByName(name));//为什么这里是1你能想明白吗？

    }




    //DefaultTransactionAttribute
    @Transactional(rollbackFor = Exception.class)
    public void createUserRight2(String name ) throws IOException {
        userRepository.save(new UserEntity(name));
        otherTask();
    }
}
