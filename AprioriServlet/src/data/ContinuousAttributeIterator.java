package data;

import java.util.Iterator;

/**
 * La classe <code>ContinuousAttributeIterator</code> realizza l’iteratore che itera sugli elementi della sequenza composta da 
 * valori reali equidistanti tra di loro (cut points) compresi tra min e max ottenuti per mezzo di discretizzazione. 
 * La classe implementa i metodi della interfaccia generica <code>Iterator<T></code> tipizzata con <code>Float</code>.
 * @author Francesco Ventura
 * @version 1.0
 */
class ContinuousAttributeIterator implements Iterator<Float> {

	/**
	 * Minimo
	 */
	private float min;
	
	/**
	 * Massimo
	 */
	private float max;
	
	/**
	 * Posizione dell’iteratore nella collezione di cut point generati per 
	 * [<code>min</code>, <code>max</code>[ tramite discretizzazione
	 */
	private int j = 0;
	
	/**
	 * Numero di intervalli di discretizzazione
	 */
	private int numValues;

	/**
	 * Avvalora i membri attributi della classe con i parametri del costruttore
	 * @param min Minimo
	 * @param max Massimo
	 * @param numValues Numero di intervalli di discretizzazione
	 */
	ContinuousAttributeIterator(float min, float max, int numValues){
		this.min = min;
		this.max = max;
		this.numValues = numValues;
	}

	/**
	 * Override del metodo <code>hasNext()</code> dell'interfaccia {@link Iterator}.
	 * @return Restituisce <code>true</code> se j<=numValues, <code>false</code> altrimenti
	 */
	@Override
	public boolean hasNext() {
		return (j <= numValues);
	}

	/**
	 * Override del metodo <code>next()</code> dell'interfaccia {@link Iterator}.
	 * @return incrementa <code>j</code>, restituisce il cut point in posizione 
	 * <code>j</code> - 1 (<code>min</code> + (<code>j</code> - 1)*(<code>max</code> - 
	 * <code>min</code>)/ <code>numValues</code>).
	 */
	@Override
	public Float next() {
		j++;
		return min + ((max - min) / numValues) * (j - 1);
	}

	/**
	 * Override del metodo <code>remove()</code> dell'interfaccia {@link Iterator}.
	 */
	@Override
	public void remove() {}
}

