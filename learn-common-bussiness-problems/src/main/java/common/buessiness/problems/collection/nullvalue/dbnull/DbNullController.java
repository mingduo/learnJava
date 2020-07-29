package common.buessiness.problems.collection.nullvalue.dbnull;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

/**
 * 
 *  
 * @since 2020/7/28
 * @author : weizc 
 */
@RequestMapping("dbnull")
@Slf4j
@RestController
public class DbNullController {

    @Autowired
    DbUserRepository dbUserRepository;
    @Autowired
    DbUserWrongRepository dbUserWrongRepository;


    @PostConstruct
    public void  init (){
        dbUserRepository.save(new DbUser());
    }

    @GetMapping("wrong")
    public void wrong(){
        log.info("db user sum:{}",dbUserWrongRepository.sumScore());

        log.info("db user count:{}",dbUserWrongRepository.countScore());

        log.info("db user score is null list:{}",dbUserWrongRepository.listDbUser());

    }

    @GetMapping("right")
    public void right(){
        log.info("db user sum:{}",dbUserRepository.sumScore());

        log.info("db user count:{}",dbUserRepository.countScore());

        log.info("db user score is null list:{}",dbUserRepository.listDbUser());
    }

}
