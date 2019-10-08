package com.ais.brm.common.domain2;

/**
 * @author zhaoyq3
 */
public class AlarmSetting {
    long id;
    long receiveRole;
    String messageTemplate;
    String emailTemplate;
    String emailIp;
    int emailPort;
    String emailProtocol;
    String emailUser;
    String emailPassword;
    String emailSender;
    int isSendMessage;
    int isSendEmail;

    /*系统登陆连接*/
    private String loginUrl;

    public String getLoginUrl() {
        return loginUrl;
    }

    public void setLoginUrl(String loginUrl) {
        this.loginUrl = loginUrl;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getReceiveRole() {
        return receiveRole;
    }

    public void setReceiveRole(long receiveRole) {
        this.receiveRole = receiveRole;
    }

    public String getMessageTemplate() {
        return messageTemplate;
    }

    public void setMessageTemplate(String messageTemplate) {
        this.messageTemplate = messageTemplate;
    }

    public String getEmailTemplate() {
        return emailTemplate;
    }

    public void setEmailTemplate(String emailTemplate) {
        this.emailTemplate = emailTemplate;
    }

    public String getEmailIp() {
        return emailIp;
    }

    public void setEmailIp(String emailIp) {
        this.emailIp = emailIp;
    }

    public int getEmailPort() {
        return emailPort;
    }

    public void setEmailPort(int emailPort) {
        this.emailPort = emailPort;
    }

    public String getEmailProtocol() {
        return emailProtocol;
    }

    public void setEmailProtocol(String emailProtocol) {
        this.emailProtocol = emailProtocol;
    }

    public String getEmailUser() {
        return emailUser;
    }

    public void setEmailUser(String emailUser) {
        this.emailUser = emailUser;
    }

    public String getEmailPassword() {
        return emailPassword;
    }

    public void setEmailPassword(String emailPassword) {
        this.emailPassword = emailPassword;
    }

    public String getEmailSender() {
        return emailSender;
    }

    public void setEmailSender(String emailSender) {
        this.emailSender = emailSender;
    }

    public int getIsSendMessage() {
        return isSendMessage;
    }

    public void setIsSendMessage(int isSendMessage) {
        this.isSendMessage = isSendMessage;
    }

    public int getIsSendEmail() {
        return isSendEmail;
    }

    public void setIsSendEmail(int isSendEmail) {
        this.isSendEmail = isSendEmail;
    }
}
