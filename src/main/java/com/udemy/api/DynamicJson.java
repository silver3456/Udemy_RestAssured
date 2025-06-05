package com.udemy.api;

import com.udemy.files.Payload;
import com.udemy.files.ReusableMethods;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;

public class DynamicJson {

    @Test(dataProvider = "booksData")
    public void addBook(String isbn, String aisle) {
        RestAssured.baseURI = "https://rahulshettyacademy.com";

        String message = given().header("Content-Type", "application/json")
                .body(Payload.addBook(isbn, aisle))
                .when().post("/Library/Addbook.php")
                .then().log().all().assertThat().statusCode(200)
                .extract().response().asString();

        JsonPath js = ReusableMethods.rawToJson(message);
        String id = js.getString("ID");
        System.out.println(id);
    }

    @DataProvider(name = "booksData")
    public Object[][]getData() {
        return new Object[][]{
                {"fdfdf", "9859"},
                {"fgdf", "5445"},
                {"fdfd", "3354"},
        };
    }
}
