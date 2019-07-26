package com.ais.brm.common.domain2;

import java.util.Date;

/**
 * 风险明细--积分.
 * 
 * @author zhaocw
 * @since 2016-7-18
 */
public class RiskResultDetailJifen {
    private long id;
    private String tradeId;
    private String serialNumber;
    private String customerUserTypeCode;
	private String customerName;
    private Date operTime;
    private OperType operType;
    private String operTypeCode;
    private long jifenCount;
    private String bakSerialNumber;
    private String bakCustomerUserTypeCode;
    private String bakCustomerName;
    

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

    public OperType getOperType() {
        return operType;
    }

    public void setOperType(OperType operType) {
        this.operType = operType;
    }

    public String getOperTypeCode() {
		return operTypeCode;
	}

	public void setOperTypeCode(String operTypeCode) {
		this.operTypeCode = operTypeCode;
	}

	public long getJifenCount() {
        return jifenCount;
    }

    public void setJifenCount(long jifenCount) {
        this.jifenCount = jifenCount;
    }

    public String getBakSerialNumber() {
        return bakSerialNumber;
    }

    public void setBakSerialNumber(String bakSerialNumber) {
        this.bakSerialNumber = bakSerialNumber;
    }

    public String getBakCustomerName() {
        return bakCustomerName;
    }

    public void setBakCustomerName(String bakCustomerName) {
        this.bakCustomerName = bakCustomerName;
    }
    
    public String getCustomerUserTypeCode() {
		return customerUserTypeCode;
	}

	public void setCustomerUserTypeCode(String customerUserTypeCode) {
		this.customerUserTypeCode = customerUserTypeCode;
	}

	public String getBakCustomerUserTypeCode() {
		return bakCustomerUserTypeCode;
	}

	public void setBakCustomerUserTypeCode(String bakCustomerUserTypeCode) {
		this.bakCustomerUserTypeCode = bakCustomerUserTypeCode;
	}
}
