package com.ais.brm.common.domain2;

import com.ais.brm.common.Constants;
import com.ais.brm.common.domain.Result;
import com.ais.brm.common.domain.notifs.GeneralKakfaNotif;

/**
 * 风险点的数据计算请求.接收者是risker模块.
 * Created by zhaocw on 2016/6/1.
 *
 * @author zhaocw
 */
public class RiskComputeDoneNotif extends GeneralKakfaNotif {
    private long modelId;//模型ID.
    private long riskId;//风险点.
    private Result result;
    private String modelNotifId;

    public RiskComputeDoneNotif() {
        setType(Constants.KAFKA_NOTIF_TYPE_RISKCOMPUTEDONE);
    }

    public long getModelId() {
        return modelId;
    }

    public void setModelId(long modelId) {
        this.modelId = modelId;
    }

    public long getRiskId() {
        return riskId;
    }

    public void setRiskId(long riskId) {
        this.riskId = riskId;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public Result getResult() {
        return result;
    }

    @Override
    public String toString() {
        return "RiskComputeDoneNotif{" +
                "modelId=" + modelId +
                ", riskId=" + riskId +
                ", result=" + result +
                '}';
    }

    public void setModelNotifId(String modelNotifId) {
        this.modelNotifId = modelNotifId;
    }

    public String getModelNotifId() {
        return modelNotifId;
    }
}
