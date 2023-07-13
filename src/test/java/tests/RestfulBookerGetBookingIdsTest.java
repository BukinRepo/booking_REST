package tests;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashMap;

@Getter
@Setter
@Builder

public class RestfulBookerGetBookingIdsTest {

    @BeforeMethod
    public void setup(){
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";
        RestAssured.requestSpecification=new RequestSpecBuilder()
                .build();
    }

    @Test
    public void getBookingIds (){
        Response response = RestAssured.given().log().all().get("/booking");
        response.then().statusCode(200);
        response.prettyPrint();
    }

}
