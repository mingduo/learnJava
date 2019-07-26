package com.ais.brm.common.hive;

import com.ais.brm.common.utils.CollectionUtils;
import com.ais.brm.common.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.lang.reflect.*;
import java.sql.*;
import java.sql.Array;
import java.util.*;

/**
 * hive jdbc method tools.
 * Created by zhaocw on 2016-8-18.
 *
 * @author zhaocw
 */
@Component
public class HiveUtils {
    static Logger log = LoggerFactory.getLogger(HiveUtils.class);
    @Autowired @Lazy
    private HiveConnectionFactory hiveConnectionFactory;

    /**
     * 执行SQL，包括指定的列，通用的查询行模块,支持查询一行或者多行数据.
     *
     * @param conn
     * @param sql
     * @param result
     * @param columnNames
     * @param onlyOneRow  只查一行.
     * @throws Exception
     */
    private void processConn(Connection conn, String sql,
                             List<Map<String, Object>> result,
                             String[] columnNames, boolean onlyOneRow)
            throws Exception {
        if (conn != null) {
            log.debug("got connection ok");
            try (Statement stmt = conn.createStatement();
                 ResultSet res = stmt.executeQuery(sql)) {
                while (res.next()) {
                    Map<String, Object> map = new HashMap<>();
                    constructRow(map, res, columnNames);
                    result.add(map);
                    if (onlyOneRow) {
                        break;//只查询一行.
                    }
                }
            }
        }else {
            log.warn("conn is null");
        }
    }

    /**
     * 执行sql，包括所有列.
     *
     * @param conn
     * @param sql
     * @param result
     * @throws Exception
     */
    private void processConn2(Connection conn, String sql, List<Map<String, Object>> result)
            throws Exception {
        if (conn != null) {
            log.debug("got connection ok");
            try (Statement stmt = conn.createStatement();
                 ResultSet res = stmt.executeQuery(sql)) {
                ResultSetMetaData rsmd = res.getMetaData();
                while (res.next()) {
                    Map<String, Object> map = new HashMap<>();
                    constructRow(map, res, rsmd);
                    result.add(map);
                }
            }
        }else {
            log.warn("conn is null");
        }
    }

    /**
     * 查询一行数据.
     *
     * @param columnNames 要查询的列名.
     */
    public Map<String, Object> queryForObject(IHiveSettings hiveSettings,
                                              String sql, String... columnNames) throws Exception {
        try (Connection conn = hiveConnectionFactory.getConnection(hiveSettings)) {
            return queryForObject(conn,sql,columnNames);
        }
    }

    public Map<String, Object> queryForObject(Connection connection,
                                              String sql, String... columnNames) throws Exception {
        if(!isConnectionOk(connection)) {
            throw new IllegalArgumentException("bad connection param.");
        }
        Map<String, Object> result = null;
        {
            log.debug("queryForObject sql={}", sql);
            //check inputs
            if (StringUtils.isEmpty(sql) || CollectionUtils.isEmpty(columnNames)) {
                throw new IllegalArgumentException("bad queryForObject inputs,sql or columnNames is empty");
            }
            List<Map<String, Object>> list = new ArrayList<>();
            processConn(connection, sql, list, columnNames, true);
            if (list.size() > 0) {
                result = list.get(0);
            }else {
                log.warn("no data found after execute sql {}",sql);
            }
        }
        return result;
    }


    /**
     * 查询N行数据.
     * 注意：此方法参数的SQL不能有？，如果需要?，请调用queryForPreparedObjects.
     *
     * @param columnNames 要查询的列名.
     */
    public List<Map<String, Object>> queryForObjects(
            IHiveSettings hiveSettings, String sql, String... columnNames) throws Exception {
        try (Connection conn = hiveConnectionFactory.getConnection(hiveSettings)) {
            return queryForObjects(conn,sql,columnNames);
        }
    }

    public List<Map<String, Object>> queryForObjects(
            Connection connection, String sql, String... columnNames) throws Exception {
        if(!isConnectionOk(connection)) {
            throw new IllegalArgumentException("bad connection param.");
        }
        List<Map<String, Object>> result = new ArrayList<>();
        {
            log.debug("queryForObjects sql={}", sql);
            //check inputs
            if (StringUtils.isEmpty(sql) || CollectionUtils.isEmpty(columnNames)) {
                throw new IllegalArgumentException("bad queryForObjects inputs,sql or columnNames is empty");
            }
            processConn(connection, sql, result, columnNames, false);
        }
        return result;
    }

    /**
     * 查询N行数据.
     */
    public List<Map<String, Object>> queryForObjects(IHiveSettings hiveSettings, String sql) throws Exception {
        List<Map<String, Object>> result = new ArrayList<>();
        try (Connection conn = hiveConnectionFactory.getConnection(hiveSettings)) {
            log.debug("queryForObjects sql={}", sql);
            //check inputs
            if (StringUtils.isEmpty(sql)) {
                throw new IllegalArgumentException("bad queryForObjects inputs,sql or columnNames is empty");
            }
            processConn2(conn, sql, result);
        }
        return result;
    }


    /**
     * 查询N行数据.
     *
     * @param sql         the preparedsql statements, it may contains '?' for params.
     * @param paramValues 和上述sql对应的问号的值列表
     * @param columnNames 结果返回的列名列表.
     */
    public List<Map<String, Object>> queryForPreparedObjects(
            IHiveSettings hiveSettings,
            String sql, List<Object> paramValues, List<String> columnNames)
            throws Exception {
        try (Connection conn = hiveConnectionFactory.getConnection(hiveSettings)) {
            return queryForPreparedObjects(conn,sql,paramValues,columnNames);
        }
    }

    public List<Map<String, Object>> queryForPreparedObjects(
            Connection connection,
            String sql, List<Object> paramValues, List<String> columnNames)
            throws Exception {
        if(!isConnectionOk(connection)) {
            throw new IllegalArgumentException("bad connection param.");
        }
        List<Map<String, Object>> result = new ArrayList<>();
        {
            log.debug("queryForObjects sql={}", sql);
            //check inputs
            if (StringUtils.isEmpty(sql) || CollectionUtils.isEmpty(columnNames)) {
                log.warn("warning: queryForObjects " +
                        "inputs,sql or columnNames is empty");
            }
            if (sql.contains("?") && CollectionUtils.isEmpty(paramValues)) {
                throw new IllegalArgumentException("bad " +
                        "queryForObjects inputs,sql has param but no paramValues supplied.");
            }
            processConn3(connection, sql, result, paramValues, columnNames);
        }
        return result;
    }

    private void processConn3(Connection conn, String sql, List<Map<String, Object>> result,
                              List<Object> paramValues, List<String> columnNames)
            throws Exception {
        if (conn != null) {
            log.debug("got connection ok");
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                setStatementValues(stmt, paramValues);
                log.debug("start executing prepared sql " + sql);
                long now = System.currentTimeMillis();
                try (ResultSet res = stmt.executeQuery()) {
                    log.debug("prepared sql {} executed ok,cost {} seconds",
                            sql, ((System.currentTimeMillis() - now) / 1000));
                    while (res.next()) {
                        Map<String, Object> map = new HashMap<>();
                        if (CollectionUtils.isNotEmpty(columnNames)) {
                            for (String c : columnNames) {
                                map.put(c, res.getObject(c));
                            }
                        } else {
                            ResultSetMetaData rsmd = res.getMetaData();
                            //while (res.next())
                            {
                                constructRow(map, res, rsmd);
                            }
                        }
                        result.add(map);
                    }
                }
            }
        } else {
            log.warn("conn is null");
        }
    }

    private void setStatementValues(PreparedStatement stmt, List<Object> paramValues) throws Exception {
        for (int i = 0; i < paramValues.size(); i++) {
            Object pv = paramValues.get(i);
            int pos = i + 1;
            if (pv instanceof Integer) {
                stmt.setInt(pos, (Integer) pv);
            } else if (pv instanceof Long) {
                stmt.setLong(pos, (Long) pv);
            } else if (pv instanceof Object[]) {
                Array array = stmt.getConnection().createArrayOf("VARCHAR",(Object[])pv);
                stmt.setArray(pos,array);
            }
            else {
                stmt.setString(pos, pv.toString());
            }
        }
    }

    /**
     * 查询N行数据.
     *
     * @param sql         the preparedsql statements, it may contains '?' for params.
     * @param paramValues 和上述sql对应的问号的值列表
     */
    public List<Map<String, Object>> queryForPreparedObjects(
            Connection connection,
            String sql, List<Object> paramValues)
            throws Exception {
    	log.debug("@@queryForPreparedObjects");
        if(!isConnectionOk(connection)) {
            throw new IllegalArgumentException("bad connection param");
        }
        List<Map<String, Object>> result = new ArrayList<>();
        {
            log.debug("queryForPreparedObjects sql={}", sql);
            //check inputs
            if (StringUtils.isEmpty(sql)) {
                throw new IllegalArgumentException("bad queryForPreparedObjects " +
                        "inputs,sql or columnNames is empty");
            }
            if (sql.contains("?") && CollectionUtils.isEmpty(paramValues)) {
                throw new IllegalArgumentException("bad " +
                        "queryForPreparedObjects inputs,sql has param but no paramValues supplied.");
            }

            processConn3(connection, sql, result, paramValues, null);
        }
        return result;
    }

    private boolean isConnectionOk(Connection connection) throws SQLException {
        return connection!=null/*&&!connection.isClosed()*/;
    }


    /**
     * 查询N行数据.
     *
     * @param sql         the preparedsql statements, it may contains '?' for params.
     * @param paramValues 和上述sql对应的问号的值列表
     */
    public List<Map<String, Object>> queryForPreparedObjects(
            IHiveSettings hiveSettings,
            String sql, List<Object> paramValues)
            throws Exception {
    	log.debug("url:"+hiveSettings.getHiveJdbcUrl()+
    			",username:"+hiveSettings.getHiveUserName()+
    			",password:"+hiveSettings.getHivePasswd());
        try (Connection conn = hiveConnectionFactory.getConnection(hiveSettings)) {
        	log.debug("##queryForPreparedObjects");
            return queryForPreparedObjects(conn,sql,paramValues);
        }catch (Exception e) {
        	log.debug("hiveConnectionFactory.getConnection=>exception:"+e.getMessage());
        	return null;
		}
    }

    private void constructRow(Map<String, Object> result,
                              ResultSet res, String... columnNames) throws SQLException {
        for (String c : columnNames) {
            result.put(c, res.getObject(c));
        }
    }

    private void constructRow(Map<String, Object> result,
                              ResultSet res, ResultSetMetaData rsmd) throws SQLException {
        int count = rsmd.getColumnCount();
        for (int i = 1; i <= count; i++) {
            String c = rsmd.getColumnName(i);
            result.put(c, res.getObject(c));
        }
    }
}