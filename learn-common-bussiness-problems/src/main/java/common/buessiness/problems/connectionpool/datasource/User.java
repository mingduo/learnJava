package common.buessiness.problems.connectionpool.datasource;

import lombok.Data;

import javax.persistence.*;

/**
 * 
 *  
 * @since 2020/7/22
 * @author : weizc 
 */
@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(length = 64)
    private String name;

}
