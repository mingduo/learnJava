package com.ais.brm.study.brmTest.proxy;

import org.aopalliance.intercept.MethodInterceptor;
import org.jboss.logging.Logger;
import org.junit.Test;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.interceptor.ExposeInvocationInterceptor;
import org.springframework.aop.interceptor.SimpleTraceInterceptor;
import org.springframework.data.projection.DefaultMethodInvokingMethodInterceptor;
import org.springframework.data.repository.core.support.SurroundingTransactionDetectorMethodInterceptor;
import org.springframework.util.StopWatch;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author : weizc
 * @description: 代理
 * @since 2019/1/21
 */
public class ProxyTest {

    public static void main(String[] args) {
        //被代理的对象
        UserService userService = new UserServiceImpl();
        //获取日志的InvocationHandler
        LogInteceptor logInteceptor = new LogInteceptor();

        logInteceptor.setTarget(userService);//把被代理的对象设为userDao

        //设置代理对象，参数1:被代理对象的classloader,参数2:被代理对象所实现的接口（该对象必须要实现接口，不然无法产生代理）,参数3：指定处理的InvocationHandler
        UserService userServiceProxy = (UserService) Proxy.newProxyInstance(userService.getClass().getClassLoader(), new Class[]{UserService.class}, logInteceptor);
        //执行方法
        userServiceProxy.createUser();
        userServiceProxy.deleteUser();
        userServiceProxy.updateUser(1001);
    }

    @Test
    public void test() {
        ProxyFactory result = new ProxyFactory();
        result.setTarget(new UserServiceImpl());
        result.setInterfaces(UserService.class);

        StopWatch sw = new StopWatch(getClass().getSimpleName());

        sw.start(this.getClass().getName());


        result.addAdvice(SurroundingTransactionDetectorMethodInterceptor.INSTANCE);
        result.addAdvisor(ExposeInvocationInterceptor.ADVISOR);
        result.addAdvice(new DefaultMethodInvokingMethodInterceptor());


        Logger logger = Logger.getLogger(SimpleTraceInterceptor.class);


        SimpleTraceInterceptor simpleTraceInterceptor = new SimpleTraceInterceptor();

        result.addAdvice(simpleTraceInterceptor);

        result.addAdvice((MethodInterceptor) invocation -> {
            System.out.println("Before: invocation=[" + invocation + "]");
            Object rval = invocation.proceed();
            System.out.println("Invocation returned");
            return rval;
        });
        UserService userServiceProxy = (UserService) result.getProxy();

        userServiceProxy.createUser();
        userServiceProxy.deleteUser();
        userServiceProxy.updateUser(1001);


        sw.stop();
        System.out.println(sw.prettyPrint());
    }


}


interface UserService {

    void createUser();

    void deleteUser();

    void updateUser(int id);

}

class UserServiceImpl implements UserService {

    public void createUser() {
        System.out.println("user saved...");

    }

    public void deleteUser() {
        System.out.println("delete user...");
    }

    public void updateUser(int id) {
        System.out.println("update user...");
    }

}

class LogInteceptor implements InvocationHandler {

    private Object target;//被代理的对象

    public Object getTarget() {
        return target;
    }

    public void setTarget(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args)
            throws Throwable {
        beforeMethod(method);//在方法执行前所要执行的业务逻辑
        long starttime = System.currentTimeMillis();
        method.invoke(target, args);
        long result = System.currentTimeMillis() - starttime;
        System.out.println("执行时间为：" + result + "毫秒");
        afterMethod(method);//在方法执行后所要执行的业务逻辑
        return null;
    }

    public void beforeMethod(Method m) {
        System.out.println(m.getName() + "执行before....");
    }

    public void afterMethod(Method m) {
        System.out.println(m.getName() + "执行after...");
    }

}