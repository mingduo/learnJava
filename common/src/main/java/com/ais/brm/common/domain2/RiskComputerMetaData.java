package com.ais.brm.common.domain2;

import com.ais.brm.common.utils.StringUtils;

import java.util.Map;

/**
 * 风险点对应的计算方法描述信息.
 * Created by zhaocw on 2016-7-18.
 *
 * @author zhaocw
 */
public class RiskComputerMetaData {
    private long id;
    private long riskId;
    private String className;
    private Map<String, String> paramValues;
    private String description;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getRiskId() {
        return riskId;
    }

    public void setRiskId(long riskId) {
        this.riskId = riskId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Map<String, String> getParamValues() {
        return paramValues;
    }

    public void setParamValues(Map<String, String> paramValues) {
        this.paramValues = paramValues;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * .
     *
     * @return
     */
    @Override
    public String toString() {
        return String.format("RiskComputerMetaData:类名%s,参数：%s",
                className, StringUtils.convertMap2String(paramValues));
    }
}
