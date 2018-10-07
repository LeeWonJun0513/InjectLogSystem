package JDBC_program;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.lang.Throwable;
//import java.lang.Exception.*;
//import java.lang.Error.*;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

public class ClientSql {

	public static final String url = "jdbc:mysql://localhost/test?useUnicode=true&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
	public static final String driver = "com.mysql.cj.jdbc.Driver";
	private static final Logger logger = Logger.getLogger(ClientSql.class);
	
	public void insertToDb(int log_id, String log_url, int log_status) {
		Connection connection = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);

			connection = DriverManager.getConnection(url, "root", "1234");
			
			String sql = "INSERT INTO log VALUES (?,?,?)";
			pstmt = connection.prepareStatement(sql);

			pstmt.setInt(1, log_id);
			pstmt.setString(2, log_url);
			pstmt.setInt(3, log_status);

			int count = pstmt.executeUpdate();

			if (count == 0) {
				System.out.println("데이터 입력 실패");
			} else {
				System.out.println(log_id + " " + log_url + " " + log_status);
				System.out.println("데이터 입력 성공");
			}

		} catch (SQLException e) {
			System.out.println("printStackTrace : "); e.printStackTrace();
			logger.error(e);
			insertToDb(log_id + 1, log_url, log_status);
	
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			logger.error(e);
		} finally {
			try {
				if (connection != null && !connection.isClosed()) {
					connection.close();
				}
				if (pstmt != null && !pstmt.isClosed()) {
					pstmt.close();
				}
			} catch (SQLException e) {
				logger.error(e);
				e.printStackTrace();
				
			}

		}
	}

	public void selectFromDb(int log_id) {
		Connection connection = null;
		Statement stmt = null;
		ResultSet rs = null;
		

		try {
			Class.forName(driver);
			connection = DriverManager.getConnection(url, "root", "1234");

			stmt = connection.createStatement();

			String sql = "SELECT idx, id, password FROM log WHERE " + String.valueOf(log_id);

			rs = stmt.executeQuery(sql);
			

			while (rs.next()) {
				int selected_id = rs.getInt(1);
				String selected_url = rs.getString(2);
				int selected_status = rs.getInt(3);
				if (log_id == selected_id) {
					System.out.println(selected_id + " " + selected_url + " " + selected_status);

				}
			}
		
		} catch (ClassNotFoundException e) {
			logger.error(e);
			System.out.println("드라이버 로딩 실패");
		} catch (SQLException e) {
			logger.error(e);
			System.out.println("에러" + e);

		} finally {
			try {
				if (connection != null && !connection.isClosed()) {
					connection.close();
				}
				if (stmt != null && stmt.isClosed()) {
					stmt.close();
				}
				if (rs != null && !rs.isClosed()) {
					rs.close();
				}
			} catch (SQLException e) {
				logger.error(e);
				e.printStackTrace();
			}
		}
	}

	public void selectFromDb() {
		Connection connection = null;
		Statement stmt = null;
		ResultSet rs = null;
		String executedQuery =null; //삭제 대상 String executedQuery = rs.getStatement().toString();
		try {
			Class.forName(driver);
			connection = DriverManager.getConnection(url, "root", "1234");

			stmt = connection.createStatement();

			String sql = "SELECT idx, id, password FROM log";

			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				int log_id = rs.getInt(1);
				String log_url = rs.getString(2);
				int log_status = rs.getInt(3);

				System.out.println(log_id + " " + log_url + " " + log_status);
			}
		} catch (ClassNotFoundException e) {
			logger.error(e);
			e.printStackTrace();
			System.out.println("드라이버 로딩 실패");
		} catch (SQLException e) {
			logger.error(e);
			e.printStackTrace();

		} finally {
			try {
				if (connection != null && !connection.isClosed()) {
					connection.close();
				}
				if (stmt != null && stmt.isClosed()) {
					stmt.close();
				}
				if (rs != null && !rs.isClosed()) {
					rs.close();
				}
			} catch (SQLException e) {
				logger.error(e);
				e.printStackTrace();
			}
		}
	}

	public void deleteToDb(int log_id) {
		Connection connection = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			connection = DriverManager.getConnection(url, "root", "1234");

			String sql = "DELETE FROM log WHERE idx = ?";
			pstmt = connection.prepareStatement(sql);

			pstmt.setInt(1, log_id);

			int count = pstmt.executeUpdate();

			if (count == 0) {
				System.out.println("데이터 삭제 실패 : 대상에 없습니다.");
				
				
			} else {
				System.out.println(log_id);
				System.out.println("데이터 삭제 성공");
			}

		} catch (SQLException e) {
			logger.error(e);
			e.printStackTrace();
			
		} catch (ClassNotFoundException e) {
			logger.error(e);
			System.out.println("드라이버 로딩 실패");
			
		} finally {
			try {
				if (connection != null && !connection.isClosed()) {
					connection.close();
				}
				if (pstmt != null && !pstmt.isClosed()) {
					pstmt.close();
				}
			} catch (SQLException e) {
				logger.error(e);
				e.printStackTrace();
			}

		}
	}

	public void updateToDB(String log_url, int log_status, int log_id) {
		Connection connection = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			connection = DriverManager.getConnection(url, "root", "1234");

			String sql = "UPDATE log SET id = ?, password = ? WHERE idx = ?";

			pstmt = connection.prepareStatement(sql);

			pstmt.setString(1, log_url);
			pstmt.setInt(2, log_status);
			pstmt.setInt(3, log_id);

			int count = pstmt.executeUpdate();

			if (count == 0) {
				System.out.println("데이터 수정 실패");
			} else {
				System.out.println(log_id + " " + log_url + " " + log_status);
				System.out.println("데이터 수정 성공");
			}

		} catch (SQLException e) {
			logger.error(e);
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			logger.error(e);
			System.out.println("드라이버 로딩 실패");
		} finally {
			try {
				if (connection != null && !connection.isClosed()) {
					connection.close();
				}
				if (pstmt != null && !pstmt.isClosed()) {
					pstmt.close();
				}
			} catch (SQLException e) {
				logger.error(e);
				e.printStackTrace();
			}

		}
	}

}
