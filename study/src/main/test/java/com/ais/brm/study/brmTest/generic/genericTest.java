package com.ais.brm.study.brmTest.generic;

import org.junit.Test;

import java.text.ParseException;

/**
 * <table border="1">
 * <tr><th>@Description:</th></tr>
 * <tr><td>@Date:Created in 2018-5-11</td>
 * </tr>
 * </table>
 *
 * @author :    weizc
 */
public class genericTest {
    @Test
    public void test4() throws ParseException {
        System.out.println(getName("123"));
        System.out.println(getName(123));

    }

    public static <T> T getName(T t){
            return t;
    }
}
