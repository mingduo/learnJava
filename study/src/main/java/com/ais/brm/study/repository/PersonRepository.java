package com.ais.brm.study.repository;

import org.springframework.data.repository.NoRepositoryBean;

//import org.springframework.cache.annotation.CachePut;
//import org.springframework.data.repository.CrudRepository;

/**
 * 人员 仓库
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy</a>
 * @see
 * @since 2017.08.04
 */
@NoRepositoryBean
public interface PersonRepository {


    String findOne(String id);

    void add(String id, String value);


}
