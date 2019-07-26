package com.ais.brm.common.domain2.ruleegine;

import com.google.gson.annotations.SerializedName;

import java.sql.Timestamp;

/**
 * @author  weizc
 */
public class RiskIndexResultHis extends FactDef implements  Cloneable{
    @SerializedName("risk_index_id")
    private int riskIndexId;
    private int riskObjectTypeId;
    private String riskObjectId;
    
    private String riskIndexValue;

    @SerializedName("collect_time")
    private Timestamp collectTime;
    private Timestamp saveTime;

    private  String conditionValue;

    public int getRiskIndexId() {
        return riskIndexId;
    }

    public void setRiskIndexId(int riskIndexId) {
        this.riskIndexId = riskIndexId;
    }

    public int getRiskObjectTypeId() {
        return riskObjectTypeId;
    }

    public void setRiskObjectTypeId(int riskObjectTypeId) {
        this.riskObjectTypeId = riskObjectTypeId;
    }

    public String getRiskObjectId() {
        return riskObjectId;
    }

    public void setRiskObjectId(String riskObjectId) {
        this.riskObjectId = riskObjectId;
    }

    public String getRiskIndexValue() {
        return riskIndexValue;
    }

    public void setRiskIndexValue(String riskIndexValue) {
        this.riskIndexValue = riskIndexValue;
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

    public String getConditionValue() {
        return conditionValue;
    }

    public void setConditionValue(String conditionValue) {
        this.conditionValue = conditionValue;
    }

    @Override
    public RiskIndexResultHis clone() {
        try {
            return (RiskIndexResultHis)super.clone();
        } catch (CloneNotSupportedException e) {
            // TODO Auto-generated catch block
            return null;// clone failed
        }
    }

    @Override
    public String toString() {
        return "RiskIndexResultHis{" +
                "riskIndexId=" + riskIndexId +
                ", riskObjectTypeId=" + riskObjectTypeId +
                ", riskObjectId='" + riskObjectId + '\'' +
                ", riskIndexValue='" + riskIndexValue + '\'' +
                ", collectTime=" + collectTime +
                ", saveTime=" + saveTime +
                ", conditionValue='" + conditionValue + '\'' +
                '}';
    }
}
