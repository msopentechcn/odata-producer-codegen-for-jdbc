/**
 * 
 */
package olingo.odata.jpa.entitygen.infos;

import java.util.List;

/**
 * 2014-11-11
 * @author Bruce Li
 */
public class TableInfo {
	private String tableName;
	private String databaesName;
	private List<PrimaryKeyInfo> primaryKeyInfos;
	private List<ImportedKeyInfo> importedKeyInfos;
	private List<ExportedKeyInfo> exportedKeyInfos;
	private String catalog;
	private String tableNamePattern;
	private String[] types;
	private List<ColumnInfo> columnInfos;
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getDatabaesName() {
		return databaesName;
	}
	public void setDatabaesName(String databaesName) {
		this.databaesName = databaesName;
	}
	public List<PrimaryKeyInfo> getPrimaryKeyInfos() {
		return primaryKeyInfos;
	}
	public void setPrimaryKeyInfos(List<PrimaryKeyInfo> primaryKeyInfos) {
		this.primaryKeyInfos = primaryKeyInfos;
	}
	public List<ImportedKeyInfo> getImportedKeyInfos() {
		return importedKeyInfos;
	}
	public void setImportedKeyInfos(List<ImportedKeyInfo> importedKeyInfos) {
		this.importedKeyInfos = importedKeyInfos;
	}
	public List<ExportedKeyInfo> getExportedKeyInfos() {
		return exportedKeyInfos;
	}
	public void setExportedKeyInfos(List<ExportedKeyInfo> exportedKeyInfos) {
		this.exportedKeyInfos = exportedKeyInfos;
	}
	public String getCatalog() {
		return catalog;
	}
	public void setCatalog(String catalog) {
		this.catalog = catalog;
	}
	public String getTableNamePattern() {
		return tableNamePattern;
	}
	public void setTableNamePattern(String tableNamePattern) {
		this.tableNamePattern = tableNamePattern;
	}
	public String[] getTypes() {
		return types;
	}
	public void setTypes(String[] types) {
		this.types = types;
	}
	public List<ColumnInfo> getColumnInfos() {
		return columnInfos;
	}
	public void setColumnInfos(List<ColumnInfo> columnInfos) {
		this.columnInfos = columnInfos;
	}
	
}
