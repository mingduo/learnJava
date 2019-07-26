package com.ais.brm.common.domain2;

/**
 * 代表一个指标.
 * Created by zhaocaiwen on 2018/3/1.
 * @author zhaocw
 */
public class RiskIndex {

    private long riskIndexId;
    private int riskObjectTypeId;
    private String sqlContent;
    private String sqlParams;
    private String sourceDatasource;

    public long getRiskIndexId() {
        return riskIndexId;
    }

    public void setRiskIndexId(long riskIndexId) {
        this.riskIndexId = riskIndexId;
    }

    public int getRiskObjectTypeId() {
        return riskObjectTypeId;
    }

    public void setRiskObjectTypeId(int riskObjectTypeId) {
        this.riskObjectTypeId = riskObjectTypeId;
    }

    public String getSqlContent() {
        return sqlContent;
    }

    public void setSqlContent(String sqlContent) {
        this.sqlContent = sqlContent;
    }

    public String getSqlParams() {
        return sqlParams;
    }

    public void setSqlParams(String sqlParams) {
        this.sqlParams = sqlParams;
    }

    public String getSourceDatasource() {
        return sourceDatasource;
    }

    public void setSourceDatasource(String sourceDatasource) {
        this.sourceDatasource = sourceDatasource;
    }
}
