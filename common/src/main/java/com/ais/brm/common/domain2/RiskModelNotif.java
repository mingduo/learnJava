package com.ais.brm.common.domain2;

import com.ais.brm.common.Constants;
import com.ais.brm.common.domain.notifs.GeneralKakfaNotif;

/**
 * 风险点的模型请求.接收者是risker模块.
 * Created by zhaocw on 2016/6/1.
 * @author zhaocw
 */
public class RiskModelNotif extends GeneralKakfaNotif {
    private long modelId;//模型ID.

    public RiskModelNotif() {
        super();
        setType(Constants.KAFKA_NOTIF_TYPE_RISKMODEL);
    }

    public long getModelId() {
        return modelId;
    }

    public void setModelId(long modelId) {
        this.modelId = modelId;
    }


    @Override
    public String toString() {
        return "RiskModelNotif{" +
                "modelId=" + modelId +
                ", notifId=" + getNotifId()+
                '}';
    }
}
