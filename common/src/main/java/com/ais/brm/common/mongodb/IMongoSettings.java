package com.ais.brm.common.mongodb;

/**
 * settings used by mongoUtils.
 * Created by zhaocaiwen on 2017/2/27.
 *
 * @author zhaocw@asiainfo-sec.com
 */
public interface IMongoSettings {
    /**
     * YOu must provide the mongodb host ip address.
     * @return
     */
    String getHostIp();

    default int getPort() {
        return 27017;
    }

    default String getUsername() {
        return null;
    }

    default String getPassword() {
        return null;
    }

    String getDb();
}
