package ms.open.technologies.jpa.entitygen.infos;
/**
 * 2014-11-11
 * @author Bruce Li
 */
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
