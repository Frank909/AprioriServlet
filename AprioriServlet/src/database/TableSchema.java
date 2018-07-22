package database;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * TableSchema è una classe realizzata allo scopo di modellare lo schema 
 * di una tabella nel database relazionale
 * @author Francesco Ventura
 * @version 1.0
 */
public class TableSchema {
	
	/**
	 * Column è un'inner class che rappresenta una colonna presente in una 
	 * tabella del database relazionale.
	 * @author Francesco Ventura
	 * @version 1.0
	 */
	public class Column{
		/**
		 * Nome della column
		 */
		private String name;
		
		/**
		 * Tipo di dato rappresentato nella column
		 */
		private String type;
		
		/**
		 * Costruttore della classe Column.
		 * Avvalori i membri <code>name</code> e <code>type</code>, passati come parametro.
		 * @param name Nome della colonna
		 * @param type Tipo di dato rappresentato dalla colonna
		 */
		Column(String name, String type){
			this.name = name;
			this.type = type;
		}
		
		/**
		 * @return Restituisce il nome della colonna.
		 */
		public String getColumnName(){
			return name;
		}
		
		/**
		 * @return Restituisce vero se il tipo di dato rappresentato dalla colonna è un numero,
		 * falso altrimenti.
		 */
		public boolean isNumber(){
			return type.equals("number");
		}
		
		/**
		 * Override del metodo <code>toString()</code> della superclasse Object
		 * @see Object#toString()
		 * @return Restituisce una stringa nel formato < nome colonna > : < tipo >
		 */
		@Override
		public String toString(){
			return name + ":" + type;
		}
	}
	
	/**
	 * Lista di colonne rappresentate nella tabella.
	 */
	List<Column> tableSchema = new ArrayList<Column>();

	/**
	 * Costruttore della classe <code>TableSchema</code> che inizializza il mapping e
	 * la connessione al DB.
	 * @param db connessione al database
	 * @param tableName nome della tabella
	 * @throws SQLException Eccezione lanciata nel caso di problemi con operazioni su DB
	 */
	public TableSchema(DbAccess db, String tableName) throws SQLException{
		HashMap<String,String> mapSQL_JAVATypes = new HashMap<String, String>();
		//http://java.sun.com/j2se/1.3/docs/guide/jdbc/getstart/mapping.html
		mapSQL_JAVATypes.put("CHAR","string");
		mapSQL_JAVATypes.put("VARCHAR","string");
		mapSQL_JAVATypes.put("LONGVARCHAR","string");
		mapSQL_JAVATypes.put("BIT","string");
		mapSQL_JAVATypes.put("SHORT","number");
		mapSQL_JAVATypes.put("INT","number");
		mapSQL_JAVATypes.put("LONG","number");
		mapSQL_JAVATypes.put("FLOAT","number");
		mapSQL_JAVATypes.put("DOUBLE","number");

		Connection con = db.getConnection();
		DatabaseMetaData meta = con.getMetaData();
		ResultSet res = meta.getColumns(null, null, tableName, null);

		while (res.next()) {
			if(mapSQL_JAVATypes.containsKey(res.getString("TYPE_NAME")))
				tableSchema.add(new Column(
						res.getString("COLUMN_NAME"),
						mapSQL_JAVATypes.get(res.getString("TYPE_NAME")))
						);
		}
		res.close();

	}

	/**
	 * Metodo di accesso alla size della lista <code>tableSchema</code>.
	 * @return Restituisce il numero di attributi di cui è composta la tabella.
	 */
	public int getNumberOfAttributes(){
		return tableSchema.size();
	}

	/**
	 * @param index Posizione della colonna nella tabella.
	 * @return Restituisce la colonna in posizione <code>index</code>
	 */
	public Column getColumn(int index){
		return tableSchema.get(index);
	}

}





