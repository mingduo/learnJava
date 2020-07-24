package common.buessiness.problems.transation.transactionproxyfailed;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * 
 *  
 * @since 2020/7/24
 * @author : weizc 
 */
@NoArgsConstructor
@Data
@Entity
public class UserEntity {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    Long id;

    @Column(length = 64)
    private String name;


    public UserEntity(String name) {
        this.name=name;
    }
}
