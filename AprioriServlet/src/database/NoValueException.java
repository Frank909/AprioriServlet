package database;

/**
 * Classe eccezione NoValueException che estende {@link Exception} 
 * per modellare l’assenza di un valore all’interno di un resultset
 * @author Francesco Ventura
 * @version 1.0
 */
public class NoValueException extends Exception{
	
	private static final long serialVersionUID = 1L;

	public NoValueException() {
		super();
	}
	
	public NoValueException(String msg){
		super(msg);
	}
}
