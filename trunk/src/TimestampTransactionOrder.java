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

import java.util.Iterator;
import java.util.Vector;

class TimestampTransactionOrder {
	
	public void add(int transactionId)
	{
		Iterator it = timestampOrder.iterator();
		boolean addTransaction = true;
		while (it.hasNext())
		{
			if (((Integer) it.next()).intValue() == transactionId)
				addTransaction = false;
		}
		
		if (addTransaction)
		{
			timestampOrder.add(new Integer(transactionId));
			System.out.println("** Adicionado marca de tempo para T"+transactionId);
		}
	}
	
	public void remove(int transactionId)
	{
		Vector tmp = new Vector();
		Iterator it = timestampOrder.iterator();
		while (it.hasNext())
		{
			Integer t = (Integer) it.next();
			if (t.intValue() != transactionId)
				tmp.add(t);
		}
	}
	
	public String toString()
	{
		return timestampOrder.toString();
	}
	
	public boolean olderThan(int t1, int t2)
	{
		int idx1 = 0;
		int idx2 = 0;

		// Checado se existe uma marca de tempo para as transacoes 
		Iterator it = timestampOrder.iterator();
		while (it.hasNext())
		{
			Integer t = (Integer) it.next();
			
			if ( t.intValue() == t1 )
				idx1 = timestampOrder.indexOf(t);
			
			if ( t.intValue() == t2 )
				idx2 = timestampOrder.indexOf(t); 
		}
		
		if (idx1 == 0 && idx2 == 0)
			// Lancar excecao 
			return false;
		
		if (idx1 < idx2)
		{
		  System.out.println("*** T"+t1+" é mais antiga do que T"+t2);
		  return true;
		}
		
		System.out.println("*** T"+t1+" é mais nova do que T"+t2);
		return false;
	}
	
	
	private Vector timestampOrder = new Vector();

}
