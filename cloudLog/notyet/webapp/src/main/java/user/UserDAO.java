package user;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.apache.log4j.Logger;
import org.apache.log4j.MDC;
public class UserDAO {
	private Connection conn;          // DB에 접근하는 객체
	private PreparedStatement pstmt; // SQL이 준비된 상태가 담긴 객체
	private ResultSet rs;            // 쿼리문의 정보를 담는 객체
	Logger logger = Logger.getLogger(this.getClass());
	public UserDAO() { // UserDAO 생성자에 DB정보 입력
		try {
			String dbURL = "jdbc:mysql://localhost:3306/test?serverTimezone=UTC&useSSL=false";
			String dbID = "root";
			String dbPassword = "1234";
			Class.forName("com.mysql.cj.jdbc.Driver"); // MySQL에 접속할수 있도록 매개체 역할하는 라이브러리
			conn = DriverManager.getConnection(dbURL, dbID, dbPassword);
		}catch(Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
	}
	
	public int login(String userID, String userPassword) {
		String SQL = "SELECT userPassword FROM USER WHERE userID = ?";
		try { // SQL Injection 방어하기 위해 유저아이디를 ?에 받을수 있게 셋팅함
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, userID); 
			logger.info(pstmt);
			MDC.put("userName", userID);
			rs = pstmt.executeQuery(); // rs에 실행할 쿼리문(아이디) 담음
			if (rs.next()) { // 아이디가 있는 경우
				if (rs.getString(1).equals(userPassword)) { // 쿼리 결과로 나온 비밀번호와 userPassword가 동일하다면
					return 1; // 로그인 성공
				} else {
					return 0; // 비밀번호 불일치
				}
			}
			return -1; // 아이디가 없음
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return -2; //데이터베이스 오류
	}
	
	public int join(User user) {
		String SQL = "INSERT INTO USER VALUES (?, ?, ?, ?, ?)";
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, user.getUserID());
			pstmt.setString(2, user.getUserPassword());
			pstmt.setString(3, user.getUserName());
			pstmt.setString(4, user.getUserGender());
			pstmt.setString(5, user.getUserEmail());
			logger.info(pstmt);
			
			return pstmt.executeUpdate();
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}	
		return -1; // 데이터베이스 에러
	}
	
}
































