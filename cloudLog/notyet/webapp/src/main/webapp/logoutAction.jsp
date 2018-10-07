<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="org.apache.log4j.*"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Web Application</title>
</head>
<body>
	<%
		MDC.remove("userName");
		session.invalidate(); // 세션 해지함
	%>
	<script>
		location.href = "main.jsp";
	</script>
</body>
</html>

