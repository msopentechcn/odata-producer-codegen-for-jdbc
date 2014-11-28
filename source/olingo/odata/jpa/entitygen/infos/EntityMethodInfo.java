/**
 * 
 */
package olingo.odata.jpa.entitygen.infos;

/**
 * 2014-11-12
 * @author Bruce Li
 */
public class EntityMethodInfo {
	private StringBuilder methodStr;
	private String typeName;
	private String fieldMethodName;
	private String fieldName;
	public StringBuilder getMethodStr() {
		return methodStr;
	}
	public void setMethodStr(StringBuilder methodStr) {
		this.methodStr = methodStr;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getFieldMethodName() {
		return fieldMethodName;
	}
	public void setFieldMethodName(String fieldMethodName) {
		this.fieldMethodName = fieldMethodName;
	}
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	
}
