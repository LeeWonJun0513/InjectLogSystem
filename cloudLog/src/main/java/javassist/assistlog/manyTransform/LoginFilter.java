package assistlog.manyTransform;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.CtMethod;

public class LoginFilter implements Transform {

//	Exception e = new Exception();
	

	public byte[] instrument(String className, byte[] defaultBytes, ClassLoader loader) {
		byte[] byteCode = defaultBytes;
		
		ClassPool pool = ClassPool.getDefault();
		CtClass ctClass = null; //현재 클래스
		CtClass login = null;
	
	    try {
			
			
	            pool.appendClassPath(new javassist.LoaderClassPath(loader));
	            
	            ctClass = pool.makeClass(new java.io.ByteArrayInputStream(defaultBytes));
	    
	            //파일 받아왔고, 필요한 package 추가.
	            pool.importPackage( "org.apache.log4j.Logger"); //log4j 패키지 추가.
	            pool.importPackage( "org.apache.log4j.*"); //log4j 패키지 추가.
	            
	            //넘겨받은 클래스 저장.
				ctClass = pool.get(className);

				login = pool.get("crm.Login");
//			
				
				//처리부 
				StringBuilder exceptionlog= new StringBuilder();
//				executeupdate.append("System.out.println(\"executeUpdate가 사용됨\");");
				exceptionlog.append("org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(crm.Login.class);");
//				
//				exceptionlog.append("logger.error("error");");
//				exceptionlog.append("logger = null;");
	                
				if(ctClass.getName().equals(login.getName())){
//					CtField[] fields = ctClass.getFields();
					CtMethod[] methods = ctClass.getMethods();
					System.out.println("login 클래스 이름 "+ctClass.getName());
					for(int i=0; i<methods.length; i++) {
//							System.out.println(methods[i].getName());
//						System.out.println("login 메소드 이름 " + methods[i].getLongName());
						if(methods[i].getName().equals("jButton1ActionPerformed")){
							System.out.println("성공");
							methods[i].insertAfter("{System.out.println(\"\");}");
//							methods[i].insertAt(217, true, exceptionlog.toString());
						}
						
							
					}
					
				}

				byteCode = ctClass.toBytecode();

			
          
			} catch (Exception e) {
				e.printStackTrace();
	        } finally {
	        	
	        	if (ctClass != null) {
	        		ctClass.detach();
	        	}
	        }
	        	 
        return byteCode;
	}
}