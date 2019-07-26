package com.ais.brm.common.domain2.index;

import com.ais.brm.common.Constants;
import com.ais.brm.common.domain.notifs.GeneralKakfaNotif;

/**
 * 指标补录notif
 *
 * @author by gengrc
 * @since 2018/4/16
 */
public class RiskReIndexNewReqNotif extends GeneralKakfaNotif {
    private long requestId;
	public RiskReIndexNewReqNotif() {
        super();
        setType(Constants.KAFKA_NOTIF_TYPE_RISKREINDEX);
    }

    public long getRequestId() {
        return requestId;
    }

    public void setRequestId(long requestId) {
        this.requestId = requestId;
    }

    @Override
    public String toString() {
        return "RiskReIndexNewReqNotif{" +
                "requestId=" + requestId +
                '}';
    }
}
