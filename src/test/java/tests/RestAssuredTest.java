package tests;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.APIHelper;
import utils.Reqres;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThan;

public class RestAssuredTest {

    @Test
    public void SingleUserBDDTest() {
        when()
                .get("https://reqres.in/api/users/2")
                .then().statusCode(200)
                .body("data.email",equalTo("janet.weaver@reqres.in"))
                .time(lessThan(5000L));

    }

    @Test
    public void ListAllUsersTest() {
        Response response=
                given()
                        .when()
                        .get("https://reqres.in/api/users?page=2")
                        .then().statusCode(200)
                        .extract().response();

        String responsedata=response.asPrettyString();

        System.out.println(responsedata);
    }

    @Test
    public void CreateUserTest() {
        String data="{\n" +
                "    \"name\": \"corpheus\",\n" +
                "    \"job\": \"leader\"\n" +
                "}";

        given()
                .contentType(ContentType.JSON)
                .body(data)
                .when()
                .post("https://reqres.in/api/users")
                .then()
                .statusCode(201)
                .body("name",equalTo("corpheus"));
    }


    @Test
    public void userOperationsTests() {
        //Tüm kullanıcıları çağır
        Response resp=APIHelper.getAllUsers();

        String userJsonList=resp.asPrettyString();


        //ID=9 olan kullanıcıyı çağır
        Reqres reqres=APIHelper.getUser(9);

        System.out.println("Kullanıcı adı : "+reqres.firstName);
        System.out.println("Kullanıcı mail adresi : "+reqres.email);


        //gelen kullanıcının mesleğini değiştir
        Response updateUserResponse=APIHelper.updateUser(reqres,"Çaycı");
        Assert.assertEquals("Çaycı",updateUserResponse.path("job"));

        try {
            System.out.println(updateUserResponse.asPrettyString());
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }


        //yeni bir kullanıcı oluştur
        Response createNewUser=APIHelper.createUser("Fatih","mühendis");
        Assert.assertEquals(createNewUser.getStatusCode(),201);




        //kullanıcıyı sil
        Response deleteUserResponse=APIHelper.deleteUser(reqres.id);

        try {
            System.out.println(deleteUserResponse.asPrettyString());
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        Assert.assertEquals(deleteUserResponse.getStatusCode(),204);


    }
}
