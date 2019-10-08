package com.ais.brm.common.domain.scenes;

/**
 * 渠道业务平均值.
 * Created by zhaocw on 2016-7-5.
 *
 * @author zhaocw
 */
public class ChannelOperAvg {
    private String operId;
    private int count;//操作平均值.

    public ChannelOperAvg(String operId, int count) {
        this.operId = operId;
        this.count = count;
    }

    public ChannelOperAvg() {
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getOperId() {
        return operId;
    }

    public void setOperId(String operId) {
        this.operId = operId;
    }
}
