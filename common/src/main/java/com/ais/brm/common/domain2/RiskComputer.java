package com.ais.brm.common.domain2;

/**
 * 风险点计算器.
 * Created by zhaocw on 2016-7-18.
 *
 * @author zhaocw
 */
public interface RiskComputer {
    /**
     * 实际的风险点计算实现.
     *
     * @param notif
     * @param riskComputerMetaData
     */
    void doCompute(RiskComputeNotif notif, RiskComputerMetaData riskComputerMetaData);
}
