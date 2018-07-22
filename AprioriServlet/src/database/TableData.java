package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import database.TableSchema.Column;

/**
 * <code>TableData</code> è una classe che rappresenta e modella
 * l'insieme delle tuple collezionate in una tabella.
 * @author Francesco Ventura
 * @version 1.0
 */
public class TableData {

	/**
	 * Attributo che rappresenta la connessione al database
	 */
	private DbAccess db;
	
	/**
	 * Costruttore della classe <code>TableData</code> che inizializza l'attributo per la connessione al 
	 * database
	 * @param db variabile che rappresenta la connessione a database
	 */
	public TableData(DbAccess db){
		this.db = db;
	}
	
	/**
	 * <code>TupleData</code> è la classe che rappresenta la singola tupla modellata.
	 * @author Francesco Ventura
	 * @version 1.0
	 */
	public class TupleData{
		
		/**
		 * Attributo che rappresenta una lista di tuple.
		 */
		public List<Object> tuple = new ArrayList<Object>();
		
		/**
		 * Override del metodo <code>toString()</code> ereditato da Object.
		 * Itera sulla liste di tuple della tabella.
		 * @return Restituisce la stringa contenente tutte le tuple della tabella.
		 * @see Object#toString() 
		 */
		@Override
		public String toString(){
			String value = "";
			Iterator<Object> it = tuple.iterator();
			
			while(it.hasNext())
				value += (it.next().toString() + " ");
	
			return value;
		}
	}

	/**
	 * Ricava lo schema della tabella con nome table. Esegue una interrogazione per estrarre le tuple da tale tabella. 
	 * Per ogni tupla del resultset, si crea un oggetto, istanza della classe Tupla, il cui riferimento va incluso nella lista da restituire. 
	 * In particolare, per la tupla corrente nel resultset, si estraggono i valori dei singoli campi 
	 * (usando <code>getFloat()</code> o <code>getString()</code>), e li si aggiungono all’oggetto istanza della classe Tupla che si sta costruendo.
	 * @param table nome della tabella nel database
	 * @return Lista di tuple memorizzate nella tabella.
	 * @throws SQLException Eccezione lanciata nel caso di problemi con operazioni su DB
	 */
	public List<TupleData> getTransazioni(String table) throws SQLException{
		
		LinkedList<TupleData> transSet = new LinkedList<TupleData>();
		Statement statement;
		TableSchema tSchema = new TableSchema(db, table);

		String query = "select ";

		for(int i = 0; i < tSchema.getNumberOfAttributes(); i++){
			Column c = tSchema.getColumn(i);
			if(i > 0)
				query += ",";
			query += c.getColumnName();
		}

		if(tSchema.getNumberOfAttributes() == 0)
			throw new SQLException();

		query += (" FROM " + table);

		statement = db.getConnection().createStatement();
		ResultSet rs = statement.executeQuery(query);

		while (rs.next()) {
			TupleData currentTuple = new TupleData();
			for(int i = 0; i < tSchema.getNumberOfAttributes(); i++)
				if(tSchema.getColumn(i).isNumber())
					currentTuple.tuple.add(rs.getFloat(i+1));
				else
					currentTuple.tuple.add(rs.getString(i+1));
			transSet.add(currentTuple);
		}

		rs.close();
		statement.close();
		return transSet;
	}
	
	/**
	 * Formula ed esegue una interrogazione SQL per estrarre i valori distinti ordinati di <code>column</code> e popolare una lista da restituire.
	 * @param table nome della tabella nel DB
	 * @param column colonna della tabella nel DB
	 * @return Lista di valori distinti ordinati in modalità ascendente che l’attributo identificato da nome <code>column</code> assume 
	 * 		   nella tabella identificata dal nome <code>table</code>.
	 * @throws SQLException Eccezione lanciata nel caso di problemi con operazioni su DB
	 */
	public List<Object> getDistinctColumnValues(String table, Column column) throws SQLException{
		LinkedList<Object> transSet = new LinkedList<>();
		
		Statement statement;
		String nameCol = column.getColumnName();
		String query = "select distinct " + nameCol + " FROM " + table + " ORDER BY " + nameCol +" ASC";

		statement = db.getConnection().createStatement();
		ResultSet rs = statement.executeQuery(query);
		
		while(rs.next()){
			if(column.isNumber())
				transSet.add(rs.getFloat(column.getColumnName()));
			else
				transSet.add(rs.getString(column.getColumnName()));
		}
		
		return transSet;
	}

	/**
	 * Formula ed esegue una interrogazione SQL per estrarre il valore aggregato (valore minimo o valore massimo) 
	 * cercato nella colonna di nome <code>column</code> della tabella di nome <code>table</code>. 
	 * Il metodo solleva e propaga una <code>NoValueException</code> se il resultset è vuoto o il valore calcolato è pari a <code>null</code>
	 * @param table nome della tabella nel DB
	 * @param column colonna della tabella nel DB
	 * @param aggregate operatore di aggregazione SQL
	 * @return Restituisce l'aggregato cercato.
	 * @throws SQLException Eccezione lanciata nel caso di problemi con operazioni su DB
	 * @throws NoValueException Eccezione lanciata nel caso in cui ci sia l'assenza di valori all'interno del resultset
	 */
	public Object getAggregateColumnValue(String table, Column column, QUERY_TYPE aggregate) throws SQLException, NoValueException{
		Statement statement;
		//TableSchema tSchema = new TableSchema(db, table);
		Object value = null;
		String aggregateOp = "";
		String query = "select ";

		if(aggregate == QUERY_TYPE.MAX)
			aggregateOp += "max";
		else
			aggregateOp += "min";

		query += aggregateOp + "(" + column.getColumnName() + ") FROM " + table;

		statement = db.getConnection().createStatement();

		ResultSet rs = statement.executeQuery(query);
		if (rs.next()) {
			if(column.isNumber())
				value = rs.getFloat(1);
			else
				value = rs.getString(1);
		}

		rs.close();
		statement.close();
		
		if(value == null)
			throw new NoValueException("No " + aggregateOp + " on " + column.getColumnName());

		return value;
	}
}

