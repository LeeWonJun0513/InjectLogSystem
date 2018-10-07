<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="user.UserDAO" %>
<%@ page import="java.io.PrintWriter" %>
<%@ page import="org.apache.log4j.*"%>
<% request.setCharacterEncoding("UTF-8"); %>  
<jsp:useBean id="user" class="user.User" scope="page" />
<jsp:setProperty name="user" property="userID" />
<jsp:setProperty name="user" property="userPassword" />
<jsp:setProperty name="user" property="userName" /> <!-- DB에 입력될 필드 전부 속성으로 추가해야함 -->
<jsp:setProperty name="user" property="userGender" />
<jsp:setProperty name="user" property="userEmail" />
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
			MDC.put("UserName", userID);
		}
		if (userID != null){
			PrintWriter script = response.getWriter();
			script.println("<script>");
			script.println("alert('이미 로그인이 되어있습니다');");
			script.println("location.href = 'main.jsp'");
			script.println("</script>");
			MDC.put("UserName", userID);
			
		}	
		
		if(user.getUserID() == null || user.getUserPassword() == null || user.getUserName() == null
		|| user.getUserGender() == null || user.getUserEmail() == null ){ // 모든 항목중 값이 빈게 있을때
			PrintWriter script = response.getWriter();
			script.println("<script>");
			script.println("alert('입력이 안 된 사항이 있습니다.');");
			script.println("history.back();");
			script.println("</script>");			
		} else {
			UserDAO userDAO = new UserDAO();
			int result = userDAO.join(user); // user의 속성을 다가지고 있는 user ID가 매개변수로 들어감
			if (result == -1){ // 데이터베이스 에러가 이때는 존재하는 경우이나 아이디가 겹치는 경우임
				PrintWriter script = response.getWriter();
				script.println("<script>");
				script.println("alert('이미 존재하는 아이디입니다.');");
				script.println("history.back();");
				script.println("</script>");
			}
			else {
				session.setAttribute("userID", user.getUserID()); // 세션아이디 추가
				PrintWriter script = response.getWriter();
				script.println("<script>");
				script.println("location.href = 'main.jsp'");
				script.println("</script>");
			}
		}

	%>
</body>
</html>


