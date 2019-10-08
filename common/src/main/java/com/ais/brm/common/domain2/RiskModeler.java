package com.ais.brm.common.domain2;

/**
 * 模型器.
 * Created by zhaocw on 2016-7-18.
 *
 * @author zhaocw
 */
public interface RiskModeler {
    /**
     * 实际的模型实现.
     *
     * @param notif
     */
    void doModel(RiskModelNotif notif);

    /**
     * 当模型某个风险点计算完成，会发此消息出来.
     *
     * @param riskComputeDoneNotif
     */
    void modelRiskComputeDone(RiskComputeDoneNotif riskComputeDoneNotif);
}
