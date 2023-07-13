package tests.Auth;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.annotations.BeforeMethod;
import tests.APIBodys.AuthBody;

public class GetToken {
    private String username;
    private String password;

    public GetToken(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @BeforeMethod
    public void setup(){
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";
        RestAssured.requestSpecification=new RequestSpecBuilder()
                .addHeader("Content-Type", "application/json")
                .build();
    }
    public String getAuthToken(){
        AuthBody authBody = new AuthBody().builder()
                .username(username)
                .password(password)
                .build();

        Response response = RestAssured.given()
                .body(authBody)
                .post("/auth");
        response.then().statusCode(200);
        response.prettyPrint();
        return response.jsonPath().get("token");
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
