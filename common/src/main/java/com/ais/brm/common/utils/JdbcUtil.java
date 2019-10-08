package com.ais.brm.common.utils;

import com.ais.brm.common.domain.database.DatabaseInfo;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.*;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

/**
 * 数据库操作的工具类.
 * Created by xuechen on 2016-10-13.
 *
 * @author xuechen
 */
public class JdbcUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(JdbcUtil.class);
    private static DataSource dataSource = null;
    private static Map<String, String> driverMap = new HashMap<String, String>();
    public static final String TYPE_JSON = "json";
    public static final String TYPE_XML = "xml";
    public static final String TYPE_TIMESTAMP = "timestamp";
    public static final String TYPE_TIME = "time";
    public static final String TYPE_DATE = "date";
    public static final String TYPE_INT = "int";
    public static final String TYPE_OBJECT = "object";
    public static final String TYPE_DECIMAL = "decimal";
    public static final String TYPE_TEXT = "text";
    public final static int RETURN_OK = 0;// 返回成功
    public final static int RETURN_FAILD = -1;// 返回失败
    public final static int DB_SUBMIT = 500;// 数据库批量提交1000条

    public static final int ORACLE = 1;
    public static final int DB2 = 2;
    public static final int MSSQL = 3;
    public static final int MYSQL = 4;
    public static final int HIVE = 5;

    /**
     * @throws
     * @description
     * @see
     */
    public static void createPool() {
        synchronized (JdbcUtil.class) {
            // 通过Map作为参数获取连接
            Map<String, String> paramMap = new HashMap<String, String>();
            // 向map中传递参数
            paramMap.put("url", "jdbc:postgresql://192.168.11.201:9532/etldb");// 数据库地址
            paramMap.put("username", "etl");// 用户名
            paramMap.put("password", "etl");// 密码
            // 申请连接的时候检测，如果空闲时间大于timeBetweenEvictionRunsMillis，执行validationQuery检测连接是否有效。
            paramMap.put("testWhileIdle", "true");
            paramMap.put("validationQuery", "SELECT 1");
            paramMap.put("initialSize", "3");
            // 创建数据源
            try {
                dataSource = DruidDataSourceFactory.createDataSource(paramMap);
            } catch (Exception e) {
                LOGGER.error("创建内嵌式数据池失败", e);
            }
        }
    }

    /**
     * @throws
     * @description
     * @see
     */
    public DataSource getDataSource() {
        return dataSource;
    }

    /**
     * @throws
     * @description
     * @see
     */
    public static Connection getConnection() {
        Connection conn = null;
        synchronized (JdbcUtil.class) {
            try {
                conn = dataSource.getConnection();
            } catch (SQLException e) {
                LOGGER.error("获得数据库连接失败", e);
            }
        }
        return conn;
    }


    public static int getDBType(JdbcTemplate jdbcTemplate) {
        Connection connection = null;
        try {
            connection = jdbcTemplate.getDataSource().getConnection();
            String databaseProductName = connection.
                    getMetaData().getDatabaseProductName();
            if (("Oracle").equals(databaseProductName)) {
                return ORACLE;
            } else if ("MySQL".equals(databaseProductName)) {
                return MYSQL;
            }

        } catch (Exception e) {
            LOGGER.error("获得数据库连接失败，请检查数据库配置是否正确", e);
        } finally {
            DbUtils.closeQuietly(connection);

        }
        return 0;
    }

    /**
     * @throws
     * @description
     * @see
     */
    public static Connection getConnection(DatabaseInfo databaseInfo) {
        Connection conn = null;
        try {
            if (DB2 == databaseInfo.getDatabaseType()) {
                conn = getConnection("com.ibm.db2.jcc.DB2Driver", databaseInfo.getJdbcUrl(),
                        databaseInfo.getName(), databaseInfo.getPassword());
            } else if (ORACLE == databaseInfo.getDatabaseType()) {
                conn = getConnection("oracle.jdbc.driver.OracleDriver", databaseInfo.getJdbcUrl(),
                        databaseInfo.getName(), databaseInfo.getPassword());
            } else if (MSSQL == databaseInfo.getDatabaseType()) {
                conn = getConnection("net.sourceforge.jtds.jdbc.Driver", databaseInfo.getJdbcUrl(),
                        databaseInfo.getName(), databaseInfo.getPassword());
            } else if (MYSQL == databaseInfo.getDatabaseType()) {
                conn = getConnection("org.gjt.mm.mysql.Driver", databaseInfo.getJdbcUrl(),
                        databaseInfo.getName(), databaseInfo.getPassword());
            } else if (HIVE == databaseInfo.getDatabaseType()) {
                if (databaseInfo.getJdbcUrl().contains("hive2")) {
                    conn = getConnection("org.apache.hive.jdbc.HiveDriver",
                            databaseInfo.getJdbcUrl(),
                            databaseInfo.getName(),
                            databaseInfo.getPassword());
                } else {
                    conn = getConnection("org.apache.hadoop.hive.jdbc.HiveDriver",
                            databaseInfo.getJdbcUrl(),
                            databaseInfo.getName(),
                            databaseInfo.getPassword());
                }
            } else {
                conn = getConnection("sun.jdbc.odbc.JdbcOdbcDriver", databaseInfo.getJdbcUrl(),
                        databaseInfo.getName(), databaseInfo.getPassword());
            }
        } catch (Exception e) {
            LOGGER.error("获得数据库连接失败，请检查数据库配置是否正确", e);
        }
        return conn;
    }

    /**
     * @param driver   驱动类
     * @param url      数据库连接URl
     * @param userName 数据库连接用户名
     * @param password 数据库连接密码
     * @return 返回数据库连接
     * @throws
     * @description 获取数据库连接，连接信息由调用者提供
     * @see
     */
    public static Connection getConnection(String driver, String url, String userName, String password) {
        try {
            if ("org.postgresql.Driver".equalsIgnoreCase(driver)) {
                if (driverMap.get(driver) == null) {
                    Class.forName(driver, false, JdbcUtil.class.getClassLoader());
                    driverMap.put(driver, driver);
                }
            } else {
                if (driverMap.get(driver) == null) {
                    Class.forName(driver);
                    driverMap.put(driver, driver);
                }
            }
            Connection connection = DriverManager.getConnection(url, userName, password);
            return connection;
        } catch (ClassNotFoundException e) {
            LOGGER.error("加载驱动失败，请检查驱动类名称或者驱动包是否在classpath中" + driver, e);
        } catch (SQLException e) {
            LOGGER.error("获得数据库连接失败，请检查数据库配置是否正确", e);
        }
        return null;
    }

    /**
     * @param connection 数据库连接
     * @param sql        要执行的sql语句
     * @param params     替换sql语句中的?,如果没有参数要替换sql的?，则该Object... params可以不填写
     * @return 返回一个List<Map < K, V>>,每一行就相当于结果集中的每一行数据，Map中的key是列明，value是列的值
     * @throws Exception
     * @throws
     * @description 查询，数据库连接由调用者提供
     * @see
     */
    public static List<Map<String, Object>> queryForMapList(Connection connection, String sql, Object... params)
            throws Exception {
        try {
            QueryRunner queryRunner = new QueryRunner();
            return queryRunner.query(connection, sql,
                    new MapListHandler(), params);
        } catch (SQLException e) {
            LOGGER.error("查询失败，sql语句: " + sql, e);
            throw new Exception(e);
        }
    }

    /**
     * @param connection 数据库连接
     * @param clazz      返回结果要包装的java类型
     * @param sql        要执行的sql语句
     * @param params     替换sql语句中的?,如果没有参数要替换sql的?，则该Object... params可以不填写
     * @return 返回按clazz类型包装后的一个List集合
     * @throws
     * @description 查询，数据库连接由调用者提供
     * @see
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static <T> List<T> queryForList(Connection connection, Class<T> clazz, String sql, Object... params) {
        try {
            QueryRunner queryRunner = new QueryRunner();
            List<T> results = (List<T>) queryRunner.query(connection, sql,
                    new BeanListHandler(clazz), params);
            return results;
        } catch (SQLException e) {
            LOGGER.error("查询失败，sql语句: " + sql, e);
        } finally {
            DbUtils.closeQuietly(connection);
        }
        return null;
    }

    /**
     * @param connection 数据库连接
     * @param clazz      返回结果要包装的java类型
     * @param sql        要执行的sql语句
     * @param close      是否关闭流
     * @param params     替换sql语句中的?,如果没有参数要替换sql的?，则该Object... params可以不填写
     * @return 返回按clazz类型包装后的一个List集合
     * @throws SQLException
     * @description 查询，数据库连接由调用者提供
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static <T> List<T> queryForList(Connection connection, Class<T> clazz, String sql,
                                           boolean close, Object... params) throws SQLException {
        List<T> results = null;
        try {
            QueryRunner queryRunner = new QueryRunner();
            results = (List<T>) queryRunner.query(connection, sql, new BeanListHandler(clazz), params);
        } finally {
            if (close) {
                DbUtils.closeQuietly(connection);
            }
        }
        return results;
    }

    /**
     * @param connection 数据库连接
     * @param clazz      返回结果要包装的java类型
     * @param sql        要执行的sql语句
     * @param close      是否关闭流
     * @param params     替换sql语句中的?,如果没有参数要替换sql的?，则该Object... params可以不填写
     * @return 返回按clazz类型包装后的一个List集合
     * @throws SQLException
     * @description 查询，数据库连接由调用者提供
     */
    public static List<Object[]> queryForList(Connection connection, String sql, boolean close, Object... params)
            throws SQLException {
        List<Object[]> results = null;
        try {
            QueryRunner queryRunner = new QueryRunner();
            results = queryRunner.query(connection, sql, new ArrayListHandler());
        } finally {
            if (close) {
                DbUtils.closeQuietly(connection);
            }
        }
        return results;
    }

    /**
     * @param connection 数据库连接
     * @param clazz      返回结果要包装的java类型,请注意该类中的属性名和 表中的字段名称一样
     * @param sql        要执行的sql语句
     * @param params     替换sql语句中的?,如果没有参数要替换sql的?，则该Object... params可以不填写
     * @return 返回按clazz类型包装后的java对象
     * @throws
     * @description 查询，查询结果为一条记录，要提供数据库连接
     * @see
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static <T> T query(Connection connection, Class<T> clazz, String sql, Object... params) {
        try {
            QueryRunner queryRunner = new QueryRunner();
            T result = (T) queryRunner.query(connection, sql, new BeanHandler(clazz), params);
            return result;
        } catch (SQLException e) {
            LOGGER.error("查询失败，sql语句: " + sql, e);
        } finally {
            DbUtils.closeQuietly(connection);
        }
        return null;
    }

    /**
     * @throws
     * @description
     * @see
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static <T> T query(Connection connection, Class<T> clazz, String sql, boolean ifcolose) {
        try {
            QueryRunner queryRunner = new QueryRunner();
            T result = (T) queryRunner.query(connection, sql, new BeanHandler(clazz));
            return result;
        } catch (SQLException e) {
            LOGGER.error("查询失败，sql语句: " + sql, e);
        } finally {
            if (true == ifcolose) {
                DbUtils.closeQuietly(connection);
            }
        }
        return null;
    }

    /**
     * @throws
     * @description
     * @see
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static List<Object> query(Connection connection, String sql, Object... params) {
        try {
            List<Object> resultList = new ArrayList<Object>();
            QueryRunner queryRunner = new QueryRunner();
            resultList = (List<Object>) queryRunner.query(connection, sql, new ColumnListHandler(), params);
            return resultList;
        } catch (SQLException e) {
            LOGGER.error("查询失败，sql语句: " + sql, e);
        } finally {
            DbUtils.closeQuietly(connection);
        }
        return null;

    }

    /**
     * @throws
     * @description
     * @see
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static List<Object> query(Connection connection, String sql, boolean ifclose, Object... params) {
        try {
            List<Object> resultList = new ArrayList<Object>();
            QueryRunner queryRunner = new QueryRunner();
            resultList = (List<Object>) queryRunner.query(connection, sql, new ColumnListHandler(), params);
            return resultList;
        } catch (SQLException e) {
            LOGGER.error("查询失败，sql语句: " + sql, e);
        } finally {
            if (ifclose == true) {
                DbUtils.closeQuietly(connection);
            }
        }
        return null;

    }

    /**
     * @param connection 数据库连接
     * @param sql        要执行的sql语句
     * @param params     替换sql语句中的?,如果没有参数要替换sql的?，则该Object... params可以不填写
     * @return 返回数据库影响的行数
     * @throws
     * @description 添加，更新，删除
     * @see
     */
    public static int execute(Connection connection, String sql, Object... params) {
        try {
            QueryRunner queryRunner = new QueryRunner();
            return queryRunner.update(connection, sql, params);
        } catch (SQLException e) {
            LOGGER.error("sql执行失败，sql语句: " + sql, e);
        } finally {
            DbUtils.commitAndCloseQuietly(connection);
        }
        return 0;
    }

    /**
     * @throws
     * @description
     * @see
     */
    public static int execute_close(Connection connection, String sql, boolean ifcolse, Object... params) {
        try {
            QueryRunner queryRunner = new QueryRunner();
            return queryRunner.update(connection, sql, params);
        } catch (SQLException e) {
            LOGGER.error("sql执行失败，sql语句: " + sql, e);
        } finally {
            if (ifcolse == true) {
                DbUtils.commitAndCloseQuietly(connection);
            }
        }
        return 0;
    }

    /**
     * @param connection 数据库连接
     * @param clazz      返回结果要包装的java类型,请注意该类中的属性名和 表中的字段名称一样
     * @param sql        要执行的sql语句
     * @param params     替换sql语句中的?,如果没有参数要替换sql的?，则该Object... params可以不填写
     * @return 返回按clazz类型包装后的java对象
     * @throws SQLException
     * @throws
     * @description 查询，查询结果为一条记录，要提供数据库连接
     * @see
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static <T> T query(Connection connection, boolean isCloseConnection, Class<T> clazz, String sql,
                              Object... params) throws SQLException {
        QueryRunner queryRunner = new QueryRunner();
        T result;
        try {
            result = (T) queryRunner.query(connection, sql, new BeanHandler(clazz), params);
            return result;
        } finally {
            if (isCloseConnection) {
                DbUtils.commitAndCloseQuietly(connection);
            }
        }
    }

    /**
     * 预编译方式执行sql
     *
     * @param conn
     * @param sql
     * @return
     * @throws Exception
     */
    public static <T> int excuteByPre(Connection conn, String sql) throws Exception {
        int iReturn = RETURN_FAILD;
        PreparedStatement stmt = null;
        stmt = conn.prepareStatement(sql);
        stmt.execute();
        return iReturn;
    }

    /**
     * ====================================
     * 批量修改=================================================
     *
     * @param conn       JDBC连接
     * @param tableName  表名
     * @param field      字段名
     * @param property   字段名对应Object属性参数
     * @param whereField 条件字段名
     * @param property   条件字段名对应Object属性参数
     * @param list       参数值
     * @return
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     * @throws NoSuchMethodException
     * @throws InstantiationException
     * @throws SQLException
     */
    public static <T> int batchUpdateByPre(Connection conn, String tableName, String[] field,
                                           String[] whereField, String[] whereProperty, String[] property, List<T> list) throws Exception {
        int iReturn = RETURN_FAILD;
        conn.setAutoCommit(false);
        PreparedStatement stmt = null;
        StringBuffer sqlBuffer = new StringBuffer();
        sqlBuffer.append("update ")// 拼接update sql语句
                .append(tableName).append(" set ");
        int fieldLen = field.length;
        for (int i = 0; i < fieldLen; i++) {
            sqlBuffer.append(field[i]).append("=?").append(",");
        }
        sqlBuffer = sqlBuffer.deleteCharAt(sqlBuffer.length() - 1);
        sqlBuffer.append(" where ");

        int whereLen = whereField.length;
        for (int i = 0; i < whereLen; i++) {
            if (i == whereLen - 1) {
                sqlBuffer.append(whereField[i]).append("=").append("?");
            } else {
                sqlBuffer.append(whereField[i]).append("=").append("? and ");
            }
        }

        // update语句拼接完成,进行预编译
        stmt = conn.prepareStatement(sqlBuffer.toString());
        String[] newProperty = new String[property.length + whereProperty.length];
        for (int i = 0; i < newProperty.length; i++) {
            if (i < property.length) {
                newProperty[i] = property[i];
            } else {
                newProperty[i] = whereProperty[i - property.length];
            }
        }

        // list对象计数器,达到DB_FETCH数值时进行批量提交
        int icount = 0;
        // 设置PreparedStatement参数，从1开始
        for (Object entity : list) {
            injectValueToPst(entity, newProperty, stmt, 1);
            stmt.addBatch();
            icount++;
            if (icount % DB_SUBMIT == 0) {
                stmt.executeBatch();
            }
        }
        if (icount % DB_SUBMIT != 0) {
            stmt.executeBatch();
        }

        iReturn = RETURN_OK;
        return iReturn;
    }

    /**
     * ========================================预编译方式批量insert操作==================
     * ===============================
     *
     * @param conn      jdbc连接
     * @param tableName 表名
     * @param field     插入的字段
     * @param property  插入的<T>属性
     * @param list      对象集合
     * @return
     * @throws Exception
     */
    public static <T> int batchInsertByPre(Connection conn, String tableName, String[] field,
                                           String[] property, List<T> list) throws Exception {
        int iReturn = RETURN_FAILD;
        conn.setAutoCommit(false);
        PreparedStatement stmt = null;
        StringBuffer sqlBuffer = new StringBuffer();
        StringBuffer valSql = new StringBuffer();
        sqlBuffer.append("insert ")// 拼接insert sql语句
                .append(" into ").append(tableName).append("(");
        int fieldLen = field.length;
        for (int i = 0; i < fieldLen; i++) {
            sqlBuffer.append(field[i]).append(",");
            valSql.append("?,");
        }
        sqlBuffer = sqlBuffer.deleteCharAt(sqlBuffer.length() - 1);
        valSql = valSql.deleteCharAt(valSql.length() - 1);

        sqlBuffer.append(") values (").append(valSql).append(")");

        // update语句拼接完成,进行预编译
        stmt = conn.prepareStatement(sqlBuffer.toString());

        // list对象计数器,达到DB_FETCH数值时进行批量提交
        int icount = 0;
        // 设置PreparedStatement参数，从1开始
        for (Object entity : list) {
            injectValueToPst(entity, property, stmt, 1);
            stmt.addBatch();
            icount++;
            if (icount % DB_SUBMIT == 0) {
                stmt.executeBatch();
            }
        }
        if (icount % DB_SUBMIT != 0) {
            stmt.executeBatch();
        }
        iReturn = RETURN_OK;
        return iReturn;
    }

    /**
     * @param conn        数据库连接
     * @param sql         sql只可以是ddl或者是dml语句，不能是查询语句
     * @param paramTypes  预编译sql中的每个参数的类型，按照sql中的顺序排列
     * @param paramValues 要操作的每一行的每一个参数的值
     * @return 每一条sql执行完成之后影响的行数
     * @throws SQLException
     * @description 批量执行预编译sql
     */
    public static int[] batchExecute(Connection conn, String sql, String[] paramTypes,
                                     Object[][] paramValues) throws SQLException {
        int[] ret = new int[]{};
        PreparedStatement stmt = null;
        stmt = conn.prepareStatement(sql);
        int icount = 0;
        for (int i = 0; i < paramValues.length; i++) {
            for (int j = 0; j < paramTypes.length; j++) {
                Object paramValue = paramValues[i][j];
                int paramIndex = j + 1;
                if (null == paramValue) {
                    stmt.setObject(paramIndex, paramValue);
                } else {
                    if (TYPE_XML.equalsIgnoreCase(paramTypes[j])) {
                        SQLXML xml = stmt.getConnection().createSQLXML();
                        xml.setString(paramValue.toString());
                        stmt.setSQLXML(paramIndex, xml);
                    } else if (TYPE_TIMESTAMP.equalsIgnoreCase(paramTypes[j])) {
                        SimpleDateFormat dateFormat = new SimpleDateFormat
                                ("yyyy-MM-dd HH:mm:ss.SSS");
                        java.sql.Timestamp stmp = java.sql.Timestamp.
                                valueOf(dateFormat.format(paramValue));
                        stmt.setTimestamp(paramIndex, stmp);
                    } else if (TYPE_TIME.equalsIgnoreCase(paramTypes[j])) {
                        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
                        java.sql.Time time = java.sql.Time.
                                valueOf(dateFormat.format(paramValue));
                        stmt.setTime(paramIndex, time);
                    } else if (TYPE_DATE.equalsIgnoreCase(paramTypes[j])) {
                        SimpleDateFormat dateFormat = new SimpleDateFormat
                                ("yyyy-MM-dd HH:mm:ss.SSS");
                        stmt.setTimestamp(paramIndex, java.sql.Timestamp.
                                valueOf(dateFormat.format(paramValue)));
                    } else if (TYPE_JSON.equalsIgnoreCase(paramTypes[j])) {
                    } else if (TYPE_INT.equalsIgnoreCase(paramTypes[j])) {
                        stmt.setInt(paramIndex, (Integer) paramValue);
                    } else if (TYPE_DECIMAL.equalsIgnoreCase(paramTypes[j])) {
                        stmt.setBigDecimal(paramIndex, (BigDecimal) paramValue);
                    } else if (TYPE_TEXT.equalsIgnoreCase(paramTypes[j])) {
                        stmt.setString(paramIndex, String.valueOf(paramValue));
                    } else {
                        stmt.setObject(paramIndex, paramValue);
                    }
                }
            }
            stmt.addBatch();
            icount++;
            if (icount % DB_SUBMIT == 0) {
                int[] subRet = stmt.executeBatch();
                ret = ArrayUtils.addAll(subRet, ret);
            }
        }
        if (icount % DB_SUBMIT != 0) {
            int[] subRet = stmt.executeBatch();
            ret = ArrayUtils.addAll(subRet, ret);
        }
        return ret;
    }

    /**
     * =====================根据参数属性对参数进行预编译==========================
     *
     * @param t
     * @param property
     * @param pst
     * @param index
     * @return
     * @throws SQLException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InstantiationException
     */
    private static <T> int injectValueToPst(T t, String[] property, PreparedStatement pst,
                                            int index) throws Exception {
        Class<?> clazz = t.getClass();
        int len = property.length;
        for (int i = 0; i < len; i++) {
            try {
                Method method = clazz.getMethod("get" +
                        property[i].substring(0, 1).toUpperCase() +
                        property[i].substring(1, property[i].length()));
                Object param = method.invoke(t);
                if ("jobXmlDefine".equals(property[i])) {
                    SQLXML xml = pst.getConnection().createSQLXML();
                    xml.setString(param.toString());
                    pst.setSQLXML(index, xml);
                } else if (param instanceof Timestamp) {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
                    java.sql.Timestamp stmp = java.sql.Timestamp.valueOf(dateFormat.format(param));
                    pst.setTimestamp(index, stmp);
                } else if (param instanceof Time) {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
                    java.sql.Time time = java.sql.Time.valueOf(dateFormat.format(param));
                    pst.setTime(index, time);
                } else if (param instanceof Date) {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
                    pst.setTimestamp(index, java.sql.Timestamp.valueOf(dateFormat.format(param)));
                } else if (param instanceof JSONObject) {
                } else if (param instanceof Integer) {
                    pst.setInt(index, (Integer) param);
                } else {
                    pst.setObject(index, param);
                }
                index++;
            } catch (SecurityException e) {
                LOGGER.debug(clazz.getName() + "属性为" + property[i] + "反射失败");
            }
        }
        return index;
    }

    /**
     * @param connection 数据库连接
     * @param sql        要执行的sql语句
     * @param close      是否关闭流
     * @param params     替换sql语句中的?,如果没有参数要替换sql的?，则该Object... params可以不填写
     * @return 返回数据库影响的行数
     * @throws
     * @description 添加，更新，删除
     * @see
     */
    public static int execute(Connection connection, boolean close, String sql, Object... params) {
        try {
            QueryRunner queryRunner = new QueryRunner();
            return queryRunner.update(connection, sql, params);
        } catch (SQLException e) {
            LOGGER.error("sql执行失败，sql语句: " + sql, e);
        } finally {
            if (close) {
                DbUtils.commitAndCloseQuietly(connection);
            }
        }
        return 0;
    }

    /**
     * @throws
     * @description
     * @see
     */
    public static int delete(Connection conn, boolean close, JSONObject jsonObject,
                             String tableName, String... primaryKeys) {
        int iReturn = RETURN_FAILD;
        try {
            Object[] params = new Object[primaryKeys.length];
            StringBuffer deleteSql = new StringBuffer("delete from ");
            deleteSql.append(tableName);
            deleteSql.append(" where 1=1");
            for (int i = 0; i < primaryKeys.length; i++) {
                deleteSql.append(" and ");
                deleteSql.append(primaryKeys[i]);
                deleteSql.append(" =?");
                params[i] = jsonObject.get(primaryKeys[i]);
            }
            QueryRunner queryRunner = new QueryRunner();
            queryRunner.update(conn, deleteSql.toString(), params);
            iReturn = RETURN_OK;
        } catch (Exception e) {
            LOGGER.error("sql执行异常", e);
        } finally {
            if (close) {
                DbUtils.commitAndCloseQuietly(conn);
            }
        }
        return iReturn;
    }

    /**
     * @throws
     * @description
     * @see
     */
    public static int execute(Connection conn, boolean close, JSONObject jsonObject, String tableName) {
        int iReturn = RETURN_FAILD;
        try {
            StringBuffer insertSql = new StringBuffer("insert into ");
            StringBuffer insertValueSql = new StringBuffer(" values(");
            insertSql.append(tableName);
            insertSql.append("(");
            int keySize = jsonObject.keySet().size();
            Object[] params = new Object[keySize];
            int i = 0;
            for (String key : jsonObject.keySet()) {
                insertSql.append(key);
                insertSql.append(",");
                insertValueSql.append("?,");
                params[i] = jsonObject.get(key);
                i++;
            }
            insertSql.replace(insertSql.lastIndexOf(","), insertSql.length(), ")");
            insertValueSql.replace(insertValueSql.lastIndexOf(","), insertValueSql.length(), ")");
            insertSql.append(insertValueSql);
            QueryRunner queryRunner = new QueryRunner();
            int updateRows = queryRunner.update(conn, insertSql.toString(), params);
            if (updateRows > 0) {
                iReturn = RETURN_OK;
            }
        } catch (Exception e) {
            LOGGER.error("sql执行异常", e);
        } finally {
            if (close) {
                DbUtils.commitAndCloseQuietly(conn);
            }
        }
        return iReturn;
    }

    /**
     * @param connection
     * @param sql
     * @param parms
     * @param isCloseConnection
     * @return
     * @throws SQLException
     * @description 批量插入数据
     */
    public static int batchExecute(Connection connection, String sql, Object[][] parms,
                                   boolean isCloseConnection) throws SQLException {
        try {
            QueryRunner queryRunner = new QueryRunner();
            int[] returnCodes = queryRunner.batch(connection, sql, parms);
            return returnCodes.length;
        } finally {
            if (isCloseConnection) {
                DbUtils.commitAndCloseQuietly(connection);
            }
        }
    }

    /**
     * @throws
     * @description
     * @see
     */
    public static void closeResultSet(ResultSet rs) {
        DbUtils.closeQuietly(rs);
    }

    /**
     * @throws
     * @description
     * @see
     */
    public static Object getCallableStatementValue(CallableStatement cstm, int index) throws SQLException {
        Object obj = cstm.getObject(index);
        if (obj instanceof Blob) {
            obj = cstm.getBytes(index);
        } else if (obj instanceof Clob) {
            obj = cstm.getString(index);
        } else if (obj != null && obj.getClass().getName().startsWith("oracle.sql.TIMESTAMP")) {
            obj = cstm.getTimestamp(index);
        } else if (obj != null && obj.getClass().getName().startsWith("oracle.sql.DATE")) {
            String metaDataClassName = cstm.getMetaData().getColumnClassName(index);
            if ("java.sql.Timestamp".equals(metaDataClassName)
                    || "oracle.sql.TIMESTAMP".equals(metaDataClassName)) {
                obj = cstm.getTimestamp(index);
            } else {
                obj = cstm.getDate(index);
            }
        } else if (obj != null && obj instanceof java.sql.Date) {
            if ("java.sql.Timestamp".equals(cstm.getMetaData().getColumnClassName(index))) {
                obj = cstm.getTimestamp(index);
            }
        }
        return obj;
    }

    /**
     * @throws
     * @description
     * @see
     */
    public static Object getCallableStatementValue(CallableStatement cstm, String name) throws SQLException {
        Object obj = cstm.getObject(name);
        if (obj instanceof Blob) {
            obj = cstm.getBytes(name);
        } else if (obj instanceof Clob) {
            obj = cstm.getString(name);
        } else if (obj != null) {
            if (obj instanceof java.sql.Timestamp || "java.sql.Timestamp".equals(obj.getClass().getName())) {
                obj = cstm.getTimestamp(name);
            } else if (obj.getClass().getName().startsWith("oracle.sql.TIMESTAMP")) {
                obj = cstm.getTimestamp(name);
            } else if (obj instanceof java.sql.Date || "java.sql.Date".equals(obj.getClass().getName())) {
                obj = cstm.getDate(name);
            }
        }
        return obj;
    }

    /**
     * @param connection
     * @param isCloseConnection
     * @param handler
     * @param sql
     * @param params
     * @return
     * @throws SQLException
     * @description
     */
    public static <T> T query(Connection connection, boolean isCloseConnection,
                              ResultSetHandler<T> handler, String sql, Object... params) throws SQLException {
        QueryRunner queryRunner = new QueryRunner();
        T result;
        try {
            result = queryRunner.query(connection, sql, handler, params);
            return result;
        } finally {
            if (isCloseConnection) {
                DbUtils.commitAndCloseQuietly(connection);
            }
        }
    }

    /**
     * @throws
     * @description
     * @see
     */
    public static Connection getConnectionThrowException(String driver, String url, String userName,
                                                         String password) throws ClassNotFoundException,
            SQLException {
        if ("org.postgresql.Driver".equalsIgnoreCase(driver)) {
            if (driverMap.get(driver) == null) {
                Class.forName(driver, false, JdbcUtil.class.getClassLoader());
                driverMap.put(driver, driver);
            }
        } else {
            if (driverMap.get(driver) == null) {
                Class.forName(driver);
                driverMap.put(driver, driver);
            }
        }
        Connection connection = DriverManager.getConnection(url, userName, password);
        return connection;
    }

    /**
     * @throws
     * @description
     * @see
     */
    public static int batchInsertUpdateByPre(Connection conn, String sql, List<Object[]> datas)
            throws Exception {

        int iReturn = RETURN_FAILD;
        conn.setAutoCommit(false);
        PreparedStatement stmt = null;
        // insert语句拼接完成,进行预编译
        stmt = conn.prepareStatement(sql);

        // list对象计数器,达到DB_FETCH数值时进行批量提交
        int icount = 0;
        // 设置PreparedStatement参数，从1开始
        for (Object[] entity : datas) {
            injectValueToPst(entity, stmt, 1);
            stmt.addBatch();
            icount++;
            if (icount % DB_SUBMIT == 0) {
                stmt.executeBatch();
                LOGGER.debug(DB_SUBMIT + "executeBatch succuss......");
            }
        }
        if (icount % DB_SUBMIT != 0) {
            stmt.executeBatch();
        }
        iReturn = RETURN_OK;
        conn.commit();
        LOGGER.debug("batchInsertUpdateByPre commit......");
        return iReturn;
    }

    /**
     * @throws
     * @description
     * @see
     */
    private static int injectValueToPst(Object[] entity, PreparedStatement pst, int index) throws Exception {
        int len = entity.length;
        for (int i = 0; i < len; i++) {
            pst.setObject(index, entity[i]);
            index++;
        }
        return index;
    }
}
