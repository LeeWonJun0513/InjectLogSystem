package assistlog;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;



public class Transformer implements ClassFileTransformer {

    Transform[] Transformat = new Transform[] {
    		
//		we will apply another format 'new NewFormat()'......    
//    	new LoginFilter(),
    	new MySQLTransform()	

    };

    public byte[] transform(ClassLoader loader, String className, Class classBeingRedefined,
                            ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {

        String replacedClassName = className.replace("/", "."); 
        //System.out.println("replacedClassName : "+ replacedClassName);
        byte[] byteCode = classfileBuffer;

        for (Transform transform : Transformat) { 

                byteCode = transform.instrument(replacedClassName, byteCode, loader); 

        }

        return byteCode;
    }
}