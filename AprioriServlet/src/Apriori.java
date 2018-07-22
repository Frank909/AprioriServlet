import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.LinkedList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import data.Data;
import data.EmptySetException;
import database.DatabaseConnectionException;
import database.NoValueException;
import mining.AssociationRule;
import mining.AssociationRuleArchive;
import mining.AssociationRuleMiner;
import mining.FrequentPattern;
import mining.FrequentPatternMiner;
import mining.OneLevelPatternException;

/**
 * Implementazione della classe servlet <code>Apriori</code>.
 * @author Francesco Ventura
 * @version 1.0
 */
@WebServlet("/Apriori")
public class Apriori extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Apriori() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		doRequest(request, response);
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doRequest(request, response);
	}
	
	/**
	 * <code>doRequest</code> è un metodo privato che riceve in ingresso le richieste inviate dal client e le processa.
	 * Possiede i seguenti casi:
	 * 
	 * 1: Riceve dal client i parametri che serviranno a calcolare le regole e il nome del file in cui verranno salvate.
	 * 	  Calcola i pattern e le regole di associazione estrapolandole da un dataset presente su DB. Se l'esito dell'operazione è
	 *    positivo, salva in un file serializzato sul server, i risultati appena calcolati.
	 *    
	 * 2: Riceve dal client il nome del file da consultare, restituendo al client il contenuto come risposta.
	 * 
	 * 3: Riceve dal client il nome del file da cancellare e processa la richiesta di cancellazione.
	 * 
	 * Al termine di ogni operazione verrà sempre informato il client del successo/fallimento nel processare la richiesta.  
	 * @param request Richiesta servlet
	 * @param response Responso servlet
	 * @throws ServletException Eccezione lanciata dalla servlet
	 * @throws IOException Eccezione lanciata nel caso di errori input/output
	 */
	private void doRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		final String REALPATH = getServletContext().getRealPath("/") + "\\";
		
		String tableName = null, 
				param = request.getParameter("command"), 
				output = "",
				message = "";
		
		AssociationRuleArchive archive = null;
		
		Float minSup = null, minConf = null;
		
		boolean opDone = false;
		
		int command = 0;		
		
		if(param != null)
			command = Integer.valueOf(param);
		
		switch (command) {
		case 1: // Learning a new archive from DB and save on file
			try {
				message = "Operation : Learning Rules From DB";
				archive = new AssociationRuleArchive();
				tableName = request.getParameter("table");
				Data trainingSet = new Data(tableName);
				minSup = Float.valueOf(request.getParameter("minSup"));
				minConf = Float.valueOf(request.getParameter("minConf"));
				try {
					LinkedList<FrequentPattern> outputFP = FrequentPatternMiner.frequentPatternDiscovery(trainingSet, minSup);
					Iterator<FrequentPattern> it = outputFP.iterator();
					while (it.hasNext()) {
						FrequentPattern FP = it.next();
						archive.put(FP);
						LinkedList<AssociationRule> outputAR = null;
						try {
							outputAR = AssociationRuleMiner.confidentAssociationRuleDiscovery(trainingSet, FP,
									minConf);
							Iterator<AssociationRule> itRule = outputAR.iterator();
							while (itRule.hasNext()) {
								archive.put(FP, itRule.next());
							}
						} catch (OneLevelPatternException e) {
							System.out.println(e.getMessage());
						}
					}
					output = archive.toString();
					message += "\nPatterns and Rules successfully calculated.";
					opDone = true;
				} catch (EmptySetException e) {
					message = "\n" + e.getMessage();
				}
			} catch (DatabaseConnectionException e1) {
				message = "\nDatabase Connection Error:\n" + e1.getMessage();
			} catch (SQLException e1) {
				message = "\nDB error:\nTable [" + tableName + "] not found!";
			} catch (NoValueException e1) {
				message = "\nPatterns and Rules not generated\n" + e1.getMessage();
			} finally {
				if(opDone) {		
					String fileName = (String) request.getParameter("filenamedata") + ".dat";					
					String fileRealPath = REALPATH  + fileName;
					boolean isStored = true;
					try {
						File myfile = new File(fileRealPath);
						if(!myfile.exists())
							myfile.createNewFile();							
						archive.salva(fileRealPath);
					} catch (IOException e) {
						System.out.println(e);
						isStored = false;
					}
					if(isStored)
						message += "\nOutput successfully saved in: " + fileName;
					else
						message += "\nOutput not saved.";
				}				
				request.setAttribute("output", output);
				request.setAttribute("message", message);
				request.getRequestDispatcher("index.jsp").forward(request, response);
			}
			break;

		case 2: // STORING PATTERNS and RULES stored from a FILE
			String fileArchive = (String) request.getParameter("loadfile");
			message = "Operation : Reading Rules From File";
			try{					
				archive = AssociationRuleArchive.carica(REALPATH + fileArchive);
				output = archive.toString();
				message += "\nFile " + fileArchive + " correctly loaded.";				
			} catch (FileNotFoundException e) {
				message += "\nFile " + fileArchive + " does not exists.";
			} catch (IOException | ClassNotFoundException e) {
				message += "\n" + e.getMessage();
			} finally {				
				request.setAttribute("output", output);
				request.setAttribute("message", message);
				request.getRequestDispatcher("index.jsp").forward(request, response);
			}
			break;
			
		case 3:// Delete file stored in the server
			message = "Operation : Delete File From Server";
			String filename = (String) request.getParameter("loadfile");
			
			if(filename != null)
				try {
					Files.deleteIfExists(Paths.get(REALPATH + filename));
					message += "\nFile " + filename + " deleted successfully"; 
				}catch(IOException e){
					message += "\nError during delete the file " + filename;
				}
			else
				message += "\nError, no file selected.";
			
			request.setAttribute("output", output);
			request.setAttribute("message", message);
			request.getRequestDispatcher("index.jsp").forward(request, response);
			break;

		default:
			request.getRequestDispatcher("error.jsp").forward(request, response);
		}// END SWITCH
	}
	
	
}
