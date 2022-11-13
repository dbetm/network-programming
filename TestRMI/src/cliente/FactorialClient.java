package cliente;

import java.net.MalformedURLException; 
import java.rmi.Naming; 
import java.rmi.NotBoundException; 
import java.rmi.RemoteException; 
import remote_interfaces.Factorial;

public class FactorialClient { 
	public static void main(String[] args) 
	{ 

		try { 
                    Factorial Factorial = null;
			// Create an remote object with the same name 
			// Cast the lookup result to the interface 
			Factorial c = (Factorial); 
                        Naming.rebind("rmi:// localhost/FactorialService", c); 
 

			// Call the method for the results 
			System.out.println(c.fact(30)); 
		} 

		// If any error occur 
		catch (MalformedURLException murle) { 
			System.out.println("\nMalformedURLException: "
							+ murle); 
		} 

		catch (RemoteException re) { 
			System.out.println("\nRemoteException: "
							+ re); 
		} 

		catch (java.lang.ArithmeticException ae) { 
			System.out.println("\nArithmeticException: " + ae); 
		} 
	} 
} 

