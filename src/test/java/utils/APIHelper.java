package utils;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import utils.Reqres;

public class APIHelper {

    public static Reqres getUser(int userID){
        RestAssured.baseURI=ConfigReader.getProperty("baseURL");

        Response response=given()
                .pathParam("id",userID)
                .when()
                .get("/api/users/{id}")
                .then()
                .extract()
                .response();

        int id=response.path("data.id");
        String firstname=response.path("data.first_name");
        String lastname=response.path("data.last_name");
        String email=response.path("data.email");
        String avatar=response.path("data.avatar");

        return new Reqres(id,firstname,lastname,email,avatar);
    }

    public static Response getAllUsers(){
        Response response=
                given()
                        .when()
                        .get("https://reqres.in/api/users?page=2")
                        .then().statusCode(200)
                        .extract().response();

        return response;
    }

    public static Response createUser(String name, String job){
        RestAssured.baseURI=ConfigReader.getProperty("baseURL");

        String requestBody = "{ \"name\": \"" + name + "\", \"job\": \"" + job + "\" }";

        Response response=given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/api/users")
                .then()
                .statusCode(201)
                .extract()
                .response();


        return response;
    }

    public static Response updateUser (Reqres user,String job){
        RestAssured.baseURI=ConfigReader.getProperty("baseURL");

        String requestBody = "{ \"name\": \"" + user.firstName + "\", \"job\": \"" + job + "\" }";
        Response response=given()
                .contentType(ContentType.JSON)
                .pathParam("id",user.id.toString())
                .body(requestBody)
                .when()
                .put("/api/users/{id}")
                .then()
                .statusCode(200)
                .extract()
                .response();

        return response;
    }
    public static Response deleteUser(int userID){
        RestAssured.baseURI=ConfigReader.getProperty("baseURL");

        Response response=given()
                .pathParam("id",userID)
                .when()
                .delete("/api/users/{id}")
                .then()
                .extract()
                .response();

        return response;
    }


}
