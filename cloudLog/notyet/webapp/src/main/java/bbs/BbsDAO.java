package bbs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import org.apache.log4j.*;
public class BbsDAO {
	private Connection conn;
	private ResultSet rs;
	Logger logger = Logger.getLogger(getClass());
	public BbsDAO() { // BbsDAO 생성자에 DB정보 입력
		try {
			// 호스팅에 업로드할때는 DB정보를 호스팅업체에 맞게 바꿔야함
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
	
	// 글 쓸때 시각
	public String getDate() {
		String SQL = "SELECT NOW()";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			logger.info(SQL);
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				return rs.getString(1);
			}
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return ""; // 데이터베이스 오류
	}

	// 게시물 다음글 번호
	public int getNext() {
		String SQL = "SELECT bbsID FROM BBS ORDER BY bbsID DESC";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			logger.info(SQL);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				return rs.getInt(1) + 1;
			}
			return 1; // 첫번째 게시물인 경우
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return -1; // 데이터베이스 오류
	}
	
	// 글 작성
	public int write(String bbsTitle, String userID, String bbsContent) {
		String SQL = "INSERT INTO BBS VALUES (?, ?, ?, ?, ?, ?)";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, getNext()); // 다음글에 쓰일 게시물 번호
			pstmt.setString(2, bbsTitle); // 제목
			pstmt.setString(3, userID);
			pstmt.setString(4, getDate());
			pstmt.setString(5, bbsContent);
			pstmt.setInt(6, 1);
			MDC.put("userName", userID);
			logger.info(pstmt);
			return pstmt.executeUpdate(); // 첫번째 게시물인 경우
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		return -1; // 데이터베이스 오류		
	}
	
	// 게시글 목록 : 10개씩 내림차순으로 불러오기
	public ArrayList<Bbs> getList(int pageNumber){
		String SQL = "SELECT * FROM BBS WHERE bbsID < ? AND bbsAvailable = 1 ORDER BY bbsID DESC LIMIT 10";
		ArrayList<Bbs> list = new ArrayList<Bbs>();
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, getNext() - (pageNumber-1) * 10);
			logger.info(pstmt);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Bbs bbs = new Bbs();
				bbs.setBbsID(rs.getInt(1));
				bbs.setBbsTitle(rs.getString(2));
				bbs.setUserID(rs.getString(3));
				bbs.setBbsDate(rs.getString(4));
				bbs.setBbsContent(rs.getString(5));
				bbs.setBbsAvailable(rs.getInt(6));
				list.add(bbs);
			}
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return list;	
	}

	// 10페이지로 끊기는 경우 페이징처리
	public boolean nextPage(int pageNumber) {
		String SQL = "SELECT * FROM BBS WHERE bbsID < ? AND bbsAvailable = 1";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, getNext() - (pageNumber-1) * 10);
			
			logger.info(pstmt);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		return false;		
	}
	
	// 글 내용 불러오기
	public Bbs getBbs(int bbsID) {
		String SQL = "SELECT * FROM BBS WHERE bbsID = ?";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, bbsID);
			logger.info(pstmt);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				Bbs bbs = new Bbs();
				bbs.setBbsID(rs.getInt(1));
				bbs.setBbsTitle(rs.getString(2));
				bbs.setUserID(rs.getString(3));
				bbs.setBbsDate(rs.getString(4));
				bbs.setBbsContent(rs.getString(5));
				bbs.setBbsAvailable(rs.getInt(6));
				return bbs;
			}
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return null;	
	}
	
	// 글 수정하기
	public int update(int bbsID, String bbsTitle, String bbsContent) {
		String SQL = "UPDATE BBS SET bbsTitle = ?, bbsContent = ? WHERE bbsID = ?";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, bbsTitle);
			pstmt.setString(2, bbsContent);
			pstmt.setInt(3, bbsID);
			logger.info(pstmt);
			return pstmt.executeUpdate();
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return -1; // 데이터베이스 오류			
	}
	
	// 글 삭제하기
	public int delete(int bbsID) {
		String SQL = "UPDATE BBS SET bbsAvailable = 0 WHERE bbsID = ?";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, bbsID);
			logger.info(pstmt);
			return pstmt.executeUpdate();
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return -1; // 데이터베이스 오류			
	}
}


