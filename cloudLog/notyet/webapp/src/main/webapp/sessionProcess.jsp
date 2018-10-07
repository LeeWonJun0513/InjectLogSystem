<%@ page language="java" contentType="text/html; charset=EUC-KR"  pageEncoding="EUC-KR"%>
<%@ page import="java.util.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>세션  예제</title>
</head>
<body>

<center><h3> 세션  예제  </h3></center>

<%
//고유한 세션 객체의 ID를 되돌려준다.
String id_str=session.getId();
//세션에 마지막으로 엑세스한 시간을 되돌려준다.
long lasttime=session.getLastAccessedTime();
//세션이 생성된 시간을 되돌려 준다.
long createdtime=session.getCreationTime();
//세션에 마지막으로 엑세스한 시간에서 세션이  생성된 시간을 빼면
//웹사이트에 머문시간이 계산된다.
long time_used=(lasttime-createdtime)/60000;
//세션의 유효시간 얻어오기
int inactive=session.getMaxInactiveInterval()/60;
//세션이 새로 만들어졌는지 알려 준다.
boolean b_new=session.isNew();
%>
[1] 세션 ID는 [<%=session.getId()%>] 입니다.<br>
[2] 당신의 웹사이트에 머문 시간은 <%=time_used%> 입니다.<br>
[3] 세션의 유효시간은 <%=inactive%> 분입니다.<br>
[4] 세션이 새로 만들어 졌나요?<br>
<%
if(b_new)
 out.println(" 새로운 세션 생성.");
else
 out.println("  새로운 세션 생성하지 않음");
%>
<hr>

</body>
</html>
