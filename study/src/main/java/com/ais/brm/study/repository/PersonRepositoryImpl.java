package com.ais.brm.study.repository;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

/**
 * {@link PersonRepository} 实现
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy</a>
 * @see
 * @since 2017.08.04
 */
@Repository
public class PersonRepositoryImpl implements PersonRepository {

    private final Map<String, String> map = new HashMap<>();


    @Override
    public String findOne(String id) {
        return map.get(id);
    }

    @Override
    public void add(String id, String value) {
        map.put(id, value);
    }
}
