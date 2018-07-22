package mining;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * FrequentPattern è una classe che rappresenta un itemset (o pattern) frequente
 * @author Francesco Ventura
 * @version 1.0
 */
public class FrequentPattern implements Serializable{

	private static final long serialVersionUID = 1L;

	/**
	 * Lista che contiene riferimenti a oggetti istanza della classe Item che definiscono il pattern
	 */
	private List<Item> fp;
	
	/**
	 * Valore di supporto calcolato per il pattern fp
	 */
	private float support;
	
	/**
	 * Costruttore che inizializza la lista <code>fp</code>
	 */
	FrequentPattern(){
		fp = new LinkedList<Item>();
	}
	
	/**
	 * Si estende la dimensione di fp di 1 e si inserisce in ultima posizione l’argomento della procedura
	 * @param item Oggetto Item da aggiungere al pattern
	 */
	void addItem(Item item){ //aggiunge un nuovo item al pattern
		fp.add(item);
	}
	
	/**
	 * Metodo di accesso all'Item che occupa la posizione indicata in fp
	 * @param index Posizione in fp
	 * @return Restituisce l'item in posizione index di fp
	 */
	Item getItem(int index){
		return fp.get(index);
	}
	
	/**
	 * Valore di supporto del pattern
	 * @return Restituisce il membro <code>support</code>
	 */
	float getSupport(){
		return support;
	}
	
	/**
	 * Metodo di accesso alla lunghezza del pattern
	 * @return Restituisce la dimensione (lunghezza) di <code>fp</code>
	 */
	int getPatternLength(){
		return fp.size();
	}
	
	/**
	 * Assegna al membro <code>support</code> il parametro della procedura
	 * @param support: Valore di supporto del pattern
	 */
	void setSupport(float support){
		this.support = support;
	}
	
	/**  
	 * Override del metodo <code>toString()</code> ereditato da Object.
	 * Si scandisce fp al fine di concatenare in una stringa la rappresentazione degli item; 
	 * alla fine si concatena il supporto.
	 * @return Restituisce la stringa rappresentante item set e supporto
	 * @see Object#toString() 
	 */
	@Override
	public String toString(){
		String value = "";
		for(int i = 0; i < fp.size() - 1; i++)
			value += fp.get(i) +" AND ";
		if(fp.size() > 0){
			value += fp.get(fp.size() - 1);
			value += "[" + support + "]";
		}	
		return value;
	}
}
