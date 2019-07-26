package com.ais.brm.common.domain;

/**
 * 基础的kafka配置接口.
 * Created by zhaocw on 2016/6/7.
 * @author zhaocw
 */
public interface IKafkaConfig {
    String getBootstrapServers();

    String getBatchSize();

    String getBufferMemory();

    int getRetries();
}
