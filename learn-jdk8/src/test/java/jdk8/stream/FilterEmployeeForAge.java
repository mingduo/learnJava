package com.ais.brm.study.brmTest.jdk8.stream;

public class FilterEmployeeForAge implements MyPredicate<Employee> {

    @Override
    public boolean test(Employee t) {
        return t.getAge() <= 35;
    }

}
