package com.ais.brm.study.brmTest.generic;

import lombok.ToString;

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
public class GenericMethodEx {
    public static void main(String[] args) {
        int count = countAllOccurrences(Arrays.asList("a", "b", "c", "a"), "a");
        System.out.println("count = [" + count + "]");
        //Generics with Wildcards
        Dimension<?> dimension=new Dimension<>(
                "10",
                "20","50");
        System.out.println("dimension = [" + dimension + "]");

    }

    //泛型方法
    public static <T> int countAllOccurrences(List<T> list, T item) {
        int count = 0;
        if (item == null) {
            for ( T listItem : list )
                if (listItem == null)
                    count++;
        }
        else {
            for ( T listItem : list )
                if (item.equals(listItem))
                    count++;
        }
        return count;
    }
}

@ToString
class Dimension<T>
{
    private T length;
    private T width;
    private T height;

    //Generic constructor
    public Dimension(T length, T width, T height)
    {
        super();
        this.length = length;
        this.width = width;
        this.height = height;
    }
}