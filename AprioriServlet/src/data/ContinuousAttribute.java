package data;

import java.io.Serializable;
import java.util.Iterator;

/**
 * ContinuousAttribute è una classe che modella un attributo continuo (numerico)
 * rappresentandone il dominio. Estende la classe {@link Attribute} ed implementa le interfacce 
 * {@link Serializable} e {@link Iterable}.
 * 
 * @author Francesco Ventura
 * @version 1.0
 */

public class ContinuousAttribute extends Attribute implements Iterable<Float>, Serializable{

	private static final long serialVersionUID = 1L;

	/**
	 * Estremo superiore dell'intervallo del dominio
	 * */
	private float max;
	
	/**
	 * Estremo inferiore dell'intervallo del dominio
	 */
	private float min;
	
	
	/**
	 * Costruttore della classe ContinuousAttribute, invoca il costruttore della superclasse
	 * Attribute e inizializza il valore dei parametri.
	 * 
	 * @param name Nome dell'attributo
	 * @param index Identificativo numerico dell'attributo
	 * @param min Estremo inferiore del dominio
	 * @param max Estremo superiore del dominio
	 * @see Attribute#Attribute(String, int)
	 */
	public ContinuousAttribute(String name, int index, float min, float max) {
		super(name, index);
		this.max = max;
		this.min = min;
	}
	
	/**
	 * Metodo di accesso all'attributo privato <code>min</code>
	 * @return Restituisce il valore dell'attributo <code>min</code>
	 */
	float getMin(){
		return min;
	}
	
	/**
	 * Metodo di accesso all'attributo private <code>max</code> 
	 * @return Restituisce il valore dell'attributo <code>max</code>
	 */
	float getMax(){
		return max;
	}

	/**
	 * Istanzia e restituisce un riferimento ad oggetto di classe 
	 * {@link ContinuousAttributeIterator} con numero di intervalli di discretizzazione pari a 5.
	 * @return Restituisce un riferimento a una istanza di {@link ContinuousAttributeIterator} 
	 */
	@Override
	public Iterator<Float> iterator() {
		return new ContinuousAttributeIterator(min, max, 5); 
	}
}
