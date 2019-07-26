package com.ais.brm.common.domain2;

import java.util.Date;

/**
 * 监控对象类型.
 * Created by zhaocw on 2017-1-13.
 *
 * @author zhaocw
 */
public class RiskObjectType {
    private int id;
    private String name;
    private String description;
    private Date createTime;
    private boolean enabled;
    private String sqlContent;
    private String dataSourceTypeId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getSqlContent() {
        return sqlContent;
    }

    public void setSqlContent(String sqlContent) {
        this.sqlContent = sqlContent;
    }

    public String getDataSourceTypeId() {
        return dataSourceTypeId;
    }

    public void setDataSourceTypeId(String dataSourceTypeId) {
        this.dataSourceTypeId = dataSourceTypeId;
    }
}
