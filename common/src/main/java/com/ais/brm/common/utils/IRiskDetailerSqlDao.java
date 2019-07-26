package com.ais.brm.common.utils;

import java.io.IOException;
import java.util.List;

/**
 * Created by zhaocaiwen on 2017/4/10.
 * @author zhaocw
 */
public interface IRiskDetailerSqlDao {

    void testSqlWithParams(String s, List<Object> paramValues,String datasourceId) throws IOException;

    void testSql(String sql,String datasourceId) throws IOException;

    List<String> getColumnNames(String sql,String datasourceId);
}
