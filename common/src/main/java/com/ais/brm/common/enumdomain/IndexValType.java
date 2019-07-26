package com.ais.brm.common.enumdomain;


import java.util.Arrays;
import java.util.List;

/**
 * <table border="1">
 * <tr><th>@Description:指标值的类型（数值型10（缺省类型）、
 * 枚举型11、字符串12</th></tr>
 * <tr><td>@Date:Created in 2018-3-16</td>
 * </tr>
 * </table>
 *
 * @author :    weizc
 */
public enum IndexValType {

    NUM_TYPE(10, "数值型"),
    ENUM_TYPE(11, "枚举型"),
    STR_TYPE(12, "字符串");

    private final int typeId;
    private final String typeName;

    IndexValType(int typeId, String typeName) {
        this.typeId = typeId;
        this.typeName = typeName;
    }

    public int getTypeId() {
        return typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public static List<IndexValType> getAllTypes() {
        return Arrays.asList(NUM_TYPE, ENUM_TYPE, STR_TYPE);
    }

    public static IndexValType getType(int index) {
        for (IndexValType c : IndexValType.values()) {
            if (c.getTypeId() == index) {
                return c;
            }
        }
        return null;
    }
}
