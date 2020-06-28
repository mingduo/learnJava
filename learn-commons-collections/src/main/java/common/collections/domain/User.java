package common.collections.domain;

import lombok.Data;

/**
 * 
 * @apiNode:
 * @since 2020/6/24
 * @author : weizc 
 */
@Data
public class User {

    private Long id;
    private String name;

    private City city;

    private Car car;
}
