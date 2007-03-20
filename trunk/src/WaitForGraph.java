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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

class WaitForGraph {

	public void addEdge(int from, int to)
	{
		// Adicionar aresta ao grafo
		ArrayList tmp = new ArrayList();
		tmp.add(""+from);
		tmp.add(""+to);
		
		edges.add(tmp);
		System.out.println("*** Adicionado "+from+" -> "+to+" no Grafo de Espera (WFG)");
		
	}
	
	public boolean hasCycle()
	{
		// Procurar por ciclos
		Iterator it = edges.iterator();
		while (it.hasNext())
		{
			ArrayList edge = (ArrayList) it.next();
			String from = (String) edge.get(0);
			String to   = (String) edge.get(1);
			
			Iterator it2 = edges.iterator();
			while (it2.hasNext())
			{
				ArrayList edge1 = (ArrayList) it2.next();
				String from1 = (String) edge1.get(0);
				String to1   = (String) edge1.get(1);

				
				if (edge != edge1 &&
				    from.equals(to1) &&
				    to.equals(from1)
				   )
				{
					cycle = "T"+from+" esperando por T"+to+" e T"+from1+" esperando por T"+to1;
					return true;
				}
			}

		}
		
		return false;
		
	}
	
	public String getCycle()
	{
		return cycle;
	}
	
	public void removeEdges(int transactionId)
	{
		Vector tmp = new Vector();
		Iterator it = edges.iterator();
		
		while (it.hasNext())
		{
			ArrayList edge = (ArrayList) it.next();
			int from = Integer.parseInt( (String) edge.get(0));
			int to   = Integer.parseInt( (String) edge.get(1));
			
			if ( from != transactionId && to != transactionId)
				tmp.add(edge);
		}
		
		edges = tmp;
	}
	
	
	private Vector edges = new Vector();
	private String cycle;

}
