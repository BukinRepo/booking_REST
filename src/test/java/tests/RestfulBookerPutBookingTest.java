package tests;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.RandomStringUtils;
import org.hamcrest.Matchers;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import tests.APIBodys.BookingBody;
import tests.APIBodys.BookingDatesBody;
import tests.Auth.GetToken;

import java.util.ArrayList;

@Getter
@Setter
@Builder

public class RestfulBookerPutBookingTest {
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
        Response responseGetIds = RestAssured.given().log().all().get("/booking");
        responseGetIds.then().statusCode(200);
        responseGetIds.prettyPrint();

        ArrayList<Integer> listOfIds = responseGetIds.jsonPath().get("findAll{it.bookingid}.bookingid");
        int id = listOfIds.get((int) ((Math.random() * (listOfIds.size()-1 - 0)) + 0));


        Response responseGetBooking = RestAssured.given().log().all().get("/booking/" + id);
        responseGetBooking.then().statusCode(200);
        responseGetBooking.prettyPrint();


        String firstName = RandomStringUtils.randomAlphabetic(6);
        String lastName = responseGetBooking.jsonPath().get("lastname");
        int totalPrice = responseGetBooking.jsonPath().get("totalprice");
        Boolean depositPaid = responseGetBooking.jsonPath().get("depositpaid");
        String checkin = responseGetBooking.jsonPath().get("bookingdates.checkin");
        String checkout = responseGetBooking.jsonPath().get("bookingdates.checkout");
        String additionalneedsRan = RandomStringUtils.randomAlphabetic(10);


        GetToken getToken = new GetToken("admin","password123");
        String token = "token";
        String tokenKey = getToken.getAuthToken();

        BookingBody body = new BookingBody().builder()
                .firstname(firstName)
                .lastname(lastName)
                .totalprice(totalPrice)
                .depositpaid(depositPaid)
                .bookingdates(BookingDatesBody.builder().checkin(checkin).checkout(checkout).build())
                .additionalneeds(additionalneedsRan)
                .build();

        Response responsePatch = RestAssured.given().log().all()
                .cookie(token, tokenKey)
                .body(body)
                .put("/booking/" + id);
        responsePatch.then().statusCode(200);

        Response responseCheckBooking = RestAssured.given().log().all().get("/booking/" + id);
        responseCheckBooking.then().statusCode(200);
        responseCheckBooking.then().body("firstname", Matchers.equalTo(firstName));
        responseCheckBooking.then().body("lastname", Matchers.equalTo(lastName));
        responseCheckBooking.then().body("totalprice", Matchers.equalTo(totalPrice));
        responseCheckBooking.then().body("depositpaid", Matchers.equalTo(depositPaid));
        responseCheckBooking.then().body("bookingdates.checkin", Matchers.equalTo(checkin));
        responseCheckBooking.then().body("bookingdates.checkout", Matchers.equalTo(checkout));
        responseCheckBooking.then().body("additionalneeds", Matchers.equalTo(additionalneedsRan));
    }
}
