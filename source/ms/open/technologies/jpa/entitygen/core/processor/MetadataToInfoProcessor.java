package ms.open.technologies.jpa.entitygen.core.processor;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ms.open.technologies.jpa.entitygen.exception.EntitygenException;
import ms.open.technologies.jpa.entitygen.infos.ColumnInfo;
import ms.open.technologies.jpa.entitygen.infos.DatabaseInfo;
import ms.open.technologies.jpa.entitygen.infos.ExportedKeyInfo;
import ms.open.technologies.jpa.entitygen.infos.ImportedKeyInfo;
import ms.open.technologies.jpa.entitygen.infos.PathInfo;
import ms.open.technologies.jpa.entitygen.infos.PrimaryKeyInfo;
import ms.open.technologies.jpa.entitygen.infos.TableInfo;
import ms.open.technologies.jpa.entitygen.util.MetadataUtil;
/**
 * 2014-11-11
 * @author Bruce Li
 */
public class MetadataToInfoProcessor {
	private String JPA_ENTITY__EXPOSE_TABLENAME = "MS_Open_Technologies_JPA_ExposeTable";
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
				resultSet = connection.prepareStatement("SELECT * FROM "+JPA_ENTITY__EXPOSE_TABLENAME).executeQuery();
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
					"create table "+JPA_ENTITY__EXPOSE_TABLENAME+"("+
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
		String deleteSql = "delete from "+JPA_ENTITY__EXPOSE_TABLENAME;
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
		sql = "insert into "+JPA_ENTITY__EXPOSE_TABLENAME+" values "+values.substring(0, values.length()-1)+";";
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
