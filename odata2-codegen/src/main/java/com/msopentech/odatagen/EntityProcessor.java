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
package com.msopentech.odatagen;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.lang.Runtime;
import com.msopentech.odatagen.core.processor.MetadataToInfoProcessor;
import com.msopentech.odatagen.exception.EntitygenException;
import com.msopentech.odatagen.infos.EntityClassInfo;
import com.msopentech.odatagen.infos.EntityFieldInfo;
import com.msopentech.odatagen.infos.EntityInfo;
import com.msopentech.odatagen.infos.EntityMethodInfo;
import com.msopentech.odatagen.infos.ModelInfo;
import com.msopentech.odatagen.infos.PathInfo;
import com.msopentech.odatagen.util.StringUtil;
public class EntityProcessor {
	private String JAVAX_PERSISTENCE_NAME = "javax.persistence";
	private Runtime runtime = Runtime.getRuntime();
	private PathInfo pathInfo;
	private static String DEFAULT_FILE = "Default.model";
	private String CODER = "utf-8";
	private String BODY_KEY = "Body";
	public EntityProcessor(PathInfo pathInfo) {
		this.pathInfo = pathInfo;
	}

	public EntityClassInfo modelReadToEntityClassInfo(PathInfo pathInfo)
			throws IOException {
		/* set EntityClassInfo */
		return setEntityClassInfo(pathInfo);
	}

	public void entityGenerate() throws Exception {
		/* Get ModelInfo fo DatabaesInfo */
		ModelInfo modelInfo = new DatabaesInfoCaseEntityClassInfoProcessor()
				.getModelInfo(new MetadataToInfoProcessor()
						.getDatabaseInfo(pathInfo.getPersistenceUnitName()),
						modelReadToEntityClassInfo(pathInfo), pathInfo);
		/* Write ModelInfo to source code */
		pathInfo = genCode(modelInfo, pathInfo);
		/* Updata persistence file with <class></class> set */
		new PersistenceXMLProcessor().updataPersistenceXML(pathInfo);
	}

	private PathInfo genCode(ModelInfo modelInfo, PathInfo pathInfo)
			throws IOException {
		/* Detection the folder EntityPackage if exsisted? */
		String codeFolderPath = getCodeFolderPath(pathInfo);
		List<EntityClassInfo> entityClassInfos = modelInfo
				.getEntityClassInfos();
		List<String> entityPackAndNames = new ArrayList<String>();
		for (int i = 0; entityClassInfos != null && i < entityClassInfos.size(); i++) {
			EntityClassInfo entityClassInfo = entityClassInfos.get(i);
			String className = entityClassInfo.getClassName();
			String resourceCodePath = codeFolderPath + "/" + className
					+ ".java";
			FileWriter fileWriter = new FileWriter(resourceCodePath, false);
			/* Get code as String */
			HashMap<String,String> fillMap = new HashMap<String,String>();
			fillMap.put(BODY_KEY, entityCode(entityClassInfo.getEntityInfo()));
			String code = new FillCodeProcessor().fillCodeToString(entityClassInfo
					.getNoEntityInfoStr(), fillMap);
			fileWriter.write(code);
			fileWriter.flush();
			fileWriter.close();
			/* Is compile */
			if (pathInfo.isCompile()) {
				String javacStr = getJavacStr(resourceCodePath);
				Process process = runtime.exec(javacStr);
				InputStream in = process.getErrorStream();
				boolean isCompileSucces = true;
				StringBuilder errorSB = new StringBuilder();
				int end;
				byte[] b = new byte[1024];
				while ((end = in.read(b)) > 0) {
					isCompileSucces = false;
					errorSB = errorSB.append(new String(b,0,end, "gbk"));
				}
				if(!isCompileSucces){
					System.out.println(errorSB.toString());
					throw new EntitygenException("[Compile java code error]: "+errorSB.toString());
				}
				in.close();
			}
			entityPackAndNames.add(pathInfo.getEntityPackage() + "."
					+ className);
		}
		pathInfo.setEntityPackAndNames(entityPackAndNames);
		return pathInfo;
	}
	
	protected String getJavacStr(String resourceCodePath){
		String persistenceJarName = getPersistenceJarName(pathInfo.getWebInfPath());
		return "javac -classpath " +persistenceJarName+ " "+resourceCodePath;
	}

	private String getPersistenceJarName(String webInfPath) {
		String libPath = webInfPath+"/lib";
		File file = new File(libPath);
		if(file.isDirectory()){
			String[] fileNames = file.list();
			for(int i = 0;fileNames != null && i < fileNames.length;i++){
				String filePath = libPath + "/" + fileNames[i];
			    File temFile = new File(filePath); 
			    if (!temFile.isDirectory()) { 
			    	String name = temFile.getName();
			    	if((name != null)&&(name.contains(JAVAX_PERSISTENCE_NAME))){
			    		return filePath;
			    	}
			    }
			}
		}
		return "";
	}

	/**
	 * Detection the folder EntityPackage if exsisted? If not exsist,auto create
	 * these folder
	 * 
	 * @param pathInfo
	 */
	protected String getCodeFolderPath(PathInfo pathInfo) {
		String codeFolderPath = pathInfo.getRealPath() + "/"
				+ pathInfo.getApplicationToSourcePath() + "/"
				+ pathInfo.getEntityPackage().replaceAll("\\.", "/");
		getEmptyDirectory(codeFolderPath);
		return codeFolderPath;
	}
	
	protected File getEmptyDirectory(String directory){
		File sourceDirectory = new File(directory);
		if(sourceDirectory.isDirectory()){
			String[] fileNames = sourceDirectory.list();
			for(int i = 0;fileNames != null && i < fileNames.length;i++){
			    File delfile = new File(directory + "\\" + fileNames[i]); 
			    if (!delfile.isDirectory()) { 
			      delfile.delete();
			    }
			}
		}else{
			sourceDirectory.mkdirs();
		}
		return sourceDirectory;
	}

	private String entityCode(EntityInfo entityInfo) {
		StringBuilder entityCodeSB = new StringBuilder();
		/* Get field code */
		List<EntityFieldInfo> entityFieldInfos = entityInfo
				.getEntityFieldInfos();
		for (int i = 0; entityFieldInfos != null && i < entityFieldInfos.size(); i++) {
			EntityFieldInfo entityFieldInfo = entityFieldInfos.get(i);
			entityCodeSB.append(entityFieldInfo.getFieldStr());
		}
		/* Get method code */
		List<EntityMethodInfo> entityMethodInfos = entityInfo
				.getEntityMethodInfos();
		for (int i = 0; entityMethodInfos != null
				&& i < entityMethodInfos.size(); i++) {
			EntityMethodInfo entityMethodInfo = entityMethodInfos.get(i);
			entityCodeSB.append(entityMethodInfo.getMethodStr());
		}
		return entityCodeSB.toString();
	}

	private EntityClassInfo setEntityClassInfo(PathInfo pathInfo)
			throws IOException {
		EntityClassInfo entityClassInfo = new EntityClassInfo();
		/* read Default.model file */
		InputStream input = EntityProcessor.class
				.getResourceAsStream(DEFAULT_FILE);
		StringBuilder entityClassInfoStr = new StringBuilder();
		int end;
		byte[] b = new byte[1024];
		while ((end = input.read(b)) > 0) {
			entityClassInfoStr.append(new String(b,0,end, CODER));
		}
		input.close();
		/* set EntityClassInfo */
		int startIndex = StringUtil.getStartIndex(
				entityClassInfoStr.toString(), "<entity>");
		int endIndex = StringUtil.getEndIndex(entityClassInfoStr.toString(),
				"</entity>") + 1;
		StringBuilder entityInfoSB = new StringBuilder(
				entityClassInfoStr.substring(startIndex, endIndex));
		entityClassInfo.setNoEntityInfoStr(entityClassInfoStr.delete(
				startIndex, endIndex + 1));
		entityClassInfo.setEntityInfo(setEntityInfo(entityInfoSB));
		return entityClassInfo;
	}

	private EntityInfo setEntityInfo(StringBuilder entityInfoSB) {
		EntityInfo entityInfo = new EntityInfo();
		int fieldStartIndex = StringUtil.getEndIndex(entityInfoSB.toString(),
				"<field>") + 1;
		int fieldeEndIndex = StringUtil.getStartIndex(entityInfoSB.toString(),
				"</field>");
		StringBuilder fieldInfoSB = new StringBuilder(entityInfoSB.substring(
				fieldStartIndex, fieldeEndIndex));
		int methodStartIndex = StringUtil.getEndIndex(entityInfoSB.toString(),
				"<method>") + 1;
		int methodEndIndex = StringUtil.getStartIndex(entityInfoSB.toString(),
				"</method>");
		StringBuilder methodInfoSB = new StringBuilder(entityInfoSB.substring(
				methodStartIndex, methodEndIndex));
		entityInfo.setEntityFieldInfos(setFieldInfos(fieldInfoSB));
		entityInfo.setEntityMethodInfos(setMethodInfos(methodInfoSB));
		return entityInfo;
	}

	private List<EntityFieldInfo> setFieldInfos(StringBuilder fieldInfoSB) {
		List<EntityFieldInfo> entityFieldInfos = new ArrayList<EntityFieldInfo>();
		EntityFieldInfo entityFieldInfo = new EntityFieldInfo();
		entityFieldInfo.setFieldStr(fieldInfoSB);
		entityFieldInfos.add(entityFieldInfo);
		return entityFieldInfos;
	}

	private List<EntityMethodInfo> setMethodInfos(StringBuilder methodInfoSB) {
		List<EntityMethodInfo> entityMethodInfos = new ArrayList<EntityMethodInfo>();
		EntityMethodInfo entityMethodInfo = new EntityMethodInfo();
		entityMethodInfo.setMethodStr(methodInfoSB);
		entityMethodInfos.add(entityMethodInfo);
		return entityMethodInfos;
	}

	public void upOpen_Technologies_JPA_ExposeTable(String exposeTableNames) {
		new MetadataToInfoProcessor().upOpen_Technologies_JPA_ExposeTable(pathInfo, StringUtil.splitTrime(exposeTableNames,","));		
	}
	
}
