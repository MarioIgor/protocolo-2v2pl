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
