package common.buessiness.problems.advancedfeatures.reflectionissue;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationTargetException;

/**
 * @author : weizc
 * @since 2020/8/12
 */
@Slf4j
public class ReflectionissueDemo {


    @SneakyThrows
    public static void main(String[] args) {

        ReflectionissueDemo demo = new ReflectionissueDemo();
        demo.wrong();
        demo.right();

    }

    private void wrong() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        this.getClass().getDeclaredMethod("age",Integer.TYPE).invoke(this,Integer.valueOf(36));
    }

    private void right() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        this.getClass().getDeclaredMethod("age",Integer.class).invoke(this,Integer.valueOf(36));
        this.getClass().getDeclaredMethod("age",Integer.class).invoke(this,36);
        this.getClass().getDeclaredMethod("age",int.class).invoke(this,Integer.valueOf(36));

    }


    private void age(int age){
        log.info("int age = {}",age);
    }

    private void age(Integer age){
        log.info("Integer age = {}",age);
    }

}
