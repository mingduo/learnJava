package com.ais.brm.common.domain2;
/**
 * 
 * @author zhaoyq3	
 * @since 20161118
 *
 */
public class AlarmThreshold {
	int id ;
	String alarmLevelCode;
	String alarmLevelName;
	int 	oneLevelMinCount;
	int 	oneLevelMaxCount;
	int 	twoLevelMinCount;
	int 	twoLevelMaxCount;
	int 	treeLevelMinCount;
	int 	treeLevelmaxCount;
	int 	fourLevelMinCount;
	int 	fourLevelMaxCount;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getAlarmLevelCode() {
		return alarmLevelCode;
	}
	public void setAlarmLevelCode(String alarmLevelCode) {
		this.alarmLevelCode = alarmLevelCode;
	}
	public String getAlarmLevelName() {
		return alarmLevelName;
	}
	public void setAlarmLevelName(String alarmLevelName) {
		this.alarmLevelName = alarmLevelName;
	}
	public int getOneLevelMinCount() {
		return oneLevelMinCount;
	}
	public void setOneLevelMinCount(int oneLevelMinCount) {
		this.oneLevelMinCount = oneLevelMinCount;
	}
	public int getOneLevelMaxCount() {
		return oneLevelMaxCount;
	}
	public void setOneLevelMaxCount(int oneLevelMaxCount) {
		this.oneLevelMaxCount = oneLevelMaxCount;
	}
	public int getTwoLevelMinCount() {
		return twoLevelMinCount;
	}
	public void setTwoLevelMinCount(int twoLevelMinCount) {
		this.twoLevelMinCount = twoLevelMinCount;
	}
	public int getTwoLevelMaxCount() {
		return twoLevelMaxCount;
	}
	public void setTwoLevelMaxCount(int twoLevelMaxCount) {
		this.twoLevelMaxCount = twoLevelMaxCount;
	}
	public int getTreeLevelMinCount() {
		return treeLevelMinCount;
	}
	public void setTreeLevelMinCount(int treeLevelMinCount) {
		this.treeLevelMinCount = treeLevelMinCount;
	}
	public int getTreeLevelmaxCount() {
		return treeLevelmaxCount;
	}
	public void setTreeLevelmaxCount(int treeLevelmaxCount) {
		this.treeLevelmaxCount = treeLevelmaxCount;
	}
	public int getFourLevelMinCount() {
		return fourLevelMinCount;
	}
	public void setFourLevelMinCount(int fourLevelMinCount) {
		this.fourLevelMinCount = fourLevelMinCount;
	}
	public int getFourLevelMaxCount() {
		return fourLevelMaxCount;
	}
	public void setFourLevelMaxCount(int fourLevelMaxCount) {
		this.fourLevelMaxCount = fourLevelMaxCount;
	}
	
	
}
