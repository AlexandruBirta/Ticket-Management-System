package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import model.DBConnection;

public class InsertFlight {

	private static final String INSERT_STATEMENT = "INSERT INTO tickets" + "(" + "airline_company_code,"
			+ "flight_number," + "airline_company," + "from_location," + "from_airport," + "to_location,"
			+ "to_airport," + "boarding," + "departure," + "arrival," + "class," + "gate," + "price," + "return_option,"
			+ "one_way_option)" + "VALUES" + "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

	public static void insertFlight(String airline_company_code, String flight_number, String airline_company,
			String from_location, String from_airport, String to_location, String to_airport, String boarding,
			String departure, String arrival, String classSelection, String gate, double price, int return_option,
			int one_way_option) {

		try (Connection conn = DBConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(INSERT_STATEMENT);) {

			stmt.setString(1, airline_company_code);
			stmt.setString(2, flight_number);
			stmt.setString(3, airline_company);
			stmt.setString(4, from_location);
			stmt.setString(5, from_airport);
			stmt.setString(6, to_location);
			stmt.setString(7, to_airport);
			stmt.setString(8, boarding);
			stmt.setString(9, departure);
			stmt.setString(10, arrival);
			stmt.setString(11, classSelection);
			stmt.setString(12, gate);
			stmt.setDouble(13, price);
			stmt.setInt(14, return_option);
			stmt.setInt(15, one_way_option);

			stmt.executeUpdate();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}
}
