package com.msopentech.odatagen;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.msopentech.odatagen.exception.EntitygenException;
import com.msopentech.odatagen.infos.ColumnInfo;
import com.msopentech.odatagen.infos.DatabaseInfo;
import com.msopentech.odatagen.infos.EntityClassInfo;
import com.msopentech.odatagen.infos.EntityFieldInfo;
import com.msopentech.odatagen.infos.EntityInfo;
import com.msopentech.odatagen.infos.EntityMethodInfo;
import com.msopentech.odatagen.infos.ModelInfo;
import com.msopentech.odatagen.infos.PathInfo;
import com.msopentech.odatagen.infos.PrimaryKeyInfo;
import com.msopentech.odatagen.infos.TableInfo;
import com.msopentech.odatagen.type.AnnotationTypes;
import com.msopentech.odatagen.type.JavaDataTypes;
import com.msopentech.odatagen.util.StringUtil;

/**
 * 2014-11-12
 * @author Bruce Li
 */
public class DatabaesInfoCaseEntityClassInfoProcessor {
	private String CLASSNAME_KEY = "ClassName";
	private String ENTITYPACKAGE_KEY = "EntityPackage";
	private String ANNOTATIONTYPE_KEY = "AnnotationType";
	private String TYPENAME_KEY = "TypeName";
	private String FIELDNAME_KEY = "FieldName";
	private String FIELDMETHODNAME_KEY = "FieldMethodName";
	public ModelInfo getModelInfo(DatabaseInfo databaseInfo, EntityClassInfo entityClassInfo,PathInfo pathInfo) throws Exception{
		ModelInfo modelInfo = new ModelInfo();
		List<TableInfo> tableInfos = databaseInfo.getTableInfos();
		if(tableInfos == null || tableInfos.size() == 0){
			throw new EntitygenException("The databaes ["+databaseInfo.getDatabaesName()+"] no tables can to creat models");
		}
		List<EntityClassInfo> entityClassInfos = new ArrayList<EntityClassInfo>();
		String entityPackage = pathInfo.getEntityPackage();
		for(int i=0;tableInfos!=null&&i<tableInfos.size();i++){
			TableInfo tableInfo = tableInfos.get(i);
			/* Creat a EntityClassInfo */
			EntityClassInfo entityClassInfoTem = new EntityClassInfo();
	    	String tableName = tableInfo.getTableName();
	    	String className = StringUtil.toUpperCaseFirst(tableName);
	    	entityClassInfoTem.setClassName(className);
	    	
	    	HashMap<String,String> fillMap = new HashMap<String,String>();
			fillMap.put(CLASSNAME_KEY, className);
			fillMap.put(ENTITYPACKAGE_KEY, entityPackage);
			StringBuilder noEntityInfoSB = new FillCodeProcessor().fillCodeToStringBuilder(entityClassInfo.getNoEntityInfoStr(), fillMap);
	    	
			entityClassInfoTem.setNoEntityInfoStr(noEntityInfoSB);
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
		for(int i=0;columnInfos!=null&&i<columnInfos.size();i++){
			ColumnInfo columnInfo = columnInfos.get(i);
			String annotationType = getAnnotationType(columnInfo,primaryKeyInfos);
			String typeName = setTypeName(columnInfo.getColumnType());
			String fieldName = columnInfo.getColumnName();
			/* Add a fieldInfo */
			EntityFieldInfo entityFieldInfo = new EntityFieldInfo();
			entityFieldInfo.setFieldName(fieldName);
			entityFieldInfo.setAnnotationType(annotationType);
			entityFieldInfo.setTypeName(typeName);
			HashMap<String,String> fillMap = new HashMap<String,String>();
			fillMap.put(ANNOTATIONTYPE_KEY, annotationType);
			fillMap.put(TYPENAME_KEY, typeName);
			fillMap.put(FIELDNAME_KEY, fieldName);
			StringBuilder fieldSB = new FillCodeProcessor().fillCodeToStringBuilder(entityInfo.getEntityFieldInfos().get(0).getFieldStr(), fillMap);
			entityFieldInfo.setFieldStr(fieldSB);
			/* Add a get set method */
			EntityMethodInfo entityMethodInfo = new EntityMethodInfo();
			entityMethodInfo.setFieldMethodName(StringUtil.toUpperCaseFirst(fieldName));
			entityMethodInfo.setFieldName(fieldName);
			entityMethodInfo.setTypeName(typeName);
			fillMap.clear();
			fillMap.put(FIELDMETHODNAME_KEY, StringUtil.toUpperCaseFirst(fieldName));
			fillMap.put(TYPENAME_KEY, typeName);
			fillMap.put(FIELDNAME_KEY, fieldName);
			StringBuilder methodSB = new FillCodeProcessor().fillCodeToStringBuilder(entityInfo.getEntityMethodInfos().get(0).getMethodStr(), fillMap);
			entityMethodInfo.setMethodStr(methodSB);
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
//			else if(false){
//				/* Other annotationType */
//			}
		}
		return AnnotationTypes.ANNOTATIONTYPES_BASIC;
	}
	private String setTypeName(Integer columnType){
		if(JavaDataTypes.JAVA_SQL_INTEGER == columnType){
			return JavaDataTypes.JAVA_TYPE_INTEGER_NAME;
		}else if((JavaDataTypes.JAVA_SQL_STRING == columnType) || (JavaDataTypes.JAVA_SQL_TEXT == columnType)){
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
