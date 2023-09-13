package lib;

import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.codehaus.groovy.ast.tools.GeneralUtils.param;
import static org.hamcrest.Matchers.equalTo;

public class ApiCoreRequests {
    @Step("Make a Get-request with token and auth cookie")
    public Response makeGetRequeas(String url, String token, String cookie) {
        return given()
                .filter(new AllureRestAssured())
                .header(new Header("x-csrf-token", token))
                .cookie("auth_sid", cookie)
                .get(url)
                .andReturn();
    }

    @Step("Make a Get-request with auth cookie only")
    public Response makeGetRequestWithCookie(String url, String cookie) {
        return given()
                .filter(new AllureRestAssured())
                .cookie("auth_sid", cookie)
                .get(url)
                .andReturn();
    }

    @Step("Make a Get-request with token only")
    public Response makeGetRequestWithToken (String url, String token) {
        return given()
                .filter(new AllureRestAssured())
                .header(new Header("x-csrf-token", token))
                .get(url)
                .andReturn();
    }

    @Step("Make a POST-request")
    public Response makePostRequeas(String url, Map<String, String> authData) {
        return given()
                .filter(new AllureRestAssured())
                .body(authData)
                .post(url)
                .andReturn();
    }

    @Step ("Make a POST-request invalid email")
    public Response makePostUserWithInvalidEmail(){
            String email ="vinkotovexample.com";
            Map<String, String> userData = new HashMap<>();
            userData.put("email", email);
            userData = DataGenerator.getRegistrationData(userData);

            Response responseCreateAuth = RestAssured
                    .given()
                    .body(userData)
                    .post("https://playground.learnqa.ru/api/user/")
                    .andReturn();

            Assertions.assertResponseCodeEquals(responseCreateAuth, 400);
            Assertions.assertResponseTextEquals(responseCreateAuth, "Users with email '" + email + "' alreade exists");
        }
    @ParameterizedTest
    @DisplayName("Creating a user without specifying one of the fields")
    @ValueSource(strings = {"email", "name"})
    public void createUserWithoutField(String field) {
        given()
                .param("email", "vinkotov@example.com")
                .param("name", "Name Name")
                .queryParam(field, "")
                .when()
                .post("https://playground.learnqa.ru/api/user/")
                .then()
                .statusCode(400)
                .body("message", equalTo("The following fields are required: " + field));
    }

    @Test
    @DisplayName("Creating a user with a very short name of one character")
    public void createUserWithShortName() {
        given()
                .param("email", "vinkotov@example.com")
                .param("name", "v")
                .when()
                .post("https://playground.learnqa.ru/api/user/")
                .then()
                .statusCode(400)
                .body("message", equalTo("The 'name' field must be at least 2 characters long"));
    }

    @Test
    @DisplayName("Creating a user with a very long name - longer than 250 characters")
    public void createUserWithLongName() {
        String longName = "1234567890".repeat(10);
        given()
                .param("email", "vinkotov@example.com")
                .param("name", longName)
                .when()
                .post("https://playground.learnqa.ru/api/user/")
                .then()
                .statusCode(400)
                .body("message", equalTo("The 'name' field must be at most 250 characters long"));
    }

}
