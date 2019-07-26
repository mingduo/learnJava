package com.ais.brm.common.domain2;

/**
 * 渠道的风险结果.
 * Created by zhaocw on 2016-7-18.
 * @author zhaocw
 */
public class RiskResultChannel extends RiskResultBase{
    private String channelId;

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }
}
