package com.ais.brm.common.domain2.monitor;

import java.util.Date;

/**
 * 系统监控结果.
 * Created by zhaocaiwen on 2018/1/4.
 *
 * @author zhaocaiwen
 */
public class MonitorResult {
    private int ruleId;
    private Date theTime;
    private String theValue;
    private String notifId;
    //add by xu.chenyang
    private Integer targetId;

    public int getRuleId() {
        return ruleId;
    }

    public void setRuleId(int ruleId) {
        this.ruleId = ruleId;
    }

    public Date getTheTime() {
        return theTime;
    }

    public void setTheTime(Date theTime) {
        this.theTime = theTime;
    }

    public String getTheValue() {
        return theValue;
    }

    public void setTheValue(String theValue) {
        this.theValue = theValue;
    }

    public String getNotifId() {
        return notifId;
    }

    public void setNotifId(String notifId) {
        this.notifId = notifId;
    }

    public Integer getTargetId() {
        return targetId;
    }

    public void setTargetId(Integer targetId) {
        this.targetId = targetId;
    }
}
