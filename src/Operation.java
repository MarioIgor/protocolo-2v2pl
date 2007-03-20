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
