/*
Simulador 2V2PL Simulator - Simula o escalonamento de operacoes de escrita, leitura
e commit em uma ou varias transacoes em um banco de dados.

Copyright (C) 2006 David Rodrigues Pinheiro - davidrodriguespinheiro at gmail dot com

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 2
of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
*/

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
