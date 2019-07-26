package com.ais.brm.common.domain2;

import java.sql.Timestamp;

import com.ais.brm.web.domain.BaseDomain;

/**
 * 明细采集请求
 * @author dukm
 *
 */
public class DetailCollectRequest extends BaseDomain{
	private Integer id;
	
	private String name;
	
	private String riskIndexName;
	private Timestamp createTime;
	
	private Timestamp prvUpdateTime;
	
	private Integer riskIndexId;
	
	private Integer riskId;
	private Integer riskIndexAlarmId;
	
	private String riskObjectId;
	
	private String riskObjectTypeId;
	
	private String collectDatesourceId;
	
	private String resultDatesourceId;
	
	private Integer collectNum;
	
	private String collectNumParams;
	
	private Integer schedulingMode;
	
	private String schedulingModeParams;
	
	private Integer failureRetryType;
	
	private String failureRetryTypeParams;
	
	private Timestamp collectStartTime;
	
	private Timestamp collectEndTime;
	
	private Integer isSuccess;
	
	private Long collectResultNum;
	
	private String collectSql;
	
	private String collectSqlParams;

	private String description;

	private String riskObjectTypeName;
	
	private String typeName;
	private String resultDatesourceName;
	private String collectDatesourceName;
	
	private String modelName;
	
	private String operateTime;
	
	public String getOperateTime() {
		return operateTime;
	}

	
	



	public void setOperateTime(String operateTime) {
		this.operateTime = operateTime;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public String getRiskIndexName() {
		return riskIndexName;
	}

	public void setRiskIndexName(String riskIndexName) {
		this.riskIndexName = riskIndexName;
	}

	public String getResultDatesourceName() {
		return resultDatesourceName;
	}

	public void setResultDatesourceName(String resultDatesourceName) {
		this.resultDatesourceName = resultDatesourceName;
	}

	public String getCollectDatesourceName() {
		return collectDatesourceName;
	}

	public void setCollectDatesourceName(String collectDatesourceName) {
		this.collectDatesourceName = collectDatesourceName;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getRiskObjectTypeName() {
		return riskObjectTypeName;
	}

	public void setRiskObjectTypeName(String riskObjectTypeName) {
		this.riskObjectTypeName = riskObjectTypeName;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getRiskIndexId() {
		return riskIndexId;
	}

	public void setRiskIndexId(Integer riskIndexId) {
		this.riskIndexId = riskIndexId;
	}

	public Integer getRiskId() {
		return riskId;
	}

	public void setRiskId(Integer riskId) {
		this.riskId = riskId;
	}

	public String getRiskObjectId() {
		return riskObjectId;
	}

	public void setRiskObjectId(String riskObjectId) {
		this.riskObjectId = riskObjectId;
	}

	public String getRiskObjectTypeId() {
		return riskObjectTypeId;
	}

	public void setRiskObjectTypeId(String riskObjectTypeId) {
		this.riskObjectTypeId = riskObjectTypeId;
	}

	public String getCollectDatesourceId() {
		return collectDatesourceId;
	}

	public void setCollectDatesourceId(String collectDatesourceId) {
		this.collectDatesourceId = collectDatesourceId;
	}

	public String getResultDatesourceId() {
		return resultDatesourceId;
	}

	public void setResultDatesourceId(String resultDatesourceId) {
		this.resultDatesourceId = resultDatesourceId;
	}

	public Integer getCollectNum() {
		return collectNum;
	}

	public void setCollectNum(Integer collectNum) {
		this.collectNum = collectNum;
	}

	public String getCollectNumParams() {
		return collectNumParams;
	}

	public void setCollectNumParams(String collectNumParams) {
		this.collectNumParams = collectNumParams;
	}

	public Integer getSchedulingMode() {
		return schedulingMode;
	}

	public void setSchedulingMode(Integer schedulingMode) {
		this.schedulingMode = schedulingMode;
	}

	public String getSchedulingModeParams() {
		return schedulingModeParams;
	}

	public void setSchedulingModeParams(String schedulingModeParams) {
		this.schedulingModeParams = schedulingModeParams;
	}

	public Integer getFailureRetryType() {
		return failureRetryType;
	}

	public void setFailureRetryType(Integer failureRetryType) {
		this.failureRetryType = failureRetryType;
	}

	public String getFailureRetryTypeParams() {
		return failureRetryTypeParams;
	}

	public void setFailureRetryTypeParams(String failureRetryTypeParams) {
		this.failureRetryTypeParams = failureRetryTypeParams;
	}

	public Integer getIsSuccess() {
		return isSuccess;
	}

	public void setIsSuccess(Integer isSuccess) {
		this.isSuccess = isSuccess;
	}

	public Integer getRiskIndexAlarmId() {
		return riskIndexAlarmId;
	}

	public void setRiskIndexAlarmId(Integer riskIndexAlarmId) {
		this.riskIndexAlarmId = riskIndexAlarmId;
	}

	public Long getCollectResultNum() {
		return collectResultNum;
	}

	public void setCollectResultNum(Long collectResultNum) {
		this.collectResultNum = collectResultNum;
	}

	public String getCollectSql() {
		return collectSql;
	}

	public void setCollectSql(String collectSql) {
		this.collectSql = collectSql;
	}

	public String getCollectSqlParams() {
		return collectSqlParams;
	}

	public void setCollectSqlParams(String collectSqlParams) {
		this.collectSqlParams = collectSqlParams;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public Timestamp getPrvUpdateTime() {
		return prvUpdateTime;
	}

	public Timestamp getCollectStartTime() {
		return collectStartTime;
	}

	public Timestamp getCollectEndTime() {
		return collectEndTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public void setPrvUpdateTime(Timestamp prvUpdateTime) {
		this.prvUpdateTime = prvUpdateTime;
	}

	public void setCollectStartTime(Timestamp collectStartTime) {
		this.collectStartTime = collectStartTime;
	}

	public void setCollectEndTime(Timestamp collectEndTime) {
		this.collectEndTime = collectEndTime;
	}

	@Override
	public String toString() {
		return "DetailCollectRequest [id=" + id + ", name=" +
	name + ", createTime=" + createTime + ", prvUpdateTime="
				+ prvUpdateTime + ", riskIndexId=" + riskIndexId + ", "
						+ "riskId=" + riskId + ", riskIndexAlarmId="
				+ riskIndexAlarmId + ", riskObjectId=" + 
						riskObjectId + ", riskObjectTypeId=" + riskObjectTypeId
				+ ", collectDatesourceId=" + collectDatesourceId 
				+ ", resultDatesourceId=" + resultDatesourceId
				+ ", collectNum=" + collectNum + ", collectNumParams=" 
				+ collectNumParams + ", schedulingMode="
				+ schedulingMode + ", schedulingModeParams=" 
				+ schedulingModeParams + ", failureRetryType="
				+ failureRetryType + ", failureRetryTypeParams=" 
				+ failureRetryTypeParams + ", collectStartTime="
				+ collectStartTime + ", collectEndTime=" +
				collectEndTime + ", isSuccess=" + isSuccess
				+ ", collectResultNum=" + collectResultNum +
				", collectSql=" + collectSql + ", collectSqlParams="
				+ collectSqlParams + ", description=" + description +
				", riskObjectTypeName=" + riskObjectTypeName
				+ ", typeName=" + typeName + "]";
	}
}