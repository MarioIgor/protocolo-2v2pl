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
