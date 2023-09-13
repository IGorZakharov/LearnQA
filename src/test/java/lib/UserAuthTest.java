package lib;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lib.BaseTestcase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import lib.Assertions;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.DisplayName;
import lib.ApiCoreRequests;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Epic("Authorisation cases")
@Feature("Authorisation")
public class UserAuthTest extends BaseTestcase {

    String cookie;
    String header;
    int userIdOnAuth;
private final ApiCoreRequests apiCoreRequests = new ApiCoreRequests();
    @BeforeEach
    public void loginUser(){
/** Выносим сюда запрос на авторизацию с данными */

        Map<String, String> authData = new HashMap<>();
        authData.put("email", "vinkotov@xample.com");
        authData.put("password", "1234");

        Response responseGetAuth = apiCoreRequests
                .makePostRequeas("https://playground.learnqa.ru/api/user/login", authData);

        this.cookie = this.getCookie(responseGetAuth, "auth_sid");
        this.header = this.getHeader(responseGetAuth,"x-csrf-token");
        this.userIdOnAuth = this.getIntFromJson(responseGetAuth, "user_id");

        //int statusCode = responseGetAuth.getStatusCode();
        //System.out.println(statusCode);
    }
    @Test
    @Description("This test successfully authorize user by email and password")
    @DisplayName("Test positive auth user")
    public void testAuthUser(){

        /**Теперь проверяем действительно ли мы залогинины */

        Response responseCheckAuth = apiCoreRequests
                .makeGetRequeas(
                        "https://playground.learnqa.ru/api/user/auth",
                        this.header,
                        this.cookie
                );
        Assertions.assertJsonByName(responseCheckAuth, "user_sid", this.userIdOnAuth);
    }
@Description("This test checks auth status w/o sending auth cookie or token")
@DisplayName("Test negative auth user")
    @ParameterizedTest
    @ValueSource(strings = {"cookie", "headers"})
    public void testNegativeAuthUser(String condition){

        RequestSpecification spec = RestAssured.given();
        spec.baseUri("https://playground.learnqa.ru/api/user/auth");

    if (condition.equals("cookie")) {
        Response responseForCheck = apiCoreRequests.makeGetRequestWithCookie(
                "https://playground.learnqa.ru/api/user/auth",
                this.cookie
        );
        Assertions.assertJsonByName(responseForCheck,"user_id", 0);
    } else if (condition.equals("headers")) {
        Response responseForCheck = apiCoreRequests.makeGetRequestWithToken(
                "https://playground.learnqa.ru/api/user/auth",
                this.header
        );
        Assertions.assertJsonByName(responseForCheck, "user_id", 0);
    } else {
        throw new IllegalArgumentException("Condition value is not know:" + condition);
    }

    }

}



