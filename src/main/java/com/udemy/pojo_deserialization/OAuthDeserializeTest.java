package com.udemy.pojo_deserialization;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import java.util.List;

import static io.restassured.RestAssured.given;

public class OAuthDeserializeTest {

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

        // Get course title
        String courseTitle = gc.getCourses().getApi().get(1).getCourseTitle();
        System.out.println(courseTitle);

        // Get course price without using index get(1) since it might change in the future
        List<Api> apiList = gc.getCourses().getApi();
        String coursePrice = null;
        for (Api api : apiList) {
            if (api.getCourseTitle().equalsIgnoreCase("SoapUI Webservices testing")) {
                coursePrice = api.getPrice();
                break;
            }
        }
        System.out.println(coursePrice);

        // Get price using stream
        String price = apiList.stream()
                .filter(s ->
                        s.getCourseTitle().equalsIgnoreCase("SoapUI Webservices testing"))
                .findFirst()
                .map(Api::getPrice)
                .orElse(null);
        System.out.println(price);


    }
}
