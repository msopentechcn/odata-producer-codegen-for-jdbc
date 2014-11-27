/**
 * 
 */
package olingo.apache.auto.entity.infos;

import java.util.List;

/**
 * 2014-11-12
 * @author Bruce Li
 */
public class EntityInfo {
	private List<EntityFieldInfo> fieldInfos;
	private List<EntityMethodInfo> methodInfos;
	public List<EntityFieldInfo> getEntityFieldInfos() {
		return fieldInfos;
	}
	public void setEntityFieldInfos(List<EntityFieldInfo> fieldInfos) {
		this.fieldInfos = fieldInfos;
	}
	public List<EntityMethodInfo> getEntityMethodInfos() {
		return methodInfos;
	}
	public void setEntityMethodInfos(List<EntityMethodInfo> methodInfos) {
		this.methodInfos = methodInfos;
	}
	
}
