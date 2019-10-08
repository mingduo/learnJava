package com.ais.brm.common.domain2;

import java.util.List;

/**
 * 用于配置型风险点的THE_SQL字段解析结果保存.
 * Created by zhaocw on 2017-1-9.
 *
 * @author zhaocw
 */
public class RiskCollectorSqlBean {
    private String theSql;//完整的the_sql内容，含sql和参数配置
    private String realSql;//前面的sql.
    private List<RiskCollectorSqlParam> params;

    //数据源类型，方便动态切换校验数据库
    private String datasourceId;

    public String getDatasourceId() {
        return datasourceId;
    }

    public void setDatasourceId(String datasourceId) {
        this.datasourceId = datasourceId;
    }

    @Override
    public String toString() {
        return "RiskCollectorSqlBean{" +
                "theSql='" + theSql + '\'' +
                ", realSql='" + realSql + '\'' +
                '}';
    }

    public String getTheSql() {
        return theSql;
    }

    public void setTheSql(String theSql) {
        this.theSql = theSql;
    }

    public String getRealSql() {
        return realSql;
    }

    public void setRealSql(String realSql) {
        this.realSql = realSql;
    }

    public List<RiskCollectorSqlParam> getParams() {
        return params;
    }

    public void setParams(List<RiskCollectorSqlParam> params) {
        this.params = params;
    }
}
