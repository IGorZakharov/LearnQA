package lib;

import io.restassured.response.Response;

import static org.hamcrest.Matchers.hasKey;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Assertions {
    public static void assertJsonByName(Response Response, String name,int expectedValue) {
        Response.then().assertThat().body("$", hasKey(name));

        int value = Response.jsonPath().getInt(name);
        assertEquals(expectedValue, value, "JSON value in not equal to expected value");
    }

    public static void assertResponseTextEquals (Response Response, int expectedAnswer){
        assertEquals(
                expectedAnswer,
                Response.asString(),
                "Ответ не верный"
        );
    }

    public static void assertResponseCodeEquals (Response Response, String expectedStatusCode){
        assertEquals(
                expectedStatusCode,
                Response.asString(),
                "Код ответа не верный"
        );
    }
}
