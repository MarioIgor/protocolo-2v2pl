
class Operation {
	
	Operation(int operationType, int transactionId, char target) throws Exception
	{
		if ( operationType != Operation.WRITE &&
		     operationType != Operation.READ 		
		   )
		   throw new Exception("Esse construtor so aceita operacoes do tipo READ ou WRITE");
		
		this.operationType = operationType;
		this.transactionId = transactionId;
		this.target = target;
	}
	
	Operation(int operationType, int transactionId) throws Exception
	{
		
		if (operationType != Operation.COMMIT)
			throw new Exception("Esse construtor so aceita operacoes do tipo COMMIT");
		
		this.operationType = operationType;
		this.transactionId = transactionId;
	}
	
	public int getOperationType() {
		return operationType;
	}
	public char getTarget() {
		
		// Se a operacao for de COMMIT, lancar excecao
		return target;
	}
	public int getTransactionId() {
		return transactionId;
	}
	
	public String toString()
	{
		switch(operationType)
		{
		  case Operation.READ :
			return "r"+transactionId+""+target;
		  
		  case Operation.WRITE :
			return "w"+transactionId+""+target;			  
		  
		  case Operation.COMMIT :
			return "c"+transactionId;
			
		  default:
		    return "[Nao reconhecido]";
		}
	}

	
	private int operationType;
	private int transactionId;
	private char target;
	
	public static final int WRITE = 1;
	public static final int READ = 2;
	public static final int COMMIT = 3;	
	
}
