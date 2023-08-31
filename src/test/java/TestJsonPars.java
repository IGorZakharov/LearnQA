import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class TestJsonPars {
    @Test
    public void testJson(){

            RestAssured.useRelaxedHTTPSValidation();
            Map<String, Object> params = new HashMap<>();

            JsonPath response = RestAssured
                    .given()
                    .queryParams(params)
                    .get("https://playground.learnqa.ru/api/get_json_homework")
                    .jsonPath();

            String message = response.getString("messages[1].message");
            System.out.println(message);
        }
    }
