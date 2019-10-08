package com.ais.brm.common.domain2;

import com.ais.brm.common.Constants;
import com.ais.brm.common.domain.notifs.GeneralKakfaNotif;

/**
 * 风险点的指标采集请求.接收者是risker模块.
 * Created by zhaocw on 2016/6/1.
 *
 * @author zhaocw
 */
public class RiskIndexNotif extends GeneralKakfaNotif {
    private long indexId;//指标ID.

    public RiskIndexNotif() {
        super();
        setType(Constants.KAFKA_NOTIF_TYPE_RISKINDEX);
    }

    public long getIndexId() {
        return indexId;
    }

    public void setIndexId(long indexId) {
        this.indexId = indexId;
    }

    @Override
    public String toString() {
        return "RiskIndexNotif{" +
                "indexId=" + indexId +
                ", notifId=" + getNotifId() +
                '}';
    }
}
