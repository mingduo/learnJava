package com.ais.brm.common.domain.scenes;

/**
 * 渠道业务汇总.
 * Created by zhaocw on 2016-7-5.
 *
 * @author zhaocw
 */
public class ChannelOperSum {
    private Channel channel;
    private int count;//操作总量.

    public ChannelOperSum(Channel channel, int count) {
        this.channel = channel;
        this.count = count;
    }

    public ChannelOperSum() {
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
