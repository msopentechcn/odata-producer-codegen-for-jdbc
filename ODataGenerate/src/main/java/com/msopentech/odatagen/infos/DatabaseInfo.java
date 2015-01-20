package com.msopentech.odatagen.infos;
import java.sql.ResultSet;
import java.util.List;
/**
 * 2014-11-11
 * @author Bruce Li
 */
public class DatabaseInfo {
	private String databaesName;
	private String userName;
	private String systemFunctions;
	private String timeDateFunctions;
	private String stringFunctions;
	private String schemaTerm;
	private String URL;
	private boolean readOnly;
	private String databaseProductName;
	private String databaseProductVersion;
	private String driverName;
	private String driverVersion;
	private ResultSet tableTypes;
	private List<TableInfo> tableInfos;
	public String getDatabaesName() {
		return databaesName;
	}
	public void setDatabaesName(String databaesName) {
		this.databaesName = databaesName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getSystemFunctions() {
		return systemFunctions;
	}
	public void setSystemFunctions(String systemFunctions) {
		this.systemFunctions = systemFunctions;
	}
	public String getTimeDateFunctions() {
		return timeDateFunctions;
	}
	public void setTimeDateFunctions(String timeDateFunctions) {
		this.timeDateFunctions = timeDateFunctions;
	}
	public String getStringFunctions() {
		return stringFunctions;
	}
	public void setStringFunctions(String stringFunctions) {
		this.stringFunctions = stringFunctions;
	}
	public String getSchemaTerm() {
		return schemaTerm;
	}
	public void setSchemaTerm(String schemaTerm) {
		this.schemaTerm = schemaTerm;
	}
	public String getURL() {
		return URL;
	}
	public void setURL(String URL) {
		this.URL = URL;
	}
	public boolean isReadOnly() {
		return readOnly;
	}
	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
	}
	public String getDatabaseProductName() {
		return databaseProductName;
	}
	public void setDatabaseProductName(String databaseProductName) {
		this.databaseProductName = databaseProductName;
	}
	public String getDatabaseProductVersion() {
		return databaseProductVersion;
	}
	public void setDatabaseProductVersion(String databaseProductVersion) {
		this.databaseProductVersion = databaseProductVersion;
	}
	public String getDriverName() {
		return driverName;
	}
	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}
	public String getDriverVersion() {
		return driverVersion;
	}
	public void setDriverVersion(String driverVersion) {
		this.driverVersion = driverVersion;
	}
	public ResultSet getTableTypes() {
		return tableTypes;
	}
	public void setTableTypes(ResultSet tableTypes) {
		this.tableTypes = tableTypes;
	}
	public List<TableInfo> getTableInfos() {
		return tableInfos;
	}
	public void setTableInfos(List<TableInfo> tableInfos) {
		this.tableInfos = tableInfos;
	}
}
