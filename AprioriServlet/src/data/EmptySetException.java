package data;

/**
 * Classe eccezione EmptySetException per modellare l’eccezione
 * che occorre qualora l'insieme di training fosse vuoto 
 * (non contiene transazioni/esempi).
 * @author Francesco Ventura
 * @version 1.0
 */
public class EmptySetException extends Exception {

	private static final long serialVersionUID = 1L;

	public EmptySetException(){
		super();
	}
	
	public EmptySetException(String msg){
		super(msg);
	}
	
}
