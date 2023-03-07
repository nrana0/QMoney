package com.qmoney.impl.api;

import java.util.*;
import java.util.logging.Logger;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

import com.qmoney.util.RestInvocationUtil;

/***
 * This class contains implementation steps for weatherapi.feature file
 */

public class WeatherAPI {

    Logger logger = Logger.getLogger(WeatherAPI.class.getName());
    RestInvocationUtil rest = new RestInvocationUtil();
    String API_KEY = "f82991de0bf348a98c93478176e3df6d";
    Map<String, String> mapResponse = new HashMap<String, String>();
    Map<String, String> mapCapitalCities = new HashMap<String, String>();
    List lstCityIds = new ArrayList<String>();

    @Given("I am a frequent flyer interested in to current weather data for multiple cities")
    public void i_am_a_frequent_flyer_interest_in_to_current_weather_data_for_multiple_cities() throws Throwable {
        // Write code here that turns the phrase above into concrete actions

    }

    @When("I invoke weather api to get data with multiple cities id {string}")
    public void i_click_invoke_weather_api_to_get_data_with_multiple_cities(String cityId) throws Throwable {
        mapResponse = rest.invoke("https://api.weatherbit.io/v2.0/current?city_id=" + cityId + "&key=" + API_KEY + "&include=minutely", null, false);
        logger.info("RESPONSE : " + mapResponse);
    }

    @Then("I get response back for city {string}")
    public void i_get_response_back(String cityName) throws Throwable {
        String countFromResponse = rest.getJsonObjectValue(mapResponse.get("response"), "count");
        String cityNameFromResponse = rest.getJsonArrayValue(mapResponse.get("response"), "data", 0, "city_name");

        Assert.assertTrue(Integer.parseInt(countFromResponse) > 0);

        Assert.assertTrue(cityNameFromResponse.equals(cityName));
    }


    @When("I invoke weather api to get data with given latitude {string} and longitude {string}")
    public void i_click_invoke_weather_api_to_get_data_with_given_latitude_and_longitude(String lat, String lon) throws Throwable {

        mapResponse = rest.invoke("https://api.weatherbit.io/v2.0/current?lat=" + lat + "&lon=" + lon + "&key=" + API_KEY, null, false);
        logger.info("RESPONSE : " + mapResponse);
    }

    @Then("I get response back with city {string} and given {string} and {string}")
    public void i_get_response_back_with_city_and_given_and(String cityName, String lat, String lon) throws Throwable {
        String countFromResponse = rest.getJsonObjectValue(mapResponse.get("response"), "count");
        String cityNameFromResponse = rest.getJsonArrayValue(mapResponse.get("response"), "data", 0, "city_name");
        String cityLatFromResponse = rest.getJsonArrayValue(mapResponse.get("response"), "data", 0, "lat");
        String cityLongFromResposnse = rest.getJsonArrayValue(mapResponse.get("response"), "data", 0, "lon");

        Assert.assertTrue(Integer.parseInt(countFromResponse) > 0);
        Assert.assertTrue(cityNameFromResponse.equals(cityName));
        Assert.assertTrue(lat.contains(cityLatFromResponse));
        Assert.assertTrue(lon.contains(cityLongFromResposnse));

    }

    @When("I invoke weather api to get data for australian capital cities")
    public void i_invoke_weather_api_to_get_data_australian_capital_cities(DataTable table) throws Throwable {

        String cityIds = "";
        for (Map<String, String> mapData : table.asMaps()) {
            for (Map.Entry<String, String> entry : mapData.entrySet()) {
                if (entry.getKey().equals("city_id")) {
                    cityIds = cityIds + entry.getValue() + ",";
                    lstCityIds.add(entry.getValue());
                }
            }
        }
        cityIds = cityIds.substring(0, cityIds.length() - 1);
        System.out.println("CITY IDS : " + cityIds);
        mapResponse = rest.invoke("https://api.weatherbit.io/v2.0/current?cities=" + cityIds + "&key=" + API_KEY, null, false);
        logger.info("RESPONSE : " + mapResponse);
    }


    @Then("I get response back for all cities and finds hottest capital city")
    public void i_get_response_back_for_all_cities() throws Throwable {
        String countFromResponse = rest.getJsonObjectValue(mapResponse.get("response"), "count");
        Assert.assertTrue(Integer.parseInt(countFromResponse) == lstCityIds.size());

        Set<Double> setTempForCities = new TreeSet<Double>();
        for (int i = 0; i < lstCityIds.size(); i++) {

            String cityNameFromResponse = rest.getJsonArrayValue(mapResponse.get("response"), "data", i, "city_name");
            String cityTempFromResponse = rest.getJsonArrayValue(mapResponse.get("response"), "data", i, "app_temp");
            setTempForCities.add(Double.parseDouble(cityTempFromResponse));
            mapCapitalCities.put(cityTempFromResponse, cityNameFromResponse);
        }
        Object lastElement = null;

        for (Iterator collectionItr = setTempForCities.iterator(); collectionItr.hasNext(); ) {
            lastElement = collectionItr.next();
        }
        logger.info("Cities : " + mapCapitalCities);
        logger.info("Highest Temperature capital city is : " + mapCapitalCities.get(lastElement.toString()));


    }



    @When("I invoke weather api to get data for australian capital cities {string}")
    public void i_invoke_weather_api_to_get_data_australian_capital_cities(String captialCities) throws Throwable {

        String[] arrOfStr = captialCities.split(",");

        for (String city_id : arrOfStr)
            lstCityIds.add(city_id);

        logger.info("List of City IDs : " + lstCityIds);
        mapResponse = rest.invoke("https://api.weatherbit.io/v2.0/current?cities=" + captialCities + "&key=" + API_KEY, null, false);
        logger.info("RESPONSE : " + mapResponse);
    }

}
