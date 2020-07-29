package common.buessiness.problems.collection.nullvalue.dbnull;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DbUserWrongRepository extends CrudRepository<DbUser,Long> {

    @Query(nativeQuery = true,value = "select sum(score) from db_user")
    Long sumScore();

    @Query(nativeQuery = true,value = "SELECT count(score) from db_user")
    Long countScore();

    @Query(nativeQuery = true,value = "select *  from db_user where score =null")
    List<DbUser> listDbUser();
}
