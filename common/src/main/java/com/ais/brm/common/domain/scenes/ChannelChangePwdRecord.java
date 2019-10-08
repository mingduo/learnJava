package com.ais.brm.common.domain.scenes;

/**
 * 渠道业务：修改密码
 * Created by zhaocw on 2016-7-5.
 *
 * @author zhaocw
 */
public class ChannelChangePwdRecord {
    private Channel channel;
    private String serialNumber;
    private String servantName;//营业员名称.

    public ChannelChangePwdRecord(Channel channel, String serialNumber, String servantName) {
        this.channel = channel;
        this.serialNumber = serialNumber;
        this.servantName = servantName;
    }

    public ChannelChangePwdRecord() {
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getServantName() {
        return servantName;
    }

    public void setServantName(String servantName) {
        this.servantName = servantName;
    }
}
