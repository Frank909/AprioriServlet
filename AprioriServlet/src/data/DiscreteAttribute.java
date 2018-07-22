package data;

import java.io.Serializable;

/**
 * DiscreteAttribute è una classe che modella un attributo discreto rappresentando 
 * l’insieme di valori distinti del relativo dominio. Estende la classe {@link Attribute}
 * 
 * @author Francesco Ventura
 * @version 1.0
 */

public class DiscreteAttribute extends Attribute implements Serializable{


	private static final long serialVersionUID = 1L;
	
	/*
	 * array di stringhe, una per ciascun valore discreto
	 */
	private String[] values;
	
	/**
	 * Costruttore della classe DiscreteAttribute invoca il costruttore della superclasse
	 * Attribute e avvalora l'array values[] con i valori discreti in input.
	 * 
	 * @see Attribute#Attribute(String, int)
	 * @param name Nome simbolico dell'attributo
	 * @param index Identificativo numerico dell'attributo
	 * @param values Array di stringhe di valori discreti che ne costituiscono il dominio 
	 */
	public DiscreteAttribute(String name, int index, String[] values) {
		super(name, index);
		this.values = values.clone(); //shallow copy
	}
	
	
	/**
	 * Metodo di accesso alla lunghezza dell'array privato <code>values</code>
	 * @return Restituisce la cardinalità del membro <code>values</code>
	 */
	public int getNumberOfDistinctValues(){
		return values.length;
	}
	
	
	/**
	 * Metodo di accesso ad un valore dell'array values, dato in input l'indice <code>index</code>
	 * @param index indice di tipo intero
	 * @return Restituisce il valore in posizione <code>index</code> del membro <code>values</code>
	 */
	public String getValue(int index){
		return values[index];
	}
}
