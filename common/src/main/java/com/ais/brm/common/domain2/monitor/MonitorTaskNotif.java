package com.ais.brm.common.domain2.monitor;

import com.ais.brm.common.Constants;
import com.ais.brm.common.domain.notifs.GeneralKakfaNotif;

/**
 * 代表一个监控任务
 * Created by zhaocaiwen on 2017/11/21.
 *
 * @author zhaocaiwen
 */
public class MonitorTaskNotif extends GeneralKakfaNotif {
    private int ruleId;//监控规则id

    public MonitorTaskNotif() {
        setType(Constants.KAFKA_NOTIF_TYPE_MONITOR);
    }

    public int getRuleId() {
        return ruleId;
    }

    public void setRuleId(int ruleId) {
        this.ruleId = ruleId;
    }
}
