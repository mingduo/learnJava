package com.ais.brm.common.domain2.monitor;


import java.util.Date;
import java.util.List;

/**
 * 监控代理.
 * Created by zhaocaiwen on 2018/1/4.
 *
 * @author zhaocaiwen
 */
public class MonitorAgent {
    private int id;
    private String name;
    private String description;
    private Date lastHeartBeatTime;
    private Date firstHeartBeatTime;
    private long processedTaskCount;
    private List<String> supportedMonitorTypes;


    public List<String> getSupportedMonitorTypes() {
        return supportedMonitorTypes;
    }

    public void setSupportedMonitorTypes(List<String> supportedMonitorTypes) {
        this.supportedMonitorTypes = supportedMonitorTypes;
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

    public Date getLastHeartBeatTime() {
        return lastHeartBeatTime;
    }

    public void setLastHeartBeatTime(Date lastHeartBeatTime) {
        this.lastHeartBeatTime = lastHeartBeatTime;
    }

    public Date getFirstHeartBeatTime() {
        return firstHeartBeatTime;
    }

    public void setFirstHeartBeatTime(Date firstHeartBeatTime) {
        this.firstHeartBeatTime = firstHeartBeatTime;
    }

    public long getProcessedTaskCount() {
        return processedTaskCount;
    }

    public void setProcessedTaskCount(long processedTaskCount) {
        this.processedTaskCount = processedTaskCount;
    }
}
