package com.ais.brm.common.domain2;

import com.ais.brm.common.domain.notifs.GeneralKakfaNotif;

/**
 * 咪咕接口机的文件导入请求.
 * Created by zhaocw on 2017/6/2.
 *
 * @author zhaocw
 */
public class MiguJiekoujiImporterRequestNotif extends GeneralKakfaNotif {
    private String targetTableName;
    private String importerClassName;
    private String theDay;

    public String getTargetTableName() {
        return targetTableName;
    }

    public void setTargetTableName(String targetTableName) {
        this.targetTableName = targetTableName;
    }

    public String getImporterClassName() {
        return importerClassName;
    }

    public void setImporterClassName(String importerClassName) {
        this.importerClassName = importerClassName;
    }

    public String getTheDay() {
        return theDay;
    }

    public void setTheDay(String theDay) {
        this.theDay = theDay;
    }
}
