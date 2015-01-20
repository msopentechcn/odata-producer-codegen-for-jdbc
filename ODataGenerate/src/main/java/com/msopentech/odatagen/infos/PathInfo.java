package com.msopentech.odatagen.infos;
import java.util.List;
/**
 * 2014-11-13
 * @author Bruce Li
 */
public class PathInfo {
	private String realPath = System.getProperty("user.dir");
	private String webInfPath;
	private String entityPackage = "ms.open.technologies.jpa.entitys";
	private String applicationToSourcePath = "WEB-INF/classes";
	private List<String> entityPackAndNames;
	private boolean isCompile = true;
	private String persistenceUnitName;
	private String persistenceClassPath = "persistence.xml";
	
	
	public String getWebInfPath() {
		return webInfPath;
	}
	public void setWebInfPath(String webInfPath) {
		this.webInfPath = webInfPath;
	}
	public String getPersistenceClassPath() {
		return persistenceClassPath;
	}
	public void setPersistenceClassPath(String persistenceClassPath) {
		this.persistenceClassPath = persistenceClassPath;
	}
	public String getPersistenceUnitName() {
		return persistenceUnitName;
	}
	public void setPersistenceUnitName(String persistenceUnitName) {
		this.persistenceUnitName = persistenceUnitName;
	}
	public String getRealPath() {
		return realPath;
	}
	public void setRealPath(String realPath) {
		this.realPath = realPath;
	}
	public String getEntityPackage() {
		return entityPackage;
	}
	public void setEntityPackage(String entityPackage) {
		this.entityPackage = entityPackage;
	}
	public String getApplicationToSourcePath() {
		return applicationToSourcePath;
	}
	public void setApplicationToSourcePath(String applicationToSourcePath) {
		this.applicationToSourcePath = applicationToSourcePath;
	}
	public List<String> getEntityPackAndNames() {
		return entityPackAndNames;
	}
	public void setEntityPackAndNames(List<String> entityPackAndNames) {
		this.entityPackAndNames = entityPackAndNames;
	}
	public boolean isCompile() {
		return isCompile;
	}
	public void isCompile(boolean isCompile) {
		this.isCompile = isCompile;
	}
	
}
