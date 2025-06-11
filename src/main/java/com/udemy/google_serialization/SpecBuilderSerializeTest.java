package com.udemy.google_serialization;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import java.util.List;

import static io.restassured.RestAssured.given;

public class SpecBuilderSerializeTest {
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

        RequestSpecification req = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").addQueryParam("key", "qaclick123")
                .setContentType(ContentType.JSON).build();

        ResponseSpecification rs = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();

        RequestSpecification res = given().spec(req).body(place);

        Response response = res.when().post("/maps/api/place/add/json")
                .then().spec(rs).extract().response();

        String responseString = response.asString();
        System.out.println(responseString);
    }
}
