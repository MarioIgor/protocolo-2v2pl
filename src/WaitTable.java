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

class WaitTable {

  public void add(Operation op)
  {
	  System.out.println("*** "+op.toString()+" adicionado a lista de espera");
	  operations.add(op);
  }
  
  public void remove(Operation op)
  {
	  System.out.println("*** "+op.toString()+" removido da lista de espera");	  
	  operations.remove(op);
  }
  
  public Iterator iterator()
  {
	  return operations.iterator();
  }
  
  public boolean empty()
  {
	  return (operations.size() == 0);
  }
  
  public Vector removeAll()
  {
	  Vector tmp = new Vector();
	  Iterator it = operations.iterator();
	  while (it.hasNext())
		  tmp.add( (Operation) it.next());
	  
	  operations.clear();
	  return tmp;
  }
  
  public void abortTransaction(int transactionId)
  {
	Vector tmp = new Vector();
	Iterator it = operations.iterator();
	
	while(it.hasNext())
	{
		Operation op = (Operation) it.next();
		if (op.getTransactionId() != transactionId)
			tmp.add(op);
	}
	
	operations = tmp;  	
  	
  }
  
   
  private Vector operations = new Vector();
}
