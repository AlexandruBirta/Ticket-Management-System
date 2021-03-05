package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import model.DBConnection;

public class InsertUser {
	
	private static final String INSERT_STATEMENT = "INSERT INTO users (username, pwd, first_name, last_name, email, country, phone_number, administrator, date_of_birth) "
			+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
	
	public static void insertUser(String username, String pwd, String first_name, String last_name, String email, String country, String phone_number, String date_of_birth) {
		
		try(Connection conn = DBConnection.getConnection();
			PreparedStatement stmt = conn.prepareStatement(INSERT_STATEMENT);) {
			
			stmt.setString(1, username);
			stmt.setString(2, pwd);
			stmt.setString(3, first_name);
			stmt.setString(4, last_name);
			stmt.setString(5, email);
			stmt.setString(6, country);
			stmt.setString(7, phone_number);
			stmt.setInt(8, 0);
			stmt.setString(9, date_of_birth);
			
			stmt.executeUpdate();
		} catch (SQLException ex) {
			System.out.println("SQL Exception!");
		}
	}
}
