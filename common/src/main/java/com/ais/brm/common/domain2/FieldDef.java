package com.ais.brm.common.domain2;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author zhaocw
 */
public class FieldDef {
	private String fieldType;
	private String fieldName;
	
	public FieldDef(String fieldName, String fieldType) {
		this.fieldName = fieldName;
		this.fieldType = fieldType;
	}
	
	public String getFieldType() {
		return fieldType;
	}

	public String getFieldName() {
		return fieldName;
	}
	
	public FieldType getType() {
		return FieldType.fieldType(this.fieldType);
	}

	/**
	 * .
	 * @param rs
	 * @return
	 * @throws SQLException
     */
	public Object getValue(ResultSet rs) throws SQLException {
		switch(this.getType()) {
			case CHAR: {
				String v = rs.getString(this.fieldName);
				return (v != null && v.length() > 0) ? v.charAt(0) : null;
			}
			
			case STRING: return rs.getString(this.fieldName);
			
			case BOOLEAN: {
				boolean v = rs.getBoolean(this.fieldName);
				return (rs.wasNull() ? null : v);
			}

			case NUMERIC: return rs.getBigDecimal(this.fieldName);
			
			case BYTE: {
				byte v = rs.getByte(this.fieldName);
				return (rs.wasNull() ? null : v);
			}
				
			case SHORT: {
				short v = rs.getShort(this.fieldName);
				return (rs.wasNull() ? null : v);
			}
			
			case INT: {
				int v = rs.getInt(this.fieldName);
				return (rs.wasNull() ? null : v);
			}
			
			case LONG: {
				long v = rs.getLong(this.fieldName);
				return (rs.wasNull() ? null : v);
			}
			
			case FLOAT: {				
				float v = rs.getFloat(this.fieldName);
				return (rs.wasNull() ? null : v);
			}

			case DOUBLE: {
				double v = rs.getDouble(this.fieldName);
				return (rs.wasNull() ? null : v);
			}

			case BYTES: return rs.getBytes(this.fieldName);
			case DATE: return rs.getDate(this.fieldName);
			case TIME: return rs.getTime(this.fieldName);
			case TIMESTAMP: return rs.getTimestamp(this.fieldName);
		}
		
		return null;
	}
}
