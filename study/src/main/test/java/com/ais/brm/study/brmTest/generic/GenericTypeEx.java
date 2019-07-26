package com.ais.brm.study.brmTest.generic;

/**
 * <table border="1">
 * <tr><th>@Description:</th></tr>
 * <tr><td>@Date:Created in 2018-10-10</td>
 * </tr>
 * </table>
 *
 * @author :    weizc
 */
public class GenericTypeEx {

    public static void main(String[] args) {
        DemoInterface demoClass = new DemoClass();
        System.out.println(demoClass.doSomeOperation(10));
        System.out.println(demoClass.doReverseOperation("hi"));

    }
}

interface DemoInterface<T1, T2> {
    T2 doSomeOperation(T2 t);

    T1 doReverseOperation(T1 t);
}

//A class implementing generic interface
class DemoClass implements DemoInterface<String, Integer> {

    @Override
    public Integer doSomeOperation(Integer t) {
        return t;
    }

    @Override
    public String doReverseOperation(String t) {
        return t;
    }
}