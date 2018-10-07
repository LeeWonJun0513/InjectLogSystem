<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="user.UserDAO" %> <!-- Database Access Object가 필요 -->
<%@ page import="java.io.PrintWriter" %> <!-- 자바스크립트 사용하기 위해 필요 -->
<%@ page import="org.apache.log4j.*"%>

<% request.setCharacterEncoding("UTF-8"); %>  <!-- 데이터 문자코드를 UTF-8로 변경 -->
<jsp:useBean id="user" class="user.User" scope="page" />
<jsp:setProperty name="user" property="userID" />
<jsp:setProperty name="user" property="userPassword" />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Web Application</title>
</head>
<body>
	<%
		// 재로그인 방지
		String userID = null;
		if(session.getAttribute("userID") != null){
			userID = (String) session.getAttribute("userID");
			MDC.put("userName", session.getId());
			
		}
		if (userID != null){
			PrintWriter script = response.getWriter();
			script.println("<script>");
			script.println("alert('이미 로그인이 되어있습니다');");
			script.println("location.href = 'main.jsp'");
			script.println("</script>");
			MDC.put("UserName", userID);
		}
		
		UserDAO userDAO = new UserDAO();
		int result = userDAO.login(user.getUserID(), user.getUserPassword());
		if (result == 1){
			session.setAttribute("userID", user.getUserID()); // 세션아이디 추가
			PrintWriter script = response.getWriter();
			script.println("<script>");
			script.println("location.href = 'main.jsp'");
			script.println("</script>");
		}
		else if (result == 0){
			PrintWriter script = response.getWriter();
			script.println("<script>");
			script.println("alert('비밀번호가 틀립니다.');");
			script.println("history.back();");
			script.println("</script>");
			
		}
		else if (result == -1){
			PrintWriter script = response.getWriter();
			script.println("<script>");
			script.println("alert('존재하지 않는 아이디입니다.');");
			script.println("history.back();");
			script.println("</script>");
		}
		else if (result == -2){
			PrintWriter script = response.getWriter();
			script.println("<script>");
			script.println("alert('데이터베이스 오류가 발생했습니다');");
			script.println("history.back();");
			script.println("</script>");
		}
	%>
</body>
</html>

