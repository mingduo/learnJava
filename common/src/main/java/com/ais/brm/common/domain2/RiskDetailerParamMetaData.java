package com.ais.brm.common.domain2;

import java.util.Map;

/**
 * 风险点对应的可配置明细采集方法的参数信息,取自tbl_dm_sql_params表.
 * Created by zhaocw on 2016-7-18.
 * @author zhaocw
 */
public class RiskDetailerParamMetaData {
    private long detailMethodId;
    private String paramName;
    private String paramDesc;
    private String paramValue;
    private int paramIndex;
    private int paramType;
    private String paramDataType;

    public long getDetailMethodId() {
        return detailMethodId;
    }

    public void setDetailMethodId(long detailMethodId) {
        this.detailMethodId = detailMethodId;
    }

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public String getParamDesc() {
        return paramDesc;
    }

    public void setParamDesc(String paramDesc) {
        this.paramDesc = paramDesc;
    }

    public String getParamValue() {
        return paramValue;
    }

    public void setParamValue(String paramValue) {
        this.paramValue = paramValue;
    }

    public int getParamIndex() {
        return paramIndex;
    }

    public void setParamIndex(int paramIndex) {
        this.paramIndex = paramIndex;
    }

    public int getParamType() {
        return paramType;
    }

    public void setParamType(int paramType) {
        this.paramType = paramType;
    }

    public String getParamDataType() {
        return paramDataType;
    }

    public void setParamDataType(String paramDataType) {
        this.paramDataType = paramDataType;
    }

    @Override
    public String toString() {
        return "RiskDetailerParamMetaData{" +
                "detailMethodId=" + detailMethodId +
                ", paramName='" + paramName + '\'' +
                ", paramDesc='" + paramDesc + '\'' +
                ", paramValue='" + paramValue + '\'' +
                ", paramIndex=" + paramIndex +
                ", paramType=" + paramType +
                ", paramDataType='" + paramDataType + '\'' +
                '}';
    }
}
