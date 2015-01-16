package ms.open.technologies.jpa.entitygen.exception;
/**
 * 2014-12-17
 * @author Bruce Li
 */
public class EntitygenException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public EntitygenException(final String excepMes){
		super(excepMes);
	}
}
