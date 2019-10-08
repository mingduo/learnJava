package com.ais.brm.common.domain2;

/**
 * 风险结果的基类.
 * Created by zhaocw on 2016-7-21.
 *
 * @author zhaocw
 */
public class RiskResultBase {
    private long id;
    private long modelId;

    private int riskLevel;
    private int starCount;

    private String theMonth;

    private String riskObjectId;//2.0 added by zhaocw
    private int riskObjectTypeId;//2.0 added by zhaocw

    private String riskObjectName;// added by lulj

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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getModelId() {
        return modelId;
    }

    public void setModelId(long modelId) {
        this.modelId = modelId;
    }

    public int getRiskLevel() {
        return riskLevel;
    }

    public void setRiskLevel(int riskLevel) {
        this.riskLevel = riskLevel;
    }

    public int getStarCount() {
        return starCount;
    }

    public void setStarCount(int starCount) {
        this.starCount = starCount;
    }

    public String getTheMonth() {
        return theMonth;
    }

    public void setTheMonth(String theMonth) {
        this.theMonth = theMonth;
    }

    public String getRiskObjectName() {
        return riskObjectName;
    }

    public void setRiskObjectName(String riskObjectName) {
        this.riskObjectName = riskObjectName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RiskResultBase that = (RiskResultBase) o;

        return id == that.id;

    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public String toString() {
        return "RiskResultBase{" +
                "modelId=" + modelId +
                ", riskLevel=" + riskLevel +
                ", starCount=" + starCount +
                ", theMonth='" + theMonth + '\'' +
                '}';
    }
}
