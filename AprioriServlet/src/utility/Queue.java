package utility;

/**
 * Queue è una classe generica che modella una struttura coda di tipo <code>T</code> che è poi 
 * usata come contenitore a modalità FIFO per i pattern frequenti scoperti a livello k da 
 * usare per generare i pattern candidati a livello k + 1
 * @author Francesco Ventura
 * @version 1.0
 * @param T Parametro generico della classe che specifica il tipo di elemento che verrà
 * contenuto nella struttura dati.
 */
public class Queue<T>{
	
	/**
	 * Inizializzazione del primo elemento della coda
	 */
	private Record begin = null;
	/**
	 * Inizializzazione del ultimo elemento della coda
	 */
	private Record end = null;
	
	/**
	 * Classe privata <code>Record</code> che conterrà l'elemento corrente e successivo
	 * @author Francesco Ventura
	 * @version 1.0
	 */
	private class Record{
		/**
		 * Elemento di tipo <T> generico.
		 */
 		public T elem;
 		
 		/**
 		 * <code>Record</code> che punterà al prossimo elemento.
 		 */
 		public Record next;

 		/**
 		 * Costruttore dell'inner class <code>Record</code> che inizializza elemento corrente
 		 * tramite il parametro generico <code>e</code> e inizializza il Record successivo.
 		 * @param e Parametro generico che rappresenta l'elemento corrente
 		 */
		public Record(T e) {
			this.elem = e; 
			this.next = null;
		}
	}
	
	/**
	 * @return Restituisce <code>true</code> se la coda è vuota, 
	 * altrimenti <code>false</code>
	 */
	public boolean isEmpty() {
		return this.begin == null;
	}

	/**
	 * Se la coda è vuota, inserisce l'elemento in prima posizione (la fine e l'inizio della coda
	 * saranno uguali), altrimenti l'elemento inserito "diventa" la fine della coda. 
	 * @param e Elemento di tipo <T> da inserire in coda
	 */
	public void enqueue(T e) {
		if (this.isEmpty())
			this.begin = this.end = new Record(e);
		else {
			this.end.next = new Record(e);
			this.end = this.end.next;
		}
	}

	/**
	 * @return Restituisce il primo elemento in coda.
	 */
	public T first(){
		return this.begin.elem;
	}

	/**
	 * Procedura che modifica l'inizio della coda puntando alla successiva occorrenza. 
	 * @throws EmptyQueueException Eccezione lanciata se si prova ad eseguire 
	 * <code>dequeue()</code> se la coda è vuota.
	 */
	public void dequeue() throws EmptyQueueException{
		if(this.begin == this.end){
			if(this.begin == null)
				throw new EmptyQueueException("Queue is empty!");
			else
				this.begin = this.end = null;
		}else{
			begin = begin.next;
		}
	}
}