package controller;

import java.util.ArrayList;
import java.util.Locale;

public class GenerateCountries {

	public static ArrayList<String> getCountryList() {
		ArrayList<String> countries = new ArrayList<>();

		String[] countryCodes = Locale.getISOCountries();

		for (String countryCode : countryCodes) {

			Locale obj = new Locale("", countryCode);

			countries.add(obj.getDisplayCountry());

		}
		return countries;
	}

}
