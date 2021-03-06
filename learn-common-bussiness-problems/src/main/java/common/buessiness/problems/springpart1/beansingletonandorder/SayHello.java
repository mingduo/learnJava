package common.buessiness.problems.springpart1.beansingletonandorder;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

/**
 * 
 *  
 * @since 2020/9/9
 * @author : weizc 
 */
@Service
@Slf4j
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE,proxyMode = ScopedProxyMode.TARGET_CLASS)
public class SayHello extends SayService {

    @Override
    public void say() {
        super.say();
        log.info("hello");
    }
}
