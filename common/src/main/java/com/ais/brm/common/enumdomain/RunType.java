package com.ais.brm.common.enumdomain;

/**
 * <table border="1">
 * <tr><th>@Description:</th></tr>
 * <tr><td>@Date:Created in 2018-4-23</td>
 * </tr>
 * </table>
 *
 * @author :    weizc
 */
public enum RunType {
    byDay(10), byHour(11), byStream(12);


    private int value = 0;

    private RunType(int value) {
        this.value = value;
    }

    public int value() {
        return this.value;
    }


}
