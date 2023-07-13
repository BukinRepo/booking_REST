package tests;


import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hamcrest.Matchers;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import tests.Auth.GetToken;

import java.util.ArrayList;

import static org.hamcrest.Matchers.equalTo;

@Getter
@Setter
@Builder

public class RestfulBookerDeleteBookingTest {

    @BeforeMethod
    public void setup(){
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";
        RestAssured.requestSpecification=new RequestSpecBuilder()
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .build();
    }

    @Test
    public void deleteBooking () {
        GetToken getToken = new GetToken("admin","password123");
        String token = "token";
        String tokenKey = getToken.getAuthToken();

        Response responseGetIds = RestAssured.given().log().all().get("/booking");
        responseGetIds.then().statusCode(200);
        responseGetIds.prettyPrint();

        ArrayList<Integer> listOfIds = responseGetIds.jsonPath().get("findAll{it.bookingid}.bookingid");
        int id = listOfIds.get((int) ((Math.random() * (listOfIds.size() - 1 - 0)) + 0));

        Response responseDeleteBooking = RestAssured.given().log().all().cookie(token, tokenKey).delete("/booking/" + id);
        responseDeleteBooking.then().statusCode(201);
    }

}
