package mining;

import java.io.Serializable;

/**
 * AssociationRule è una classe che modella una regola di associazione confidente.
 * Implementa l'interfaccia <code>Comparable</code>.
 * @author Francesco Ventura
 * @version 1.0
 */
public class AssociationRule implements Comparable<AssociationRule>, Serializable{
	
	private static final long serialVersionUID = 1L;

	/**
	 * Attributo che modella l'antecedente della regola di associazione inizialmente vuoto
	 */
	private Item antecedent[] = new Item[0];
	
	/**
	 * Attributo che modella il conseguente della regola di associazione inizialmente vuoto
	 */
	private Item consequent[] = new Item[0];
	
	/**
	 * Supporto della regola
	 */
	private float support;
	
	/**
	 * Confidenza della regola 
	 */
	private float confidence;
	
	/**
	 * Costruttore che inizializza il membro <code>support</code> con l’argomento del costruttore
	 * @param support Supporto del pattern dal quale la regola sarà creata
	 */
	AssociationRule(float support){
		this.support = support;
	}
	
	/**
	 * Metodo di accesso al valore del supporto della regola
	 * @return Restituisce il membro support
	 */
	float getSupport(){
		return support;
	}
	
	/**
	 * Metodo di accesso al valore della confidenza della regola
	 * @return Restituisce il membro confidence
	 */
	float getConfidence(){
		return confidence;
	}
	
	/**
	 * Metodo di accesso al valore della lunghezza dell'antecedente
	 * @return Restituisce la lunghezza dell'array antecedent
	 */
	int getAntecedentLength(){
		return antecedent.length;
	}
	
	/**
	 * Metodo di accesso al valore della lunghezza del conseguente
	 * @return Restituisce la lunghezza dell'array consequent
	 */
	int getConsequentLength(){
		return consequent.length;
	}
	
	/**
	 * Estende di una unità la dimensione dell’array antecedent e aggiunge 
	 * l’argomento della procedura nell’ultima posizione di tale array
	 * @param item Item da aggiungere all’antecedente
	 */
	void addAntecedentItem(Item item){
		int length = antecedent.length;
		
		Item[] _antecedent = new Item[length + 1];
		System.arraycopy(antecedent, 0, _antecedent, 0, length);
	
		_antecedent[length] = item;
		antecedent = _antecedent;
	}
	
	/**
	 * Estende di una unità la dimensione dell’array consequent e aggiunge 
	 * l’argomento della procedura nell’ultima posizione di tale array
	 * @param item Item da aggiungere al conseguente
	 */
	void addConsequentItem(Item item){
		int length = consequent.length;
		
		Item[] _consequent = new Item[length + 1];
		System.arraycopy(consequent, 0, _consequent, 0, length);
		
		_consequent[length] = item;
		consequent = _consequent;
	}
	
	/**
	 * Metodo di accesso ad un elemento dell'array <code>antecedent</code>
	 * @param index Indice nell’array antecedent
	 * @return Restituisce l’elemento in posizione <code>index</code> nell'array <code>antecedent</code>
	 */
	Item getAntecedentItem(int index){
		return antecedent[index];
	}

	/**
	 * Metodo di accesso ad un elemento dell'array <code>consequent</code>
	 * @param index Indice nell’array consequent
	 * @return Restituisce l’elemento in posizione <code>index</code> nell'array <code>consequent</code>
	 */
	Item getConsequentItem(int index){
		return consequent[index];
	}
	
	/**
	 * Assegna al membro <code>confidence</code> il parametro della procedura
	 * @param confidence Confidenza della regola
	 */
	void setConfidence(float confidence){
		this.confidence = confidence;
	}
	
	/**
	 * Override del metodo <code>toString()</code> della superclasse Object
	 * Si ottiene una rappresentazione in stringa di antecedent e consequent. 
	 * Si crea e restituisce la stringa che rappresenta la regola, supporto e confidenza
	 * @see Object#toString()
	 * @return Restituisce una stringa contenente la regola nella forma 
	 * 		“antecedente => conseguente (supporto, confidenza)” 
	 */
	@Override
	public String toString(){ 
		String result = "";
		
		for(int i = 0; i < getAntecedentLength(); i++){	
			result += getAntecedentItem(i);
			if(i < getAntecedentLength() - 1)
				result += " AND ";
		}

		result += "==>";
		
		for(int i = 0; i < getConsequentLength(); i++){
			result += getConsequentItem(i);
			if(i < getConsequentLength() - 1)
				result += " AND ";
		}
		
		result += "[" + support + ", " + confidence + "]";
		
		return result;
	}

	/*
	 * Metodo ereditato dell'interfaccia <code>Comparable</code> per il
	 * confronto tra regole rispetto alla confidenza.
	 * @param ar regola di associazione in input
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(AssociationRule ar){ 
		if (confidence == ar.getConfidence())
			if(this.equals(ar))
				return 0;
		if (confidence > ar.getConfidence())
			return 1;
		else
			return -1;		
	}
	
}
