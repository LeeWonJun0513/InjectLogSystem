package JDBC_program;


public class ClientMain {


	public static void main(String args[]) 
	{
		
		System.out.println("this is for test");
		ClientTest ts = new ClientTest();
		String id = ts.login();
		
	    ts.sqlTest();
	    
	    
	}
}
