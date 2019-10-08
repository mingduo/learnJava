package com.ais.brm.common.domain2.whitelist;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 * 风险分析白名单过滤器定义
 *
 * @author lulj
 */
public class WhitelistFilterDef {

    private int whitelistType;

    private String whitelistName;

    private String matchRiskObjectTypes;

    private String filterBean;

    private Date createTime;

    public int getWhitelistType() {
        return whitelistType;
    }

    public void setWhitelistType(int whitelistType) {
        this.whitelistType = whitelistType;
    }

    public String getWhitelistName() {
        return whitelistName;
    }

    public void setWhitelistName(String whitelistName) {
        this.whitelistName = whitelistName;
    }

    public String getMatchRiskObjectTypes() {
        return matchRiskObjectTypes;
    }

    public void setMatchRiskObjectTypes(String matchRiskObjectTypes) {
        this.matchRiskObjectTypes = matchRiskObjectTypes;
    }

    public int[] matchRiskObjectTypes() {
        int[] result = new int[0];

        if (matchRiskObjectTypes != null) {
            String[] ss = matchRiskObjectTypes.trim().split("[,:; ]");
            if (ss.length > 0) {
                result = new int[ss.length];
                for (int i = 0; i < ss.length; i++) {
                    try {
                        result[i] = Integer.parseInt(ss[i]);
                    } catch (NumberFormatException nfe) {
                        //ignore;
                    }
                }
            }
        }

        return result;
    }

    public String getFilterBean() {
        return filterBean;
    }

    public void setFilterBean(String filterBean) {
        this.filterBean = filterBean;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public WhitelistFilterDef(ResultSet rs) throws SQLException {
        this.whitelistType = rs.getInt("whitelist_type");
        this.whitelistName = rs.getString("whitelist_name");
        this.matchRiskObjectTypes = rs.getString("risk_object_types");
        this.filterBean = rs.getString("filter_bean");
        this.createTime = rs.getTimestamp("create_time");
    }
}

