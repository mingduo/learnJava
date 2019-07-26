package com.ais.brm.common.enumdomain;

import java.util.Arrays;
import java.util.List;

/**
 * 系统监控类型.
 * Created by zhaocaiwen on 2018/1/4.
 * @author zhaocaiwen
 */
public enum RiskIndexSingleMonRuleType {
    BLACK_LIST(101,"黑名单"),
    WHITE_LIST(102,"白名单"),
    CHANGE(103,"变化"),
    HIGH(104,"高于阀值"),
    LOW(105,"低于阀值"),
    BETWEEN(106,"介于值范围"),
    OUT_BETWEEN(107,"不介于值范围"),
    FAST_CHANGE(108,"突变"),
    BURST(109,"突现"),
    MISFIT(110,"不符合均值");
    
    private final int typeId;
    private final String typeName;

    RiskIndexSingleMonRuleType(int typeId,String description) {
        this.typeId = typeId;
        this.typeName = description;
    }

    public int getTypeId() {
        return typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public static List<RiskIndexSingleMonRuleType> getAllTypes() {
        return Arrays.asList(BLACK_LIST,WHITE_LIST,CHANGE,HIGH,LOW,BETWEEN,OUT_BETWEEN,FAST_CHANGE,BURST,
        		MISFIT);
    }

    public static RiskIndexSingleMonRuleType getMonitorType(int index) {
        for (RiskIndexSingleMonRuleType c : RiskIndexSingleMonRuleType.values()) {
            if (c.getTypeId() == index) {
                return c;
            }
        }
        return null;
    }
}
