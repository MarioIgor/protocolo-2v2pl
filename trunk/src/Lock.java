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
