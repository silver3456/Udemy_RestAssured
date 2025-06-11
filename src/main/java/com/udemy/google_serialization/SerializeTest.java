package com.udemy.google_serialization;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.util.List;

import static io.restassured.RestAssured.*;

public class SerializeTest {
    public static void main(String[] args) {

        RestAssured.baseURI = "https://rahulshettyacademy.com";



        AddPlace place = new AddPlace();
        place.setAccuracy(50);
        place.setAddress("32, side layout, cohen 09");
        place.setName("Frontline house");
        place.setPhoneNumber("(+91) 983 893 3937");
        place.setWebsite("http://google.com");
        place.setLanguage("French-IN");
        place.setTypes(List.of("shoe park", "shop"));

        Location location = new Location();
        location.setLat(-38.383494);
        location.setLng(33.427362);

        place.setLocation(location);


        Response res = given().queryParams("key", "qaclick123")
                .body(place)
                .when()
                .post("/maps/api/place/add/json")
                .then()
                .assertThat().statusCode(200).extract().response();

        String responseString = res.asString();
        System.out.println(responseString);
    }
}
