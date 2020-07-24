package common.buessiness.problems.transation.transactionpropagation;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * 
 * 
 * @since 2020/7/24
 * @author : weizc 
 */
@Repository
public interface UserRepository extends CrudRepository<UserEntity,Long> {


    int countByName(String name);
}
