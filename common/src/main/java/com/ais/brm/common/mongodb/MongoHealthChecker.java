package com.ais.brm.common.mongodb;

/**
 * Created by zhaocaiwen on 2017/3/2.
 *
 * @author zhaocw@asiainfo-sec.com
 */
public interface MongoHealthChecker {
    boolean isMongoOk();

    void reconnect();
}
