package com.ais.brm.common.domain2;

/**
 * 用于配置型风险点的THE_SQL字段解析结果中的参数保存.
 * Created by zhaocw on 2017-1-9.
 *
 * @author zhaocw
 */
public class RiskCollectorSqlParam {
    private String queryMethodName;//m/p10/p20
    private String queryMethodDescription;
    private boolean isValueRequired;//值是否必填.
    private String paramName;
    private String paramDescription;
    private String paramValueType;//i,l,s
    private String paramValue;

    @Override
    public String toString() {
        return "RiskCollectorSqlParam{" +
                "queryMethodName='" + queryMethodName + '\'' +
                ", queryMethodDescription='" + queryMethodDescription + '\'' +
                ", isValueRequired=" + isValueRequired +
                ", paramName='" + paramName + '\'' +
                ", paramDescription='" + paramDescription + '\'' +
                ", paramValueType='" + paramValueType + '\'' +
                ", paramValue='" + paramValue + '\'' +
                '}';
    }

    public String getQueryMethodName() {
        return queryMethodName;
    }

    public void setQueryMethodName(String queryMethodName) {
        this.queryMethodName = queryMethodName;
    }

    public String getQueryMethodDescription() {
        return queryMethodDescription;
    }

    public void setQueryMethodDescription(String queryMethodDescription) {
        this.queryMethodDescription = queryMethodDescription;
    }

    public boolean isValueRequired() {
        return isValueRequired;
    }

    public void setValueRequired(boolean valueRequired) {
        isValueRequired = valueRequired;
    }

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public String getParamDescription() {
        return paramDescription;
    }

    public void setParamDescription(String paramDescription) {
        this.paramDescription = paramDescription;
    }

    public String getParamValueType() {
        return paramValueType;
    }

    public void setParamValueType(String paramValueType) {
        this.paramValueType = paramValueType;
    }

    public String getParamValue() {
        return paramValue;
    }

    public void setParamValue(String paramValue) {
        this.paramValue = paramValue;
    }
}
