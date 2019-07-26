package com.ais.brm.common.utils;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * <table border="1">
 * <tr><th>@Description: 用于字符转化等操作</th></tr>
 * <tr><td>@Date:Created in 2018-3-21</td>
 * </tr>
 * </table>
 *
 * @author :    weizc
 */
public class FormatUtis {
    static DecimalFormat df=new DecimalFormat("######0.00");
    // 0.2134325->0.21
    public static String toDecimal(double decimal)
    {
        String result=df.format(decimal);
        return result;
    }
    //0.2134325->21.34%
    public static String toPercent(double decimal)
    {
        //获取格式化对象
        NumberFormat nt = NumberFormat.getPercentInstance();
        //设置百分数精确度2即保留两位小数
        nt.setMinimumFractionDigits(2);
        return nt.format(decimal);
    }


}
