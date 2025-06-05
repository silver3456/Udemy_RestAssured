package com.udemy.api;

import com.udemy.files.Payload;
import io.restassured.path.json.JsonPath;

import java.util.HashMap;
import java.util.Map;

public class ComplexJsonParse {
    public static void main(String[] args) {

        JsonPath js = new JsonPath(Payload.coursePrice());

        // Print number of courses returned by API
        // Since courses is a collection that's why we use size()
        int count = js.getInt("courses.size()");
        System.out.println(count);

        // Print purchase amount
        int amount = js.getInt("dashboard.purchaseAmount");
        System.out.println(amount);

        // Print title of the first course
        String title = js.getString("courses[0].title");
        System.out.println(title);

        // Print all course titles and their respective prices
        Map<String, Integer> map = new HashMap<>();
        int coursesCount = js.getInt("courses.size()");
        for (int i = 0; i < coursesCount; i++) {
            String courseTitle = js.getString("courses[" + i + "].title");
            int price = js.getInt("courses[" + i + "].price");
            map.put(courseTitle, price);
        }
        System.out.println(map);

        // Print number of copies sold by RPA Course
        for (int i = 0; i < coursesCount; i++) {
            String courseTitle = js.getString("courses[" + i + "].title");
            if (courseTitle.equalsIgnoreCase("RPA")) {
                int numOfCopies = js.getInt("courses[" + i + "].copies");
                System.out.println("RPA copies:: " + numOfCopies);
                break;
            }
        }

        // Verify if Sum of all courses price matches with Purchase Amount
        int purchaseAmount = 0;
        for (int i = 0; i < coursesCount; i++) {
            int coursePrice = js.getInt("courses[" + i + "].price");
            int courseCopies = js.getInt("courses[" + i + "].copies");
            int courseTotalPrice = coursePrice * courseCopies;
            purchaseAmount += courseTotalPrice;
        }
        System.out.println("Total amount:: " + purchaseAmount);


    }
}
