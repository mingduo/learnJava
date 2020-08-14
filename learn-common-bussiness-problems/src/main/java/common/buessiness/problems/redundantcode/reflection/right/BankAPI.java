package common.buessiness.problems.redundantcode.reflection.right;


import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface BankAPI {

    String url();
    String desc() default "";
}
