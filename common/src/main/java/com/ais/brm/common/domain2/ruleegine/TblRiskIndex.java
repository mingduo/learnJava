package com.ais.brm.common.domain2.ruleegine;

import java.util.Date;

/**
 * 风险指标
 *
 * @author szg
 */

public class TblRiskIndex {
    private Long id;

    private String name;

    private String description;

    private Integer riskObjectTypeId;

    private Integer riskIndexTypeId;

    private String riskIndexCode;

    private Integer collectType;

    private String datasourceType;

    private Integer indexValueType;

    private Date createTime;

    private Date lastModTime;

    private String createUser;

    private String lastModUser;

    private Integer isEnabled;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getRiskObjectTypeId() {
        return riskObjectTypeId;
    }

    public void setRiskObjectTypeId(Integer riskObjectTypeId) {
        this.riskObjectTypeId = riskObjectTypeId;
    }

    public Integer getRiskIndexTypeId() {
        return riskIndexTypeId;
    }

    public void setRiskIndexTypeId(Integer riskIndexTypeId) {
        this.riskIndexTypeId = riskIndexTypeId;
    }

    public String getRiskIndexCode() {
        return riskIndexCode;
    }

    public void setRiskIndexCode(String riskIndexCode) {
        this.riskIndexCode = riskIndexCode;
    }

    public Integer getCollectType() {
        return collectType;
    }

    public void setCollectType(Integer collectType) {
        this.collectType = collectType;
    }

    public String getDatasourceType() {
        return datasourceType;
    }

    public void setDatasourceType(String datasourceType) {
        this.datasourceType = datasourceType;
    }

    public Integer getIndexValueType() {
        return indexValueType;
    }

    public void setIndexValueType(Integer indexValueType) {
        this.indexValueType = indexValueType;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastModTime() {
        return lastModTime;
    }

    public void setLastModTime(Date lastModTime) {
        this.lastModTime = lastModTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getLastModUser() {
        return lastModUser;
    }

    public void setLastModUser(String lastModUser) {
        this.lastModUser = lastModUser;
    }

    public Integer getIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(Integer isEnabled) {
        this.isEnabled = isEnabled;
    }


}
