package tests;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import tests.APIBodys.BookingIdsPatchBody;
import tests.APIBodys.BookingIdsPutBody;
import tests.Auth.GetToken;

import java.util.ArrayList;

@Getter
@Setter
@Builder
public class RestfulBookerPatchBookingTest {
    @BeforeMethod
    public void setup(){
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";
        RestAssured.requestSpecification=new RequestSpecBuilder()
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .build();
    }

    @Test
    public void patchBooking (){
        Response responseGet = RestAssured.given().log().all().get("/booking");
        responseGet.then().statusCode(200);
        responseGet.prettyPrint();

        ArrayList <Integer> listOfIds = responseGet.jsonPath().get("findAll{it.bookingid}.bookingid");


        int price = (int) ((Math.random() * (Integer.MAX_VALUE - 0)) + 0);
        int id = listOfIds.get((int) ((Math.random() * (listOfIds.size()-1 - 0)) + 0));

        GetToken getToken = new GetToken("admin","password123");
        String token = "token";
        String tokenKey = getToken.getAuthToken();

//        JSONObject body = new JSONObject();
//        body.put("totalprice", price);

        BookingIdsPatchBody body = new BookingIdsPatchBody().builder()
                .totalprice(price)
                .build();

        Response responsePatch = RestAssured.given().log().all()
                .cookie(token, tokenKey)
                .body(body)
                .patch("/booking/" + id);
        responsePatch.then().statusCode(200);
        responsePatch.prettyPrint();

        Response responseCheckBooking = RestAssured.given().log().all().get("/booking/" + id);
        responseCheckBooking.then().statusCode(200);
        responseCheckBooking.then().body("totalprice", Matchers.equalTo(price));
    }
}
