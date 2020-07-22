package common.buessiness.problems.connectionpool.datasource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

/**
 * 
 *  
 * @since 2020/7/22
 * @author : weizc 
 */
@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Transactional
    public User register(){
        User user = new User();
        user.setName("new-user-"+System.currentTimeMillis());
        userRepository.save(user);
        try {
            TimeUnit.MILLISECONDS.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return user;

    }
}
