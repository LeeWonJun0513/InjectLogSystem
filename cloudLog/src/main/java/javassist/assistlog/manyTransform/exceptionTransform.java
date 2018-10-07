package assistlog.manyTransform;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.CtMethod;

public class exceptionTransform implements Transform {

//	Exception e = new Exception();
	
	// 언급된 클래스의 적용할 메소드를 적어준다.
//	private String[] METHOD_NAMES = new String[] {"SQLException" ,"SQLError"}; 
//	"executeUpdate","executeQuery", "printStackTrace","SQLIntegrityConstraintViolationException","Exception", "createSQLException","SQLException" ,"SQLError"
	public byte[] instrument(String className, byte[] defaultBytes, ClassLoader loader) {
		byte[] byteCode = defaultBytes;
		
		ClassPool pool = ClassPool.getDefault();
		CtClass ctClass = null; //현재 클래스
		CtClass clientsql = null;
		CtClass sqlexception_ =null;
		CtClass sitxax = null;
		CtClass intergrity = null;
		CtClass statement = null; //statement를 위한 클래스
	    CtClass jdbcerror = null;
		CtClass throwa = null;
	    try {
			
			
	            pool.appendClassPath(new javassist.LoaderClassPath(loader));
	            
	            ctClass = pool.makeClass(new java.io.ByteArrayInputStream(defaultBytes));
	    
	            //파일 받아왔고, 필요한 package 추가.
	            pool.importPackage( "org.apache.log4j.Logger"); //log4j 패키지 추가.
	            pool.importPackage( "org.apache.log4j.*"); //log4j 패키지 추가.
	            
	            //넘겨받은 클래스 저장.
				ctClass = pool.get(className);
			
				
				////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
				sqlexception_ = pool.get("java.sql.SQLException");
				throwa = pool.get("java.lang.Throwable");
				
				sitxax =pool.get("java.sql.SQLSyntaxErrorException");
				intergrity = pool.get("java.sql.SQLIntegrityConstraintViolationException");
				jdbcerror = pool.get("com.mysql.cj.jdbc.exceptions.SQLError");
				
				clientsql = pool.get("JDBC_program.ClientSql");

				StringBuilder sqlexception= new StringBuilder();
				sqlexception.append("System.out.println(\"sqlexception가 사용됨\");");
				sqlexception.append("org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(java.sql.SQLException.class);");						
				sqlexception.append("logger.error($0);");
				sqlexception.append("logger = null;");
				sqlexception.append("System.out.println(\"hi\");");
				
				StringBuilder clientsql_s= new StringBuilder();
				clientsql_s.append("System.out.println(\"clientsql가 사용됨\");");
				clientsql_s.append("org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(javasql.ClientSql);");						
				clientsql_s.append("logger.debug($0);");
				clientsql_s.append("logger = null;");
				clientsql_s.append("System.out.println(\"hi\");");
				
				StringBuilder executeupdate= new StringBuilder();
				executeupdate.append("System.out.println(\"executeUpdate가 사용됨\");");
				executeupdate.append("org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(java.sql.PreparedStatement.class);");
				executeupdate.append("logger.debug($0);");
				executeupdate.append("logger = null;");
//				//구문 미리 입력.
				StringBuilder executequery= new StringBuilder();
				executequery.append("System.out.println(\"executeQuery가 사용됨\");");
				executequery.append("org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(java.sql.Statement.class);");						
				executequery.append("logger.debug($1);");
				executequery.append("logger = null;");
				
				
				//처리부 
				
				while(true) {

	                //test
					if(ctClass.getName().equals(clientsql.getName())){
						CtMethod[] methods = ctClass.getMethods();
						System.out.println("clientsql 클래스 이름 "+ctClass.getName());
						for(int i=0; i<methods.length; i++) {

							System.out.println("clientsql 메소드 이름 " + methods[i].getLongName());
							
							//로그 처리 
							StringBuilder clientsql_s1= new StringBuilder();
							clientsql_s1.append("System.out.println(\"clientsql가 사용됨\");");
							clientsql_s1.append("org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(javasql.ClientSql.class);");						
							clientsql_s1.append("logger.debug(\"lee\");");
							
							if(methods[i].getName().equals("insertToDb")) {
								
								
								CtField[] ctfields =  ctClass.getFields();
								for(int j=0; j<ctfields.length; j++) {
									System.out.println("필드 이름 : "+ctfields[j].getName());
								}
								methods[i].insertBefore("{" +	clientsql_s1.toString() + "}");
//								methods[i].insertAt(56,"{System.out.println(\"insertDB: \"+$0);}"); //insertAt line num in original class
							}
							
							if(methods[i].getName().equals("selectFromDb")) {
								methods[i].insertBefore("{" +	clientsql_s1.toString() + "}");
//								methods[i].insertAt(56,"{System.out.println(\"selectDB: \"$0);}");
							}
//							if(methods[i].getName().equals("toString")) {
//								System.out.println("clientSQl - toString");
//							}
								
						}
						
					}
					

					CtClass etype = ClassPool.getDefault().get("java.sql.SQLException");
					
					if ( ctClass.subtypeOf(etype) && !ctClass.isInterface()) {
						
						StringBuilder throwprint_= new StringBuilder();
						throwprint_.append("System.out.println(\"etype가 사용됨\");");
						throwprint_.append("org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(java.sql.SQLException.class);");						
						throwprint_.append("logger.error(\"error\");");
						throwprint_.append("logger = null;");
						
						
						System.out.println("etype exception 클래스 이름 "+ctClass.getName());
						CtMethod[] methods = ctClass.getMethods();
						for(int i=0; i<methods.length; i++) {
//							methods[i].getName();
							
//							if(methods[i].getName().equals("SQLIntegrityConstraintViolationException")) {
//								System.out.println("인터그리티");
//							}
//							
//							if(methods[i].getName().equals("SQLSyntaxErrorException")) {
//								System.out.println("신택스");
//							}
							if(methods[i].getName().equals("printStackTrace")){
								System.out.println("프릔트 스택 트레이싀");
								System.out.println("프린트 스택이 잡혔으면 그 이름은? :"+ methods[i].getMethodInfo());
								
									methods[i].insertAfter("{"+"System.out.println(\"hi\");" + "}");
//									methods[i].insertAt(51, "logger.debug($0)");
									System.out.println("로거 오류 추출 준비 완료");
								
									
//								methods[i].insertBefore("{" +	throwprint_.toString() + "}");
							}
							
//							if(methods[i].getName().equals("getMessage")) {
//								System.out.println("getMessage확인.");
//								System.out.println(methods[i].getMethodInfo());
//								
//								methods[i].insertBefore("{" +	throwprint_.toString() + "}");
//								methods[i].insertAt(51, "logger.debug($0)");
//							}
//							if(methods[i].getName().equals("SQLIntegrityConstraintViolationException") || methods[i].getName().equals("java.sql.SQLSyntaxErrorException"))
//								methods[i].insertBefore("{" +	throwprint_.toString() + "}");
						
					
						}
					}
					
					
					
					byteCode = ctClass.toBytecode();
					break;
					
				}
				
          
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
