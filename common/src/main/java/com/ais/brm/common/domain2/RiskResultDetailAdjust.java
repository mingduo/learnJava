package com.ais.brm.common.domain2;

import java.util.Date;

/**
 * 风险明细--调账.
 * Created by zhaocw on 2016-7-18.
 * @author zhaocw
 */
public class RiskResultDetailAdjust {
    private long id;
    private String tradeId;
    private String serialNumber;
    private String customerName;
    private Date operTime;
    private int adjustType;
    private long adjustFee;
    private String adjustRemark;
    private String adjustStaffId;
    private String adjustStaffName;
    private String adjustDepartId;
    private String adjustDepartName;
    private String adjustEparchyCode;//地市编码.
    private String adjustEparchyName;//地市名称.
    private int cancelTag;
    private String userTypeCode;
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTradeId() {
        return tradeId;
    }

    public void setTradeId(String tradeId) {
        this.tradeId = tradeId;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Date getOperTime() {
        return operTime;
    }

    public void setOperTime(Date operTime) {
        this.operTime = operTime;
    }

    public int getAdjustType() {
        return adjustType;
    }

    public void setAdjustType(int adjustType) {
        this.adjustType = adjustType;
    }

    public long getAdjustFee() {
        return adjustFee;
    }

    public void setAdjustFee(long adjustFee) {
        this.adjustFee = adjustFee;
    }

    public String getAdjustRemark() {
        return adjustRemark;
    }

    public void setAdjustRemark(String adjustRemark) {
        this.adjustRemark = adjustRemark;
    }

    public String getAdjustStaffId() {
        return adjustStaffId;
    }

    public void setAdjustStaffId(String adjustStaffId) {
        this.adjustStaffId = adjustStaffId;
    }

    public String getAdjustStaffName() {
        return adjustStaffName;
    }

    public void setAdjustStaffName(String adjustStaffName) {
        this.adjustStaffName = adjustStaffName;
    }

    public String getAdjustDepartId() {
        return adjustDepartId;
    }

    public void setAdjustDepartId(String adjustDepartId) {
        this.adjustDepartId = adjustDepartId;
    }

    public String getAdjustDepartName() {
        return adjustDepartName;
    }

    public void setAdjustDepartName(String adjustDepartName) {
        this.adjustDepartName = adjustDepartName;
    }

    public String getAdjustEparchyCode() {
        return adjustEparchyCode;
    }

    public void setAdjustEparchyCode(String adjustEparchyCode) {
        this.adjustEparchyCode = adjustEparchyCode;
    }

    public String getAdjustEparchyName() {
        return adjustEparchyName;
    }

    public void setAdjustEparchyName(String adjustEparchyName) {
        this.adjustEparchyName = adjustEparchyName;
    }

    public int getCancelTag() {
        return cancelTag;
    }

    public void setCancelTag(int cancelTag) {
        this.cancelTag = cancelTag;
    }

	public String getUserTypeCode() {
		return userTypeCode;
	}

	public void setUserTypeCode(String userTypeCode) {
		this.userTypeCode = userTypeCode;
	}
    
}
