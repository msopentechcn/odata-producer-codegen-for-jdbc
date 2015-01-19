package com.msopentech.odatagen.infos;
/**
 * 2014-11-11
 * @author Bruce Li
 */
public class ColumnInfo {
	private String columnName;
	private Integer columnType;
	private String columnTypeName;
	private Integer precision;
	private boolean isAutoIncrement;
	private int isNullable;
	private boolean isReadOnly;
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	public Integer getColumnType() {
		return columnType;
	}
	public void setColumnType(Integer columnType) {
		this.columnType = columnType;
	}
	public String getColumnTypeName() {
		return columnTypeName;
	}
	public void setColumnTypeName(String columnTypeName) {
		this.columnTypeName = columnTypeName;
	}
	public Integer getPrecision() {
		return precision;
	}
	public void setPrecision(Integer precision) {
		this.precision = precision;
	}
	public boolean isAutoIncrement() {
		return isAutoIncrement;
	}
	public void setAutoIncrement(boolean isAutoIncrement) {
		this.isAutoIncrement = isAutoIncrement;
	}
	public int isNullable() {
		return isNullable;
	}
	public void setNullable(int isNullable) {
		this.isNullable = isNullable;
	}
	public boolean isReadOnly() {
		return isReadOnly;
	}
	public void setReadOnly(boolean isReadOnly) {
		this.isReadOnly = isReadOnly;
	}
	
}
