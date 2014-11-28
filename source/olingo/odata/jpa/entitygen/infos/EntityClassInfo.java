/**
 * 
 */
package olingo.odata.jpa.entitygen.infos;

/**
 * 2014-11-12
 * @author Bruce Li
 */
public class EntityClassInfo {
	private String className;
	private StringBuilder noEntityInfoStr;
	private EntityInfo entityInfo;
	public StringBuilder getNoEntityInfoStr() {
		return noEntityInfoStr;
	}
	public void setNoEntityInfoStr(StringBuilder noEntityInfoStr) {
		this.noEntityInfoStr = noEntityInfoStr;
	}
	public EntityInfo getEntityInfo() {
		return entityInfo;
	}
	public void setEntityInfo(EntityInfo entityInfo) {
		this.entityInfo = entityInfo;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	
}
