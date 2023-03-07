@api
Feature: This file contains scenarios for weather APIs

  @city @AC1
  Scenario Outline: As a frequent flyer , I want to get current weather data for multiple cities in the world
    Given I am a frequent flyer interested in to current weather data for multiple cities
    When I invoke weather api to get data with multiple cities id "<cityId>"
    Then I get response back for city "<city_name>"
    Examples:
      | city_name | cityId  |
      | Hornsby   | 2163137 |


  @latAndLon @AC2
  Scenario Outline: As a frequent flyer , I want to get current weather data for the city at given latitude and longitude
    Given I am a frequent flyer interested in to current weather data for multiple cities
    When I invoke weather api to get data with given latitude "<lat>" and longitude "<long>"
    Then I get response back with city "<city_name>" and given "<lat>" and "<long>"
    Examples:
      | lat        | long       | city_name |
      | -33.865143 | 151.209900 | Sydney    |


  @capitalcities @AC3
  Scenario: As a frequent flyer , I want to get current warmest capital city in australia
    Given I am a frequent flyer interested in to current weather data for multiple cities
    When I invoke weather api to get data for australian capital cities
      | city_id | city_name |
      | 2147714 | Sydney    |
      | 2172517 | Canberra  |
      | 2174003 | Brisbane  |
      | 2078025 | Adelaide  |
      | 2158177 | Melbourne |
      | 2153391 | Perth     |
      | 2163355 | Hobart    |
    Then I get response back for all cities and finds hottest capital city


  @AC3 @capitalcities
  Scenario Outline: As a frequent flyer , I want to get current warmest capital city using specification by example
    Given I am a frequent flyer interested in to current weather data for multiple cities
    When I invoke weather api to get data for australian capital cities "<cities_id>"
    Then I get response back for all cities and finds hottest capital city
    Examples:
      | cities_name                                              | cities_id                                               |
      | Sydney,Canberra,Brisbane,Adelaide,Melbourne,Perth,Hobart | 2147714,2172517,2174003,2078025,2158177,2153391,2163355 |

