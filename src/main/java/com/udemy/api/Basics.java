package com.udemy.api;

import com.udemy.files.Payload;
import com.udemy.files.ReusableMethods;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class Basics {
    public static void main(String[] args) {
        RestAssured.baseURI = "https://rahulshettyacademy.com";

        //given - all input details
        //chain methods in the same order as in Postman

        // when - Submit the API
        // resource, http method under 'when'
        // post() accepts resource name. In our case is 'maps/api/place/add/json'

        //then - validate the response

        // Scenario: Add place -> Update place with new address -> Get Place to validate if new address is present in response
        String response = given().log().all().queryParam("key", "qaclick5678").header("Content-Type", "application/json")
                .body(Payload.addPlace())
                .when().post("maps/api/place/add/json")
                .then().assertThat().statusCode(200).body("scope", equalTo("APP"))
                .header("server", "Apache/2.4.52 (Ubuntu)").extract().response().asString();

        System.out.println(response);

        // for parsing json
        JsonPath js = new JsonPath(response);
        String placeId = js.getString("place_id");

        System.out.println(placeId);

        // Update place

        String newAddress = "Trefoleva 12";

        given().log().all().queryParam("key", "qaclick5678").header("Content-Type", "application/json")
                .body("{\n" +
                        "\"place_id\":\"" + placeId + "\",\n" +
                        "\"address\":\"" + newAddress + "\",\n" +
                        "\"key\":\"qaclick5678\"\n" +
                        "}")
                .when().put("maps/api/place/update/json")
                .then().assertThat().log().all().statusCode(200).body("msg", equalTo("Address successfully updated"));


        // Get place
        // Do not put header because in GET and DELETE it is not needed

        String getPlaceResponse = given().log().all().queryParam("key", "qaclick5678")
                .queryParam("place_id", placeId)
                .when().get("maps/api/place/get/json")
                .then().assertThat().log().all().statusCode(200).extract().asString();

        JsonPath js1 = ReusableMethods.rawToJson(getPlaceResponse);
        String actualAddress = js1.getString("address");
        System.out.println("Actual address:: " + actualAddress);
        Assert.assertEquals(actualAddress, newAddress);


    }


}