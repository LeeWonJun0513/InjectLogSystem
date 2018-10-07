import java.io.IOException;

import org.objectweb.asm.ClassReader;

public class ClassView 
{
	public static void main(String args[]) 
	{
		
		try {
			ClassPrinter cp = new ClassPrinter();
			ClassReader cr = new ClassReader("AsmTest");
			cr.accept(cp, 0);

		} catch (Throwable t) {
			// TODO Auto-generated catch block
			t.printStackTrace();
			throw new RuntimeException(t);
		}
	}
}
