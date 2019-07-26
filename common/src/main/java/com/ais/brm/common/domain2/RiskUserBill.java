package com.ais.brm.common.domain2;

/**
 * @author zhaocw.
 */
public class RiskUserBill {
	private String userId;
	private String serialNumber;
	private long fee;
	private int billMonth;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	public long getFee() {
		return fee;
	}
	public void setFee(long fee) {
		this.fee = fee;
	}
	public int getBillMonth() {
		return billMonth;
	}
	public void setBillMonth(int billMonth) {
		this.billMonth = billMonth;
	}
	
	
}
