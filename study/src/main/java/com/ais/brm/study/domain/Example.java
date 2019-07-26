package com.ais.brm.study.domain;

import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.List;

@Data
public class Example {
    private List<Inventor> inventions = new ArrayList<>();

    public Example() {
        GregorianCalendar cal = new GregorianCalendar();
        cal.set(1856, 7, 9);
        Inventor inventor = new Inventor("Nicolas Tesla", cal.getTime(), "serbian");

        Inventor inventor2 = new Inventor("bmw", cal.getTime(), "jetty");

        Inventor inventor3 = new Inventor("法拉利", cal.getTime(), "tom");

        Inventor inventor4 = new Inventor("劳斯拉斯", cal.getTime(), "fuzyy");

        inventions.addAll( Arrays.asList(inventor, inventor2, inventor3, inventor4));

    }


}