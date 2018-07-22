package mining;
import java.util.LinkedList;

import data.Data;
import mining.OneLevelPatternException;

/**
 * AssociationRuleMiner è una classe che modella la scoperta di regole di 
 * associazione confidenti a partire da un pattern frequente P
 * @author Francesco Ventura
 * @version 1.0
 */
public class AssociationRuleMiner{

	/**
	 * Per ogni pattern di <code>fp</code>, si generano le regole di associazione (chiamando 
	 * <code>confidentAssociationRuleDiscovery()</code>) e si calcola la confidenza. 
	 * Le regole confidenti sono inserite in una lista.
	 * @param data L'insieme di transazioni di training
	 * @param fp Pattern di origine dal quale generare la regola
	 * @param minConf Minima confidenza
	 * @return Restituisce la lista linkata popolata con regole di associazioni confidenti scoperte da fp in data
	 * @throws OneLevelPatternException Eccezione che viene eseguita nel caso in cui
	 * il pattern <code>fp</code> sia di lunghezza 1.
	 */
	public static LinkedList<AssociationRule> confidentAssociationRuleDiscovery(Data data, FrequentPattern fp, float minConf) throws OneLevelPatternException{
		if(fp.getPatternLength() == 1)
			throw new OneLevelPatternException("Length of \"" + fp.toString() + "\" is 1");
		else{
			LinkedList<AssociationRule> outputAR = new LinkedList<>();

			for(int iCut = 0; iCut < fp.getPatternLength(); iCut++){
				AssociationRule AR = confidentAssociationRuleDiscovery(data, fp, minConf, iCut);
				if(AR.getConfidence() >= minConf)
					outputAR.add(AR);
			}
			return outputAR;
		}
	}

	/**
	 * Crea una regola di associazione estraendo come antecedente l’insieme degli item posizionati in <code>fp</code>
	 * prima dell’indice <code>iCut</code> e come conseguente l’insieme degli item posizionati in <code>fp</code> dopo 
	 * dell’indice <code>iCut</code> (compresi <code>iCut</code>). Si calcola la confidenza della regola
	 * @param data Insieme di transazioni
	 * @param fp Pattern di origine dal quale generare la regola di associazione
	 * @param minConf Minima confidenza
	 * @param iCut Posizione dell’item di taglio in <code>fp</code>
	 * @return Restituisce la regola di associazione
	 */
	private static AssociationRule confidentAssociationRuleDiscovery(Data data, FrequentPattern fp, float minConf, int iCut){
		AssociationRule AR = new AssociationRule(fp.getSupport());

		//to generate the antecedent of the association rule
		for(int j = 0; j < iCut; j++){
			AR.addAntecedentItem(fp.getItem(j));		
		}

		//to generate the consequent of the association rule
		for(int j = iCut; j < fp.getPatternLength(); j++){
			AR.addConsequentItem(fp.getItem(j));
		}	

		AR.setConfidence(AssociationRuleMiner.computeConfidence(data, AR));
		return AR;
	}

	//Aggiorna il supporto
	/**
	 * Si ottiene da AR il numero di transazioni che supportano il pattern, si calcola il numero di 
	 * transazione che supportano l’antecedente, si calcola/restituisce la confidenza secondo la formula 
	 * indicata in precedenza.
	 * @param data L’insieme delle transazioni
	 * @param AR La regola di associazione di cui calcolare la confidenza
	 * @return Restituisce confidenza di AR (# transazioni in cui il pattern AR.antecedente U
	 * 		AR.consguente è verificato / (# transazioni in cui l’antecedente della regola AR.antecente è verificato)
	 */
	static float computeConfidence(Data data, AssociationRule AR){
		FrequentPattern fp = new FrequentPattern();
		float patternSupport = AR.getSupport();

		for(int i = 0; i < AR.getAntecedentLength(); i++)
			fp.addItem(AR.getAntecedentItem(i));
		fp.setSupport(FrequentPatternMiner.computeSupport(data, fp));

		return patternSupport/fp.getSupport();
	}
}