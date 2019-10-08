package com.ais.brm.common.domain2;

import com.ais.brm.common.Constants;
import com.ais.brm.common.domain.notifs.GeneralKakfaNotif;

/**
 * 风险点的指标分析请求.接收者是risker模块.
 * Created by zhaocw on 2016/6/1.
 *
 * @author zhaocw
 */
public class RiskIndexAnalyzeNotif extends GeneralKakfaNotif {
    private long ruleId;//指标规则ID.

    public RiskIndexAnalyzeNotif() {
        super();
        setType(Constants.KAFKA_NOTIF_TYPE_RISKANALYZE);
    }

    public long getRuleId() {
        return ruleId;
    }

    public void setRuleId(long ruleId) {
        this.ruleId = ruleId;
    }

    @Override
    public String toString() {
        return "RiskIndexAnalyzeNotif{" +
                "ruleId=" + ruleId +
                '}';
    }
}
