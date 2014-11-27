/**
 * 
 */
package olingo.apache.auto.entity.core;

import java.util.ArrayList;
import java.util.List;


import olingo.apache.auto.entity.infos.ColumnInfo;
import olingo.apache.auto.entity.infos.DatabaseInfo;
import olingo.apache.auto.entity.infos.EntityClassInfo;
import olingo.apache.auto.entity.infos.EntityFieldInfo;
import olingo.apache.auto.entity.infos.EntityInfo;
import olingo.apache.auto.entity.infos.EntityMethodInfo;
import olingo.apache.auto.entity.infos.ModelInfo;
import olingo.apache.auto.entity.infos.PathInfo;
import olingo.apache.auto.entity.infos.PrimaryKeyInfo;
import olingo.apache.auto.entity.infos.TableInfo;
import olingo.apache.auto.entity.type.AnnotationTypes;
import olingo.apache.auto.entity.type.JavaDataTypes;
import olingo.apache.auto.entity.util.StringUtil;

/**
 * 2014-11-12
 * @author Bruce Li
 */
public class DatabaesInfoToEntityClassInfoProcessor {
	public ModelInfo getModelInfo(DatabaseInfo databaseInfo, EntityClassInfo entityClassInfo,PathInfo pathInfo) throws Exception{
		ModelInfo modelInfo = new ModelInfo();
		List<TableInfo> tableInfos = databaseInfo.getTableInfos();
		if(tableInfos == null || tableInfos.size() == 0){
			throw new RuntimeException("The databaes ["+databaseInfo.getDatabaesName()+"] no tables can to creat models");
		}
		List<EntityClassInfo> entityClassInfos = new ArrayList<EntityClassInfo>();
		String noEntityInfoStr;
		String entityPackage = pathInfo.getEntityPackage();
		for(int i=0;tableInfos!=null&&i<tableInfos.size();i++){
			TableInfo tableInfo = tableInfos.get(i);
			/* Creat a EntityClassInfo */
			EntityClassInfo entityClassInfoTem = new EntityClassInfo();
			/* Replace ${ClassName} to class name */
			noEntityInfoStr = entityClassInfo.getNoEntityInfoStr().toString();
	    	String tableName = tableInfo.getTableName();
	    	String className = StringUtil.toUpperCaseFirst(tableName);
	    	entityClassInfoTem.setClassName(className);
			entityClassInfoTem.setNoEntityInfoStr(new StringBuilder(noEntityInfoStr.replaceAll("\\$\\{ClassName\\}", className).replaceAll("\\$\\{EntityPackage\\}", entityPackage)));
			entityClassInfoTem.setEntityInfo(setEntityInfo(entityClassInfo.getEntityInfo(),tableInfo));
			entityClassInfos.add(entityClassInfoTem);
		}
		modelInfo.setEntityClassInfos(entityClassInfos);
		return modelInfo;
	}
	private EntityInfo setEntityInfo(EntityInfo entityInfo,TableInfo tableInfo){
		EntityInfo EntityInfoTem = new EntityInfo();
		List<EntityFieldInfo> fieldInfos = new ArrayList<EntityFieldInfo>();
		List<EntityMethodInfo> methodInfos = new ArrayList<EntityMethodInfo>();
		/* get primary keys */
		List<PrimaryKeyInfo> primaryKeyInfos = tableInfo.getPrimaryKeyInfos();
		List<ColumnInfo> columnInfos = tableInfo.getColumnInfos();
		String fieldStr;
		String methodStr;
		for(int i=0;columnInfos!=null&&i<columnInfos.size();i++){
			fieldStr = entityInfo.getEntityFieldInfos().get(0).getFieldStr().toString();
			methodStr = entityInfo.getEntityMethodInfos().get(0).getMethodStr().toString();
			ColumnInfo columnInfo = columnInfos.get(i);
			String annotationType = getAnnotationType(columnInfo,primaryKeyInfos);
			String typeName = setTypeName(columnInfo.getColumnType());
			String fieldName = columnInfo.getColumnName();
			/* Add a fieldInfo */
			EntityFieldInfo entityFieldInfo = new EntityFieldInfo();
			entityFieldInfo.setFieldName(fieldName);
			entityFieldInfo.setAnnotationType(annotationType);
			entityFieldInfo.setTypeName(typeName);
			entityFieldInfo.setFieldStr(new StringBuilder(fieldStr.replaceAll("\\$\\{AnnotationType\\}", annotationType)
					.replaceAll("\\$\\{TypeName\\}", typeName).replaceAll("\\$\\{FieldName\\}", fieldName)));
			/* Add a get set method */
			EntityMethodInfo entityMethodInfo = new EntityMethodInfo();
			entityMethodInfo.setFieldMethodName(StringUtil.toUpperCaseFirst(fieldName));
			entityMethodInfo.setFieldName(fieldName);
			entityMethodInfo.setTypeName(typeName);
			entityMethodInfo.setMethodStr(new StringBuilder(methodStr.replaceAll("\\$\\{FieldMethodName\\}", StringUtil.toUpperCaseFirst(fieldName))
					.replaceAll("\\$\\{TypeName\\}", typeName).replaceAll("\\$\\{FieldName\\}", fieldName)));
			fieldInfos.add(entityFieldInfo);
			methodInfos.add(entityMethodInfo);
		}
		EntityInfoTem.setEntityFieldInfos(fieldInfos);
		EntityInfoTem.setEntityMethodInfos(methodInfos);
		return EntityInfoTem;
	}
	private String getAnnotationType(ColumnInfo columnInfo , List<PrimaryKeyInfo> primaryKeyInfos){
		String columnName = columnInfo.getColumnName();
		for(int i=0;primaryKeyInfos!=null&&i<primaryKeyInfos.size();i++){
			if(columnName != null && columnName.equals(primaryKeyInfos.get(i).getPrimaryKeyName())){
				return AnnotationTypes.ANNOTATIONTYPES_ID;
			}
			else if(false){
				/* Other annotationType */
			}
		}
		return AnnotationTypes.ANNOTATIONTYPES_BASIC;
	}
	private String setTypeName(Integer columnType){
		if(JavaDataTypes.JAVA_SQL_INTEGER == columnType){
			return JavaDataTypes.JAVA_TYPE_INTEGER_NAME;
		}else if(JavaDataTypes.JAVA_SQL_STRING == columnType){
			return JavaDataTypes.JAVA_TYPE_STRING_NAME;
		}else if(JavaDataTypes.JAVA_SQL_DOUBLE == columnType){
			return JavaDataTypes.JAVA_TYPE_DOUBLE_NAME;
		}else if(JavaDataTypes.JAVA_SQL_FLOAT == columnType){
			return JavaDataTypes.JAVA_TYPE_FLOAT_NAME;
		}else if(JavaDataTypes.JAVA_SQL_CHAR == columnType){
			return JavaDataTypes.JAVA_TYPE_CHAR_NAME;
		}else if(JavaDataTypes.JAVA_SQL_BIT == columnType){
			return JavaDataTypes.JAVA_TYPE_BIT_NAME;
		}else if(JavaDataTypes.JAVA_SQL_BLOB == columnType){
			return JavaDataTypes.JAVA_TYPE_BLOB_NAME;
		}else if(JavaDataTypes.JAVA_SQL_DATE == columnType){
			return JavaDataTypes.JAVA_TYPE_DATE_NAME;
		}else if(JavaDataTypes.JAVA_SQL_JAVA_OBJECT == columnType){
			return JavaDataTypes.JAVA_TYPE_JAVA_OBJECT_NAME;
		}else if(JavaDataTypes.JAVA_SQL_JAVA_TIME == columnType){
			return JavaDataTypes.JAVA_TYPE_TIME_NAME;
		}else if(JavaDataTypes.JAVA_SQL_JAVA_TIMESTAMP == columnType){
			return JavaDataTypes.JAVA_TYPE_TIMESTAMP_NAME;
		}else{
			return null;
		}
	}
}
