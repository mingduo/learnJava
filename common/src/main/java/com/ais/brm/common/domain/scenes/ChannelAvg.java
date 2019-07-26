package com.ais.brm.common.domain.scenes;

/**
 * 所有渠道操作量的均值.
 * Created by zhaocw on 2016-7-7.
 * @author zhaocw
 */
public class ChannelAvg {
    private int count;

    public ChannelAvg(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
