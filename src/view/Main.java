package view;

import javax.swing.SwingUtilities;

import model.Ticket;
import model.User;

public class Main {

	public static void main(String[] args) throws Exception {

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new WelcomeScreen();
				
				//new Login();
				
				//new Reports();
				
				//new SearchFlights(new User(0, "Alex", null, "Alex", "Popescu",
				//null, null, null, 0, null));
				
				//new FlightSearchResults(true, new User(0, "Alex", null, "Alex", "Popescu",
				//null, null, null, 0, null), "Romania", "Andorra", "2020-05-02",
				// "2020-05-03", true, false,"First");
				
				//new AdminPanel();
				
				// new BoardingPass(
				// new Ticket(5,"512", "61251", "Tarom", "Romania", "AYW", "Andorra", "YAW",
				// "2020-04-24 18:21:13",
				// "2020-03-02 13:39:36", "2020-01-31 23:21:53", "Premium Economy", "5B", 240.0,
				// 1, 0), new User(0, "Alex", null, "Alex", "Popescu", null, null, null, 0,
				// null));
			}
		});

	}
}
