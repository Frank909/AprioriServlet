package mining;

import java.io.Serializable;

/**
 * Interval è una classe che modella un intervallo reale [inf ,sup[
 * @author Francesco Ventura
 * @version 1.0
 */
class Interval implements Serializable{
	
	private static final long serialVersionUID = 1L;

	/**
	 * Estremo inferiore dell'intervallo
	 */
	float inf; 
	
	/**
	 * Estremo superiore dell'intervallo
	 */
	float sup; 
	
	/**
	 * Costruttore della classe Interval. 
	 * Avvalora i due attributi <code>inf</code> e <code>sup</code> con i 
	 * parametri del costruttore.
	 * @param inf Estremo inferiore
	 * @param sup Estremo superiore
	 */
	Interval(float inf, float sup){
		this.inf = inf;
		this.sup = sup;
	}
	
	/**
	 * Avvalora <code>inf</code> con il parametro passato
	 * @param inf Estremo inferiore
	 */
	void setInf(float inf){
		this.inf = inf;
	}
	
	/**
	 * Avvalora <code>sup</code> con il parametro passato
	 * @param sup Estremo superiore
	 */
	void setSup(float sup){
		this.sup = sup;
	}
	
	/**
	 * Metodo di accesso all'attributo <code>inf</code>.
	 * @return Restituisce il valore dell'attributo <code>inf</code>
	 */
	float getInf() {
		return inf;
	}
	
	/**
	 * Metodo di accesso all'attributo <code>sup</code>.
	 * @return Restituisce il valore dell'attributo <code>sup</code>
	 */
	float getSup() {
		return sup;
	}
	
	/**
	 * Verifica se il valore è compreso nell'intervallo.
	 * @param value Valore assunto da una attributo continuo per il quale verificare 
	 * la appartenenza all’intervallo.
	 * @return Restituisce vero se il parametro è maggiore uguale di <code>inf</code> e minore di 
	 * <code>sup</code>, <code>false</code> altrimenti.
	 */
	boolean checkValueInclusion(float value){
		return (value >= inf && value < sup);
	}
	
	/**
	 * Riferimento ad una stringa in cui si rappresenta l’intervallo [<code>inf</code>,
	 * <code>sup</code>[
	 * @return Rappresenta in una stringa gli estremi dell’intervallo e restituisce tale stringa
	 */
	@Override
	public String toString(){
		return "[" + inf + ", " + sup + "[";
	}
}
