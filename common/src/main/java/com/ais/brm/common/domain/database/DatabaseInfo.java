package com.ais.brm.common.domain.database;

import org.apache.commons.lang3.StringUtils;

/**
 * 数据源
 * Created by xuechen on 2016-10-31.
 *
 * @author xuechen
 */
public class DatabaseInfo
{
	private int databaseId;
	private String name;// 用户名
	private String password;// 密码
	private int databaseType;// 数据库类型
	private String jdbcUrl;// jdbcUrl
	private String dbName;// 数据库名
	private String tableName = StringUtils.EMPTY;
	private String ip;

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getDatabaseId() {
		return databaseId;
	}

	public void setDatabaseId(int databaseId) {
		this.databaseId = databaseId;
	}

	/**
	 * @return the userName
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @param userName
	 *            the userName to set
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * @return the userPassword
	 */
	public String getPassword()
	{
		return password;
	}

	/**
	 * @param userPassword
	 *            the userPassword to set
	 */
	public void setPassword(String password)
	{
		this.password = password;
	}

	/**
	 * @return the databaseType
	 */
	public int getDatabaseType()
	{
		return databaseType;
	}

	/**
	 * @param databaseType
	 *            the databaseType to set
	 */
	public void setDatabaseType(int databaseType)
	{
		this.databaseType = databaseType;
	}

	/**
	 * @return the jdbcUrl
	 */
	public String getJdbcUrl()
	{
		return jdbcUrl;
	}

	/**
	 * @param jdbcUrl
	 *            the jdbcUrl to set
	 */
	public void setJdbcUrl(String jdbcUrl)
	{
		this.jdbcUrl = jdbcUrl;
	}

	/**
	 * @return the dbName
	 */
	public String getDbName()
	{
		return dbName;
	}

	/**
	 * @param dbName
	 *            the dbName to set
	 */
	public void setDbName(String dbName)
	{
		this.dbName = dbName;
	}

	public String getTableName()
	{
		return tableName;
	}

	public void setTableName(String tableName)
	{
		this.tableName = tableName;
	}

}
