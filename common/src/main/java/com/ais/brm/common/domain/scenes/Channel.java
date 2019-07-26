package com.ais.brm.common.domain.scenes;

/**
 * 代表渠道.
 * Created by zhaocw on 2016-7-5.
 * @author zhaocw
 */
public class Channel {
    private String id;
    private String name;

    public Channel(String id) {
        this.id = id;
    }

    public Channel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Channel channel = (Channel) o;

        if (id != null ? !id.equals(channel.id) : channel.id != null) {
            return false;
        }
        return name != null ? name.equals(channel.name) : channel.name == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Channel{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
