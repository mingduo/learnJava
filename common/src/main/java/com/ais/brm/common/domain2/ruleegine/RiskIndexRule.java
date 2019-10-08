package com.ais.brm.common.domain2.ruleegine;

import java.util.Date;

/**
 * 代表一个单指标监控规则.
 * Created by zhaocaiwen on 2018/3/9.
 *
 * @author zhaocw.
 */
public class RiskIndexRule {
    private Long id;

    private String name;

    private String description;

    private Long indexId;

    private Integer ruleType;

    private String ruleParamsJson;

    private Date createTime;

    private Date lastModTime;

    private String createUser;

    private String lastModUser;

    private Integer isEnabled;

    private Integer alertType;

    private String alertParams;

    private Integer runType;


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

    public Long getIndexId() {
        return indexId;
    }

    public void setIndexId(Long indexId) {
        this.indexId = indexId;
    }

    public Integer getRuleType() {
        return ruleType;
    }

    public void setRuleType(Integer ruleType) {
        this.ruleType = ruleType;
    }

    public String getRuleParamsJson() {
        return ruleParamsJson;
    }

    public void setRuleParamsJson(String ruleParamsJson) {
        this.ruleParamsJson = ruleParamsJson;
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

    public Integer getAlertType() {
        return alertType;
    }

    public void setAlertType(Integer alertType) {
        this.alertType = alertType;
    }

    public String getAlertParams() {
        return alertParams;
    }

    public void setAlertParams(String alertParams) {
        this.alertParams = alertParams;
    }

    public Integer getRunType() {
        return runType;
    }

    public void setRunType(Integer runType) {
        this.runType = runType;
    }
}
