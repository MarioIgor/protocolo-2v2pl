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

public class Main {

	/*
	 * SCHEDULES DE TESTE
	 * r1xr2xw3xc3c2c1
	 * w1xw2xc1r2xc2 
	 * r1xr2xw1xw2xc1c2
	 * r3yr1yr1xw1yc1c3
	 */
	public static void main(String[] args) 
	{
		if (args.length < 2      ||
			(!args[1].equals("WFG")     && 
			 !args[1].equals("WAITDIE") && 
			 !args[1].equals("WOUNDWAIT")
	        )
		   )
		{
			System.out.println("Usage: java Main schedule prevention_type "+
                         "\n\nSchedule:"+
                         "\nOperacoes: Escrita: w, Leitura: r e Commit: c "+
                         "\nExemplo: w1xr2yc2c1 (Escrita na transacao 1 sobre o objeto x, leitura na transacao 2 no objeto y, commit de 2, commit de 1"+
                         "\n\nprevention_type = (WFG | WAITDIE | WOUNDWAIT)");
			System.exit(0);
		}
		

		try {
			
		  int preventionType=10; /*FIXME - Nao instanciar essa variavel */
		  if (args[1].equals("WAITDIE")) preventionType = DeadLockPrevention.WAITDIE;
		  if (args[1].equals("WOUNDWAIT")) preventionType = DeadLockPrevention.WOUNDWAIT;
		  if (args[1].equals("WFG")) preventionType = DeadLockPrevention.WFG;
			
		  System.out.println("SCHEDULE "+args[0]+"\n");
		  
		  Scheduler scheduler = new Scheduler(
		  		                              new ScheduleParser(args[0]).parse(),
											  preventionType
											 );
		  
		  while ( ! scheduler.empty() )
			  scheduler.execute();
		  
		  System.out.println("\nSCHEDULE ESCALONADO: "+scheduler.getScheduled());
		  
		} catch (DeadLockException e) {
			System.out.println(e.getMessage());
		
	    } catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
