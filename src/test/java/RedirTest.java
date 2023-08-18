import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;


public class RedirTest {
    @Test
    public void testUrlRedir() {

        Response response = RestAssured
                .given()
                .redirects()
                .follow(true)
                .when()
                .get("https://playground.learnqa.ru/api/long_redirect")
                .andReturn();
        int statusCode = response.getStatusCode();
        for (int i = 0; i == 200;)
             { RestAssured.given().redirects().follow(true);
                 System.out.println(statusCode);
        }
        System.out.println(statusCode);
    }


    }

