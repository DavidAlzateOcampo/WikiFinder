package co.edu.uniandes.wiki.filter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class CountryFinder {

	HashMap<String, String> cityCountry;
	ArrayList<String> cities;

	/**
	 * @param args
	 */

	public CountryFinder() {
		this.cities = new ArrayList<String>();
		this.cityCountry = new HashMap<String, String>();
		BufferedReader br = null;

		try {
			String sCurrentLine;
			br = new BufferedReader(new FileReader("capitalCities.csv"));
			while ((sCurrentLine = br.readLine()) != null) {
				String line = sCurrentLine.split(",")[1];
				String country = sCurrentLine.split(",")[2];
				this.cities.add(line);
				this.cityCountry.put(line, country);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	public String filter(String lineInput, String CountryIn) throws IOException {
		// TODO Auto-generated method stub
		/* reads the cities.csv file and save them into a list */
		String countries = "";
		boolean countryFound = false;

		for (int i = 0; i < cities.size(); i++) {
			try {
				CharSequence cityValue = cities.get(i);
				String countryValue = (String) cityCountry.get(cities.get(i));
				if (lineInput.contains(cityValue)
						|| lineInput.contains(cityValue)) {
					if (CountryIn.contains(countryValue)) {
						countryFound = true;
					}
					if (countries.length() < 1) {
						countries = countryValue;
					} else {
						countries = countries + "," + countryValue;
					}
				} else {
					continue;
				}
			} catch (Exception e) {
				continue;
			}
		}

		if (countryFound == false) {
			countries = "";
		}
		return countries;
	}
}
