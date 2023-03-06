package com.qmoney.impl.api;

import java.util.HashMap;
import java.util.Map;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

import com.qmoney.util.RestInvocationUtil;

public class WeatherAPI {

	RestInvocationUtil rest = new RestInvocationUtil();
	String API_KEY = "f82991de0bf348a98c93478176e3df6d";
	Map<String, String> mapResponse = new HashMap<String, String>();

	@Given("I am a frequent flyer interest in to current weather data for multiple cities")
	public void i_am_a_frequent_flyer_interest_in_to_current_weather_data_for_multiple_cities() throws Throwable {
		// Write code here that turns the phrase above into concrete actions

	}

	@When("I click invoke weather api to get data with multiple cities id {string}")
	public void i_click_invoke_weather_api_to_get_data_with_multiple_cities(String cityId) throws Throwable {
		mapResponse = rest.invoke(
				"https://api.weatherbit.io/v2.0/current?city_id=" + cityId + "&key=" + API_KEY + "&include=minutely",
				null, false);
		System.out.println("RESPONSE : " + mapResponse);
	}

	@Then("I get response back for city {string}")
	public void i_get_response_back(String cityName) throws Throwable {
		String countFromResponse = rest.getJsonObjectValue(mapResponse.get("response"), "count");
		String cityNameFromResponse = rest.getJsonArrayValue(mapResponse.get("response"), "data", "city_name");

		Assert.assertTrue(Integer.parseInt(countFromResponse) > 0);

		Assert.assertTrue(cityNameFromResponse.equals(cityName));
	}

	
	@When("I click invoke weather api to get data with given latitude {string} and longitude {string}")
	public void i_click_invoke_weather_api_to_get_data_with_given_latitude_and_longitude(String lat, String lon)
			throws Throwable {

		mapResponse = rest.invoke("https://api.weatherbit.io/v2.0/current?lat=" + lat + "&lon=" + lon 
				+ "&key=" + API_KEY, null, false);
		System.out.println("RESPONSE : " + mapResponse);
	}
	
	@Then("I get response back with city {string} and given {string} and {string}")
	public void i_get_response_back_with_city_and_given_and(String cityName, String lat, String lon) throws Throwable {
		String countFromResponse = rest.getJsonObjectValue(mapResponse.get("response"), "count");
		String cityNameFromResponse = rest.getJsonArrayValue(mapResponse.get("response"), "data", "city_name");
		String cityLatFromResponse = rest.getJsonArrayValue(mapResponse.get("response"), "data", "lat");
		String cityLongFromResposnse = rest.getJsonArrayValue(mapResponse.get("response"), "data", "lon");
	 
		Assert.assertTrue(Integer.parseInt(countFromResponse) > 0);

		Assert.assertTrue(cityNameFromResponse.equals(cityName));
		Assert.assertTrue(lat.contains(cityLatFromResponse));
		Assert.assertTrue(lon.contains(cityLongFromResposnse));
		
	}

}
