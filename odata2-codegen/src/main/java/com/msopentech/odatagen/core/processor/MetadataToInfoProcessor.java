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
package com.msopentech.odatagen.core.processor;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.msopentech.odatagen.exception.EntitygenException;
import com.msopentech.odatagen.infos.ColumnInfo;
import com.msopentech.odatagen.infos.DatabaseInfo;
import com.msopentech.odatagen.infos.ExportedKeyInfo;
import com.msopentech.odatagen.infos.ImportedKeyInfo;
import com.msopentech.odatagen.infos.PathInfo;
import com.msopentech.odatagen.infos.PrimaryKeyInfo;
import com.msopentech.odatagen.infos.TableInfo;
import com.msopentech.odatagen.util.MetadataUtil;
public class MetadataToInfoProcessor {
	private String COM_MSOPENTECH_ODATA_EXPOSETABLE = "COM_MSOpenTech_OData_ExposeTable";
	private String JPA_ENTITY__EXPOSE_TABLE_COLUMN = "ExposeTableName";
	private String DATABAES_METADATA_TABLE_NAME = "TABLE_NAME";
	private String DATABAES_METADATA_COLUMN_NAME = "COLUMN_NAME";
	private String DATABAES_METADATA_TABLE_CATALOG = null;
	private String DATABAES_METADATA_TABLE_PATTERN = null;
	private String DATABAES_METADATA_TABLE_SCHEMA = null;
	private String[] DATABAES_METADATA_TABLE_TYPES = null;
	private List<String> exposeTableNames;
	private String NO_TABLES_KEY = "no_exposeTables";
	private String EXPOSE_TABLES_KEY = "exposeTables";
	MetadataUtil metadataUtil = new MetadataUtil();
	private Connection getConnection(String persistenceUnitName){
		try {
			return metadataUtil.getConnection(persistenceUnitName);
		} catch (Exception e) {
			throw new EntitygenException(e.getMessage());
		}
	}
	public DatabaseInfo getDatabaseInfo(String persistenceUnitName) throws SQLException  {
		DatabaseInfo databaseInfo = new DatabaseInfo();
		Connection connection = getConnection(persistenceUnitName);
		DatabaseMetaData databaseMetaData = connection.getMetaData();
		databaseInfo.setDatabaesName("");
		databaseInfo.setDatabaseProductName(databaseMetaData
				.getDatabaseProductName());
		databaseInfo.setDatabaseProductVersion(databaseMetaData
				.getDatabaseProductVersion());
		databaseInfo.setDriverName(databaseMetaData.getDriverName());
		databaseInfo.setDriverVersion(databaseMetaData.getDriverVersion());
		databaseInfo.setReadOnly(databaseMetaData.isReadOnly());
		databaseInfo.setSchemaTerm(databaseMetaData.getSchemaTerm());
		databaseInfo.setStringFunctions(databaseMetaData.getStringFunctions());
		databaseInfo.setSystemFunctions(databaseMetaData.getSystemFunctions());
		databaseInfo.setTableInfos(getTableInfos(databaseMetaData, connection));
		databaseInfo.setTableTypes(databaseMetaData.getTableTypes());
		databaseInfo.setTimeDateFunctions(databaseMetaData
				.getTimeDateFunctions());
		databaseInfo.setURL(databaseMetaData.getURL());
		databaseInfo.setUserName(databaseMetaData.getUserName());
		return databaseInfo;
	}
    private List<TableInfo> getTableInfos(DatabaseMetaData databaseMetaData,Connection connection) throws SQLException{
    	List<TableInfo> tableInfos = new ArrayList<TableInfo>();
    	ResultSet tablesResultSet = databaseMetaData.getTables(DATABAES_METADATA_TABLE_CATALOG, DATABAES_METADATA_TABLE_PATTERN, "%", DATABAES_METADATA_TABLE_TYPES);
    	while(tablesResultSet.next()){
    		TableInfo tableInfo = new TableInfo();
			 String tableName = tablesResultSet.getString(DATABAES_METADATA_TABLE_NAME);
			 /* Is need to expose with this table? */
			 if(isNeedToExpose(connection,tableName)){
				 continue;
			 }
			 tableInfo.setCatalog(DATABAES_METADATA_TABLE_CATALOG);
			 tableInfo.setColumnInfos(getColumnInfos(connection,tableName));
			 tableInfo.setDatabaesName("");
			 tableInfo.setExportedKeyInfos(new ArrayList<ExportedKeyInfo>());
			 tableInfo.setImportedKeyInfos(new ArrayList<ImportedKeyInfo>());
			 tableInfo.setPrimaryKeyInfos(getPrimaryKeyInfos(databaseMetaData,tableName));
			 tableInfo.setTableName(tableName);
			 tableInfo.setTableNamePattern(DATABAES_METADATA_TABLE_PATTERN);
			 tableInfo.setTypes(DATABAES_METADATA_TABLE_TYPES);
			 tableInfos.add(tableInfo);
		}		
		return tableInfos;
	}
    private List<PrimaryKeyInfo> getPrimaryKeyInfos(DatabaseMetaData databaseMetaData,String tableName) throws SQLException{
    	List<PrimaryKeyInfo> primaryKeyInfos = new ArrayList<PrimaryKeyInfo>();
    	ResultSet primaryKeyResultSet = databaseMetaData.getPrimaryKeys(DATABAES_METADATA_TABLE_CATALOG, DATABAES_METADATA_TABLE_SCHEMA, tableName);
    	while(primaryKeyResultSet.next()){	
    	    PrimaryKeyInfo primaryKeyInfo = new PrimaryKeyInfo();
    	    primaryKeyInfo.setPrimaryKeyName(primaryKeyResultSet.getString(DATABAES_METADATA_COLUMN_NAME));
    	    primaryKeyInfos.add(primaryKeyInfo);
    	}
		return primaryKeyInfos;
	}
    private boolean isNeedToExpose(Connection connection,String tableName){
    	boolean isNeedToExpose = false;
		List<String> exposeTableNameList = getExposeTableNames(connection);
		for (int i = 0 ;exposeTableNameList!=null&&i<exposeTableNameList.size();i++) {
			isNeedToExpose = true;
			if (tableName.toUpperCase().equals(exposeTableNameList.get(i).toUpperCase())) {
				isNeedToExpose = false;
				break;
			}
		}
    	return isNeedToExpose;
    }
    private List<String> getExposeTableNames(Connection connection){
    	if(exposeTableNames == null){
    		exposeTableNames = new ArrayList<String>();
        	ResultSet resultSet;
			try {
				resultSet = connection.prepareStatement("SELECT * FROM "+COM_MSOPENTECH_ODATA_EXPOSETABLE).executeQuery();
				 while(resultSet.next()){
		            	exposeTableNames.add(resultSet.getString(JPA_ENTITY__EXPOSE_TABLE_COLUMN));
				 }   	
			} catch (SQLException e) {
				exposeTableNames = null;
			} 
    	}
    	return exposeTableNames;
    }
    private List<ColumnInfo> getColumnInfos(Connection connection,String tableName) throws SQLException{
    	List<ColumnInfo> columnInfos = new ArrayList<ColumnInfo> ();
    	ResultSetMetaData resultSetMetaData = connection.prepareStatement("SELECT * FROM "+tableName).executeQuery().getMetaData(); 
        int columnCount = resultSetMetaData.getColumnCount();    
        for (int i = 1; i <= columnCount ; i++) {   
        	ColumnInfo columnInfo = new ColumnInfo();
        	columnInfo.setAutoIncrement(resultSetMetaData.isAutoIncrement(i));
        	columnInfo.setColumnName(resultSetMetaData.getColumnName(i));
        	columnInfo.setColumnType(resultSetMetaData.getColumnType(i));
        	columnInfo.setColumnTypeName(resultSetMetaData.getColumnTypeName(i));
        	columnInfo.setNullable(resultSetMetaData.isNullable(i));
        	columnInfo.setPrecision(resultSetMetaData.getPrecision(i));
        	columnInfo.setReadOnly(resultSetMetaData.isReadOnly(i));             
            columnInfos.add(columnInfo);
        }
		return columnInfos;
	}
    
    public Map<String,List<String>> getAlltablesAndExposeTables(String persistenceUnitName) throws SQLException{
    	Connection connection = getConnection(persistenceUnitName);
    	Map<String,List<String>> tableMap = new HashMap<String,List<String>>();
    	tableMap.put(NO_TABLES_KEY, getAllTables(connection));
    	tableMap.put(EXPOSE_TABLES_KEY, getExposeTables(connection));
    	return tableMap;
    }
	private List<String> getExposeTables(Connection connection) throws SQLException {
		List<String> exposeTableNames = getExposeTableNames(connection);
		if(exposeTableNames == null){
			/* Create  JPA_ENTITY__EXPOSE_TABLENAME */
			createOpen_Technologies_JPA_ExposeTable(connection);
		}
		return exposeTableNames;
	}
	private void createOpen_Technologies_JPA_ExposeTable(Connection connection){
		try {
			connection.createStatement().execute(
					"create table "+COM_MSOPENTECH_ODATA_EXPOSETABLE+"("+
			               "ExposeTableName varchar(50) primary key"+
			        ");"
			               );
		} catch (SQLException e) {
			throw new EntitygenException(e.getMessage());
		}finally{
			closeConnection(connection);
		}
		
	}
	
	private List<String> getAllTables(Connection connection) throws SQLException {
		List<String> alltables = new ArrayList<String>();
		DatabaseMetaData databaseMetaData = connection.getMetaData();
		ResultSet tablesResultSet = databaseMetaData.getTables(DATABAES_METADATA_TABLE_CATALOG, DATABAES_METADATA_TABLE_PATTERN, "%", DATABAES_METADATA_TABLE_TYPES);
    	while(tablesResultSet.next()){
			 String tableName = tablesResultSet.getString(DATABAES_METADATA_TABLE_NAME);
			 alltables.add(tableName);
    	}
		return alltables;
	}
	
	public void upOpen_Technologies_JPA_ExposeTable(PathInfo pathInfo, String[] split) {
		String insertSql = createInsertExposeTablSql(split);
		String deleteSql = "delete from "+COM_MSOPENTECH_ODATA_EXPOSETABLE;
		Connection connection = getConnection(pathInfo.getPersistenceUnitName());
		try {
			connection.setAutoCommit(false);
			connection.createStatement().execute(deleteSql);
			connection.createStatement().execute(insertSql);
			connection.commit();
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				throw new EntitygenException(e.getMessage());
			}
			throw new EntitygenException(e.getMessage());
		}
	}
	
	private String createInsertExposeTablSql(String[] split){
		String sql;
		StringBuilder valuesSB = new StringBuilder();
		for(int i=0;split != null && i<split.length;i++){
			String value = split[i];
			valuesSB.append("('"+value+"'),");
		}
		String values = valuesSB.toString();
		sql = "insert into "+COM_MSOPENTECH_ODATA_EXPOSETABLE+" values "+values.substring(0, values.length()-1)+";";
		return sql;
	}
	private void closeConnection(Connection connection){
		try {
			connection.close();
		} catch (SQLException e) {
			throw new EntitygenException("Close connection exception");
		}
	}
    
}
