package com.ais.brm.common.domain.database;

/**
 * 列
 * Created by xuechen on 2016-10-31.
 *
 * @author xuechen
 */
public class Column implements Comparable<Column> {
    private int columnId;
    private int metaTableId;
    private String metaTableName;
    private String columnName;
    private String columnNameCn;
    private Integer columnSeq;
    private String columnType;
    private Integer columnLength;
    private Integer isNullable;
    private Integer isPrimaryKey;
    private Integer isPartition;

    public Integer getIsPartition() {
        return isPartition;
    }

    public void setIsPartition(Integer isPartition) {
        this.isPartition = isPartition;
    }

    @Override
    public int compareTo(Column o) {
        return columnSeq > o.columnSeq ? 1 : (columnSeq < o.columnSeq ? -1 : 0);
    }

    public int getColumnId() {
        return columnId;
    }

    public void setColumnId(int columnId) {
        this.columnId = columnId;
    }

    public int getMetaTableId() {
        return metaTableId;
    }

    public void setMetaTableId(int metaTableId) {
        this.metaTableId = metaTableId;
    }

    public String getMetaTableName() {
        return metaTableName;
    }

    public void setMetaTableName(String metaTableName) {
        this.metaTableName = metaTableName;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnNameCn() {
        return columnNameCn;
    }

    public void setColumnNameCn(String columnNameCn) {
        this.columnNameCn = columnNameCn;
    }

    public Integer getColumnSeq() {
        return columnSeq;
    }

    public void setColumnSeq(Integer columnSeq) {
        this.columnSeq = columnSeq;
    }

    public String getColumnType() {
        return columnType;
    }

    public void setColumnType(String columnType) {
        this.columnType = columnType;
    }

    public Integer getColumnLength() {
        return columnLength;
    }

    public void setColumnLength(Integer columnLength) {
        this.columnLength = columnLength;
    }

    public Integer getIsNullable() {
        return isNullable;
    }

    public void setIsNullable(Integer isNullable) {
        this.isNullable = isNullable;
    }

    public Integer getIsPrimaryKey() {
        return isPrimaryKey;
    }

    public void setIsPrimaryKey(Integer isPrimaryKey) {
        this.isPrimaryKey = isPrimaryKey;
    }


}
