package testing;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import com.mysql.cj.util.StringUtils;

import model.DBConnection;
import model.Ticket;
import view.AdminPanel;

@RunWith(Parameterized.class)
public class AdminPanelTest {

	private static final String SELECT_TICKETS_STATEMENT = "SELECT * FROM tickets";

	@Parameter(0)
	public Ticket testTicket;

	@Parameters
	public static ArrayList<Ticket> data() {

		List<Ticket> ticketList = new ArrayList<Ticket>();

		ResultSet rs = null;

		try (Connection conn = DBConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(SELECT_TICKETS_STATEMENT,
						ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);) {

			rs = stmt.executeQuery();

			while (rs.next()) {
				final Ticket ticket = new Ticket(rs.getInt("id"), rs.getString("airline_company_code"),
						rs.getString("flight_number"), rs.getString("airline_company"), rs.getString("from_location"),
						rs.getString("from_airport"), rs.getString("to_location"), rs.getString("to_airport"),
						rs.getString("boarding"), rs.getString("departure"), rs.getString("arrival"),
						rs.getString("class"), rs.getString("gate"), rs.getDouble("price"),
						rs.getInt("return_option"), rs.getInt("one_way_option"));

				ticketList.add(ticket);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return (ArrayList<Ticket>) ticketList;
	}

	@Test
	public void validTicketCompanyCodeTest() {
		Assert.assertTrue(StringUtils.isStrictlyNumeric(testTicket.getAirline_company_code()));

	}

	@Test
	public void validTicketFlightNumberTest() {
		Assert.assertTrue(StringUtils.isStrictlyNumeric(testTicket.getFlight_number()));
	}

	@Test
	public void validTicketAirportsTest() {
		boolean airportCheck = false;

		if (AdminPanel.isAlpha(testTicket.getFrom_airport()) && AdminPanel.isAlpha(testTicket.getTo_airport()))
			airportCheck = true;

		Assert.assertTrue(airportCheck);
	}

	@Test
	public void validTicketPriceTest() {
		Assert.assertTrue(testTicket.getPrice() != 0.0);
	}

	@Test
	public void validTicketDatesTest() {
		boolean datesCheck = false;
		Date boardingCheck;
		Date departureCheck;
		Date arrivalCheck;

		try {
			boardingCheck = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(testTicket.getBoarding());
			departureCheck = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(testTicket.getDeparture());
			arrivalCheck = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(testTicket.getArrival());

			if (boardingCheck.before(departureCheck) && departureCheck.before(arrivalCheck))
				datesCheck = true;
		} catch (ParseException e) {
			e.printStackTrace();
		}

		Assert.assertTrue(datesCheck);
	}

}
