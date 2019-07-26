package com.ais.brm.common.domain.notifs;

import com.ais.brm.common.Constants;
import com.ais.brm.common.domain.notifs.GeneralKakfaNotif;

/**
 * 同步数据通知.接收者是sync模块.
 * Created by xuechen on 2016/11/21.
 * @author xuechen
 */
public class SyncDataNotice extends GeneralKakfaNotif {
    private String modelId;//模型ID.

    public SyncDataNotice() {
        super();
        setType(Constants.KAFKA_NOTIF_TYPE_SYNCDATA);
    }

    public String getModelId() {
		return modelId;
	}

	public void setModelId(String modelId) {
		this.modelId = modelId;
	}



	@Override
    public String toString() {
        return "SyncDataNotice{" +
                "modelId=" + modelId +
                ", notifId=" + getNotifId()+
                '}';
    }
}
