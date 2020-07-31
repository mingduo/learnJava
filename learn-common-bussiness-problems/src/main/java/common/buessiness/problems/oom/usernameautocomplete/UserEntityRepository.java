package common.buessiness.problems.oom.usernameautocomplete;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 
 * 
 * @since 2020/7/31
 * @author : weizc 
 */
@Repository
public interface UserEntityRepository extends JpaRepository<UserOOMEntity,Long> {
}
