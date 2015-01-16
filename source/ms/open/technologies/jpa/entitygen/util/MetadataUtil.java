package ms.open.technologies.jpa.entitygen.util;
import java.sql.Connection;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
/**
 * 2014-11-11
 * @author Bruce Li
 */
public class MetadataUtil {
    public  Connection getConnection(String persistenceUnitName) throws Exception{
    	EntityManager entityManager = Persistence.createEntityManagerFactory(persistenceUnitName).createEntityManager();
    	entityManager.getTransaction().begin();
		Connection connection = entityManager.unwrap(java.sql.Connection.class);
		entityManager.getTransaction().commit();
		return connection;
	}
}
