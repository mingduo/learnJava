package com.ais.brm.common.domain.scenes;

/**
 * .
 * Created by zhaocw on 2016-7-5.
 *
 * @author zhaocw
 */
public class ChannelServantHourlyOperRecord {
    private Channel channel;
    private String servantName;//营业员名称.
    private int count;
    private int hour;//0~23

    public ChannelServantHourlyOperRecord(Channel channel, String servantName, int count, int hour) {
        this.channel = channel;
        this.servantName = servantName;
        this.count = count;
        this.hour = hour;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public ChannelServantHourlyOperRecord() {
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public String getServantName() {
        return servantName;
    }

    public void setServantName(String servantName) {
        this.servantName = servantName;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }
}
