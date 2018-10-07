import org.objectweb.asm.*;

public class AsmTest
{
public String hi = "Hello world!";
	
	public static void main(String args[]) 
	{
		AsmTest at = new AsmTest();
		System.out.println(at.hi);
		
	}	
}
