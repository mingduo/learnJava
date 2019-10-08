package com.ais.brm.common.domain2;

/**
 * @author zhaocw
 */
public class RiskResultStars {
    private long id;
    private int modelTypeId;
    private long riskResultId;
    private long riskId;
    private int starCount;
    private String theDay;
    private String theHour;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getModelTypeId() {
        return modelTypeId;
    }

    public void setModelTypeId(int modelTypeId) {
        this.modelTypeId = modelTypeId;
    }

    public long getRiskResultId() {
        return riskResultId;
    }

    public void setRiskResultId(long riskResultId) {
        this.riskResultId = riskResultId;
    }

    public long getRiskId() {
        return riskId;
    }

    public void setRiskId(long riskId) {
        this.riskId = riskId;
    }

    public int getStarCount() {
        return starCount;
    }

    public void setStarCount(int starCount) {
        this.starCount = starCount;
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
