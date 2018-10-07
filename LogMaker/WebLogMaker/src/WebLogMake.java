import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
public class WebLogMake 
{
	long log_time=20180000000000L;
	String web_timestamp, web_priority, web_Id, web_class, web_method, web_content;

	
	String makeWebID(int select)
	{
		int tempNum=0;
		Random ran = new Random();
		tempNum = ran.nextInt();
		String tempId="";
		if(tempNum % 3 == 0)
		{
			tempId = "lee";
		}else if(tempNum % 3 == 1)
		{
			tempId = "veera";
		}else {
			tempId = "bank";
		}
		return tempId;
	}
	
	String makeWebPriority(int select)
	{
		String tempPriority="";
		if(select == 0)
		{
			tempPriority = "ERROR";
		}else
		{
			tempPriority = "INFO";
		}
		return tempPriority;
	}
	
	String makeWebContent(int select)
	{
		String tempContent="";
		if(select == 0)
		{
			tempContent = "java.lang.NullPointerException";
			
		}else if(select == 1)
		{
			tempContent = "com.mysql.cj.jdbc.ClientPreparedStatement: SELECT * FROM BBS WHERE bbsID < 6 AND bbsAvailable = 1 ORDER BY bbsID DESC LIMIT 10";
		}else if(select == 2)
		{ 
			tempContent = "com.mysql.cj.jdbc.ClientPreparedStatement: SELECT * FROM BBS WHERE bbsID < -3 AND bbsAvailable = 1";
		}else if(select == 3)
		{
			tempContent = "com.mysql.cj.jdbc.ClientPreparedStatement: INSERT INTO BBS VALUES (6, 'hi lee', 'l1', '2018-09-26 20:55:02', '1', 1)";
		}else if(select == 4)
		{
			tempContent = "com.mysql.cj.jdbc.ClientPreparedStatement: SELECT * FROM BBS WHERE bbsID = 6";
		}else
		{
			tempContent = "com.mysql.cj.jdbc.ClientPreparedStatement: UPDATE BBS SET bbsTitle = 'hi lee', bbsContent = '2' WHERE bbsID = 6 \r\n";
		}
		return tempContent;
	}
	
	int makeRandom(int select)
	{
		Random ran = new Random();
		select = ran.nextInt();
		return select%6;
	}
	
	String makeWebMethod(int select)
	{
		String tempMethod="";
		
		int tempNum=0;
		Random ran = new Random();
		tempNum = ran.nextInt()%6;
		
			if(tempNum == 0 )
			{
			tempMethod = "login";
			}else if(tempNum == 1){
				tempMethod = "getList";
				
			}else if(tempNum == 2)
			{ 
				tempMethod = "nextPage";
			}else if(tempNum == 3)
			{
				tempMethod = "write";
			}else if(tempNum == 4)
			{
				tempMethod = "getBbs";
			}else
			{
				tempMethod = "update";
			}
		return tempMethod;
	}
	
	String makeWebClass(int select)
	{
		String tempClass = "";
		if(select == 0)
		{
			tempClass = "user.UserDAO";
		}else
		{
			tempClass="bbs.BbsDAO";	
		}
		return tempClass;
	}
	
	
	void MakeFile()
	{
		int select = 0;
		int check=0;
		BufferedWriter bw = null;
		try {
				for(int month=0 ; month<12 ; month++)
				{
					log_time += 100000000;
					if(month>0)
					{
						log_time-=30000000;
					}
					
					for(int day = 0; day<30 ; day++)
					{
						bw = new BufferedWriter(new FileWriter("./weblog/weblog."+log_time+".txt"));

						log_time += 1000000;
						
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
							
									select = makeRandom(select);
									web_Id = makeWebID(select);
									web_class = makeWebClass(select);
									web_method = makeWebMethod(select);
									web_priority = makeWebPriority(select);
									web_content = makeWebContent(select);
									
									String line[] = {log_time + ", " +web_Id+ ", " + web_class+ ", " +
											web_method+ ", " + web_priority+ ", " + web_content};
									/*
									String line[] = {"INSERT INTO weblog (`time_stamp`, `id`, `class`, `method`, `priority`, `content`) VALUES"+
											"(\""+log_time + "\", " + "\"" +web_Id+ "\", " + "\"" + web_class+ "\", " + "\"" +
											web_method+ "\", " + "\"" + web_priority+ "\", " + "\"" + web_content + "\");"};
											*/
									
									for(String str : line)
									{
										bw.write(str);
										bw.newLine();
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
