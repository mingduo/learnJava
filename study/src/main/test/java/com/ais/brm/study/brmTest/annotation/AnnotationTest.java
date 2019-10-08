package com.ais.brm.study.brmTest.annotation;

import org.junit.Test;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

/**
 * @author : weizc
 * @description:
 * @since 2019/1/9
 */
@Component("aa")
public class AnnotationTest {
    @Test
    public void test1() {
        Component annotation = AnnotationUtils.findAnnotation(this.getClass(), Component.class);
        System.out.println(annotation.value());

    }


}
