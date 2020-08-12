package common.buessiness.problems.advancedfeatures.genericandinheritance;

import common.buessiness.problems.utils.PrintlnUtils;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * @author : weizc
 * @since 2020/8/12
 */
@Slf4j
public class GenericandinheritanceDemo {


    public static void main(String[] args) {

        wrong();

        PrintlnUtils.println();

        wrong2();

        PrintlnUtils.println();

        wrong3();

        PrintlnUtils.println();
        /**
         * getMethods 和 getDeclaredMethods 是有区别的，
         * 前者可以查询到父类方法，后者
         * 只能查询到当前类。
         * 反射进行方法调用要注意过滤桥接方法。
         */
        right();
    }

    private static void wrong() {
//两次 Parent 的 setValue 方法调用，是因为 getMethods 方法找到了两个名为
//setValue 的方法，分别是父类和子类的 setValue 方法
        Child child = new Child();
        Arrays.stream(child.getClass().getMethods())
                .filter(method -> method.getName().equals("setValue"))
                .forEach(m -> {
                    try {
                        m.invoke(child, "test");
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                });

        System.out.println(child);

    }

    private static void wrong2() {
        //其实这治标不治本，其他人使用 Child1 时还是会发现有两个 setValue 方法
        //非常容易让人困惑。
        Child child = new Child();
        Arrays.stream(child.getClass().getDeclaredMethods())
                .filter(method -> method.getName().equals("setValue"))
                .forEach(m -> {
                    try {
                        m.invoke(child, "test");
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                });

        System.out.println(child);

    }


    private static void wrong3() {

        Child2 child = new Child2();
        Arrays.stream(child.getClass().getDeclaredMethods())
                .filter(method -> method.getName().equals("setValue"))
                .forEach(m -> {
                    try {
                        m.invoke(child, "test");
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                });

        System.out.println(child);

    }

    /**
     * 同样可以看到入参为 Object 的桥接方法上标记了
     * public + synthetic + bridge 三个属性。
     * synthetic 代表由编译器生成的不可见代码，
     * bridge 代表这是泛型类型擦除后生成的桥接代码：
     */
    private static void right() {

        Child2 child = new Child2();
        Arrays.stream(child.getClass().getDeclaredMethods())
                .filter(method -> method.getName().equals("setValue"))
                .filter(method -> !method.isBridge())
                .forEach(m -> {
                    try {
                        m.invoke(child, "test");
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                });

        System.out.println(child);

    }

}

@Slf4j
class Parent<T> {
    /**
     * 有一个项目希望在类字段内容变动时记录日志，
     * 于是开发同学就想到定义一个泛型父类，并
     * 在父类中定义一个统一的日志记录方法，子类可以通过继承重用这个方法。
     * 代码上线后业务
     * 没啥问题，但总是出现日志重复记录的问题
     */
    AtomicInteger count = new AtomicInteger();

    T value;

    public void setValue(T value) {
        log.info("Parent#setValue is called");
        this.value = value;
        count.incrementAndGet();

    }

    @Override
    public String toString() {
        return "Parent{" +
                "count=" + count.get() +
                ", value=" + value +
                '}';
    }
}
@Slf4j
class Child extends Parent {

    public void setValue(String value) {
        log.info("Child#setValue is called");
        super.setValue(value);
    }
}
//是泛型类型擦除导致的问题
@Slf4j
class Child2 extends Parent<String> {

    @Override
    public void setValue(String value) {
        log.info("Child#setValue is called");
        super.setValue(value);
    }
}