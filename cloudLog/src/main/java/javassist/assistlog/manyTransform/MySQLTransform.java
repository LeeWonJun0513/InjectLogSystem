package assistlog.manyTransform;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;

public class MySQLTransform implements Transform {
	
	// 언급된 클래스의 적용할 메소드를 적어준다.
	private String[] METHOD_NAMES = new String[] {"executeUpdate","executeQuery"}; 

	public byte[] instrument(String className, byte[] defaultBytes, ClassLoader loader) {
		byte[] byteCode = defaultBytes;
		
		ClassPool pool = ClassPool.getDefault();
		CtClass ctClass = null; //현재 클래스
	    
		
		//jdbc 관련
		CtClass statement = null; 
	    CtClass preparedstatement = null;
	    //CtClass clientpreparedstatement = null;
	 
	    //error 관련 정의
//	    CtClass error_java = null;
//	    CtClass throwable = null;
//	    CtClass jdbcerror = null;
//	    CtClass sqlexception_ = null;
	    try {
			
			
            pool.appendClassPath(new javassist.LoaderClassPath(loader));
            
            ctClass = pool.makeClass(new java.io.ByteArrayInputStream(defaultBytes));
    
            //파일 받아왔고, 필요한 package 추가.
            pool.importPackage( "org.apache.log4j.Logger"); //log4j 패키지 추가.
            pool.importPackage( "org.apache.log4j.*"); //log4j 패키지 추가.
            
            //넘겨받은 클래스 저장.
			ctClass = pool.get(className);

			
			
			statement = pool.get("java.sql.Statement");
			preparedstatement = pool.get("java.sql.PreparedStatement");
//			clientpreparedstatement = pool.get("com.mysql.cj.jdbc.ClientPreparedStatement");
//			exception = pool.get("java.lang.Exception");
//			sqlexception_ = pool.get("java.sql.SQLException");
//			error_java = pool.get("java.lang.Error");
//			throwable = pool.get("java.lang.Throwable");
//			jdbcerror =  pool.get("com.mysql.cj.jdbc.exceptions.SQLError");
//			
			
			ctClass = pool.get(className);
			
			
			CtClass exception = null; 
			
			
			exception = pool.get("java.lang.Exception");
			        try {
	        	
						for (String methodName : METHOD_NAMES) { //찾고자하는 메소드를 클래스에서 찾아서 
				
							CtMethod ctMethod = ctClass.getDeclaredMethod(methodName); //현재 받아온 것
							System.out.println("검색하고 있는 메소드 이름 "+ctMethod.getName());
							
							
							StringBuilder executeupdate= new StringBuilder();
							executeupdate.append("org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(java.sql.PreparedStatement.class);");
							executeupdate.append("logger.debug($0);");
							executeupdate.append("logger = null;");

							StringBuilder executequery= new StringBuilder();
							executequery.append("org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(java.sql.Statement.class);");						
							executequery.append("logger.debug($1);");
							executequery.append("logger = null;");
					

							if ( ctClass.subtypeOf(statement) && !ctClass.isInterface()) {
								
								if(methodName == "executeUpdate") {	
									System.out.println(ctMethod.getName());
									if(ctClass.subtypeOf(preparedstatement)) {//statement > preparedstatement 때문 ||ctClass.subtypeOf(clientpreparedstatement)
										ctMethod.insertBefore("{" +	executeupdate.toString() + "}");
									}	
								}
								
								if(methodName == "executeQuery") {
									 System.out.println(ctMethod.getName());
									 if(!ctClass.subtypeOf(preparedstatement) ) {//statement > preparedstatement 때문 && !ctClass.subtypeOf(clientpreparedstatement)
										 ctMethod.insertBefore("{" + executequery.toString() + "}");
									 }
									
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
