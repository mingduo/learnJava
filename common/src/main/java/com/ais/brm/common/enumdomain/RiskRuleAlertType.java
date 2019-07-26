package com.ais.brm.common.enumdomain;


import java.util.Arrays;
import java.util.List;

/**
 * <table border="1">
 * <tr><th>@Description:</th></tr>
 * <tr><td>@Date:Created in 2018-3-13</td>
 * </tr>
 * </table>
 *
 * @author :    weizc
 */
public enum RiskRuleAlertType {

    DEFALUT_ALERT(10, "内部告警"),
    EMAIL_ALERT(11, "Email告警"),
    MSG_ALERT(12, "短信告警");

    private final int typeId;
    private final String typeName;

    RiskRuleAlertType(int typeId, String typeName) {
        this.typeId = typeId;
        this.typeName = typeName;
    }

    public int getTypeId() {
        return typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public static List<RiskRuleAlertType> getAllTypes() {
        return Arrays.asList(DEFALUT_ALERT, EMAIL_ALERT, MSG_ALERT);
    }

    public static RiskRuleAlertType getType(int index) {
        for (RiskRuleAlertType c : RiskRuleAlertType.values()) {
            if (c.getTypeId() == index) {
                return c;
            }
        }
        return null;
    }
}
