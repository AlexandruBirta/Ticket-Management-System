package model;

import java.sql.*;


public class DBConnection {
	
	private static final String USERNAME = "root";
	private static final String PASSWORD = "admin";
	private static final String URL = "jdbc:mysql://localhost:3306/flights";

	public static Connection getConnection() throws SQLException {
		
		return DriverManager.getConnection(URL, USERNAME, PASSWORD);	
	}
}
