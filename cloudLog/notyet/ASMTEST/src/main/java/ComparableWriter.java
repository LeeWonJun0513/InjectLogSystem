import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;

import java.io.FileOutputStream;

public class ComparableWriter 
{
	public byte[] toByteArray()
	{
		ClassWriter cw = new ClassWriter(0);
		
		cw.visit(Opcodes.V1_5, Opcodes.ACC_ABSTRACT + Opcodes.ACC_INTERFACE + Opcodes.ACC_PUBLIC,
				"Comparable", null, "java/lang/Object", new String[0]);
		cw.visitField(Opcodes.ACC_FINAL + Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC,
				"LESS", "I", null, new Integer(-1)).visitEnd();
		cw.visitField(Opcodes.ACC_FINAL + Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC,
				"EQUAL", "I", null, new Integer(1)).visitEnd();
		cw.visitField(Opcodes.ACC_FINAL + Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC,
				"GREATER", "I", null, new Integer(1)).visitEnd();
		cw.visitMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_ABSTRACT, 
				"compareTo", "(Ljava/lang/Object;)l", null, null).visitEnd();
		cw.visitEnd();
		
		return cw.toByteArray();
	}
	
	public static void main(String args[]) 
	{
		ComparableWriter cwr = new ComparableWriter();
		
		FileOutputStream stream = null;
		
		try
		{
			stream = new FileOutputStream("Comparable.class");
			stream.write(cwr.toByteArray());
		}catch(Throwable t) {
			t.printStackTrace();
		}finally {
			if(stream != null) {
				try {
					stream.close();
				}catch(Throwable t) {
					t.printStackTrace();
				}
			}
		}
	}
}
