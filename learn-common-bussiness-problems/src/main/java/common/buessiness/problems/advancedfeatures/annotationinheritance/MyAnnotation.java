package common.buessiness.problems.advancedfeatures.annotationinheritance;


import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface MyAnnotation {

    String value();
}
