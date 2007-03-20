
class Lock {

	public Lock(int operationType, int transactionId, char target)
	{
		this.operationType = operationType;
		this.transactionId = transactionId;
		this.target = target;
	}
	
	public int getOperationType() {
		return operationType;
	}
	public char getTarget() {
		return target;
	}
	public int getTransactionId() {
		return transactionId;
	}
	
	public void convertToCertify()
	{
		//if (operationType != Lock.WRITE)
		//	throw new Exception("[DEBUG] Operacao "+toString()+"nao pode ser convertida para Certify.");
		
		System.out.println("** Convertendo "+toString()+" para CERTIFY LOCK");
		operationType = Lock.CERTIFY;
		converted = true;
	}
	
	public boolean isConverted()
	{
		return converted;
	}
	
	public String toString()
	{
		switch(operationType)
		{
		  case Lock.READ :
			return "rl"+transactionId+""+target;
		  
		  case Lock.WRITE :
			return "wl"+transactionId+""+target;			  
		  
		  case Lock.CERTIFY :
			return "cl"+transactionId+""+target;
			
		  default:
		    return "[Nao reconhecido]";
		}
	}
	
	private int operationType;
	private int transactionId;
	private char target;
	private boolean converted = false;
	
	public static final int WRITE = 1;
	public static final int READ = 2;
	public static final int CERTIFY = 3;
	

}
