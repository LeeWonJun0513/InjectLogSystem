package assistlog;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;

public class MySQLTransform implements Transform {

	private String[] METHOD_NAMES = new String[] { "executeUpdate","executeQuery"}; //,"execute"
	public boolean appliesTo(String className) {
		
		return true;
	}
	public byte[] instrument(String className, byte[] defaultBytes, ClassLoader loader) {
		byte[] byteCode = defaultBytes;
		
		ClassPool pool = ClassPool.getDefault();
		CtClass ctClass = null; //���� Ŭ����
	    CtClass statement = null; 
	    CtClass preparedstatement = null;
		
	    try {
            pool.appendClassPath(new javassist.LoaderClassPath(loader));
            
            ctClass = pool.makeClass(new java.io.ByteArrayInputStream(defaultBytes));
    
            pool.importPackage( "org.apache.log4j.Logger"); 
            pool.importPackage( "org.apache.log4j.*");
          
			ctClass = pool.get(className);
		
			statement = pool.get("java.sql.Statement");
			
			preparedstatement = pool.get("java.sql.PreparedStatement");
              
			        try {
			        	if ( ctClass.subtypeOf(statement) && !ctClass.isInterface()) { 
						for (String methodName : METHOD_NAMES) { 
							
							CtMethod ctMethod = ctClass.getDeclaredMethod(methodName); 
						
							StringBuilder executequery= new StringBuilder();
							executequery.append("System.out.println(\"executeQuery�� ����\");");
							executequery.append("org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(java.sql.Statement.class);");
							executequery.append("logger.info(\"executeQuery �α��Է³�\");");
							
							StringBuilder executeupdate= new StringBuilder();
							executeupdate.append("System.out.println(\"executeUpdate�� ����\");");
							executeupdate.append("org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(java.sql.PreparedStatement.class);");
							executeupdate.append("logger.debug(\"executeUpdate ��\");");
							
							StringBuilder execute = new StringBuilder();
							execute.append("System.out.println(\"execute �� ����\");" );
							//execute.append("org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(java.sql.Statement.class);");
							//execute.append("logger.debug(\"execute ��\");");
							
							if(methodName == "executeUpdate") {
									if(ctClass.subtypeOf(preparedstatement)) //statement > preparedstatement ����
										ctMethod.insertBefore("{" + executeupdate.toString() + "}");
									
							}
							else if(methodName == "executeQuery") {
								ctMethod.insertBefore("{" + executequery.toString() + "}");
								
							}
							
						}
		        	}
		        } catch (NotFoundException e) {
		        	
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