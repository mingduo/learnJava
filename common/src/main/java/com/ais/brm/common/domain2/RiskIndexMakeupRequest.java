package com.ais.brm.common.domain2;


import java.util.Date;

import com.ais.brm.web.domain.BaseDomain;

/**
 * 指标补录请求
 *
 * @author dukm
 */
public class RiskIndexMakeupRequest extends BaseDomain {

    private Integer id;
    private Integer riskIndexId;
    private Integer timeRangeType;
    private String timeRangeStart;
    private String timeRangeEnd;
    private Integer isDelOld;
    private Date realStartTime;
    private Date realEndTime;
    private Integer isSuccess;
    private Date createTime;
    private String creator;
    private String indexName;
    private String riskIndexCode;
    private String reindexTimeRange;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRiskIndexId() {
        return riskIndexId;
    }

    public void setRiskIndexId(Integer riskIndexId) {
        this.riskIndexId = riskIndexId;
    }

    public Integer getTimeRangeType() {
        return timeRangeType;
    }

    public void setTimeRangeType(Integer timeRangeType) {
        this.timeRangeType = timeRangeType;
    }

    public String getTimeRangeStart() {
        return timeRangeStart;
    }

    public void setTimeRangeStart(String timeRangeStart) {
        this.timeRangeStart = timeRangeStart;
    }

    public String getTimeRangeEnd() {
        return timeRangeEnd;
    }

    public void setTimeRangeEnd(String timeRangeEnd) {
        this.timeRangeEnd = timeRangeEnd;
    }

    public Integer getIsDelOld() {
        return isDelOld;
    }

    public void setIsDelOld(Integer isDelOld) {
        this.isDelOld = isDelOld;
    }

    public Date getRealStartTime() {
        return realStartTime;
    }

    public void setRealStartTime(Date realStartTime) {
        this.realStartTime = realStartTime;
    }

    public Date getRealEndTime() {
        return realEndTime;
    }

    public void setRealEndTime(Date realEndTime) {
        this.realEndTime = realEndTime;
    }

    public Integer getIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(Integer isSuccess) {
        this.isSuccess = isSuccess;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getIndexName() {
        return indexName;
    }

    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }

    public String getRiskIndexCode() {
        return riskIndexCode;
    }

    public void setRiskIndexCode(String riskIndexCode) {
        this.riskIndexCode = riskIndexCode;
    }

    public String getReindexTimeRange() {
        return reindexTimeRange;
    }

    public void setReindexTimeRange(String reindexTimeRange) {
        this.reindexTimeRange = reindexTimeRange;
    }


}