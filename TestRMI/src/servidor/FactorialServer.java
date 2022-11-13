package servidor;

import cliente.FactorialImpl;
import java.rmi.Naming; 
import remote_interfaces.Factorial;
  
public class FactorialServer { 
  
    // Implement the constructor of the class 
    public FactorialServer() 
    { 
        try { 
            // Create a object reference for the interface 
            Factorial c = new FactorialImpl(); 
  
            // Bind the localhost with the service 
            Naming.rebind("rmi:// localhost/FactorialServer", c); 
            //Naming.rebind("127.0.0.1", c);
            
        } 
        catch (Exception e) { 
            // If any error occur 
            System.out.println("ERR: " + e); 
        } 
    } 
  
    public static void main(String[] args) 
    { 
        // Create an object
        FactorialServer factorialServer = new FactorialServer(); 
    } 

}
