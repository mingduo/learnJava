package com.ais.brm.common.domain2.monitor;

import com.ais.brm.common.enumdomain.MonitorType;

import java.util.List;

/**
 * 系统监控规则.
 * Created by zhaocaiwen on 2018/1/4.
 *
 * @author zhaocaiwen
 */
public class MonitorRule {
    private int id;
    private String name;
    private String description;
    private MonitorType monitorType;
    private String monitorConfigJson;//json
    private String alertConfigJson;//json
    private boolean enabled;
    private List<MonitorTarget> monitorTargetList;
    //每个应用对应唯一targetId
    private int targetId;


    public int getTargetId() {
        return targetId;
    }

    public void setTargetId(int targetId) {
        this.targetId = targetId;
    }


    public List<MonitorTarget> getMonitorTargetList() {
        return monitorTargetList;
    }

    public void setMonitorTargetList(List<MonitorTarget> monitorTargetList) {
        this.monitorTargetList = monitorTargetList;
    }

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

    public MonitorType getMonitorType() {
        return monitorType;
    }

    public void setMonitorType(MonitorType monitorType) {
        this.monitorType = monitorType;
    }

    public String getMonitorConfigJson() {
        return monitorConfigJson;
    }

    public void setMonitorConfigJson(String monitorConfigJson) {
        this.monitorConfigJson = monitorConfigJson;
    }

    public String getAlertConfigJson() {
        return alertConfigJson;
    }

    public void setAlertConfigJson(String alertConfigJson) {
        this.alertConfigJson = alertConfigJson;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
