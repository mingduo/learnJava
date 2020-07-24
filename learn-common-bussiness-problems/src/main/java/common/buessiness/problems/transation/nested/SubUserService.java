package common.buessiness.problems.transation.nested;

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
    UserMapper userMapper;

    //比较切换为REQUIRES_NEW，这里的createSubUser可以插入数据成功
    @Transactional(propagation = Propagation.NESTED)
    void createSubUser(String name){
        userMapper.insert(name,"sub");
    }
}
