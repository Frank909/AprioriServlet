package mining;
import java.io.Serializable;

import data.Attribute;

/**
 * Item è una classe astratta che modella un generico item
 * @author Francesco Ventura
 * @version 1.0
 * */

abstract class Item implements Serializable{
	
	private static final long serialVersionUID = 1L;

	/**
	 * Attributo coinvolto nell'item
	 */
	private Attribute attribute;
	
	/**
	 * Valore assegnato all'attributo
	 */
	private Object value;
	
	/**
	 * Inizializza i valori dei membri attributi con i parametri passati come argomento al costruttore
	 * @param attribute attributo dell'item 
	 * @param value valore dell'item
	 */
	Item(Attribute attribute, Object value){
		this.attribute = attribute;
		this.value = value;
	}
	
	/**
	 * Metodo di accesso all'attributo privato <code>attribute</code>
	 * @return Restituisce il membro <code>attribute</code>
	 */
	Attribute getAttribute(){
		return attribute;
	}
	
	/**
	 * Metodo di accesso all'attributo privato <code>value</code>
	 * @return Restituisce il membro value
	 */
	Object getValue(){
		return value;
	}
	
	/**
	 * Metodo astratto da realizzare nelle sottoclassi
	 * @param value Valore dell'item
	 * @return Restituisce un valore booleano
	 */
	abstract boolean checkItemCondition(Object value);
	
	/**
	 * Override del metodo <code>toString()</code> ereditato da <code>Object</code>.
	 * @return Restituisce una stringa nella forma <code>attribute = value</code>
	 * @see Object#toString()
	 */
	@Override
	public String toString(){
		return "(" + attribute + "=" + value + ")";
	}
}
