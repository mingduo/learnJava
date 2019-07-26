package com.ais.brm.common.domain2.ruleegine;

import java.sql.Timestamp;

/**
 * <table border="1">
 * <tr><th>@Description:</th></tr>
 * <tr><td>@Date:Created in 2018-3-13</td>
 * </tr>
 * </table>
 *
 * @author :    weizc
 */
public class RiskIndexAlerts {
    private Long id;

    private Timestamp alertTime;

    private String alertContent;

    private Integer riskIndexId;

    private String riskObjectId;

    private Integer isRead;

    private Long ruleId;

    private int riskObjectTypeId;

    // 默认0, 1多指标
    private int IsMulRule;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Timestamp getAlertTime() {
        return alertTime;
    }

    public void setAlertTime(Timestamp alertTime) {
        this.alertTime = alertTime;
    }

    public String getAlertContent() {
        return alertContent;
    }

    public void setAlertContent(String alertContent) {
        this.alertContent = alertContent;
    }

    public Integer getRiskIndexId() {
        return riskIndexId;
    }

    public void setRiskIndexId(Integer riskIndexId) {
        this.riskIndexId = riskIndexId;
    }

    public String getRiskObjectId() {
        return riskObjectId;
    }

    public void setRiskObjectId(String riskObjectId) {
        this.riskObjectId = riskObjectId;
    }

    public Integer getIsRead() {
        return isRead;
    }

    public void setIsRead(Integer isRead) {
        this.isRead = isRead;
    }

    public Long getRuleId() {
        return ruleId;
    }

    public void setRuleId(Long ruleId) {
        this.ruleId = ruleId;
    }

    public int getRiskObjectTypeId() {
        return riskObjectTypeId;
    }

    public void setRiskObjectTypeId(int riskObjectTypeId) {
        this.riskObjectTypeId = riskObjectTypeId;
    }

    public int getIsMulRule() {
        return IsMulRule;
    }

    public void setIsMulRule(int isMulRule) {
        IsMulRule = isMulRule;
    }
}
