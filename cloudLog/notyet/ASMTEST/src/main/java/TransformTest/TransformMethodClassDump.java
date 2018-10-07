package TransformTest;

import java.util.*;
import org.objectweb.asm.*;

public class TransformMethodClassDump implements Opcodes
{
	public static byte[] dump () throws Exception 
	{ 
		ClassWriter cw = new ClassWriter(0); 
		FieldVisitor fv; 
		MethodVisitor mv; 
		AnnotationVisitor av0; 
		cw.visit(Opcodes.V1_1, ACC_PUBLIC + ACC_SUPER, "TransformMethodClass", null, "java/lang/Object", null); 
		{ 
			mv = cw.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null); 
			mv.visitCode(); mv.visitVarInsn(ALOAD, 0); 
			mv.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V"); 
			mv.visitInsn(RETURN); 
			mv.visitMaxs(1, 1); 
			mv.visitEnd(); 
		} 
		
		{ 
		mv = cw.visitMethod(ACC_PUBLIC, "m", "()V", null, new String[] { "java/lang/Exception" }); 
		mv.visitCode(); 
		mv.visitMethodInsn(INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J"); 
		mv.visitVarInsn(LSTORE, 1); mv.visitLdcInsn(new Long(100L)); 
		mv.visitMethodInsn(INVOKESTATIC, "java/lang/Thread", "sleep", "(J)V"); 
		mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;"); 
		mv.visitMethodInsn(INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J"); 
		mv.visitVarInsn(LLOAD, 1); mv.visitInsn(LSUB); 
		mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(J)V"); 
		mv.visitInsn(RETURN); mv.visitMaxs(5, 3); 
		mv.visitEnd(); 
		} cw.visitEnd(); 
		
		return cw.toByteArray(); 
		
	}

	
}

