package com.ais.brm.common.domain2;

import com.ais.brm.common.utils.StringUtils;

import java.util.Map;

/**
 * 风险点对应的采集方法描述信息.
 * Created by zhaocw on 2016-7-18.
 *
 * @author zhaocw
 */
public class RiskCollectorMetaData {
    private long id;
    private long riskId;
    private String className;
    private Map<String, String> paramValues;
    private String description;
    private String theSql;
    private int riskObjectTypeId;
    private int riskCategoryId;
    private String datasourceTypeId;

    public String getDatasourceTypeId() {
        return datasourceTypeId;
    }

    public void setDatasourceTypeId(String datasourceTypeId) {
        this.datasourceTypeId = datasourceTypeId;
    }

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

    public String getTheSql() {
        return theSql;
    }

    public void setTheSql(String theSql) {
        this.theSql = theSql;
    }

    public int getRiskObjectTypeId() {
        return riskObjectTypeId;
    }

    public void setRiskObjectTypeId(int riskObjectTypeId) {
        this.riskObjectTypeId = riskObjectTypeId;
    }

    public int getRiskCategoryId() {
        return riskCategoryId;
    }

    public void setRiskCategoryId(int riskCategoryId) {
        this.riskCategoryId = riskCategoryId;
    }

    /**
     * .
     *
     * @return
     */
    @Override
    public String toString() {
        return "RiskCollectorMetaData{" +
                "id=" + id +
                ", riskId=" + riskId +
                ", className='" + className + '\'' +
                ", paramValues=" + paramValues +
                ", description='" + description + '\'' +
                ", theSql='" + theSql + '\'' +
                ", riskObjectTypeId=" + riskObjectTypeId +
                ", riskCategoryId=" + riskCategoryId +
                '}';
    }
}
