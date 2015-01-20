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
import java.util.List;
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
