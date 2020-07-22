package common.buessiness.problems.connectionpool.datasource;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 
 * 
 * @since 2020/7/22
 * @author : weizc 
 */
@Repository
public interface UserRepository extends JpaRepository<User,Long> {
}
