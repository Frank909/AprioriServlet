package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Classe che gestisce l'accesso al DB per la lettura dei dati di training
 * @author Francesco Ventura
 * @version 1.0
 */

public class DbAccess {

	/**
	 * Stringa Driver MySQL
	 */
	private static final String DRIVER_CLASS_NAME = "org.gjt.mm.mysql.Driver";
	
	/**
	 * Stringa connessione jdbc
	 */
	private static final String DBMS = "jdbc:mysql";

	/**
	 * Attributo che contiene l’identificativo del server su cui risiede la base di dati
	 */
	private final String SERVER = "localhost";

	/**
	 * Porta su cui il DBMS MySQL accetta le connessioni
	 */
	private int PORT = 3306;

	/**
	 * Attributo che contiene il nome della base di dati
	 */
	private String DATABASE = "AprioriDB";

	/**
	 * Nome dell’utente per l’accesso alla base di dati
	 */
	private String USER_ID = "AprioriUser";

	/**
	 * Password di autenticazione per l’utente identificato da USER_ID
	 */
	private String PASSWORD = "apriori";

	/**
	 * Attributo che gestisce una connessione al DB
	 */
	private Connection conn;

	/**
	 * Metodo che impartisce al class loader l’ordine di caricare il driver mysql, 
	 * inizializza la connessione riferita da <code>conn</code>. 
	 * Il metodo solleva e propaga una eccezione di tipo <code>DatabaseConnectionException</code> in caso di fallimento nella connessione al database.
	 * @throws DatabaseConnectionException Eccezione lanciata nel caso di problemi di connessione al DB
	 */
	public void initConnection() throws DatabaseConnectionException{
		String connectionString = DBMS + "://" + SERVER + ":" + PORT + "/" + DATABASE;
		try{
			Class.forName(DRIVER_CLASS_NAME).newInstance();
		}catch (IllegalAccessException e) {
			e.printStackTrace();
			throw new DatabaseConnectionException(e.toString());
		}catch (InstantiationException e) {
			e.printStackTrace();
			throw new DatabaseConnectionException(e.toString());
		}catch (ClassNotFoundException e) {
			System.out.println("Impossibile trovare il Driver: " + DRIVER_CLASS_NAME);
			throw new DatabaseConnectionException(e.toString());
		}

		try{
			conn = DriverManager.getConnection(connectionString, USER_ID, PASSWORD);
		}catch (SQLException e) {
			System.out.println("Impossibile connettersi al DB");
			e.printStackTrace();
			throw new DatabaseConnectionException(e.toString());
		}
	}
	
	/**
	 * Metodo di accesso all'attributo <code>conn</code>.
	 * @return Restituisce l'oggetto <code>conn</code>.
	 */
	public Connection getConnection(){
		return conn;
	}
	
	/**
	 * Chiude la connessione <code>conn</code> al Database.
	 * @throws SQLException Eccezione lanciata nel caso di problemi con operazioni su DB
	 */
	public void closeConnection() throws SQLException{
		conn.close();
	}
}

