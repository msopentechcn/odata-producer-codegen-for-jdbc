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
