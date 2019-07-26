package com.ais.brm.common.hive;

/**
 * Created by zhaocw on 2017-1-10.
 *
 * @author zhaocw
 */
public interface IHiveSettings {
    String getHiveJdbcUrl();

    String getHiveUserName();

    String getHivePasswd();

    default int getMaxiumPoolSize() {
        return 20;
    }
}
