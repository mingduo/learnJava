package com.ais.brm.common.domain2;

import com.ais.brm.common.domain.notifs.GeneralKakfaNotif;

/**
 * 风险点的数据计算请求.接收者是risker模块.
 * Created by zhaocw on 2016/6/1.
 *
 * @author zhaocw
 */
public class MiguETLRequestNotif extends GeneralKakfaNotif {
    private String targetTable;
    private String targetField;
    private String theDay;
    private String theHour;

    public String getTargetTable() {
        return targetTable;
    }

    public void setTargetTable(String targetTable) {
        this.targetTable = targetTable;
    }

    public String getTargetField() {
        return targetField;
    }

    public void setTargetField(String targetField) {
        this.targetField = targetField;
    }

    public String getTheDay() {
        return theDay;
    }

    public void setTheDay(String theDay) {
        this.theDay = theDay;
    }

    public String getTheHour() {
        return theHour;
    }

    public void setTheHour(String theHour) {
        this.theHour = theHour;
    }
}
