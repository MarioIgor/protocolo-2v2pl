
class DeadLockPrevention {

	public DeadLockPrevention(int preventionType, Scheduler scheduler) {
		this.preventionType = preventionType;
		this.scheduler = scheduler;
		
		if (this.preventionType == DeadLockPrevention.WFG)
			waitForGraph = new WaitForGraph();
	}
	
	public boolean detect(int t1, int t2)
	{
		switch (preventionType)
		{
		  case DeadLockPrevention.WFG:
		  	waitForGraph.addEdge(t1, t2);
	  	    System.out.println("** Procurando por possível DEADLOCK (WAITFORGRAPH)");
		  	if (waitForGraph.hasCycle())
		  	{
				System.out.println("*** Situação de deadlock prevista.");
		  		//ABORTAR MAIS RECENTE ENVOLVIDA
		  		int t = (transactionOrder.olderThan(t1,t2)) ? t2 : t1;
		  		scheduler.abortTransaction(t);
		  		return true;
		  	} else {
	          System.out.println("*** Nenhuma transação abortada.");
		  	}
		  break;
		  
		  case DeadLockPrevention.WAITDIE:
	  	    System.out.println("** Procurando por possível DEADLOCK (WAITDIE)");
			if (! transactionOrder.olderThan(t1, t2))
			{
				System.out.println("*** Situação de deadlock prevista.");
				// T de t1 aborta
                scheduler.abortTransaction(t1);
                return true;
			} else
			  System.out.println("*** Nenhuma transação abortada.");		  	
		  break;
		
		  case DeadLockPrevention.WOUNDWAIT:
	  	    System.out.println("** Procurando por possível DEADLOCK (WOUNDWAIT)");
		    if ( transactionOrder.olderThan(t1, t2))
		    {
			  System.out.println("*** Situação de deadlock prevista.");
			  // T de t1 aborta
              scheduler.abortTransaction(t1);
              return true;
		    } else
		      System.out.println("*** Nenhuma transação abortada.");			
		  break;
		
		}
		return false;
	}
	
	public void addEdge(int from, int to)
	{
		waitForGraph.addEdge(from,to);
	}
	
	public void addTimestamp(int t)
	{
		transactionOrder.add(t);
	}
	
	public void removeEdges(int transactionId)
	{
		waitForGraph.removeEdges(transactionId);
	}
	
	public void removeTimestamp(int transactionId)
	{
		transactionOrder.remove(transactionId);
	}
	
	public int getPreventionType()
	{
		return this.preventionType;
	}
	
		
	public static final int WAITDIE = 0;
	public static final int WOUNDWAIT = 1;
	public static final int WFG = 2;
	private int preventionType;
	private TimestampTransactionOrder transactionOrder = new TimestampTransactionOrder();
	private WaitForGraph waitForGraph;
	private Scheduler scheduler;

}
