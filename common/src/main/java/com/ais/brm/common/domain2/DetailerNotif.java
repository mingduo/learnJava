package com.ais.brm.common.domain2;

import com.ais.brm.common.domain.notifs.GeneralKakfaNotif;

/**
 * @author yjc
 */
public class DetailerNotif extends GeneralKakfaNotif {
    //明细采集请求id
    private Integer requestId;

    public Integer getRequestId() {
        return requestId;
    }

    public void setRequestId(Integer requestId) {
        this.requestId = requestId;
    }

    @Override
    public String toString() {
        return "DetailerNotif{requestId:" + requestId + "}";
    }

}
