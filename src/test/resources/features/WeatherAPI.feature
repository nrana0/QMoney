
Feature: This file contains scenarios for weather APIs
 
  @api @city
  Scenario Outline: As a frequent flyer , I want to get current weather data for multiple cities in the world
   Given I am a frequent flyer interest in to current weather data for multiple cities 
   When I click invoke weather api to get data with multiple cities id "<cityId>"
   Then I get response back for city "<city_name>"
   Examples:
   |city_name| cityId|
   |Hornsby  |2163137|
   
  
  @api @latAndLon
   Scenario Outline: As a frequent flyer , I want to get current weather data for the city at given latitude and longitude
   Given I am a frequent flyer interest in to current weather data for multiple cities 
   When I click invoke weather api to get data with given latitude "<lat>" and longitude "<long>"
   Then I get response back with city "<city_name>" and given "<lat>" and "<long>"
   Examples:
   |lat         |  long    | city_name|
   |-33.865143  |151.209900| Sydney   |
   