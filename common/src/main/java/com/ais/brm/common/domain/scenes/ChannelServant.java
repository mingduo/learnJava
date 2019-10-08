package com.ais.brm.common.domain.scenes;

/**
 * 营业员.
 * Created by zhaocw on 2016-7-5.
 *
 * @author zhaocw
 */
public class ChannelServant {
    private String name;
    private String id;
    private String channelId;

    public ChannelServant(String name, String id, String channelId) {
        this.name = name;
        this.id = id;
        this.channelId = channelId;
    }

    public ChannelServant() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }
}
