package com.ais.brm.study.brmTest.jdk8.optionAndLocateDate;

public interface MyInterface {

    default String getName() {
        return "呵呵呵";
    }

    public static void show() {
        System.out.println("接口中的静态方法");
    }

}
