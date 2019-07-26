package com.ais.brm.study.brmTest.generic;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <table border="1">
 * <tr><th>@Description:</th></tr>
 * <tr><td>@Date:Created in 2018-10-10</td>
 * </tr>
 * </table>
 *
 * @author :    weizc
 */
public class Wildcards {
    //you will use upper bound wildcard using “extends” keyword.
    @Test
    public void upperBounded(){
        //List of Integers
        List<Integer> ints = Arrays.asList(1,2,3,4,5);
        System.out.println(sum(ints));

        //List of Doubles
        List<Double> doubles = Arrays.asList(1.5d,2d,3d);
        System.out.println(sum(doubles));

    }
    //Method will accept
    private static Number sum (List<? extends Number> numbers){
        double s = 0.0;
        for (Number n : numbers)
            s += n.doubleValue();
        return s;
    }

    //下限
    @Test
    public void lowerBounded(){
        //List of grand children
        List<GrandChildClass> grandChildren = new ArrayList<>();
        grandChildren.add(new GrandChildClass());
        addGrandChildren(grandChildren);

        //List of grand childs
        List<ChildClass> childs = new ArrayList<>();
        childs.add(new GrandChildClass());
        addGrandChildren(childs);

        //List of grand supers
        List<SuperClass> supers = new ArrayList<>();
        supers.add(new GrandChildClass());
        addGrandChildren(supers);
    }

    public static void addGrandChildren(List<? super GrandChildClass> grandChildren)
    {
        grandChildren.add(new GrandChildClass());
        System.out.println(grandChildren);
    }
}

class SuperClass{

}
class ChildClass extends SuperClass{

}
class GrandChildClass extends ChildClass{

}
