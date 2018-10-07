import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
public class Maker 
{
	long log_time=20000000000000L;
	String log_id, log_class, log_method, log_priority, log_content;
	
	long makeLogTime(long first_time)
	{
		long temp_time = first_time;

		if( (first_time/10000) < 2018093159)
		{
			temp_time = first_time + 100L;

		}else if ((first_time/1000000) < 20180930) {
			
			temp_time = first_time + 10000L;
		}
		
		return temp_time;
	}
	
	void modTest(long time)
	{
		long temp_time = time % 10000;

		System.out.println(temp_time);
	}
	
	String makeLogClass(int select)
	{
		String tempClass = "";
		if(select == 0)
		{
			tempClass="javasql.ClientSql";	
		}else if(select == 1)
		{
			tempClass = "com.mysql.cj.jdbc.StatementImpl";
		}else{
			tempClass = "com.mysql.cj.jdbc.ClientPreparedStatement";
		}
		return tempClass;
	}
	
	String makeLogID(int select)
	{
		int tempNum=0;
		Random ran = new Random();
		tempNum = ran.nextInt();
		String tempClass="";
		if(tempNum % 3 == 0)
		{
			tempClass = "Kim";
		}else if(tempNum % 3 == 1)
		{
			tempClass = "lee";
		}else {
			tempClass = "park";
		}
		return tempClass;
	}
	
	String makeLogMethod(int select)
	{
		String tempMethod="";
		if(select == 0)
		{
			select = makeRandom(select);

			if(select == 0 )
			{
			tempMethod = "insertToDb";
			}else if(select == 1){
				tempMethod = "deleteFromDb";
				
			}else 
			{ 
				tempMethod = "updateToDb";
			}
		}else if(select == 1)
		{
			tempMethod = "executeUpdate";
		}else {
			tempMethod = "executeQuery";
		}
		return tempMethod;
	}
	
	String makeLogPriority(int select)
	{
		String tempPriority="";
		if(select == 0)
		{
			tempPriority = "ERROR";
		}else if(select == 1)
		{
			tempPriority = "DEBUG";
		}else {
			tempPriority = "DEBUG";
		}
		return tempPriority;
	}
	
	String makeLogContent(int select)
	{
		String tempContent="";
		if(select == 0)
		{
			tempContent = "java.sql.SQLIntegrityConstraintViolationException: Duplicate entry '10' for key 'PRIMARY'";
			
		}else if(select == 1)
		{
			tempContent = "com.mysql.cj.jdbc.ClientPreparedStatement: INSERT INTO log VALUES (33,'u1',1)";
		}else{
			tempContent = "SELECT idx, id, password FROM log";
		}
		return tempContent;
	}
	
	int makeRandom(int select)
	{
		Random ran = new Random();
		select = ran.nextInt();
		return select%3;
	}
	
	void MakeFile()
	{
		int select = 0;
		int check=0;
		BufferedWriter bw = null;
		try {
			
			for(int year =0; year<17; year++)
			{
				log_time += 10000000000L;

				if(year>0)
				{
					log_time-=1200000000;
					log_time-=30000000;
				}
				for(int month=0 ; month<12 ; month++)
				{
					log_time += 100000000;
					if(month>0)
					{
						log_time-=30000000;
					}
					
					for(int day = 0; day<30 ; day++)
					{
						log_time += 1000000;
						bw = new BufferedWriter(new FileWriter("./dblog/dblog."+log_time+".txt"));
						
						for(int hour=0; hour<24; hour++)
						{
							log_time += 10000;
							if(hour == 23)
							{
								log_time-=240000;
							}				
							
							for(int min=0; min<60; min++)
							{
								
								log_time += 100;
								if(min == 59)
								{
									log_time-=6000;
								}
								
								
								for(int mil=0; mil<60; mil++)
								{
									
									log_time += 1;
									if(mil == 59)
									{
										log_time-=60;
									}
								
									select = makeRandom(select);
									log_id = makeLogID(select);
									log_class = makeLogClass(select);
									log_method = makeLogMethod(select);
									log_priority = makeLogPriority(select);
									log_content = makeLogContent(select);
									
									String line[] = {"INSERT INTO systemlog (`time_stamp`, `id`, `class`, `method`, `priority`, `content`) VALUES"+
											"(\""+log_time + "\", " + "\"" +log_id+ "\", " + "\"" + log_class+ "\", " + "\"" +
									log_method+ "\", " + "\"" + log_priority+ "\", " + "\"" + log_content + "\");"};
										
									
											
									for(String str : line)
									{
										bw.write(str);
										bw.newLine();
									}
								}
									
							}
						}
					}
				}
			}
	} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				bw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
		
	
	
}
