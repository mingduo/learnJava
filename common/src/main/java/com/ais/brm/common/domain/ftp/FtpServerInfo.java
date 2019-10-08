package com.ais.brm.common.domain.ftp;

/**
 * ftpserver
 * Created by xuechen on 2016-11-3.
 *
 * @author xuechen
 */
public class FtpServerInfo {
    private int ftpId;
    private String ftpAppName;
    private String ftpUser;
    private String ftpPassword;
    private String serverIp;
    private String protocolType;
    private String ftpType; // 'FTP,SFTP'
    private Integer cmdPort;//命令端口
    private Integer dataPort;//数据端口
    private Integer serverType;//服务类型
    private boolean isPasv;//true主动 false被动

    public int getFtpId() {
        return ftpId;
    }

    public void setFtpId(int ftpId) {
        this.ftpId = ftpId;
    }

    public String getFtpAppName() {
        return ftpAppName;
    }

    public void setFtpAppName(String ftpAppName) {
        this.ftpAppName = ftpAppName;
    }

    public String getFtpUser() {
        return ftpUser;
    }

    public void setFtpUser(String ftpUser) {
        this.ftpUser = ftpUser;
    }

    public String getFtpPassword() {
        return ftpPassword;
    }

    public void setFtpPassword(String ftpPassword) {
        this.ftpPassword = ftpPassword;
    }

    public String getServerIp() {
        return serverIp;
    }

    public void setServerIp(String serverIp) {
        this.serverIp = serverIp;
    }

    public String getProtocolType() {
        return protocolType;
    }

    public void setProtocolType(String protocolType) {
        this.protocolType = protocolType;
    }

    public String getFtpType() {
        return ftpType;
    }

    public void setFtpType(String ftpType) {
        this.ftpType = ftpType;
    }

    public Integer getCmdPort() {
        return cmdPort;
    }

    public void setCmdPort(Integer cmdPort) {
        this.cmdPort = cmdPort;
    }

    public Integer getDataPort() {
        return dataPort;
    }

    public void setDataPort(Integer dataPort) {
        this.dataPort = dataPort;
    }

    public Integer getServerType() {
        return serverType;
    }

    public void setServerType(Integer serverType) {
        this.serverType = serverType;
    }

    public boolean isPasv() {
        return isPasv;
    }

    public void setPasv(boolean isPasv) {
        this.isPasv = isPasv;
    }

}
