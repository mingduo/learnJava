package proxy;

import org.junit.Test;
import org.springframework.aop.framework.Advised;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.interceptor.PerformanceMonitorInterceptor;
import org.springframework.aop.support.DelegatingIntroductionInterceptor;
import org.springframework.aop.target.CommonsPool2TargetSource;
import org.springframework.aop.target.HotSwappableTargetSource;
import org.springframework.aop.target.PrototypeTargetSource;
import org.springframework.aop.target.ThreadLocalTargetSource;

import java.util.stream.Stream;

/**
 * @author : weizc
 * @description:
 * @since 2019/5/7
 */
public class AopTest {

    //Advisor api
    @Test
    public void test() {
        String str = "test";
        DelegatingIntroductionInterceptor introductionInterceptor = new DelegatingIntroductionInterceptor(str);
        Class<?>[] interfaces = introductionInterceptor.getInterfaces();
        Stream.of(interfaces).forEach(System.out::println);

        UserService userService = new UserServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.setTarget(userService);

        proxyFactory.addAdvice(new PerformanceMonitorInterceptor());

        proxyFactory.addAdvice(introductionInterceptor);


        proxyFactory.setInterfaces(UserService.class);
        //jdk or cglib
        proxyFactory.setProxyTargetClass(true);

        UserService userServiceProxy = (UserService) proxyFactory.getProxy();
        userServiceProxy.createUser();

        Advised advised = proxyFactory;
        System.out.println("advised :");
        Stream.of(advised.getAdvisors()).forEach(System.out::println);

    }

    @Test
    public void testTargetSource() {
        // exists to let the target of an AOP proxy be switched while letting callers keep their references to it
        String str = "old";
        HotSwappableTargetSource hotSwappableTargetSource = new HotSwappableTargetSource(str);
        Object old = hotSwappableTargetSource.swap("new");
        System.out.println("old:" + old);
        System.out.println("new :" + hotSwappableTargetSource.getTarget());
        //PoolTargetSource
        CommonsPool2TargetSource commonsPool2TargetSource = new CommonsPool2TargetSource();
        commonsPool2TargetSource.setMaxIdle(5);
        //PrototypeTargetSource
        PrototypeTargetSource prototypeTargetSource = new PrototypeTargetSource();
        //ThreadLocalTargetSource
        ThreadLocalTargetSource threadLocalTargetSource = new ThreadLocalTargetSource();
    }

}
