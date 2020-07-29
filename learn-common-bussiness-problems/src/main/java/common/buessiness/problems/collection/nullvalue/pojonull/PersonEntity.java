package common.buessiness.problems.collection.nullvalue.pojonull;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

/**
 * 
 *  
 * @since 2020/7/28
 * @author : weizc 
 */
@DynamicUpdate
@Data
@Entity
@Table
public class PersonEntity {

    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Id
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String nickname;
    @Column(nullable = false)
    private Integer age;
    @Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date createDate;
}
