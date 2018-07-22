package data;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import database.DatabaseConnectionException;
import database.DbAccess;
import database.NoValueException;
import database.QUERY_TYPE;
import database.TableData;
import database.TableData.TupleData;
import database.TableSchema;
import database.TableSchema.Column;

/**
 * Data è una classe definita allo scopo di modellare un insieme di transazioni (vettori attributo-valore)
 * @author Francesco Ventura
 * @version 1.0
 */
public class Data {

	/**
	 * Matrice di Object che ha numero di righe pari al numero di transazioni da memorizzare 
	 * e numero di colonne pari al numero di attributi in ciascuna transazione
	 */
	private Object data[][];

	/**
	 * Cardinalità dell’insieme di transazioni
	 */
	private int numberOfExamples;

	/**
	 * Lista di attributi, che sono avvalorati in ciascuna transazione
	 */
	private List<Attribute> attributeSet;

	/**
	 * Costruttore della classe Data.
	 * Popola la matrice <code>data[][]</code> con le transazioni (14 transazioni per 5 attributi) prendendo le informazioni dalla
	 * base di dati.
	 * Avvalora la lista <code>attributeSet</code> con quattro oggetti di DiscreteAttribute, un oggetto di ContinuousAttribute, 
	 * uno per ciascun attributo.
	 * @param table Nome della tabella della base di dati dal quale ricavare i dati di addestramento.
	 * @throws DatabaseConnectionException Eccezione lanciata nel caso di problemi di connessione su DB
	 * @throws SQLException Eccezione lanciata nel caso di problemi di operazioni su DB
	 * @throws NoValueException Eccezione lanciata nel caso in cui siano assenti valori all'interno del resultset
	 */
	public Data(String table) throws DatabaseConnectionException, SQLException, NoValueException{
		data = new Object[14][5];
		attributeSet = new LinkedList<>();
		// numberOfExamples
		numberOfExamples = 14;

		DbAccess db = new DbAccess();
		db.initConnection();

		TableData tData = new TableData(db);
		TableSchema tSchema = new TableSchema(db, table);

		List<TupleData> transList = tData.getTransazioni(table);

		for(int i = 0; i < transList.size(); i++){
			TupleData row = transList.get(i);
			for(int j = 0; j < tSchema.getNumberOfAttributes(); j++)
				data[i][j] = row.tuple.get(j);
		}

		//explanatory Set
		for(int i = 0; i < tSchema.getNumberOfAttributes(); i++){
			Column column = tSchema.getColumn(i);
			if(!column.isNumber()){					
				List<Object> distinctValues = tData.getDistinctColumnValues(table, column);
				String[] values = distinctValues.toArray(new String[distinctValues.size()]);
				attributeSet.add(new DiscreteAttribute(column.getColumnName(), i, values));
			}else{
				float min = (float) tData.getAggregateColumnValue(table, column, QUERY_TYPE.MIN);
				float max = (float) tData.getAggregateColumnValue(table, column, QUERY_TYPE.MAX);
				attributeSet.add(new ContinuousAttribute(column.getColumnName(), 
						i, min, max));
			}
		}
	}

	/**
	 * Metodo di accesso all'attributo <code>numberOfExamples</code> (Cardinalità dell'insieme di transazioni)
	 * @return Restituisce il valore del membro numberOfExamples
	 */
	public int getNumberOfExamples(){
		return numberOfExamples;
	}

	/**
	 * Metodo di accesso alla lunghezza dell'array <code>attributeSet</code> (Cardinalità dell'insieme degli attributi)
	 * @return Restituisce la cardinalità del membro attributeSet
	 */
	public int getNumberOfAttributes(){
		return attributeSet.size();
	}

	/**
	 * Metodo di accesso ad un valore assunto dall’attributo identificato da <code>attributeIndex</code> nella 
	 * transazione identificata da <code>exampleIndex</code> nel membro <code>data</code>.
	 * @param exampleIndex Indice di riga per la matrice data che corrisponde ad una specifica transazione
	 * @param attributeIndex Indice di colonna per un attributo
	 * @return restituisce il valore dell' attributo <code>attributeIndex</code> per la transazione 
	 * <code>exampleIndex</code> meomorizzata in <code>data</code>
	 */
	public Object getAttributeValue(int exampleIndex, int attributeIndex){
		return data[exampleIndex][attributeIndex];
	}

	/**
	 * Metodo di accesso all'attributo con indice <code>attributeIndex</code>
	 * @param index: Indice di colonna per un attributo
	 * @return Restituisce l’attributo in posizione <code>attributeIndex</code> di <code>attributeSet</code>
	 */
	public Attribute getAttribute(int index){
		return attributeSet.get(index);
	}

	/**
	 * Override del metodo <code>toString()</code> della superclasse Object
	 * @see Object#toString()
	 * @return Per ogni transazione memorizzata in data, concatena i valori assunti dagli attributi 
	 * nella transazione separati da virgole in una stringa. Le stringhe che rappresentano ogni 
	 * transazione sono poi concatenate in un’unica stringa da restituire in output.
	 */
	@Override
	public String toString(){
		String result = "";
		for (int i = 0; i < numberOfExamples; i++){
			result += i + 1 + ": ";
			for(int j = 0; j < attributeSet.size() ; j++)
				if(j != 4)
					result += data[i][j] + ",";
				else
					result += data[i][j] + "\n";
		}
		return result;
	}


	/**
	 * Metodo main per verificare il corretto funzionamento delle classi definite.
	 * @param args Argomenti in input
	 */
	public static void main(String args[]){
		Data trainingSet = null;
		try {
			trainingSet = new Data("playtennis");
		}catch(DatabaseConnectionException e) {
			e.printStackTrace();
		}catch(SQLException e){
			e.printStackTrace();
		}catch(NoValueException e) {
			e.printStackTrace();
		}
		System.out.println(trainingSet);
	}
}

