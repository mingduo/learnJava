package com.ais.brm.common.domain2;

/**
 * 用户的风险结果.
 * Created by zhaocw on 2016-7-18.
 * @author zhaocw
 */
public class RiskResultUsers extends  RiskResultBase{
    private String userId;
    private String userName;
    private String serialNumber;
    private String userBrand;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getUserBrand() {
        return userBrand;
    }

    public void setUserBrand(String userBrand) {
        this.userBrand = userBrand;
    }

}
