<%@ page language="java" contentType="text/html; charset=EUC-KR"  pageEncoding="EUC-KR"%>
<%@ page import="java.util.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>����  ����</title>
</head>
<body>

<center><h3> ����  ����  </h3></center>

<%
//������ ���� ��ü�� ID�� �ǵ����ش�.
String id_str=session.getId();
//���ǿ� ���������� �������� �ð��� �ǵ����ش�.
long lasttime=session.getLastAccessedTime();
//������ ������ �ð��� �ǵ��� �ش�.
long createdtime=session.getCreationTime();
//���ǿ� ���������� �������� �ð����� ������  ������ �ð��� ����
//������Ʈ�� �ӹ��ð��� ���ȴ�.
long time_used=(lasttime-createdtime)/60000;
//������ ��ȿ�ð� ������
int inactive=session.getMaxInactiveInterval()/60;
//������ ���� ����������� �˷� �ش�.
boolean b_new=session.isNew();
%>
[1] ���� ID�� [<%=session.getId()%>] �Դϴ�.<br>
[2] ����� ������Ʈ�� �ӹ� �ð��� <%=time_used%> �Դϴ�.<br>
[3] ������ ��ȿ�ð��� <%=inactive%> ���Դϴ�.<br>
[4] ������ ���� ����� ������?<br>
<%
if(b_new)
 out.println(" ���ο� ���� ����.");
else
 out.println("  ���ο� ���� �������� ����");
%>
<hr>

</body>
</html>
