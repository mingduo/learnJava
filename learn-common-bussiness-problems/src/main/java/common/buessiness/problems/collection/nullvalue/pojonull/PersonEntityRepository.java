package common.buessiness.problems.collection.nullvalue.pojonull;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * 
 * 
 * @since 2020/7/28
 * @author : weizc 
 */
@Repository
public interface PersonEntityRepository extends CrudRepository<PersonEntity,Long> {
}
