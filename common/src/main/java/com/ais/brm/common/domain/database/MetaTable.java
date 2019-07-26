package com.ais.brm.common.domain.database;

import java.util.ArrayList;
import java.util.List;

/**
 * 表
 * Created by xuechen on 2016-10-31.
 *
 * @author xuechen
 * 
 */
public class MetaTable
{
	private int metaTableId;
	private String schemaName;// 模式名
	private String tableName;
	private String tableComment;
	private int tableType; // 1:月表,2:日表,3:普通表
	private String format; // 月表：YYYYMM,YYYY/MM,YYYY-MM,YYYY_MM 日表：YYYYMMDD,YYYY-MM-DD,YYYY/MM/DD,YYYY_MM_DD
	private DatabaseInfo databaseInfo;
	private String databaseId;
	private String databaseName;
	private List<Column> columns = new ArrayList<Column>();

	public String getSchemaName() {
		return schemaName;
	}

	public void setSchemaName(String schemaName) {
		this.schemaName = schemaName;
	}

	public List<Column> getColumns()
	{
		return columns;
	}

	public void setColumns(List<Column> columns)
	{
		this.columns = columns;
	}

	public int getMetaTableId() {
		return metaTableId;
	}

	public void setMetaTableId(int metaTableId) {
		this.metaTableId = metaTableId;
	}

	public DatabaseInfo getDatabaseInfo() {
		return databaseInfo;
	}

	public void setDatabaseInfo(DatabaseInfo databaseInfo) {
		this.databaseInfo = databaseInfo;
	}

	public String getTableName()
	{
		return tableName;
	}

	public void setTableName(String tableName)
	{
		this.tableName = tableName;
	}

	public String getTableComment()
	{
		return tableComment;
	}

	public void setTableComment(String tableComment)
	{
		this.tableComment = tableComment;
	}

	public String getDatabaseId()
	{
		return databaseId;
	}

	public void setDatabaseId(String databaseId)
	{
		this.databaseId = databaseId;
	}

	public String getDatabaseName()
	{
		return databaseName;
	}

	public void setDatabaseName(String databaseName)
	{
		this.databaseName = databaseName;
	}

	public int getTableType() {
		return tableType;
	}

	public void setTableType(int tableType) {
		this.tableType = tableType;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

}
