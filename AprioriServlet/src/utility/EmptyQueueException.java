package utility;

/**
 * Classe eccezione EmptyQueueException per modellare
 * l’eccezione che occorre qualora si cerca di leggere/cancellare da una coda è vuota.
 * @author Francesco Ventura
 * @version 1.0
 */
public class EmptyQueueException extends Exception {
	
	private static final long serialVersionUID = -8385830367045484826L;

	public EmptyQueueException(){
		super();
	}
	
	public EmptyQueueException(String msg){
		super(msg);
	}
	
}
