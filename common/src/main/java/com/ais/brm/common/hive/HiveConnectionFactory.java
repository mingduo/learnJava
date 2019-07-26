package com.ais.brm.common.hive;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.validation.ObjectError;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.sql.DataSource;

/**
 * hive connection factory.
 * Created by zhaocw on 2016-8-17.
 * @author zhaocw
 */

@Component
public class HiveConnectionFactory {

    //private HikariDataSource datasource;
    private Object lock = new Object();

//    @Autowired
//    @Qualifier("dsHive")
//    private DataSource hiveDatasource;
    
    @Autowired
    ApplicationContext appContext;
    
    /**
     * 每次返回一个新的连接.
     * @return
     */
    public Connection getConnection(IHiveSettings hiveSettings) throws SQLException, ClassNotFoundException {
        //Class.forName("org.apache.hive.jdbc.HiveDriver");
        //return DriverManager.getConnection(hiveSettings.getHiveJdbcUrl(),
        //        hiveSettings.getHiveUserName(), hiveSettings.getHivePasswd());

//    	return hiveDatasource.getConnection();
    	return ((DataSource)(appContext.getBean("dsHive"))).getConnection();
        //20170503 临时注释掉连接池代码，因为hikaricp 和hive低版本的jdbc驱动还不兼容，会报错method not supported.

//        synchronized (lock) {
//            if (datasource == null) {
//                initDatasource(hiveSettings);
//            }
//        }
//        return datasource.getConnection();
    }

//    private void initDatasource(IHiveSettings hiveSettings) {
//        HikariConfig config = new HikariConfig();
//        config.setJdbcUrl(hiveSettings.getHiveJdbcUrl());
//        config.setUsername(hiveSettings.getHiveUserName());
//        config.setPassword(hiveSettings.getHivePasswd());
//        config.addDataSourceProperty("cachePrepStmts", "true");
//        config.addDataSourceProperty("prepStmtCacheSize", "250");
//        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
//        config.setMaximumPoolSize(hiveSettings.getMaxiumPoolSize());
//
//        datasource = new HikariDataSource(config);
//    }

    /**
     *
     c3p0
     ---
     ComboPooledDataSource cpds = new ComboPooledDataSource();
     cpds.setJdbcUrl(connectionUri);
     cpds.setUser(username);
     cpds.setPassword(password);
     return cpds.getConnection();
     }

     dbcp
     ----
     private static Connection getJdbcConnection(String connectionUri, String username,
     String password) throws SQLException {
     BasicDataSource bds = new BasicDataSource();
     bds.setUrl(connectionUri);
     bds.setUsername(username);
     bds.setPassword(password);
     return bds.getConnection();
     }
     */
}
