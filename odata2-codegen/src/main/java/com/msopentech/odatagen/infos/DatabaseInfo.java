/*******************************************************************************
 * Copyright (c) Microsoft Open Technologies (Shanghai) Co. Ltd.  All rights reserved.
 
 * The MIT License (MIT)
 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 *******************************************************************************/
package com.msopentech.odatagen.infos;
import java.sql.ResultSet;
import java.util.List;
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
