package com.ais.brm.common.domain2.ruleegine;

import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rule;
import org.jeasy.rules.mvel.MVELRule;

import java.util.Date;

/**
 * 多指标规则配置实体
 * 
 * @author weizc
 * @since 2018/04/04
 */
public class MultiIndexRule {


	private Integer id;
	private String name;
	private String description;
	private Date createTime;
	private Date lastModTime;
	private String createUser;
	private String lastModUser;
	private Integer isEnabled;
	private Integer alertType;
	private String alertParams;
	private String expression;
	private Integer runType;
	private Integer valueGetType;

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public String getExpression() {
		return expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}


	public Integer getRunType() {
		return runType;
	}

	public void setRunType(Integer runType) {
		this.runType = runType;
	}

	public Integer getValueGetType() {
		return valueGetType;
	}

	public void setValueGetType(Integer valueGetType) {
		this.valueGetType = valueGetType;
	}

	public Rule getRule() {
		return new MVELRule().name(getName())
				.description(getDescription())
				.when(getExpression());

	}

	public boolean evaluate(Facts facts){
		return this.getRule().evaluate(facts);
	}
}
