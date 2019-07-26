package com.ais.brm.study.brmTest.guava;

import com.google.common.primitives.Ints;
import org.junit.Test;
import org.spark_project.guava.collect.Range;

/**
 * <table border="1">
 * <tr><th>@Description:</th></tr>
 * <tr><td>@Date:Created in 2018-10-10</td>
 * </tr>
 * </table>
 *
 * @author :    weizc
 */
public class RangeEx {
    @Test
    public void  test(){
        Range<Integer> range1 = Range.closed(3, 8);
        System.out.println(range1);

        Range<Integer> range2 = Range.openClosed(3, 8);
        System.out.println(range2);

        Range<Integer> range3 = Range.closedOpen(3, 8);
        System.out.println(range3);
    }

    @Test
    public void  operate(){
        Range.closed(1, 3).contains(2);
        // returns true
        Range.closed(1, 3).contains(4);
        // returns false
        Range.lessThan(5).contains(5);
        // returns false
        Range.closed(1, 4).containsAll(Ints.asList(1, 2, 3));
        // returns true
    }
}
