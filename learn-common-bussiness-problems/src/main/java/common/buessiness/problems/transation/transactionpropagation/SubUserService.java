package common.buessiness.problems.transation.transactionpropagation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 *  
 * @since 2020/7/24
 * @author : weizc 
 */
@Slf4j
@Service
public class SubUserService {

    @Autowired
    UserRepository userRepository;

    @Transactional
    public void createSubUserWithExceptionWrong(String name){
        log.info("SubUserService#createSubUserWithException start");
        userRepository.save(new UserEntity(name));
        throw new RuntimeException("invalid status");
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void createSubUserWithExceptionRight(String name){
        log.info("SubUserService#createSubUserWithException start");
        userRepository.save(new UserEntity(name));
        throw new RuntimeException("invalid status");
    }
}
