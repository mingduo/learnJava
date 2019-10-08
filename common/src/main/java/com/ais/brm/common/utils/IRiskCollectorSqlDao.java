package com.ais.brm.common.utils;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.ais.brm.common.domain2.RiskDetailerSqlBean;

/**
 * Created by zhaocaiwen on 2017/4/10.
 *
 * @author zhaocw
 */
public interface IRiskCollectorSqlDao {

    void testSqlWithParams(String s, List<Object> paramValues, String datasourceId) throws IOException;

    void testSql(String sql, String datasourceId) throws IOException;

    Map<String, Object> previewSqlWithParams(String s, List<Object> paramValues, String datasourceId) throws IOException;

    Map<String, Object> previewSqlWithParams2(RiskDetailerSqlBean bean) throws IOException;

    Map<String, Object> previewSql(String sql, String datasourceId) throws IOException;

}
