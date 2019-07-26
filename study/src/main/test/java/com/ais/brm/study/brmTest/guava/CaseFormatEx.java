package com.ais.brm.study.brmTest.guava;

import com.google.common.base.CaseFormat;
import org.junit.Test;

/**
 * <table border="1">
 * <tr><th>@Description:</th></tr>
 * <tr><td>@Date:Created in 2018-10-10</td>
 * </tr>
 * </table>
 *https://github.com/google/guava/wiki/StringsExplained
 * @author :    weizc
 */
public class CaseFormatEx {
    @Test
    public void test(){
        String constant_name = CaseFormat.UPPER_UNDERSCORE
                .to(CaseFormat.LOWER_CAMEL,
                "CONSTANT_NAME");
        System.out.println(constant_name);
    }
}
