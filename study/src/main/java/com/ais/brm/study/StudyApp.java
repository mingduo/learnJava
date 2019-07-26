package com.ais.brm.study;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * RuleEngine app.
 * Created by zhaocw on 2016/6/1.
 * @author weizc
 */
@ComponentScan("com.ais.brm")
@SpringBootApplication
public class StudyApp {
    /**
     * .
     * @param args
     */
    
    public static void main(String[] args) {
        SpringApplication.run(StudyApp.class, args);
    }
}