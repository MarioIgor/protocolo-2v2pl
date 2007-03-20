import java.util.Iterator;
import java.util.Vector;

class Scheduler {
	
	Scheduler(Vector schedule, int deadLockPreventionType)
	{
		this.schedule = schedule;
		this.locksTable = new LocksTable(this);
		this.deadLockPrevention = new DeadLockPrevention(deadLockPreventionType, this);
		
	}
	
	public void execute() throws Exception
	{
	
		Operation op = (Operation) schedule.remove(0);
		
		switch (op.getOperationType())
		{
		case Operation.READ:
		case Operation.WRITE:
		  System.out.println("* "+op.toString());			
		  deadLockPrevention.addTimestamp(op.getTransactionId());
          try {
  	        if ( locksTable.requestLock(op))
		    {
			  System.out.println("* "+op.toString()+" enviado para processamento");
			  scheduled.add(op);
		    } else {
	 		  waitTable.add(op);
	        }
          } catch (WaitDieException e) {
          	
          }
		  System.out.println("\n");

		break;
				
		case Operation.COMMIT:
			
	  	  // Se existir alguma operacao da transacao na tabela de espera
		  // o commit vai para a tabela de espera e nao converte
	  	  // para certify.
			 
		  System.out.println("* Tentativa de comitar transacao "+op.getTransactionId());
		  Iterator it2 = waitTable.iterator();
		  boolean convert = true;
			 
		  while (it2.hasNext())
		  {
		    Operation op1 = (Operation) it2.next();
		    if (op1.getTransactionId() == op.getTransactionId())
		    {
		      waitTable.add(op);
		 	  convert = false;
			  break;
		    }
	      }
			 
	      if (convert)
	      {
	      	try {
	          if ( locksTable.convertToCertify(op) )
		      {
	       
		        locksTable.removeLocks(op);
		        System.out.println("* "+op.toString()+" foi comitada\n");
		        scheduled.add(op);
			     
  		        // Depois de uma transacao comitar, 
		        // todas as operacoes em espera sao colocadas na frente
		        // da lista de operacoes do escalonador
		        if (!waitTable.empty())
		        {
		         Vector tmp = waitTable.removeAll();
			     int index = tmp.size()-1;
			    	 
			     while (index >= 0)
			       schedule.add(0, (Operation) tmp.get(index--));
		         }
		      
		   
	           } else {
	        	 
	             // Se alguma das operacoes de escrita nao se transformar em CERTIFY
		         // o COMMIT vai esperar ate que ela se torne um CERTIFY
		         waitTable.add(op);
		         System.out.println("\n");
		       
	           }
	      } catch (WaitDieException e) {
	      	
	      	// Se existir operacoes em espera, adiciona-las a lista de operacoes
	      	// do schedule
	        if (!waitTable.empty())
	        {
	         Vector tmp = waitTable.removeAll();
		     int index = tmp.size()-1;
		    	 
		     while (index >= 0)
		       schedule.add(0, (Operation) tmp.get(index--));
	         }
	      	
	      	
	      }
	      
	    }
      break;
        
 	 }
		
	}
	
	public boolean empty()
	{
		return ( schedule.size() == 0 );
	}
	
	public String getScheduled()
	{
		return scheduled.toString();
	}
	
	public void deadlock()
	{
		schedule.clear();
	}
	
	public void abortTransaction(int transactionId)
	{
		
		// Removendo operacoes do schedule da transacao
		Vector tmp = new Vector();
		Iterator it = schedule.iterator();
		
		while(it.hasNext())
		{
			Operation op = (Operation) it.next();
			if (op.getTransactionId() != transactionId)
				tmp.add(op);
		}
		
		schedule = tmp;
		
		// Removendo operacoes do schedule final da transacao
		tmp = new Vector();
		it = scheduled.iterator();
		
		while(it.hasNext())
		{
			Operation op = (Operation) it.next();
			if (op.getTransactionId() != transactionId)
				tmp.add(op);
		}
		
		scheduled = tmp;
		
		// Removento operacoes em espera da transacao
        waitTable.abortTransaction(transactionId);
		
		// Removendo bloqueios da transacao
		locksTable.removeLocks(transactionId);
		
		// Removendo arestas do WFG da transacao
		if (deadLockPrevention.getPreventionType() == DeadLockPrevention.WFG)
		  deadLockPrevention.removeEdges(transactionId);
		
		// Removendo marca de tempo da transacao
		deadLockPrevention.removeTimestamp(transactionId);
		
		System.out.println("**** T"+transactionId+" abortada\n");
	}
	
	public boolean preventDeadLock(int t1, int t2)
	{
		return deadLockPrevention.detect(t1,t2);
	}

	private Vector schedule;
	private LocksTable locksTable;
	private WaitTable waitTable = new WaitTable();
	private Vector scheduled = new Vector();
	private DeadLockPrevention deadLockPrevention;
}
