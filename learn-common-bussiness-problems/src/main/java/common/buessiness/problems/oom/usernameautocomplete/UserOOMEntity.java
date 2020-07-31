package common.buessiness.problems.oom.usernameautocomplete;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import static javax.persistence.GenerationType.IDENTITY;


/**
 * @author weizc
 */
@Entity
@Data
public class UserOOMEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String name;

    public UserOOMEntity() {
    }

    public UserOOMEntity(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
