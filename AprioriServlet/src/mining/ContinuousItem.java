package mining;

import java.io.Serializable;

import data.ContinuousAttribute;

/**
 * ContinuousItem è una classe che estende la classe astratta {@link Item} e 
 * modella la coppia < Attributo continuo - Intervallo di valori > 
 * Esempio: (Temperature in [10;30.31[)
 * @author Francesco Ventura
 * @version 1.0
 */
class ContinuousItem extends Item implements Serializable{

	private static final long serialVersionUID = 1L;

	/**
	 * Chiama il costruttore della superclasse passandogli come argomenti <code>attribute</code> 
	 * e <code>value</code>.
	 * @param attribute Attributo continuo
	 * @param value Intervallo valori
	 */
	public ContinuousItem(ContinuousAttribute attribute, Interval value){
		super(attribute, value);
	}

	/**
	 * Override del metodo <code>checkItemCondition(_)</code> della classe {@link Item}.
	 * Verifica che il parametro value rappresenti un numero reale incluso tra gli estremi 
	 * dell’intervallo associato allo item in oggetto (richiamare il metodo 
	 * <code>checkValueInclusion(_)</code> della classe {@link Interval}).
	 * @param value Valore (al run time sarà di tipo Float)
	 * @return Restituisce vero se il valore è reale e compreso fra gli estremi dell'intervallo,
	 * falso altrimenti.
	 */
	@Override
	boolean checkItemCondition(Object value) { 
		return ((Interval)getValue()).checkValueInclusion((float)value);
	}

	/**
	 * Avvalora la stringa che rappresenta lo stato dell’oggetto (per esempio, 
	 * Temperatura in [10;20.4[) e ne restituisce il riferimento.
	 * @return Stringa che rappresenta lo stato dell’oggetto nella forma 
	 * <nome attributo> in [<inf>,<sup>[ 
	 */
	@Override
	public String toString(){
		return getAttribute() + " in " + getValue();
	}
}
