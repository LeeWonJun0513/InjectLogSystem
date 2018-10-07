package JDBC_program;

import java.util.Scanner;

import org.apache.log4j.MDC;

public class ClientTest {

   
   Scanner tempScanner = new Scanner(System.in);

   ClientSql sql = new ClientSql();

   public int inputInt() 
   {

      int tempInt = tempScanner.nextInt();
            
      return tempInt;
   }
   
   public String inputString() 
   {      
      String tempString = tempScanner.next();
      
      return tempString;
   }
   
   
   
   
   public String login() {
	   String id;
	   int password;
	   
	   System.out.println("로그인");
	   System.out.println("id 입력");
	   id = inputString();
	   System.out.println("비밀번호 입력");
	   password = inputInt();
	   MDC.put("userName", id);

	   return id;
	   
	   
   }
   public void sqlTest() {
      int log_id=0, log_status=0;
      String log_url="";

      int select = 999;      
      while(!(select==0)) 
      {
         System.out.println("종료 (0)   전체검색(1)   추가(2)   삭제(3)   수정(4)   검색(5)");
         select = inputInt();
         
         switch(select)
         {
            case 1:
               System.out.println("전체검색");
               sql.selectFromDb();
               System.out.println("검색완료\n");
               break;
            
            case 2:
               System.out.println("추가할 idx 입력");
               log_id = inputInt();

               System.out.println("추가할 id 입력");
               log_url = inputString();
               
               System.out.println("추가할 password 입력");
               log_status = inputInt();
               
               sql.insertToDb(log_id, log_url, log_status);
               
               System.out.println("추가 완료");
              
               break;
            
            case 3:
               System.out.println("삭제할 idx 입력");
               log_id = inputInt();
               
               sql.deleteToDb(log_id);
               System.out.println("삭제 완료");

               break;
            
            case 4:               
               System.out.println("수정할 idx 입력");
               log_id = inputInt();

               System.out.println("수정할 id 입력");
               log_url = inputString();
               
               System.out.println("수정할 password 입력");
               log_status = inputInt();
               
               
               sql.updateToDB(log_url, log_status, log_id);
               break;
               
            case 5:
               System.out.println("검색할 idx 입력");
               log_id = inputInt();
               
               sql.selectFromDb(log_id);
               System.out.println("검색 완료");
               break;   
               
            default:
               
               break;
            case 0:
               System.out.print("test " + "종료");
               break;
         }
         
      }
      tempScanner.close();

   }
}