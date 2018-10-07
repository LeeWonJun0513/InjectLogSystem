package assistlog;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

import org.apache.log4j.Logger;

import javassist.CannotCompileException;
import javassist.ClassPath;
import javassist.ClassPool;
import javassist.CtBehavior;
import javassist.CtClass;
import javassist.LoaderClassPath;
import javassist.NotFoundException;
import javassist.URLClassPath;
import org.apache.catalina.core.StandardEngine;

public class TomcatTransformer implements ClassFileTransformer {
    ClassPool pool = null;
    public TomcatTransformer() {
        this.pool = ClassPool.getDefault();
    }
     
    public byte[] transform(ClassLoader loader, String className, Class redefiningClass, ProtectionDomain domain,
            byte[] bytes) throws IllegalClassFormatException {
        if (className.contains("StandardEngine")) {
            System.out.println("className: " + className);
            return transformClass(redefiningClass, bytes); //byte로 주는 거임.
        } else {
        	System.out.println("실패");
            return bytes;
        }
//    	Logger logger = Logger.getLogger(TomcatTransformer.class);
//    	logger.debug("");
//    	System.out.println("className: " + className);
//    	return transformClass(redefiningClass, bytes); //byte로 주는 거임.
    }
 
    private byte[] transformClass(Class classToTransform, byte[] b) {
        CtClass cl = null;
        try {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
             
            ClassPool pool = ClassPool.getDefault();
            pool.insertClassPath(new LoaderClassPath(classLoader));
            
            ClassPath cp = new URLClassPath("www.localhost", 8080, "/webapp/" , "./"); // , "org.javassist."
            pool.insertClassPath(cp);
            
            if (pool != null) {
                cl = pool.makeClass(new java.io.ByteArrayInputStream(b));
                if (cl.isInterface() == false) {
                    CtBehavior[] methods = cl.getDeclaredBehaviors();
                    for (int i = 0; i < methods.length; i++) {
                        System.out.println("methods[" + i + "]: " + methods[i]);
                        if (methods[i].isEmpty() == false) {
                            doTransform(methods[i]);
                        }
                    }
                }
                b = cl.toBytecode();
            }
        } catch (Exception e) {
            System.err.println("e111: " + e);
        } finally {
            if (cl != null) {
                cl.detach();
            }
        }
        return b;
    }
     
    private void doTransform(CtBehavior method) throws NotFoundException, CannotCompileException {
    	System.out.println("성공했습니다.");
    	if (method.getName().equals("invoke")) {
             
            System.out.println("0");
            try {
                System.out.println("1");
                method.insertBefore("{"+"System.out.println(\"succex\") "+ "}");
//                method.insertBefore(""
//                        + "String jvmRoute = System.getProperty(\"jvmRoute\");"
//                        + "String sessionId = $1.getSession().getId();"
//                        + "String uri = $1.getRequestURI();"
//                        + "pe.kr.ddakker.monitor.websocket.WSClient.send(\"{"
//                        + "server: '\" + jvmRoute + \"' "
//                        + ", sessionId: '\" + sessionId + \"' "
//                        + ", uri: '\" + uri + \"' "
//                        + ", stTime: '\" + System.currentTimeMillis() + \"' "
//                        + "}\");");
//                System.out.println("2");
//                method.insertAfter(""
//                        + "String jvmRoute = System.getProperty(\"jvmRoute\");"
//                        + "String sessionId = $1.getSession().getId();"
//                        + "String uri = $1.getRequestURI();"
//                        + "pe.kr.ddakker.monitor.websocket.WSClient.send(\"{"
//                        + "server: '\" + jvmRoute + \"' "
//                        + ", sessionId: '\" + sessionId + \"' "
//                        + ", uri: '\" + uri + \"' "
//                        + ", edTime: '\" + System.currentTimeMillis() + \"' "
//                        + ", status: '\" + $2.getStatus() + \"' "
//                        + "}\");");
                System.out.println("3");
            } catch (Exception e) {
                System.err.println("Aa e: " + e);
            }
            System.out.println("4");
        }
         
    }
 
}
