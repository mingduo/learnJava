package com.ais.brm.common.domain2;

/**
 * @author zhaoyq3
 * @since 20161118
 */
public class AlarmResult {
    long id;
    long noticeId;
    long modelId;
    int oneLevelCount;
    int oneLevelAlarm;
    int twoLevelCount;
    int twoLevelAlarm;
    int threeLevelCount;
    int threeLevelAlarm;
    int fourLevelCount;
    int fourLevelAlarm;
    int alarmLevel;
    int alarmSource;
    String modelName;

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getNoticeId() {
        return noticeId;
    }

    public void setNoticeId(long noticeId) {
        this.noticeId = noticeId;
    }

    public long getModelId() {
        return modelId;
    }

    public void setModelId(long modelId) {
        this.modelId = modelId;
    }

    public int getOneLevelCount() {
        return oneLevelCount;
    }

    public void setOneLevelCount(int oneLevelCount) {
        this.oneLevelCount = oneLevelCount;
    }

    public int getOneLevelAlarm() {
        return oneLevelAlarm;
    }

    public void setOneLevelAlarm(int oneLevelAlarm) {
        this.oneLevelAlarm = oneLevelAlarm;
    }

    public int getTwoLevelCount() {
        return twoLevelCount;
    }

    public void setTwoLevelCount(int twoLevelCount) {
        this.twoLevelCount = twoLevelCount;
    }

    public int getTwoLevelAlarm() {
        return twoLevelAlarm;
    }

    public void setTwoLevelAlarm(int twoLevelAlarm) {
        this.twoLevelAlarm = twoLevelAlarm;
    }

    public int getThreeLevelCount() {
        return threeLevelCount;
    }

    public void setThreeLevelCount(int threeLevelCount) {
        this.threeLevelCount = threeLevelCount;
    }

    public int getThreeLevelAlarm() {
        return threeLevelAlarm;
    }

    public void setThreeLevelAlarm(int threeLevelAlarm) {
        this.threeLevelAlarm = threeLevelAlarm;
    }

    public int getFourLevelCount() {
        return fourLevelCount;
    }

    public void setFourLevelCount(int fourLevelCount) {
        this.fourLevelCount = fourLevelCount;
    }

    public int getFourLevelAlarm() {
        return fourLevelAlarm;
    }

    public void setFourLevelAlarm(int fourLevelAlarm) {
        this.fourLevelAlarm = fourLevelAlarm;
    }

    public int getAlarmLevel() {
        return alarmLevel;
    }

    public void setAlarmLevel(int alarmLevel) {
        this.alarmLevel = alarmLevel;
    }

    public int getAlarmSource() {
        return alarmSource;
    }

    public void setAlarmSource(int alarmSource) {
        this.alarmSource = alarmSource;
    }


}
