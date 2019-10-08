package com.ais.brm.common.domain.scenes;

/**
 * 当天渠道操作量.
 * Created by zhaocw on 2016-7-5.
 *
 * @author zhaocw
 */
public class ChannelDailyOperSum {
    private Channel channel;
    private String operId;//业务类型.
    private int count;

    public ChannelDailyOperSum(Channel channel, String operId, int count) {
        this.channel = channel;
        this.operId = operId;
        this.count = count;
    }

    public ChannelDailyOperSum() {
    }

    public String getOperId() {
        return operId;
    }

    public void setOperId(String operId) {
        this.operId = operId;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
