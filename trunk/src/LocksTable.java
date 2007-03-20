import java.util.Iterator;
import java.util.Vector;

class LocksTable {
	
	LocksTable(Scheduler scheduler)
	{
		
		this.scheduler = scheduler;
	}
	
	public boolean requestLock(Operation op) throws WaitDieException
	{
		Iterator it = locks.iterator();
		
		while (it.hasNext())
		{
			Lock o = (Lock) it.next();
			
			switch(op.getOperationType())
			{
			  case Operation.READ:
				  if (o.getOperationType() == Operation.COMMIT && 
				      op.getTarget() == o.getTarget() &&
				      op.getTransactionId() != o.getTransactionId()
				     )
				  {
				  	  scheduler.preventDeadLock(op.getTransactionId(), o.getTransactionId());
					  return false;
				  }

			  break;
			  
			  case Operation.WRITE:
				  if ( (o.getOperationType() == Operation.COMMIT || 
						o.getOperationType() == Operation.WRITE
					   ) && 
					   op.getTarget() == o.getTarget() &&
					   op.getTransactionId() != o.getTransactionId()
					 )
				  {
				  	
				  	  if ( scheduler.preventDeadLock(op.getTransactionId(), o.getTransactionId()))
				  	  {
                        throw new WaitDieException();
				  	  } else {
			 		    System.out.println("** "+op.toString()+" n�o pode obter bloqueio (conflito com "+o.toString()+")");
				  	  }
				  	  
					  return false;
				  }
			   break;
			}
		}

	    Lock l = new Lock(op.getOperationType(), op.getTransactionId(), op.getTarget());
		locks.add(l);
		System.out.println("** "+l.toString()+" adicionado a tabela de bloqueios");
		return true;
	}
	
	public boolean convertToCertify(Operation commit) throws WaitDieException
	{
	  // Lancar excecao se a operacao nao for um commit
		
	  // Se alguma operacao nao puder ser convertida o commit vai ter que esperar
	  boolean commitWait = false;
	  Lock lock1 = null;
 
	  // Detecta os Write Locks da Transacao 
	    Iterator it = locks.iterator();
	    while (it.hasNext())
	    {
	      Lock lock = (Lock) it.next();
			  
		  if (lock.getOperationType() == Lock.WRITE &&
		      lock.getTransactionId() == commit.getTransactionId()	  
		     )
		  {
  		       // Procura por incompatibilidades de bloqueios
		       Iterator it2 = locks.iterator();
		       boolean convert = true;
		       while (it2.hasNext())
		       {
       	         lock1 = (Lock) it2.next();
		    	 
		    	 if ( lock != lock1 &&
		    	      lock1.getTarget() == lock.getTarget() &&
		    	      lock1.getTransactionId() != lock.getTransactionId() &&
		    		  ( lock1.getOperationType() == Lock.WRITE || 
		    		    lock1.getOperationType() == Lock.CERTIFY ||
		    		    lock1.getOperationType() == Lock.READ
		    		  )
		    	    )
		    	 {
		  		    
				  	 if (scheduler.preventDeadLock(lock.getTransactionId(), lock1.getTransactionId()) )
				  	 {
				  	   /*FIXME - Que tipo de exce��o deve ser lan�ada? */
				  	   throw new WaitDieException();
				  	 } else {
				  	 	
				  	   convert = false;
		    		   System.out.println("** "+lock.toString()+" nao pode ser transformado em CERTIFY LOCK (confilto com "+lock1.toString()+")");
			    	   break;
			    	   
				  	 }

		    	 }

		   }
		     
		   if (convert)
			 // Transformar Write Locks em Certify Lock
		     lock.convertToCertify();
		   else
			  commitWait = true;
		}
		  
		if (commitWait)
  		  return false;
		  
	  }
	    
	  return true;
	  
	}
	
	public void removeLocks(Operation commit)
	{
	  // lancar excecao se nao for COMMIT
		
	  System.out.println("** Liberando bloqueios de "+commit.toString());
	  Vector tmp = new Vector();
		
	  Iterator it = locks.iterator();
	  while(it.hasNext())
	  {
		  Lock op = (Lock) it.next();
		  if (op.getTransactionId() != commit.getTransactionId())
			  tmp.add(op);
		  else
			  System.out.println("*** "+op.toString()+" liberado");
	  }
	  locks = tmp;
	  
	}
	
	public void removeLocks(int transactionId)
	{
	  Vector tmp = new Vector();
		
	  Iterator it = locks.iterator();
	  while(it.hasNext())
	  {
		  Lock op = (Lock) it.next();
		  if (op.getTransactionId() != transactionId)
			  tmp.add(op);
	  }
	  locks = tmp;
	  
	}	
	
	private Vector locks = new Vector();
	private Scheduler scheduler;
}
