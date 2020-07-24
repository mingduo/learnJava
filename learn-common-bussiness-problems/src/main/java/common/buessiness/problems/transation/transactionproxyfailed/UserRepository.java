package common.buessiness.problems.transation.transactionproxyfailed;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 
 * 
 * @since 2020/7/24
 * @author : weizc 
 */
@Repository
public interface UserRepository extends CrudRepository<UserEntity,Long> {

    List<UserEntity> findByName(String name);
}
