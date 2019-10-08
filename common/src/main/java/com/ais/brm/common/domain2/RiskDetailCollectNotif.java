package com.ais.brm.common.domain2;

import com.ais.brm.common.Constants;
import com.ais.brm.common.domain.notifs.GeneralKakfaNotif;

/**
 * 风险点的明细数据采集请求.接收者是risker的GeneralDetailer模块.
 * Created by zhaocw on 2017/4/1.
 *
 * @author zhaocw
 */
public class RiskDetailCollectNotif extends GeneralKakfaNotif {
    private String detailRequestId;

    public RiskDetailCollectNotif() {
        setType(Constants.KAFKA_NOTIF_TYPE_RISKDETAILCOLLECT);
    }

    public String getDetailRequestId() {
        return detailRequestId;
    }

    public void setDetailRequestId(String detailRequestId) {
        this.detailRequestId = detailRequestId;
    }
}
