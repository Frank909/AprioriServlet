package mining;

/**
 * Classe eccezione NoPatternException in modo da modellare il caso in cui
 * nessuna regola confidente è generata da un pattern frequente.
 * @author Francesco Ventura
 * @version 1.0
 */
public class NoPatternException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public NoPatternException(){
		super();
	}
	
	public NoPatternException(String msg){
		super(msg);
	}
	
}
