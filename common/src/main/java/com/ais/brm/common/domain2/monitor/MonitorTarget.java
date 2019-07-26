package com.ais.brm.common.domain2.monitor;

import com.ais.brm.common.enumdomain.MonitorTargetCategory;

/**
 * 系统监控目标.
 * Created by zhaocaiwen on 2018/1/4.
 * @author zhaocaiwen
 */
public class MonitorTarget {
    private int id;
    private String name;
    private String description;
    private MonitorTargetCategory category;
    private String hostIp;
    private int thePort;
    private String installDir;
    private String processKey;
    private String logsDir;
    private String keyLogFiles;
    private String keyConfigFiles;
    private String sceneId;

    public String getSceneId() {
        return sceneId;
    }

    public void setSceneId(String sceneId) {
        this.sceneId = sceneId;
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

    public MonitorTargetCategory getCategory() {
        return category;
    }

    public void setCategory(MonitorTargetCategory category) {
        this.category = category;
    }

    public String getHostIp() {
        return hostIp.trim();
    }

    public void setHostIp(String hostIp) {
        this.hostIp = hostIp;
    }

    public int getThePort() {
        return thePort;
    }

    public void setThePort(int thePort) {
        this.thePort = thePort;
    }

    public String getInstallDir() {
        return installDir;
    }

    public void setInstallDir(String installDir) {
        this.installDir = installDir;
    }

    public String getProcessKey() {
        return processKey;
    }

    public void setProcessKey(String processKey) {
        this.processKey = processKey;
    }

    public String getLogsDir() {
        return logsDir;
    }

    public void setLogsDir(String logsDir) {
        this.logsDir = logsDir;
    }

    public String getKeyLogFiles() {
        return keyLogFiles;
    }

    public void setKeyLogFiles(String keyLogFiles) {
        this.keyLogFiles = keyLogFiles;
    }

    public String getKeyConfigFiles() {
        return keyConfigFiles;
    }

    public void setKeyConfigFiles(String keyConfigFiles) {
        this.keyConfigFiles = keyConfigFiles;
    }

    @Override
    public String toString() {
        return "MonitorTarget{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", category=" + category +
                ", hostIp='" + hostIp + '\'' +
                '}';
    }
}
