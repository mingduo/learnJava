package common.buessiness.problems.redundantcode.reflection.right;


import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
public @interface BankAPIField {

    int order() default -1;

    String type();

    int length();
}
