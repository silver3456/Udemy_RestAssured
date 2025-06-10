package com.udemy.oauth;

import com.udemy.pojo.GetCourse;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.given;

public class OAuthTest {

    public static void main(String[] args) {
        RestAssured.baseURI = "https://rahulshettyacademy.com";


        //get access token
        String response = given()
                .formParams("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
                .formParams("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
                .formParams("grant_type", "client_credentials")
                .formParams("scope", "trust")
                .when().log().all()
                .post("/oauthapi/oauth2/resourceOwner/token").asString();

        System.out.println(response);

        JsonPath js = new JsonPath(response);
        String accessToken = js.getString("access_token");

        //get courses
        GetCourse gc = given()
                .queryParams("access_token", accessToken)
                .when().log().all()
                .get("https://rahulshettyacademy.com/oauthapi/getCourseDetails")
                .then().assertThat().log().all().extract().as(GetCourse.class);


        String linkedIn = gc.getLinkedIn();
        System.out.println(linkedIn);


    }
}
