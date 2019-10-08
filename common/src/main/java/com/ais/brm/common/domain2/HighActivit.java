package com.ais.brm.common.domain2;

/**
 * 高活跃用户记录
 *
 * @author zhaoyq
 * @since 2016/10/10
 */
public class HighActivit {
    String departId;
    String serialNumber;
    int countSerial;

    public String getDepartId() {
        return departId;
    }

    public void setDepartId(String departId) {
        this.departId = departId;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public int getCountSerial() {
        return countSerial;
    }

    public void setCountSerial(int countSerial) {
        this.countSerial = countSerial;
    }

    @Override
    public String toString() {
        return "HihgActivit{departId=" + departId + ", serialNumber=" + serialNumber + ", countSerial=" + countSerial + "}";
    }
}
