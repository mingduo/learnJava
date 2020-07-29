package common.buessiness.problems.collection.nullvalue.dbnull;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DbUserRepository extends CrudRepository<DbUser,Long> {

    @Query(nativeQuery = true,value = "SELECT IFNULL(sum(score),0) from db_user")
    Long sumScore();

    @Query(nativeQuery = true,value = "select count(*) from db_user")
    Long countScore();

    @Query(nativeQuery = true,value = "select * from db_user where score is null")
    List<DbUser> listDbUser();
}
