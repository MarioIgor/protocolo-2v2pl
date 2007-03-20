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

import java.util.Vector;

class ScheduleParser {
	
	ScheduleParser(String schedule)
	{
		this.schedule = schedule;
	}
	
	public Vector parse() throws Exception
	{
		int pointer = 0;
		int jump = 0;
		char[] operations = schedule.toCharArray();
		Vector ops = new Vector();
		
		while (pointer < operations.length)
		{
		  switch (operations[pointer])
		  {
		    case 'r':
		    case 'w':
		      try {
  		        int operationType = ('w' == operations[pointer]) ? Operation.WRITE : Operation.READ;
		        int transactionId = Integer.parseInt( String.valueOf(operations[pointer+1]) );
		        char target = operations[pointer+2];
		        ops.add(new Operation(operationType,transactionId,target));
		      } catch (Exception e) {
			    e.printStackTrace();		    	  
		      }
		      
		      jump += 3;
		    break;
		    
		    case 'c':
			  try {
		        int transactionId = Integer.parseInt( String.valueOf(operations[pointer+1]) );			    
				ops.add(new Operation(Operation.COMMIT,transactionId));
		      } catch (Exception e) {
		        e.printStackTrace();
			  }
		      jump += 2;
		    break;
		    
		    default:
		    	throw new Exception("Schedule mal formado.");
		  
		  }
          pointer += jump;
          jump = 0;
		}
		
		return ops;
	}
	
	private String schedule;

}
