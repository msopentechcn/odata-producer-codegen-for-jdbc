/**
 * 
 */
package olingo.odata.jpa.entitygen.util;

import java.sql.Connection;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * 2014-11-11
 * @author Bruce Li
 */
public class MetadataUtil {
	private static EntityManager entityManager;
    public static Connection getConnection(String persistenceUnitName) throws Exception{
    	if(entityManager == null){
    		entityManager = Persistence.createEntityManagerFactory(persistenceUnitName).createEntityManager();
    	}
    	entityManager.getTransaction().begin();
		Connection connection = entityManager.unwrap(java.sql.Connection.class);
		entityManager.getTransaction().commit();
		return connection;
	}
}
