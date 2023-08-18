import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class TestJsonPars {
    @Test
    public void testJson(){
        Map<String, String> params = new HashMap<>();
        params.put("message", String.valueOf(0));
//        Response response = RestAssured
//                .given()
//                .get("https://playground.learnqa.ru/api/get_json_homework")
//                .andReturn();
//        response.print();

        JsonPath response2 = RestAssured
                .given()
                .get("https://playground.learnqa.ru/api/get_json_homework")
                .jsonPath();
        String answer = response2.get("second");
        System.out.println(answer);


    }
}
