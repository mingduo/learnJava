package com.ais.brm.common.domain2;

import java.sql.Timestamp;

/**
 * 一个风险对象的指标结果
 *
 * @author by gengrc
 * @since 2018/3/7
 */
public class RiskIndexResult {
    private int riskIndexId;
    private String riskObjectId;
    private int riskObjectTypeId;
    private String riskIndexValue;
    private String lastIndexValue;
    private float avgValue;
    private float dailyAvgValue;
    private float midValue;
    private Timestamp collectTime;
    private Timestamp saveTime;
    private Timestamp lastUpdateTime;

    public int getRiskIndexId() {
        return riskIndexId;
    }

    public void setRiskIndexId(int riskIndexId) {
        this.riskIndexId = riskIndexId;
    }

    public String getRiskObjectId() {
        return riskObjectId;
    }

    public void setRiskObjectId(String riskObjectId) {
        this.riskObjectId = riskObjectId;
    }

    public int getRiskObjectTypeId() {
        return riskObjectTypeId;
    }

    public void setRiskObjectTypeId(int riskObjectTypeId) {
        this.riskObjectTypeId = riskObjectTypeId;
    }

    public String getRiskIndexValue() {
        return riskIndexValue;
    }

    public void setRiskIndexValue(String riskIndexValue) {
        this.riskIndexValue = riskIndexValue;
    }

    public float getAvgValue() {
        return avgValue;
    }

    public void setAvgValue(float avgValue) {
        this.avgValue = avgValue;
    }

    public float getDailyAvgValue() {
        return dailyAvgValue;
    }

    public void setDailyAvgValue(float dailyAvgValue) {
        this.dailyAvgValue = dailyAvgValue;
    }

    public float getMidValue() {
        return midValue;
    }

    public void setMidValue(float midValue) {
        this.midValue = midValue;
    }

    public String getLastIndexValue() {
        return lastIndexValue;
    }

    public void setLastIndexValue(String lastIndexValue) {
        this.lastIndexValue = lastIndexValue;
    }

    public Timestamp getCollectTime() {
        return collectTime;
    }

    public void setCollectTime(Timestamp collectTime) {
        this.collectTime = collectTime;
    }

    public Timestamp getSaveTime() {
        return saveTime;
    }

    public void setSaveTime(Timestamp saveTime) {
        this.saveTime = saveTime;
    }

    public Timestamp getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Timestamp lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }
}
