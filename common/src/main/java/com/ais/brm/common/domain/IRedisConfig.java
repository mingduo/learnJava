package com.ais.brm.common.domain;

/**
 * 基础的redisconfig配置.
 * Created by zhaocw on 2016/6/7.
 * @author zhaocw
 */
public interface IRedisConfig {
    String getRedisHost();
    String getRedisPort();
    String getRedisPassword();
}
