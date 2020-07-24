package common.buessiness.problems.transation.transactionpropagation;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Autowired
    SubUserService subUserService;


    /**
     *  1 开启事务 t0
     *  2 main 开启事务 t1
     *  3 sub 开启事务 t2
     *  t2 -> 回滚 throw ex
     *  t0 catch ex
     *  t0 回滚
     *
     *
     * @param name
     */
    @Transactional
    public void createUserWrong1(String name) {

        createMainUser(name); //true
        subUserService.createSubUserWithExceptionWrong(name);//false

    }

    /**
     *  1 开启事务 t0
     *  2 main 开启事务 t1
     *  t1 成功
     *  3 sub 开启事务 t2
     *  t2 ->try catch -> set read only 状态
     *  t0 全局提交 失败
     *
     * @param name
     */
    @Transactional
    public void createUserWrong2(String name ) {
        createMainUser(name);
        try {
            subUserService.createSubUserWithExceptionWrong(name);
        }catch (Exception e){
            // 虽然捕获了异常，但是因为没有开启新事务，而当前事务因为异常已经被标记为rollback了，所以最终还是会回滚。
            log.error("create sub user error:{}", e.getMessage());
        }
    }


    public int getUserCount(String name){
        return userRepository.countByName(name);
    }


    /**
     *  1 开启事务 t0
     *  2 main 开启事务 t1
     *  t1 成功
     *  3 sub 开启事务 t2
     *  t2 ->  roll back
     *  t1 != t2
     *  分别处理
     *
     * @param name
     */
    @Transactional
    public void createUserRight(String name) {
        createMainUser(name);
        try {
                subUserService.createSubUserWithExceptionRight(name);
        }catch (Exception e){
            log.error("create sub user error:{}", e.getMessage());
        }

        log.info("result {} ", userRepository.countByName(name));

        throw new RuntimeException("");
    }




    @Transactional
    public void createMainUser(String name )  {
        userRepository.save(new UserEntity(name));
        log.info("UserService#createMainUser finish");

    }
}
