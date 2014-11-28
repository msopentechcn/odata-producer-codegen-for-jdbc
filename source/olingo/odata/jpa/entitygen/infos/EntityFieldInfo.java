/**
 * 
 */
package olingo.odata.jpa.entitygen.infos;

/**
 * 2014-11-12
 * @author Bruce Li
 */
public class EntityFieldInfo {
	private StringBuilder fieldStr;
	private String annotationType;
	private String typeName;
	private String fieldName;
	public StringBuilder getFieldStr() {
		return fieldStr;
	}
	public void setFieldStr(StringBuilder fieldStr) {
		this.fieldStr = fieldStr;
	}
	public String getAnnotationType() {
		return annotationType;
	}
	public void setAnnotationType(String annotationType) {
		this.annotationType = annotationType;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	
}
