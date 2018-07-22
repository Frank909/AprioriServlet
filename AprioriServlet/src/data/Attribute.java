package data;

import java.io.Serializable;

/**
 * Attribute è una classe astratta che modella un generico attributo
 * discreto o continuo. Implementa l'interfaccia {@link Serializable}.
 * 
 * @author Francesco Ventura
 * @version 1.0
 * */

public abstract class Attribute implements Serializable{
	
	private static final long serialVersionUID = 1L;

	/**
	 * Nome simbolico dell'attributo
	 */
	private String name; 
	
	/**
	 * Identificativo numerico dell'attributo
	 */
	private int index;
	
	/**
	 * Costruttore della classe Attribute, coi rispettivi parametri <code>name</code> e
	 * <code>index</code> che indicano rispettivamente il nome dell'attributo da modellare,
	 * e l'indice in cui l'attributo è posizionato. Inizializza i valori suddetti.
	 * 
	 * @param name Nome simbolico dell'attributo
	 * @param index Identificativo numerico dell'attributo 
	 * */
	Attribute(String name, int index){
		this.name = name;
		this.index = index;
	}
	
	/**
	 * Metodo di accesso all'attributo privato <code>name</code>
	 * @return Restituisce il valore dell'attributo <code>name</code>
	 * */
	String getName(){
		return name;
	}
	
	/**
	 * Metodo di accesso all'attributo private <code>index</code>
	 * @return Restituisce il valore dell'attributo <code>index</code>
	 */
	public int getIndex() {
		return index;
	}
	
	/**
	 * Override del metodo <code>toString()</code> della superclasse Object
	 * @see Object#toString()
	 * @return Restituisce il valore dell'attributo <code>name</code>
	 * */
	@Override
	public String toString(){
		return name;
	}
}
