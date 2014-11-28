/**
 * 
 */
package olingo.odata.jpa.entitygen.infos;

import java.util.List;

/**
 * 2014-11-13
 * @author Bruce Li
 */
public class ModelInfo {
	private String databaesName;
	private String jdbcUser;
	private String jdbcUrl;
	private String jdbcDriver;
	private List<EntityClassInfo> entityClassInfos;
	public String getDatabaesName() {
		return databaesName;
	}
	public void setDatabaesName(String databaesName) {
		this.databaesName = databaesName;
	}
	public String getJdbcUser() {
		return jdbcUser;
	}
	public void setJdbcUser(String jdbcUser) {
		this.jdbcUser = jdbcUser;
	}
	public String getJdbcUrl() {
		return jdbcUrl;
	}
	public void setJdbcUrl(String jdbcUrl) {
		this.jdbcUrl = jdbcUrl;
	}
	public String getJdbcDriver() {
		return jdbcDriver;
	}
	public void setJdbcDriver(String jdbcDriver) {
		this.jdbcDriver = jdbcDriver;
	}
	public List<EntityClassInfo> getEntityClassInfos() {
		return entityClassInfos;
	}
	public void setEntityClassInfos(List<EntityClassInfo> entityClassInfos) {
		this.entityClassInfos = entityClassInfos;
	}
	
}
