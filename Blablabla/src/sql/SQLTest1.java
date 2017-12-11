package sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLTest1 {
	
	// JDBC driver and database URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost:3306/new_schema";
	
	// Database credentials
	static final String USER = "root";
	static final String PASS = "Sempre.la.stessa0";
	
	public static void main(String[] args) {
		Connection conn = null;
		Statement stmt = null;
		try {
			// Register JDBC driver
			Class.forName(JDBC_DRIVER);
			
			// Open a connection
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			
			// Execute a query
			System.out.println("Creating statement...");
			stmt = conn.createStatement();
			String sql;
			sql = "SELECT id, username, pwd FROM new_table";
			ResultSet rs = stmt.executeQuery(sql);
			
			// Extract data from result set
			while(rs.next()) {
				// Retrieve by column name
				int id = rs.getInt("id");
				String username = rs.getString("username");
				String pwd = rs.getString("pwd");
			
				// Display values
				System.out.println("ID: " + id);
				System.out.println("username: " + username);
				System.out.println("password: " + pwd);
			}
			
			// Clean-up environment
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			try {
				if (stmt != null) {
					stmt.close();
				}
			} catch (SQLException se) {
				se.printStackTrace();
			}
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
	}
}
