package tests;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import lombok.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import tests.APIBodys.BookingDatesBody;
import tests.APIBodys.BookingBody;
import tests.Auth.GetToken;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@Builder



public class RestfulBookerCreateBookingTest {

    @BeforeMethod
    public void setup(){
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";
        RestAssured.requestSpecification=new RequestSpecBuilder()
                .addHeader("Content-Type", "application/json")
                .build();
    }

    @Test
    public void createBookBooking (){
//        LocalDate checkin = dateParser("2018-01-01");
//        LocalDate checkout = dateParser("2019-01-01");
        String checkin = "2018-01-01";
        String checkout = "2019-01-01";
        GetToken getToken = new GetToken("admin","password123");
        String token = "token";
        String tokenKey = getToken.getAuthToken();

//        Response response = RestAssured.given().log().all().get("/booking");
        BookingBody body = new BookingBody().builder()
                .firstname("FirstTest")
                .lastname("LastNTest")
                .totalprice(255)
                .depositpaid(true)
                .bookingdates(BookingDatesBody.builder().checkin(checkin).checkout(checkout).build())
                .additionalneeds("comment")
                .build();

        Response response = RestAssured.given()
                .header("Accept", "application/json")
                .cookie(token, tokenKey)
                .body(body)
                .log().all()
                .post("/booking");
        response.then().statusCode(200);
        response.prettyPrint();
    }

    private LocalDate dateParser (String date){
        LocalDate parsedDate = LocalDate.parse(date,
                DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        return parsedDate;
    }
}
