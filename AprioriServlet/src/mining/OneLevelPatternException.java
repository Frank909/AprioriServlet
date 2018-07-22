package mining;


/**
 * Classe eccezione OneLevelPatternException per modellare
 * l’eccezione che occorre allor quando si tenta di creare una regola da 
 * pattern di lunghezza 1.
 * @author Francesco Ventura
 * @version 1.0
 */
public class OneLevelPatternException extends Exception {

	private static final long serialVersionUID = 1L;

	public OneLevelPatternException(){
		super();
	}
	
	public OneLevelPatternException(String msg){
		super(msg);
	}
	
}
