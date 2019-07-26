package com.ais.brm.common.domain2;

import com.ais.brm.common.Constants;
import com.ais.brm.common.domain.notifs.GeneralKakfaNotif;

/**
 * 风险点的数据采集请求.接收者是risker模块.
 * Created by zhaocw on 2016/6/1.
 * @author zhaocw
 */
public class RiskCollectNotif extends GeneralKakfaNotif {
    private long modelId;//模型ID.
    private long riskId;//风险点.
    private String modelNotifId;

    public RiskCollectNotif() {
        setType(Constants.KAFKA_NOTIF_TYPE_RISKCOLLECT);
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

    @Override
    public String toString() {
        return "RiskCollectNotif{" +
                "modelId=" + modelId +
                ", riskId=" + riskId +
                ", modelNotifId='" + modelNotifId + '\'' +
                '}';
    }

    public void setModelNotifId(String modelNotifId) {
        this.modelNotifId = modelNotifId;
    }

    public String getModelNotifId() {
        return modelNotifId;
    }
}
