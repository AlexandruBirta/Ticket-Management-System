package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import model.DBConnection;

public class ResetPassword {
	
	private static final String UPDATE_STATEMENT = "UPDATE users SET pwd = ? WHERE email = ?";
	
	public static void resetPassword(String pwd, String email) {
		
		try(Connection conn = DBConnection.getConnection();
			PreparedStatement stmt = conn.prepareStatement(UPDATE_STATEMENT);) {
			
			stmt.setString(1, pwd);
			stmt.setString(2, email);
			
			stmt.executeUpdate();
		} catch (SQLException ex) {
			System.out.println("SQL Exception!");
		}
	}
}
