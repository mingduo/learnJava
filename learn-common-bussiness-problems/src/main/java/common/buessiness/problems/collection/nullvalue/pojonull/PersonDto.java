package common.buessiness.problems.collection.nullvalue.pojonull;

import lombok.Data;

import java.util.Optional;

/**
 * 
 *  
 * @since 2020/7/28
 * @author : weizc 
 */
@Data
public class PersonDto {

    private Long id;
    private Optional<String> name;
    private Optional<Integer> age;
}
