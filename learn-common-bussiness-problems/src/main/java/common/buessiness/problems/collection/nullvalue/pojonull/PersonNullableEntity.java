package common.buessiness.problems.collection.nullvalue.pojonull;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

@Data
@Entity
public class PersonNullableEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String name;
    private String nickname;
    private Integer age;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate = new Date();
}
