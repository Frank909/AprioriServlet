package database;

/**
 * Classe eccezione DatabaseConnectionException che estende {@link Exception} 
 * per modellare il fallimento nella connessione al database.
 * @author Francesco Ventura
 * @version 1.0
 */
public class DatabaseConnectionException extends Exception{

	private static final long serialVersionUID = 1L;
	
	public DatabaseConnectionException(){
		super();
	}
	
	public DatabaseConnectionException(String msg){
		super(msg);
	}
}
