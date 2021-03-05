package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import model.DBConnection;
import model.Ticket;
import model.User;

public class InsertTransaction {

	private static final String INSERT_STATEMENT = "INSERT INTO transactions (" + "    transaction_date,"
			+ "    user_id," + "    user_username," + "    user_first_name," + "    user_last_name,"
			+ "    user_country," + "    user_email," + "    user_phone_number," + "    ticket_id,"
			+ "    ticket_airline_company," + "    ticket_flight_number," + "    ticket_from_location,"
			+ "    ticket_from_airport," + "    ticket_to_location," + "    ticket_to_airport," + "    ticket_class,"
			+ "    ticket_price)" + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	public static void insertTransaction(User user, Ticket ticket) {
		try (Connection conn = DBConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(INSERT_STATEMENT);) {

			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date systemDate = new Date();

			String formattedSystemDate = dateFormat.format(systemDate);

			stmt.setString(1, formattedSystemDate);
			stmt.setInt(2, user.getId());
			stmt.setString(3, user.getUsername());
			stmt.setString(4, user.getFirstName());
			stmt.setString(5, user.getLastName());
			stmt.setString(6, user.getCountry());
			stmt.setString(7, user.getEmail());
			stmt.setString(8, user.getPhoneNumber());
			stmt.setInt(9, ticket.getId());
			stmt.setString(10, ticket.getAirline_company());
			stmt.setString(11, ticket.getFlight_number());
			stmt.setString(12, ticket.getFrom_location());
			stmt.setString(13, ticket.getFrom_airport());
			stmt.setString(14, ticket.getTo_location());
			stmt.setString(15, ticket.getTo_airport());
			stmt.setString(16, ticket.getClass_selection());
			stmt.setDouble(17, ticket.getPrice());

			stmt.executeUpdate();
		} catch (SQLException ex) {
			System.out.println("SQL Exception!");
		}
	}
}
