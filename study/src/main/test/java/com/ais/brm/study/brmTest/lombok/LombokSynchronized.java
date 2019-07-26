package com.ais.brm.study.brmTest.lombok;

import lombok.SneakyThrows;
import lombok.Synchronized;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <table border="1">
 * <tr><th>@Description: https://projectlombok.org/features/all</th></tr>
 * <tr><td>@Date:Created in 2018-9-30</td>
 * </tr>
 * </table>
 *
 * @author :    weizc
 */
public class LombokSynchronized {
    private DateFormat format = new SimpleDateFormat("MM-dd-YYYY");


    /**
     * private final java.lang.Object $lock = new java.lang.Object[0];
     * private DateFormat format = new SimpleDateFormat("MM-dd-YYYY");
     * <p>
     * public String synchronizedFormat(Date date) {
     * synchronized ($lock) {
     * return format.format(date);
     * }
     * }
     *
     * @param date
     * @return
     */
    @Synchronized
    public String synchronizedFormat(Date date) {
        return format.format(date);
    }

    @SneakyThrows
    public void testSneakyThrows() {
        throw new IllegalAccessException();
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
    }
}
