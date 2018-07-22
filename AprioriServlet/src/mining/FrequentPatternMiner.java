package mining;
import java.util.Iterator;
import java.util.LinkedList;

import data.Attribute;
import data.ContinuousAttribute;
import data.Data;
import data.DiscreteAttribute;
import data.EmptySetException;
import utility.EmptyQueueException;
import utility.Queue;

/**
 * FrequentPatternMiner è una classe che include i metodi per la 
 * scoperta di pattern frequenti con Algoritmo APRIORI
 * @author Francesco Ventura
 * @version 1.0
 *
 */
public class FrequentPatternMiner{

	/**
	 * Genera tutti i pattern k = 1 frequenti e per ognuno di questi genera quelli 
	 * con k > 1 richiamando <code>expandFrequentPatterns()</code>
	 * @param data L’insieme delle transazioni
	 * @param minSup Il minimo supporto
	 * @return Lista linkata contenente i pattern frequenti scoperti in <code>data</code>
	 * @throws EmptySetException Eccezione lanciata nel caso in cui l'insieme di training
	 * sia vuoto
	 */
	public static LinkedList<FrequentPattern> frequentPatternDiscovery(Data data, float minSup) throws EmptySetException{
		if(data.getNumberOfExamples() != 0){
			Queue<FrequentPattern> fpQueue = new Queue<>();		
			LinkedList<FrequentPattern> outputFP = new LinkedList<>();

			for(int i = 0; i < data.getNumberOfAttributes(); i++){
				Attribute currentAttribute = data.getAttribute(i);

				if(currentAttribute instanceof DiscreteAttribute){
					for(int j = 0; j < ((DiscreteAttribute)currentAttribute).getNumberOfDistinctValues(); j++){
						DiscreteItem item = new DiscreteItem((DiscreteAttribute)currentAttribute, 
								((DiscreteAttribute)currentAttribute).getValue(j));

						FrequentPattern fp = new FrequentPattern();
						fp.addItem(item);
						fp.setSupport(FrequentPatternMiner.computeSupport(data, fp));
						if(fp.getSupport() >= minSup){ // 1-FP CANDIDATE
							fpQueue.enqueue(fp);
							outputFP.add(fp);
						}	
					}
				}else{
					Iterator<Float> it = ((ContinuousAttribute)currentAttribute).iterator();
					if(it.hasNext()) {
						float inf = it.next();
						while(it.hasNext()){
							float sup = it.next();
							ContinuousItem item;
							if(it.hasNext())
								item = new ContinuousItem((ContinuousAttribute)currentAttribute,
										new Interval(inf, sup));
							else
								item = new ContinuousItem((ContinuousAttribute)currentAttribute,
										new Interval(inf, sup + 0.01f * (sup - inf)));
							inf = sup;
							FrequentPattern fp = new FrequentPattern();
							fp.addItem(item);
							fp.setSupport(FrequentPatternMiner.computeSupport(data, fp));
							if(fp.getSupport() >= minSup){ // 1-FP CANDIDATE
								fpQueue.enqueue(fp);
								outputFP.add(fp);
							}
						}
					}
				}
			}
			outputFP = expandFrequentPatterns(data, minSup, fpQueue, outputFP);
			return outputFP;
		}else
			throw new EmptySetException("Training Set is Empty!"); 
	}

	/**
	 * Finché fpQueue contiene elementi, si estrae un elemento dalla coda fpQueue, 
	 * si generano i raffinamenti per questo (aggiungendo un nuovo item non incluso). 
	 * Per ogni raffinamento si verifica se è frequente e, in caso affermativo, lo si aggiunge sia ad 
	 * fpQueue sia ad outputFP
	 * @param data L’insieme delle transazioni
	 * @param minSup Minimo supporto
	 * @param fpQueue Coda contente i pattern da valutare
	 * @param outputFP Lista dei pattern frequenti già estratti
	 * @return Restituisce la lista linkata popolata con pattern frequenti a k > 1 
	 */
	private static LinkedList<FrequentPattern> expandFrequentPatterns(Data data, float minSup, Queue<FrequentPattern> fpQueue, LinkedList<FrequentPattern> outputFP){
		LinkedList<FrequentPattern> listFP = new LinkedList<>();

		for(int i = 0; i < outputFP.size(); i++)
			listFP.add(outputFP.get(i));

		while(!fpQueue.isEmpty()){
			try{
				FrequentPattern FP = fpQueue.first();
				fpQueue.dequeue();

				Iterator<FrequentPattern> fpIterator = listFP.iterator();

				while(FP.getPatternLength() < data.getNumberOfAttributes() && 
						fpIterator.hasNext()){

					FrequentPattern _FP = fpIterator.next();

					if(_FP.getPatternLength() == 1){
						
						if(isRefineable(FP, _FP)){
							FrequentPattern refined = refineFrequentPattern(FP, _FP.getItem(0)); 
							refined.setSupport(computeSupport(data, refined));
							if(refined.getSupport() > minSup){
								outputFP.add(refined);
								fpQueue.enqueue(refined);
							}
						}
					}
				}
			}catch(EmptyQueueException ex){
				System.out.println(ex.getMessage());
			}
		}
		return outputFP;
	}

	/**
	 * Controlla se il pattern è raffinabile ulteriormente o meno, controllando gli se gli 
	 * attributi non sia già in comune fra i due pattern.
	 * @param FP FrequentPattern da raffinare.
	 * @param _FP FrequentPattern in confronto.
	 * @return Restituisce il vero se il pattern è raffinabile, falso altrimenti.
	 */
	private static boolean isRefineable(FrequentPattern FP, FrequentPattern _FP){
		boolean isRefineable = true;

		for(int i = 0; i < FP.getPatternLength(); i++)
			if(FP.getItem(i).getAttribute().getIndex() == _FP.getItem(0).getAttribute().getIndex())
				isRefineable = false;

		return isRefineable;
	}


	// Aggiorna il supporto
	/**
	 * Per ognuna delle transazioni memorizzate in <code>data</code>, 
	 * verifica se ognuno degli Item costituenti il pattern <code>FP</code> occorre
	 * @param data L’insieme delle transazioni di training 
	 * @param FP Il pattern (FP) di cui calcolare il supporto
	 * @return Restituisce il supporto di FP (# transazioni in cui FP è osservato/ # transazioni in data)
	 */
	static float computeSupport(Data data, FrequentPattern FP){
		int suppCount = 0;
		// indice esempio
		for(int i = 0; i < data.getNumberOfExamples(); i++){
			//indice item
			boolean isSupporting = true;
			for(int j = 0; j<FP.getPatternLength(); j++){
				Item item = FP.getItem(j);
				Attribute attribute = item.getAttribute();
				Object valueInExample;
				if(item instanceof DiscreteItem){
					//DiscreteAttribute
					DiscreteAttribute d_attribute = (DiscreteAttribute)attribute;	
					valueInExample = data.getAttributeValue(i, d_attribute.getIndex());
				}else{
					//ContinuousAttribute 
					ContinuousAttribute c_attribute = (ContinuousAttribute)attribute;
					valueInExample = data.getAttributeValue(i, c_attribute.getIndex());
				}
				//Extract the example value
				if(!item.checkItemCondition(valueInExample)){
					isSupporting = false;
					break; //the ith example does not satisfy fp
				}
			}
			if(isSupporting)
				suppCount++;
		}
		return ((float)suppCount)/(data.getNumberOfExamples());
	}

	/**
	 * Crea un nuovo pattern a cui aggiunge tutti gli item di <code>FP</code> e il parametro <code>item</code>
	 * @param FP Pattern da raffinare
	 * @param item Item da aggiungere ad <code>FP</code>
	 * @return Restituisce un nuovo pattern ottenuto per effetto del raffinamento 
	 */
	static FrequentPattern refineFrequentPattern(FrequentPattern FP, Item item){
		FrequentPattern fPattern = new FrequentPattern();

		for(int i = 0; i < FP.getPatternLength(); i++)
			fPattern.addItem(FP.getItem(i));

		fPattern.addItem(item);
		return fPattern;
	}
}