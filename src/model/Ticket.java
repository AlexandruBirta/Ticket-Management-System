package model;

public class Ticket {
	private int id;
	private String airline_company_code;
	private String flight_number;
	private String airline_company;
	private String from_location;
	private String from_airport;
	private String to_location;
	private String to_airport;
	private String boarding;
	private String departure;
	private String arrival;
	private String class_selection;
	private String gate;
	private double price;
	private int return_option;
	private int one_way_option;

	public Ticket(int id, String airline_company_code, String flight_number, String airline_company,
			String from_location, String from_airport, String to_location, String to_airport, String boarding,
			String departure, String arrival, String class_selection, String gate, double price, int return_option,
			int one_way_option) {
		this.id = id;
		this.airline_company_code = airline_company_code;
		this.flight_number = flight_number;
		this.airline_company = airline_company;
		this.from_location = from_location;
		this.from_airport = from_airport;
		this.to_location = to_location;
		this.to_airport = to_airport;
		this.boarding = boarding;
		this.departure = departure;
		this.arrival = arrival;
		this.class_selection = class_selection;
		this.gate = gate;
		this.price = price;
		this.return_option = return_option;
		this.one_way_option = one_way_option;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAirline_company_code() {
		return airline_company_code;
	}

	public void setAirline_company_code(String airline_company_code) {
		this.airline_company_code = airline_company_code;
	}

	public String getFlight_number() {
		return flight_number;
	}

	public void setFlight_number(String flight_number) {
		this.flight_number = flight_number;
	}

	public String getAirline_company() {
		return airline_company;
	}

	public void setAirline_company(String airline_company) {
		this.airline_company = airline_company;
	}

	public String getFrom_location() {
		return from_location;
	}

	public void setFrom_location(String from_location) {
		this.from_location = from_location;
	}

	public String getFrom_airport() {
		return from_airport;
	}

	public void setFrom_airport(String from_airport) {
		this.from_airport = from_airport;
	}

	public String getTo_location() {
		return to_location;
	}

	public void setTo_location(String to_location) {
		this.to_location = to_location;
	}

	public String getTo_airport() {
		return to_airport;
	}

	public void setTo_airport(String to_airport) {
		this.to_airport = to_airport;
	}

	public String getBoarding() {
		return boarding;
	}

	public void setBoarding(String boarding) {
		this.boarding = boarding;
	}

	public String getDeparture() {
		return departure;
	}

	public void setDeparture(String departure) {
		this.departure = departure;
	}

	public String getArrival() {
		return arrival;
	}

	public void setArrival(String arrival) {
		this.arrival = arrival;
	}

	public String getClass_selection() {
		return class_selection;
	}

	public void setClass_selection(String class_selection) {
		this.class_selection = class_selection;
	}

	public String getGate() {
		return gate;
	}

	public void setGate(String gate) {
		this.gate = gate;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getReturn_option() {
		return return_option;
	}

	public void setReturn_option(int return_option) {
		this.return_option = return_option;
	}

	public int getOne_way_option() {
		return one_way_option;
	}

	public void setOne_way_option(int one_way_option) {
		this.one_way_option = one_way_option;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((airline_company == null) ? 0 : airline_company.hashCode());
		result = prime * result + ((airline_company_code == null) ? 0 : airline_company_code.hashCode());
		result = prime * result + ((arrival == null) ? 0 : arrival.hashCode());
		result = prime * result + ((boarding == null) ? 0 : boarding.hashCode());
		result = prime * result + ((class_selection == null) ? 0 : class_selection.hashCode());
		result = prime * result + ((departure == null) ? 0 : departure.hashCode());
		result = prime * result + ((flight_number == null) ? 0 : flight_number.hashCode());
		result = prime * result + ((from_airport == null) ? 0 : from_airport.hashCode());
		result = prime * result + ((from_location == null) ? 0 : from_location.hashCode());
		result = prime * result + ((gate == null) ? 0 : gate.hashCode());
		result = prime * result + id;
		result = prime * result + one_way_option;
		long temp;
		temp = Double.doubleToLongBits(price);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + return_option;
		result = prime * result + ((to_airport == null) ? 0 : to_airport.hashCode());
		result = prime * result + ((to_location == null) ? 0 : to_location.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ticket other = (Ticket) obj;
		if (airline_company == null) {
			if (other.airline_company != null)
				return false;
		} else if (!airline_company.equals(other.airline_company))
			return false;
		if (airline_company_code == null) {
			if (other.airline_company_code != null)
				return false;
		} else if (!airline_company_code.equals(other.airline_company_code))
			return false;
		if (arrival == null) {
			if (other.arrival != null)
				return false;
		} else if (!arrival.equals(other.arrival))
			return false;
		if (boarding == null) {
			if (other.boarding != null)
				return false;
		} else if (!boarding.equals(other.boarding))
			return false;
		if (class_selection == null) {
			if (other.class_selection != null)
				return false;
		} else if (!class_selection.equals(other.class_selection))
			return false;
		if (departure == null) {
			if (other.departure != null)
				return false;
		} else if (!departure.equals(other.departure))
			return false;
		if (flight_number == null) {
			if (other.flight_number != null)
				return false;
		} else if (!flight_number.equals(other.flight_number))
			return false;
		if (from_airport == null) {
			if (other.from_airport != null)
				return false;
		} else if (!from_airport.equals(other.from_airport))
			return false;
		if (from_location == null) {
			if (other.from_location != null)
				return false;
		} else if (!from_location.equals(other.from_location))
			return false;
		if (gate == null) {
			if (other.gate != null)
				return false;
		} else if (!gate.equals(other.gate))
			return false;
		if (id != other.id)
			return false;
		if (one_way_option != other.one_way_option)
			return false;
		if (Double.doubleToLongBits(price) != Double.doubleToLongBits(other.price))
			return false;
		if (return_option != other.return_option)
			return false;
		if (to_airport == null) {
			if (other.to_airport != null)
				return false;
		} else if (!to_airport.equals(other.to_airport))
			return false;
		if (to_location == null) {
			if (other.to_location != null)
				return false;
		} else if (!to_location.equals(other.to_location))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Ticket [id=" + id + ", airline_company_code=" + airline_company_code + ", flight_number="
				+ flight_number + ", airline_company=" + airline_company + ", from_location=" + from_location
				+ ", from_airport=" + from_airport + ", to_location=" + to_location + ", to_airport=" + to_airport
				+ ", boarding=" + boarding + ", departure=" + departure + ", arrival=" + arrival + ", class_selection="
				+ class_selection + ", gate=" + gate + ", price=" + price + ", return_option=" + return_option
				+ ", one_way_option=" + one_way_option + "]";
	}

}
