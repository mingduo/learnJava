package com.ais.brm.study.brmTest.bean;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
public class Employee {
    String name;
    Date creatTime;
    Car car;


    public void speak(){
        System.out.println("speak=>"+this);
    }


}