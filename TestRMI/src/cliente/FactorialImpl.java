package cliente;
import java.math.BigInteger; 
import remote_interfaces.Factorial;

// Extends and Implement the class 
// and interface respectively 
public class FactorialImpl 
        
	extends java.rmi.server.UnicastRemoteObject 
	implements Factorial { 

	// Constructor Declaration 
	public FactorialImpl() 
		throws java.rmi.RemoteException 
	{ 
		super(); 
	} 

	// Calculation for the problem statement 
	// Implementing the method fact() 
	// to find factorial of a number 
	public BigInteger fact(int num) 
		throws java.rmi.RemoteException 
	{ 
		BigInteger factorial = BigInteger.ONE; 

		for (int i = 1; i <= num; ++i) { 
			factorial = factorial 
							.multiply( 
								BigInteger 
									.valueOf(i)); 
		} 
		return factorial; 
	} 
} 

