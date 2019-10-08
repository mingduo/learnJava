package com.ais.brm.common.domain2;

import java.util.Date;

/**
 * @author zhaocw
 */
public class RiskResultUserInfo {
    private long riskResultId;
    private String userId;
    private String customerName;
    private boolean isRealName;  // '是否实名：0-非实名，1-实名',
    private char customerType;    // '客户类型：0-个人客户，1-集团客户，2-家庭客户，3-团体客户',
    private char psptTypeCode;    // '证件类别：见参数表TD_S_PASSPORTTYPE',
    private char prepayTag;      // '预付费标志：0-后付费，1-预付费。（省内标准）',
    private String cityCode;  //用户所属地区
    private String serialNumber;
    private char userTypeCode;    // '用户类型：用于将用户分群,如普通、公免、测试卡，详见用户类型参数表，0必须为普通',
    private long productId;
    private String brandCode;
    private Date inDate;
    private char removeTag;        // '销档标志：0-正常、1-销档',
    private Date destoryTime;

    public long getRiskResultId() {
        return riskResultId;
    }

    public void setRiskResultId(long riskResultId) {
        this.riskResultId = riskResultId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public boolean isRealName() {
        return isRealName;
    }

    public void setRealName(boolean isRealName) {
        this.isRealName = isRealName;
    }

    public char getCustomerType() {
        return customerType;
    }

    public void setCustomerType(char customerType) {
        this.customerType = customerType;
    }

    public char getPsptTypeCode() {
        return psptTypeCode;
    }

    public void setPsptTypeCode(char psptTypeCode) {
        this.psptTypeCode = psptTypeCode;
    }

    public char getPrepayTag() {
        return prepayTag;
    }

    public void setPrepayTag(char prepayTag) {
        this.prepayTag = prepayTag;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public char getUserTypeCode() {
        return userTypeCode;
    }

    public void setUserTypeCode(char userTypeCode) {
        this.userTypeCode = userTypeCode;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public String getBrandCode() {
        return brandCode;
    }

    public void setBrandCode(String brandCode) {
        this.brandCode = brandCode;
    }

    public Date getInDate() {
        return inDate;
    }

    public void setInDate(Date inDate) {
        this.inDate = inDate;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public char getRemoveTag() {
        return removeTag;
    }

    public void setRemoveTag(char removeTag) {
        this.removeTag = removeTag;
    }

    public Date getDestoryTime() {
        return destoryTime;
    }

    public void setDestoryTime(Date destoryTime) {
        this.destoryTime = destoryTime;
    }
}
