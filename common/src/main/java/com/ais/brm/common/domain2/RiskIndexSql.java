package com.ais.brm.common.domain2;


/**
 * 风险指标配置sql
 *
 * @author wangyong10
 */
public class RiskIndexSql {

    private String sqlContent;
    private long id;
    private String getMethodType1;
    private String getMethodType2;
    private String getMethodType3;
    private String getMethodType4;
    private String getMethodType5;

    private String description1;
    private String description2;
    private String description3;
    private String description4;
    private String description5;

    private String eGName1;
    private String eGName2;
    private String eGName3;
    private String eGName4;
    private String eGName5;

    private String paramType1;
    private String paramType2;
    private String paramType3;
    private String paramType4;
    private String paramType5;

    private String paramValue1;
    private String paramValue2;
    private String paramValue3;
    private String paramValue4;
    private String paramValue5;

    private String riskIndexSqlDos;

    private String database;

    private String sqlAndParams;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSqlAndParams() {
        return sqlAndParams;
    }

    public void setSqlAndParams(String sqlAndParams) {
        this.sqlAndParams = sqlAndParams;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getSqlContent() {
        return sqlContent;
    }

    public void setSqlContent(String sqlContent) {
        this.sqlContent = sqlContent;
    }

    public String getGetMethodType1() {
        return getMethodType1;
    }

    public void setGetMethodType1(String getMethodType1) {
        this.getMethodType1 = getMethodType1;
    }

    public String getGetMethodType2() {
        return getMethodType2;
    }

    public void setGetMethodType2(String getMethodType2) {
        this.getMethodType2 = getMethodType2;
    }

    public String getGetMethodType3() {
        return getMethodType3;
    }

    public void setGetMethodType3(String getMethodType3) {
        this.getMethodType3 = getMethodType3;
    }

    public String getGetMethodType4() {
        return getMethodType4;
    }

    public void setGetMethodType4(String getMethodType4) {
        this.getMethodType4 = getMethodType4;
    }

    public String getGetMethodType5() {
        return getMethodType5;
    }

    public void setGetMethodType5(String getMethodType5) {
        this.getMethodType5 = getMethodType5;
    }

    public String getDescription1() {
        return description1;
    }

    public void setDescription1(String description1) {
        this.description1 = description1;
    }

    public String getDescription2() {
        return description2;
    }

    public void setDescription2(String description2) {
        this.description2 = description2;
    }

    public String getDescription3() {
        return description3;
    }

    public void setDescription3(String description3) {
        this.description3 = description3;
    }

    public String getDescription4() {
        return description4;
    }

    public void setDescription4(String description4) {
        this.description4 = description4;
    }

    public String getDescription5() {
        return description5;
    }

    public void setDescription5(String description5) {
        this.description5 = description5;
    }

    public String geteGName1() {
        return eGName1;
    }

    public void seteGName1(String eGName1) {
        this.eGName1 = eGName1;
    }

    public String geteGName2() {
        return eGName2;
    }

    public void seteGName2(String eGName2) {
        this.eGName2 = eGName2;
    }

    public String geteGName3() {
        return eGName3;
    }

    public void seteGName3(String eGName3) {
        this.eGName3 = eGName3;
    }

    public String geteGName4() {
        return eGName4;
    }

    public void seteGName4(String eGName4) {
        this.eGName4 = eGName4;
    }

    public String geteGName5() {
        return eGName5;
    }

    public void seteGName5(String eGName5) {
        this.eGName5 = eGName5;
    }

    public String getParamType1() {
        return paramType1;
    }

    public void setParamType1(String paramType1) {
        this.paramType1 = paramType1;
    }

    public String getParamType2() {
        return paramType2;
    }

    public void setParamType2(String paramType2) {
        this.paramType2 = paramType2;
    }

    public String getParamType3() {
        return paramType3;
    }

    public void setParamType3(String paramType3) {
        this.paramType3 = paramType3;
    }

    public String getParamType4() {
        return paramType4;
    }

    public void setParamType4(String paramType4) {
        this.paramType4 = paramType4;
    }

    public String getParamType5() {
        return paramType5;
    }

    public void setParamType5(String paramType5) {
        this.paramType5 = paramType5;
    }

    public String getParamValue1() {
        return paramValue1;
    }

    public void setParamValue1(String paramValue1) {
        this.paramValue1 = paramValue1;
    }

    public String getParamValue2() {
        return paramValue2;
    }

    public void setParamValue2(String paramValue2) {
        this.paramValue2 = paramValue2;
    }

    public String getParamValue3() {
        return paramValue3;
    }

    public void setParamValue3(String paramValue3) {
        this.paramValue3 = paramValue3;
    }

    public String getParamValue4() {
        return paramValue4;
    }

    public void setParamValue4(String paramValue4) {
        this.paramValue4 = paramValue4;
    }

    public String getParamValue5() {
        return paramValue5;
    }

    public void setParamValue5(String paramValue5) {
        this.paramValue5 = paramValue5;
    }

    public String getRiskIndexSqlDos() {
        return riskIndexSqlDos;
    }

    public void setRiskIndexSqlDos(String riskIndexSqlDos) {
        this.riskIndexSqlDos = riskIndexSqlDos;
    }

    @Override
    public String toString() {
        return "RiskIndexSql [sqlContent=" + sqlContent + ", getMethodType1=" + getMethodType1
                + ", getMethodType2=" + getMethodType2 + ", getMethodType3=" + getMethodType3
                + ", getMethodType4=" + getMethodType4 + ", getMethodType5=" + getMethodType5
                + ", description1=" + description1 + ", description2=" + description2 + ", description3="
                + description3 + ", description4=" + description4 + ", description5=" + description5
                + ", eGName1=" + eGName1 + ", eGName2=" + eGName2 + ", eGName3=" + eGName3 + ", eGName4="
                + eGName4 + ", eGName5=" + eGName5 + ", paramType1=" + paramType1 + ", paramType2=" + paramType2
                + ", paramType3=" + paramType3 + ", paramType4=" + paramType4 + ", paramType5=" + paramType5
                + ", paramValue1=" + paramValue1 + ", paramValue2=" + paramValue2 + ", paramValue3="
                + paramValue3 + ", paramValue4=" + paramValue4 + ", paramValue5=" + paramValue5
                + ", riskIndexSqlDos=" + riskIndexSqlDos + ",database=" + database + "]";
    }
}

