package com.ais.brm.common.domain2.ruleegine;

import java.sql.Timestamp;

/**
 * 指标批量分析过程
 *
 * @author szg
 */

public class RiskIndexAnalyzeTrace {
    private Long id;
    private Long jobId;
    private Long ruleId;

    private Timestamp startTime;
    private Timestamp endTime;
    private Integer isSuccess;
    private Timestamp analyzeStartTime;

    private Timestamp analyzeEndTime;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    public Long getRuleId() {
        return ruleId;
    }

    public void setRuleId(Long ruleId) {
        this.ruleId = ruleId;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public Integer getIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(Integer isSuccess) {
        this.isSuccess = isSuccess;
    }

    public Timestamp getAnalyzeStartTime() {
        return analyzeStartTime;
    }

    public void setAnalyzeStartTime(Timestamp analyzeStartTime) {
        this.analyzeStartTime = analyzeStartTime;
    }

    public Timestamp getAnalyzeEndTime() {
        return analyzeEndTime;
    }

    public void setAnalyzeEndTime(Timestamp analyzeEndTime) {
        this.analyzeEndTime = analyzeEndTime;
    }
}
