package com.ais.brm.common.domain2;

/**
 * 标识一个风险点的采集器.
 * Created by zhaocw on 2016-7-18.
 * @author zhaocw
 */
public interface RiskCollector {
    /**
     * 实际的采集实现方法.
     * @param notif
     * @param riskCollectorMetaData 相关采集配置信息.
     */
    void doCollect(RiskCollectNotif notif, RiskCollectorMetaData riskCollectorMetaData);
}
