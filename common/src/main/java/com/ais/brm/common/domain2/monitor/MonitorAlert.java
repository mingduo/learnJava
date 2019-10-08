package com.ais.brm.common.domain2.monitor;

/**
 * Created by zhaocaiwen on 2018/1/4.
 *
 * @author zhaocaiwen
 */
public class MonitorAlert {
    private int ruleId;
    private String threshold;//阀值定义
    private String alertContent;
    private String notifyId;
    private int alertLevel;
    private int isRead;
    //add by xu.chenyang
    private Integer targetId;
    //add by  zhangyy
    private Long theTime;


    public Long getTheTime() {
        return theTime;
    }

    public void setTheTime(Long theTime) {
        this.theTime = theTime;
    }

    public int getAlertLevel() {
        return alertLevel;
    }

    public void setAlertLevel(int alertLevel) {
        this.alertLevel = alertLevel;
    }


    public int getIsRead() {
        return isRead;
    }

    public void setIsRead(int isRead) {
        this.isRead = isRead;
    }

    public String getNotifyId() {
        return notifyId;
    }

    public void setNotifyId(String notifyId) {
        this.notifyId = notifyId;
    }

    public int getRuleId() {
        return ruleId;
    }

    public void setRuleId(int ruleId) {
        this.ruleId = ruleId;
    }

    public String getThreshold() {
        return threshold;
    }

    public void setThreshold(String threshold) {
        this.threshold = threshold;
    }

    public String getAlertContent() {
        return alertContent;
    }

    public void setAlertContent(String alertContent) {
        this.alertContent = alertContent;
    }

    public Integer getTargetId() {
        return targetId;
    }

    public void setTargetId(Integer targetId) {
        this.targetId = targetId;
    }
}
