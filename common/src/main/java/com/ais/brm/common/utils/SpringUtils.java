package com.ais.brm.common.utils;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.DatabaseMetaDataCallback;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.jdbc.support.MetaDataAccessException;

import javax.sql.DataSource;
import java.sql.*;
import java.sql.Date;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * 提供使用Spring框架相关的辅助功能
 *
 * @author lulj
 */
public final class SpringUtils {
    private static final DateTimeFormatter TS_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    private static final RowMapper<Map<String, String>> STRING_ROW_MAPPER = newStringRowMapper();

    public static final RowMapper<Map<String, String>> stringRowMapper() {
        return STRING_ROW_MAPPER;
    }

    public static final RowMapper<Map<String, String>> newStringRowMapper() {
        return new RowMapper<Map<String, String>>() {
            @Override
            public Map<String, String> mapRow(ResultSet rs, int rowNum) throws SQLException {
                Map<String, String> detail = new HashMap<>();
                int columnCount = rs.getMetaData().getColumnCount();
                for (int column = 1; column <= columnCount; column++) {
                    String key = rs.getMetaData().getColumnName(column).toLowerCase();
                    switch (rs.getMetaData().getColumnType(column)) {
                        case Types.DATE:
                            Date d = rs.getDate(column);
                            detail.put(key, d == null ? null : d.
                                    toLocalDate().format(DateTimeFormatter.BASIC_ISO_DATE));
                            break;
                        case Types.TIMESTAMP:
                            Timestamp ts = rs.getTimestamp(column);
                            detail.put(key, ts == null ? null : ts.
                                    toLocalDateTime().format(TS_FORMATTER));
                            break;
                        default:
                            detail.put(key, rs.getString(column));
                    }
                }

                return detail;
            }
        };
    }

    /**
     * 检测指定表是否存在，返回不存在的表
     *
     * @param dataSource
     * @param tableNames
     * @return
     */
    public static List<String> detectTablesExist(DataSource dataSource, Set<String> tableNames) {
        Map<String, String> nonExist = new HashMap<>();
        for (String tableName : tableNames) {
            nonExist.put(tableName.toLowerCase(), tableName);
        }

        try {
            JdbcUtils.extractDatabaseMetaData(dataSource,
                    new DatabaseMetaDataCallback() {

                        @Override
                        public Object processMetaData(DatabaseMetaData dbmd) throws SQLException {
                            ResultSet rs = dbmd.getTables(null, null,
                                    null,
                                    new String[]{"TABLE"});
                            while (rs.next()) {
                                nonExist.remove(rs.getString(3).toLowerCase());// "TABLE_NAME"
                            }
                            return null;
                        }
                    });
        } catch (MetaDataAccessException e) {
            e.printStackTrace();
        }

        return new ArrayList<>(nonExist.values());
    }

    /**
     * 检测指定表是否存在，返回不存在的表
     *
     * @param dataSource
     * @param tableNames
     * @return
     */
    public static List<String> detectTablesExist(DataSource dataSource, String[] tableNames) {
        return detectTablesExist(dataSource, new HashSet<>(Arrays.asList(tableNames)));
    }

    /**
     * 检测指定表是否存在
     *
     * @param dataSource
     * @param tableName
     * @return
     */
    public static boolean tableExist(DataSource dataSource, String tableName) {

        try {
            return (Boolean) JdbcUtils.extractDatabaseMetaData(dataSource,
                    new DatabaseMetaDataCallback() {

                        @Override
                        public Object processMetaData(DatabaseMetaData dbmd) throws SQLException {
                            ResultSet rs = dbmd.getTables(null, null,
                                    tableName.replace("_", "\\_").replace("%", "\\%"),
                                    new String[]{"TABLE"});
                            return rs.next();
                        }
                    });
        } catch (MetaDataAccessException e) {
            e.printStackTrace();
        }

        return false;
    }
}
