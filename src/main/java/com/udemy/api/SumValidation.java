package com.udemy.api;

import com.udemy.files.Payload;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SumValidation {


    // Verify if Sum of all courses price matches with Purchase Amount
    @Test
    public void sumOfCourses() {
        JsonPath js = new JsonPath(Payload.coursePrice());
        int sumOfCourses = 0;
        int coursesCount = js.getInt("courses.size()");
        for (int i = 0; i < coursesCount; i++) {
            int coursePrice = js.getInt("courses[" + i + "].price");
            int courseCopies = js.getInt("courses[" + i + "].copies");
            int courseTotalPrice = coursePrice * courseCopies;
            sumOfCourses += courseTotalPrice;
        }
        int purchaseAmount = js.getInt("dashboard.purchaseAmount");
        Assert.assertEquals(sumOfCourses, purchaseAmount, "Numbers do not match");
    }
}
