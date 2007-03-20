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
