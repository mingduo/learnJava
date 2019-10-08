package com.ais.brm.common.domain2.whitelist;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 * 风险分析白名单检查项
 *
 * @author lulj
 */
public class WhitelistEntry {
    private long whiteId;                // 白名单记录ID
    private int riskObjectTypeId;        // 适用风险对象类型
    private int whitelistType;            // 白名单类型
    private String targetId;            // 目标对象ID
    private String targetName;            // 目标名称
    private String remark;                // 备注信息
    private Date createTime;            // 创建时间

    public long getWhiteId() {
        return whiteId;
    }

    public void setWhiteId(long whiteId) {
        this.whiteId = whiteId;
    }

    public int getRiskObjectTypeId() {
        return riskObjectTypeId;
    }

    public void setRiskObjectTypeId(int riskObjectTypeId) {
        this.riskObjectTypeId = riskObjectTypeId;
    }

    public int getWhitelistType() {
        return whitelistType;
    }

    public void setWhitelistType(int whitelistType) {
        this.whitelistType = whitelistType;
    }

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getTargetName() {
        return targetName;
    }

    public void setTargetName(String targetName) {
        this.targetName = targetName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public WhitelistEntry() {
    }

    public WhitelistEntry(ResultSet rs) throws SQLException {
        this.whiteId = rs.getLong("white_id");
        this.riskObjectTypeId = rs.getInt("risk_object_type_id");
        this.whitelistType = rs.getInt("whitelist_type");
        this.targetId = rs.getString("target_id");
        this.targetName = rs.getString("target_name");
        this.remark = rs.getString("remark");
        this.createTime = rs.getTimestamp("create_time");
    }
}
