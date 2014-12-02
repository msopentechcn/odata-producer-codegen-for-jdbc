/**
 * 
 */
package olingo.odata.jpa.entitygen;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import olingo.odata.jpa.entitygen.infos.EntityClassInfo;
import olingo.odata.jpa.entitygen.infos.EntityFieldInfo;
import olingo.odata.jpa.entitygen.infos.EntityInfo;
import olingo.odata.jpa.entitygen.infos.EntityMethodInfo;
import olingo.odata.jpa.entitygen.infos.ModelInfo;
import olingo.odata.jpa.entitygen.infos.PathInfo;
import olingo.odata.jpa.entitygen.metadata.processor.MetadataToInfoProcessor;
import olingo.odata.jpa.entitygen.util.StringUtil;

/**
 * 2014-11-12
 * 
 * @author Bruce Li
 */
public class EntityProcessor {
	public EntityClassInfo modelReadToEntityClassInfo(PathInfo pathInfo)
			throws IOException {
		/* set EntityClassInfo */
		return setEntityClassInfo(pathInfo);
	}

	public void creatModel(PathInfo pathInfo) throws Exception {
		/* Get ModelInfo fo DatabaesInfo */
		ModelInfo modelInfo = new DatabaesInfoCaseEntityClassInfoProcessor()
				.getModelInfo(new MetadataToInfoProcessor()
						.getDatabaseInfo(pathInfo.getPersistenceUnitName()),
						modelReadToEntityClassInfo(pathInfo), pathInfo);
		/* Write ModelInfo to source code */
		pathInfo = writeCode(modelInfo, pathInfo);
		/* Updata persistence file with <class></class> set */
		new PersistenceXMLProcessor().updataPersistenceXML(pathInfo);
	}

	private PathInfo writeCode(ModelInfo modelInfo, PathInfo pathInfo)
			throws IOException {
		/* Detection the folder EntityPackage if exsisted? */
		String codeFolderPath = getCodeFolderPath(pathInfo);
		List<EntityClassInfo> entityClassInfos = modelInfo
				.getEntityClassInfos();
		List<String> entityPackAndNames = new ArrayList();
		for (int i = 0; entityClassInfos != null && i < entityClassInfos.size(); i++) {
			EntityClassInfo entityClassInfo = entityClassInfos.get(i);
			String className = entityClassInfo.getClassName();
			String resourceCodePath = codeFolderPath + "/" + className
					+ ".java";
			FileWriter fileWriter = new FileWriter(resourceCodePath, false);
			/* Get code as String */
			String code = entityClassInfo
					.getNoEntityInfoStr()
					.toString()
					.replaceAll("\\$\\{Body\\}",
							entityCode(entityClassInfo.getEntityInfo()));
			fileWriter.write(code.trim());
			fileWriter.flush();
			fileWriter.close();
			/* Is compile */
			if (pathInfo.isCompile()) {
				Process process = Runtime.getRuntime().exec(
						"javac " + resourceCodePath);
				InputStream in = process.getErrorStream();
				int end;
				byte[] b = new byte[1024];
				while ((end = in.read(b)) > 0) {
					System.out.println(new String(b, "gbk"));
				}
				in.close();
			}
			entityPackAndNames.add(pathInfo.getEntityPackage() + "."
					+ className);
		}
		pathInfo.setEntityPackAndNames(entityPackAndNames);
		return pathInfo;
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
		File sourcePathFolder = new File(codeFolderPath);
		if (!sourcePathFolder.isDirectory()) {
			sourcePathFolder.mkdirs();
		}
		return codeFolderPath;
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
		String realParth = pathInfo.getRealPath();
		String applicationToSourcePath = pathInfo.getApplicationToSourcePath();
		InputStream input = EntityProcessor.class
				.getResourceAsStream("Default.model");
		StringBuilder entityClassInfoStr = new StringBuilder();
		int end;
		byte[] b = new byte[1024];
		while ((end = input.read(b)) > 0) {
			entityClassInfoStr.append(new String(b, "utf-8"));
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
}