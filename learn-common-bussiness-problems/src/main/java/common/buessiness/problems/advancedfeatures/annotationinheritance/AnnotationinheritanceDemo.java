package common.buessiness.problems.advancedfeatures.annotationinheritance;

import common.buessiness.problems.utils.PrintlnUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.reflect.Method;


/**
 * @author : weizc
 * @since 2020/8/12
 */
@Slf4j
public class AnnotationinheritanceDemo {

    public static void main(String[] args)  {
        wrong();

        PrintlnUtils.println();

        right();

        PrintlnUtils.println();

        right2();
    }


    @SneakyThrows
    private static void right2() {
        Parent parent = new Parent();


        log.info("parent class : {}", AnnotatedElementUtils.findMergedAnnotation(parent.getClass(),MyAnnotation.class));
        log.info("parent method : {}", AnnotatedElementUtils.findMergedAnnotation(parent.getClass().getMethod("foo"),MyAnnotation.class));


        Child child = new Child();
        /**
         * Spring 提供了 AnnotatedElementUtils 类，来方便我们处理注解的继承问题。这个
         * 类的 findMergedAnnotation 工具方法，可以帮助我们找出父类和接口、父类方法和接口
         * 方法上的注解，并可以处理桥接方法，实现一键找到继承链的注解
         */
        log.info("child class : {}", AnnotatedElementUtils.findMergedAnnotation(child.getClass(),MyAnnotation.class));
        log.info("child method : {}",  AnnotatedElementUtils.findMergedAnnotation(child.getClass().getMethod("foo"),MyAnnotation.class));
    }

    @SneakyThrows
    private static void right() {
        Parent parent = new Parent();


        log.info("parent class : {}", AnnotationUtils.getAnnotation(parent.getClass(),MyAnnotation.class));
        log.info("parent method : {}", AnnotationUtils.getAnnotation(parent.getClass().getMethod("foo"),MyAnnotation.class));


        Child child = new Child();
        /**
         * Annotations on methods are not inherited by default, so we need to handle
         * 	 * this explicitly.
         */
        log.info("child class : {}", AnnotationUtils.findAnnotation(child.getClass(),MyAnnotation.class));
        log.info("child method : {}",  AnnotationUtils.findAnnotation(child.getClass().getMethod("foo"),MyAnnotation.class));
    }

    @SneakyThrows
    private static void wrong() {

        Parent parent = new Parent();


        log.info("parent class : {}", getAnnotation(parent.getClass()));
        log.info("parent method : {}", getAnnotation(parent.getClass().getMethod("foo")));


        Child child = new Child();
        /**
         * @Inherited 只能实现类上的注解
         * 继承。要想实现方法上注解的继承，你可以通过反射在继承链上找到方法上的注解。但，这
         * 样实现起来很繁琐，而且需要考虑桥接方法。
         */
        log.info("child class : {}", getAnnotation(child.getClass()));
        log.info("child method : {}", getAnnotation(child.getClass().getMethod("foo")));
    }


    private static MyAnnotation getAnnotation(Class<?> clazz) {
        return clazz.getAnnotation(MyAnnotation.class);
    }

    private static MyAnnotation getAnnotation(Method method) {
        return method.getAnnotation(MyAnnotation.class);
    }




}

@MyAnnotation("class")
class Parent {

    @MyAnnotation("method")
    public void foo() {

    }
}

class Child extends Parent {

    @Override
    public void foo() {

    }
}