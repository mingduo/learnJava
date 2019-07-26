package com.ais.brm.common.enumdomain;

import java.util.Arrays;
import java.util.List;

/**
 * 系统监控类型.
 * Created by zhaocaiwen on 2018/1/4.
 * @author zhaocaiwen
 */
public enum MonitorType {
    HOST(1001,"主机基本信息监控"),
    MYSQL(1002,"MySQL监控"),
    REDIS(1003,"Redis监控"),
    MONGODB(1004,"MongoDB监控"),
    KAFKA(1005,"Kafka监控"),
    TOMCAT(1006,"Tomcat监控"),
    RISKER(1007,"RISKER监控"),
    HADOOP(1008,"Hadoop监控"),
    HIVE(1009,"Hive监控"),
    ZOOKEEPER(1010,"Zookeeper监控"),
    RISK_SCENES(1012,"风险场景监控"),
    JOB(1014,"Job服务监控"),
    ETL(1015,"ETL服务监控"),
    WEBLOGIC(1016,"Weblogic监控"),
    ORACLE(1017,"Oracle监控"),
    MIGU_NAS(1018,"咪咕NAS源数据文件监控");

    private final int typeId;
    private final String typeName;

    MonitorType(int typeId,String description) {
        this.typeId = typeId;
        this.typeName = description;
    }

    public int getTypeId() {
        return typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public static List<MonitorType> getAllTypes() {
        return Arrays.asList(HOST,MYSQL,REDIS,MONGODB,KAFKA,TOMCAT,RISKER,HADOOP,HIVE,
                ZOOKEEPER,RISK_SCENES,JOB,ETL,WEBLOGIC,ORACLE,MIGU_NAS);
    }

    public static MonitorType getMonitorType(int index) {
        for (MonitorType c : MonitorType.values()) {
            if (c.getTypeId() == index) {
                return c;
            }
        }
        return null;
    }

    public static void main(String[] args) {
       System.out.println(MonitorType.values());
        System.out.println(MonitorType.valueOf(""));

    }
}
