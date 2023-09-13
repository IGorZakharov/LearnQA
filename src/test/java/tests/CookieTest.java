package tests;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CookieTest {
        @Test
        public void testCookieValue() {

            Response response = RestAssured.get("https://playground.learnqa.ru/api/homework_cookie");

            String cookieValue = response.getCookie("some_cookie_name");

            assertEquals("Ожидаемое значение cookie", "some_cookie_value", cookieValue);
        }
    }

