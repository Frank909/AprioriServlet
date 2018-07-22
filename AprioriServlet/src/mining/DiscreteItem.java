package mining;
import java.io.Serializable;

import data.DiscreteAttribute;

/**
 * DiscreteItem è una classe rappresenta la coppia <Attributo discreto - Valore discreto>
 * Estende la classe {@link Item}
 * @author Francesco Ventura
 * @version 1.0
 */
class DiscreteItem extends Item implements Serializable{

	private static final long serialVersionUID = 1L;

	/**
	 * Invoca il costruttore della superclasse per avvalorare i membri
	 * @see Item#Item(Attribute, Object)
	 * @param attribute Attributo discreto
	 * @param value Valore rispettivo
	 */
	public DiscreteItem(DiscreteAttribute attribute, String value) {
		super(attribute, value);
	}

	/**
	 * Override del metodo <code>checkItemCondition()</code> della superclasse Item
	 * @see Item#checkItemCondition(Object)
	 * @return Verifica che il membro value sia uguale (nello stato) all’argomento passato come parametro della funzione
	 */
	@Override
	boolean checkItemCondition(Object value) {
		return getValue().equals(value); 
	}
}
