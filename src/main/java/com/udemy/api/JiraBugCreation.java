package com.udemy.api;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

public class JiraBugCreation {
    public static void main(String[] args) {

        RestAssured.baseURI = "https://alexsomov2020.atlassian.net";

        String createIssueResonse = given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Basic YWxleC5zb21vdjIwMjBAZ21haWwuY29tOkFUQVRUM3hGZkdGMHZ1TUYxb2VPcm02THFIZWgxc202TFhxMkIycFk1Nk5PTldCOFd5V1JDWlRWWlBkS0djaWhyUVk0LUNyLWhTVlZ0ZHdaQUtCTlphR0l0MEZ1cTQzX3hGLU16Wm12SWxOZWRNTFpUYjRFZnJLdFpNSHBJeEdGRDNCZlhkcS1BU0Z3Z3dUOXowdFUwakJzdlhZWUtydFNndzg5U0Exd2p3NlJVVGRpWEhETFM4RT1CRTVEMURERA==")
                .body("{\n" +
                        "    \"fields\": {\n" +
                        "        \"project\": {\n" +
                        "            \"key\":\"AJ\"\n" +
                        "        },\n" +
                        "        \"summary\": \"Links are not working - automation\",\n" +
                        "        \"issuetype\": {\n" +
                        "            \"name\":\"Bug\"\n" +
                        "        }\n" +
                        "    }\n" +
                        "}")
                .log().all()
                .post("/rest/api/3/issue")
                .then().assertThat().statusCode(201)
                .extract().response().asString();

        JsonPath js = new JsonPath(createIssueResonse);
        String issueId = js.getString("id");
        System.out.println("Issue id:: " + issueId);

    }
}
