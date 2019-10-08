package com.ais.brm.common.domain2;

import com.ais.brm.common.Constants;
import com.ais.brm.common.domain.notifs.GeneralKakfaNotif;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

/**
 * 风险点的数据计算请求.接收者是risker模块.
 * Created by zhaocw on 2016/6/1.
 *
 * @author zhaocw
 */
public class RiskComputeNotif extends GeneralKakfaNotif {
    private long modelId;//模型ID.
    private long riskId;//风险点.
    private String modelNotifId;

    public RiskComputeNotif() {
        setType(Constants.KAFKA_NOTIF_TYPE_RISKCOMPUTE);
    }

    public long getModelId() {
        return modelId;
    }

    public void setModelId(long modelId) {
        this.modelId = modelId;
    }

    public long getRiskId() {
        return riskId;
    }

    public void setRiskId(long riskId) {
        this.riskId = riskId;
    }

    @Override
    public String toString() {
        return "RiskComputeNotif{" +
                "modelId=" + modelId +
                ", riskId=" + riskId +
                '}';
    }

    public void setModelNotifId(String modelNotifId) {
        this.modelNotifId = modelNotifId;
    }

    public String getModelNotifId() {
        return modelNotifId;
    }

    public static void main(String[] args) {
        Gson gson = new Gson();
        RiskComputeNotif riskComputeNotif = new RiskComputeNotif();
        riskComputeNotif.setModelId(23928392L);
        riskComputeNotif.setRiskId(23232);
        riskComputeNotif.setModelNotifId("modelNotifId1212");
        Map<String, String> props = new HashMap<>();
        props.put("the_day", "20170608");
        props.put("test", "12323");
        riskComputeNotif.setProps(props);
        System.out.println(gson.toJson(riskComputeNotif));
    }
}
