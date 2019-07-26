package com.ais.brm.study.brmTest.easyrule;

import lombok.Data;

@Data
public class Person {
    private String name;
    private int age;
    private boolean adult;
    //getters and setters omitted


    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }
}