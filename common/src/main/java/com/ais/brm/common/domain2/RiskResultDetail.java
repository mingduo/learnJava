package com.ais.brm.common.domain2;

import java.util.Date;

/**
 * 风险明细--台账.
 * Created by zhaocw on 2016-7-18.
 *
 * @author zhaocw
 */
public class RiskResultDetail {
    private long id;
    private String tradeId;
    private String serialNumber;
    private String userTypeCode;
    private String customerName;
    private String connectChannel;
    private Date operTime;
    private OperType operType;
    private int tradeType;
    private String operator;
    private String channelId;
    private String branchId;
    private String terminalIp;
    private int returnType;//返销类型

    private String intfId;

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

    public String getUserTypeCode() {
        return userTypeCode;
    }

    public void setUserTypeCode(String userTypeCode) {
        this.userTypeCode = userTypeCode;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getConnectChannel() {
        return connectChannel;
    }

    public void setConnectChannel(String connectChannel) {
        this.connectChannel = connectChannel;
    }

    public Date getOperTime() {
        return operTime;
    }

    public void setOperTime(Date operTime) {
        this.operTime = operTime;
    }

    public OperType getOperType() {
        return operType;
    }

    public void setOperType(OperType operType) {
        this.operType = operType;
    }

    public int getTradeType() {
        return tradeType;
    }

    public void setTradeType(int tradeType) {
        this.tradeType = tradeType;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getBranchId() {
        return branchId;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }

    public String getTerminalIp() {
        return terminalIp;
    }

    public void setTerminalIp(String terminalIp) {
        this.terminalIp = terminalIp;
    }

    public int getReturnType() {
        return returnType;
    }

    public void setReturnType(int returnType) {
        this.returnType = returnType;
    }

    public String getIntfId() {
        return intfId;
    }

    public void setIntfId(String intfId) {
        this.intfId = intfId;
    }

}
