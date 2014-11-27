/**
 * 
 */
package olingo.apache.auto.entity.metadata.processor;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import olingo.apache.auto.entity.infos.ColumnInfo;
import olingo.apache.auto.entity.infos.DatabaseInfo;
import olingo.apache.auto.entity.infos.ExportedKeyInfo;
import olingo.apache.auto.entity.infos.ImportedKeyInfo;
import olingo.apache.auto.entity.infos.PrimaryKeyInfo;
import olingo.apache.auto.entity.infos.TableInfo;
import olingo.apache.auto.entity.util.MetadataUtil;

/**
 * 2014-11-11
 * @author Bruce Li
 */
public class MetadataToInfoProcessor {
	private String AOTU_ENTITY__EXPOSE_TABLENAME = "Oling_Odata_ExposeTable";
	private String AOTU_ENTITY__EXPOSE_TABLE_COLUMN = "ExposeTableName";
	private String DATABAES_METADATA_TABLE_NAME = "TABLE_NAME";
	private String DATABAES_METADATA_COLUMN_NAME = "COLUMN_NAME";
	private String DATABAES_METADATA_TABLE_CATALOG = null;
	private String DATABAES_METADATA_TABLE_PATTERN = null;
	private String DATABAES_METADATA_TABLE_SCHEMA = null;
	private String[] DATABAES_METADATA_TABLE_TYPES = null;
	private List<String> exposeTableNames;
	MetadataUtil metadataUtil = new MetadataUtil();
	private Connection getConnection(String persistenceUnitName){
		try {
			return metadataUtil.getConnection(persistenceUnitName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
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
	private TableInfo getTableInfo(DatabaseMetaData databaseMetaData,String tableName){
		return null;
	}
    private List<TableInfo> getTableInfos(DatabaseMetaData databaseMetaData,Connection connection) throws SQLException{
    	List<TableInfo> tableInfos = new ArrayList<TableInfo>();
    	ResultSet tablesResultSet = databaseMetaData.getTables(DATABAES_METADATA_TABLE_CATALOG, DATABAES_METADATA_TABLE_PATTERN, "%", DATABAES_METADATA_TABLE_TYPES);
    	while(tablesResultSet.next()){
    		TableInfo tableInfo = new TableInfo();
			 String tableName = tablesResultSet.getString(DATABAES_METADATA_TABLE_NAME);
			 /* Is need to expose with this table */
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
				resultSet = connection.prepareStatement("SELECT * FROM "+AOTU_ENTITY__EXPOSE_TABLENAME).executeQuery();
				 while(resultSet.next()){
		            	exposeTableNames.add(resultSet.getString(AOTU_ENTITY__EXPOSE_TABLE_COLUMN));
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
}
