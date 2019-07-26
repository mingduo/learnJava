package com.ais.brm.common.domain2.detail;

import com.ais.brm.common.Constants;
import com.ais.brm.common.domain.notifs.GeneralKakfaNotif;

/**
 * 离线明细采集请求，接收者risker
 *
 * @author by gengrc
 * @since 2018/4/10
 */
public class OfflineDetailNotif extends GeneralKakfaNotif {
    private long requestId;//指标ID.

    public OfflineDetailNotif() {
        super();
        setType(Constants.KAFKA_NOTIF_TYPE_OFFLINE_DETAIL);
    }


    public long getRequestId() {
        return requestId;
    }

    public void setRequestId(long requestId) {
        this.requestId = requestId;
    }

    @Override
    public String toString() {
        return "OfflineDetailNotif{" +
                "requestId=" + requestId +
                '}';
    }
}
