package mining;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.TreeSet;

/**
 * AssociationRuleArchive è una classe definita al fine di modellare 
 * un contenitore di pattern regole di associazione. 
 * Implementa l'interfaccia {@link Serializable}.
 * @author Francesco Ventura
 * @version 1.0
 */
public class AssociationRuleArchive implements Serializable{

	private static final long serialVersionUID = 1L;
	
	/**
	 * Archive è definito come contenitore Map, per le coppie
	 * <Pattern Frequente, {Insieme di Regole di Associazione estratte dal pattern chiave e 
	 * ordinate in maniera crescente rispetto alla confidenza}>
	 */
	private HashMap<FrequentPattern, TreeSet<AssociationRule>> archive = new HashMap<>();

	/**
	 * Aggiunge <code>fp</code> ad <code>archive</code>, se <code>fp</code> 
	 * non vi è già contenuto come chiave.
	 * @param fp Pattern (chiave) da aggiungere ad archive
	 */
	public void put(FrequentPattern fp){
		if(!archive.containsKey(fp))
			archive.put(fp, new TreeSet<>());
	}

	/**
	 * Aggiunge e <code>fp</code> ad <code>archive</code>, se <code>fp</code> non vi è già 
	 * contenuto come chiave. Altrimenti, inserisce <code>rule</code> al TreeSet 
	 * delle regole indicizzato da <code>fp</code> in <code>archive</code>.
	 * @param fp Pattern (chiave)
	 * @param rule Regola di associazione estratta dal pattern
	 */
	public void put(FrequentPattern fp, AssociationRule rule){
		if(!archive.containsKey(fp))
			archive.put(fp, new TreeSet<>());
		else
			archive.get(fp).add(rule);
	}

	/**
	 * @param fp: Pattern (chiave) per il quale ricercare il set delle regole.
	 * @return Se <code>fp</code> compare come chiave in <code>archive</code> restituisce 
	 * il set di regole corrispondente, altrimenti solleva e 
	 * propaga una eccezione <code>NoPatternException</code>.
	 * @throws NoPatternException Eccezione lanciata qualora la chiave <code>fp</code> non sia contenuta in <code>archive</code>
	 */
	private TreeSet<AssociationRule> getRules(FrequentPattern fp) throws NoPatternException{
		if(archive.containsKey(fp))
			return archive.get(fp);
		else 
			throw new NoPatternException();
	}

	/**
	 * Procedura che si occupa di serializzare l’oggetto riferito da 
	 * <code>this</code> nel file il cui nome è passato come parametro.
	 * @param nomeFile Nome del file
	 * @throws FileNotFoundException Eccezione lanciata nel caso in cui il file non venga trovato
	 * @throws IOException Eccezione lanciata nel caso in cui si abbiano errori su operazioni
	 * input/output
	 */
	public void salva(String nomeFile) throws FileNotFoundException, IOException{
		FileOutputStream out_stream = new FileOutputStream(nomeFile);
		ObjectOutputStream out = new ObjectOutputStream(out_stream);
		
		out.writeObject(this);
		
		out.close();
		out_stream.close();
	}

	/**
	 * Funzione che si occupa di leggere e restituire l’oggetto come è memorizzato nel file 
	 * il cui nome è passato come parametro. 
	 * @param nomeFile Nome del file
	 * @return Restituisce un oggetto di tipo AssociationRuleArchive, letto da file.
	 * @throws FileNotFoundException Eccezione lanciata nel caso in cui il file non venga trovato
	 * @throws IOException Eccezione lanciata nel caso in cui si abbiano errori su operazioni
	 * input/output
	 * @throws ClassNotFoundException Eccezione lanciata nel caso in cui non venga trovata la classe
	 */
	public static AssociationRuleArchive carica(String nomeFile) throws
	FileNotFoundException, IOException, ClassNotFoundException{ 
		
		FileInputStream in_stream = new FileInputStream(nomeFile);
		ObjectInputStream in = new ObjectInputStream(in_stream);
		
		AssociationRuleArchive ar_archive = (AssociationRuleArchive) in.readObject();
		
		in.close();
		in_stream.close();
		
		return ar_archive;
	}

	/**
	 * Itera sulle chiavi di <code>archive</code> e concatena nella stringa da restituire ciascun
	 * pattern (chiave) e il corrispondente set di regole di associazione. 
	 * Override del metodo <code>toString()</code> ereditato da Object.
	 * @return Restituisce per ogni pattern le proprie regole di associazione.
	 */
	@Override
	public String toString(){
		int i = 1;
		String result = "";
		Iterator<FrequentPattern> keyIterator = archive.keySet().iterator();

		while(keyIterator.hasNext()){
			try {
				FrequentPattern tempFP = keyIterator.next();
				TreeSet<AssociationRule> tempTS = getRules(tempFP);

				if(!tempTS.isEmpty()){
					result +="\n" + i + ": " + tempFP + "\n";
					Iterator<AssociationRule> iteratorAR = tempTS.iterator();
					result +="[";
					while(iteratorAR.hasNext()){
						result += iteratorAR.next();
						if(iteratorAR.hasNext()){
							result += ",\n";
						}
					}
					result += "]\n";
				}else{
					result +="\n" + i + ": " + tempFP + "\n" + "[]\n";
				}
				
				i++;
			}catch (NoPatternException e) {
				System.out.println(e.getMessage());
			}
		}
		return result;
	}
}