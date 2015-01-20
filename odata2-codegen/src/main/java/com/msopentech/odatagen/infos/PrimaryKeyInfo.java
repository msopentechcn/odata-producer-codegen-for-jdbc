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
public class PrimaryKeyInfo {
	private String primaryKeyName;
	private Integer primaryKeyType;
	private String primaryKeyTypeName;
	private Integer primaryKeyPrecision;
	private boolean primaryKeyIsAutoIncrement;
	private boolean primaryKeyIsNullable;
	private boolean primaryKeyIsReadOnly;
	public String getPrimaryKeyName() {
		return primaryKeyName;
	}
	public void setPrimaryKeyName(String primaryKeyName) {
		this.primaryKeyName = primaryKeyName;
	}
	public Integer getPrimaryKeyType() {
		return primaryKeyType;
	}
	public void setPrimaryKeyType(Integer primaryKeyType) {
		this.primaryKeyType = primaryKeyType;
	}
	public String getPrimaryKeyTypeName() {
		return primaryKeyTypeName;
	}
	public void setPrimaryKeyTypeName(String primaryKeyTypeName) {
		this.primaryKeyTypeName = primaryKeyTypeName;
	}
	public Integer getPrimaryKeyPrecision() {
		return primaryKeyPrecision;
	}
	public void setPrimaryKeyPrecision(Integer primaryKeyPrecision) {
		this.primaryKeyPrecision = primaryKeyPrecision;
	}
	public boolean isPrimaryKeyIsAutoIncrement() {
		return primaryKeyIsAutoIncrement;
	}
	public void setPrimaryKeyIsAutoIncrement(boolean primaryKeyIsAutoIncrement) {
		this.primaryKeyIsAutoIncrement = primaryKeyIsAutoIncrement;
	}
	public boolean isPrimaryKeyIsNullable() {
		return primaryKeyIsNullable;
	}
	public void setPrimaryKeyIsNullable(boolean primaryKeyIsNullable) {
		this.primaryKeyIsNullable = primaryKeyIsNullable;
	}
	public boolean isPrimaryKeyIsReadOnly() {
		return primaryKeyIsReadOnly;
	}
	public void setPrimaryKeyIsReadOnly(boolean primaryKeyIsReadOnly) {
		this.primaryKeyIsReadOnly = primaryKeyIsReadOnly;
	}
	
}
