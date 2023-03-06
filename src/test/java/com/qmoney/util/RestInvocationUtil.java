package com.qmoney.util;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import io.restassured.RestAssured;
import io.restassured.config.SSLConfig;

import io.restassured.response.Response;

public class RestInvocationUtil {
    Map<String, String> mapWebServiceResponse = new HashMap<String, String>();

    public Map<String, String> invoke(String endPoint, String jsonString,
                                      boolean isInput) {
        Response response;
        if (isInput) {
            response = postRestWithBody(endPoint, jsonString);
        } else
            response = postRestWithNoBody(endPoint);

        String strResponse = response.getBody().asString();
        mapWebServiceResponse.put("response", strResponse);
        mapWebServiceResponse.put("statusCode", Integer.toString(0));
        mapWebServiceResponse.put("ContentType", response.getContentType());

        return mapWebServiceResponse;

    }

    public Response postRestWithBody(String endPoint, String jsonString) {
        return RestAssured
                .given()
                .config(RestAssured.config.sslConfig(new SSLConfig()
                        .relaxedHTTPSValidation()))
                .contentType("application.json").header("Authorization", "")
                .body(jsonString).when().post(endPoint).then().extract()
                .response();
    }

    public Response postRestWithNoBody(String endPoint) {
        return RestAssured
                .given()
                .config(RestAssured.config.sslConfig(new SSLConfig()
                        .relaxedHTTPSValidation()))
                .contentType("application.json").header("Authorization", "")
                .when().post(endPoint).then().extract().response();
    }

    @SuppressWarnings("unused")
    public String getJsonObjectValue(String jsonContent, String valueToRead) {
        try {
            @SuppressWarnings("deprecation")
            JsonObject jsonObject = new JsonParser().parse(jsonContent).getAsJsonObject();
            return jsonObject.get(valueToRead).getAsString();
        } catch (Exception exp) {
            System.out.print("Exception : " + exp);
            exp.printStackTrace();
        }
        return null;
    }

    @SuppressWarnings("unused")
    public String getJsonArrayValue(String jsonContent, String arrayValue, String valueToRead) {
        String reponse = null;
        try {
            JsonParser parser = new JsonParser();
            JsonObject rootObj = parser.parse(jsonContent).getAsJsonObject();
            JsonArray records = rootObj.getAsJsonArray(arrayValue);

            for (JsonElement element : records) {

                JsonObject weatherElement = element.getAsJsonObject();

                reponse = weatherElement.get(valueToRead).getAsString();
            }
        } catch (Exception exp) {
            System.out.print("Exception : " + exp);
            exp.printStackTrace();
        }
        return reponse;
    }

    public Object getResponse(Object obj, String strRsponse) {

        ObjectMapper objectMapper = new ObjectMapper();

        objectMapper.configure(
                DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(
                DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        try {
            return objectMapper.readValue(strRsponse,
                    obj.getClass());
        } catch (JsonParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JsonMappingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;

    }
}

