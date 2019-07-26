package com.ais.brm.common.domain2;

import com.ais.brm.common.domain.notifs.GeneralKakfaNotif;

/**
 * 咪咕ML的请求.
 * Created by zhaocw on 2017/6/2.
 * @author zhaocw
 */
public class MiguMLOfRequestNotif extends GeneralKakfaNotif {
    private String module;
    private String theDay;
    private String theHour;
    private long modelId;
    private long riskId;
    private String modelNotifId;

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

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public void setModelId(long modelId) {
        this.modelId = modelId;
    }

    public long getModelId() {
        return modelId;
    }

    public void setRiskId(long riskId) {
        this.riskId = riskId;
    }

    public long getRiskId() {
        return riskId;
    }

    public void setModelNotifId(String modelNotifId) {
        this.modelNotifId = modelNotifId;
    }

    public String getModelNotifId() {
        return modelNotifId;
    }
}

