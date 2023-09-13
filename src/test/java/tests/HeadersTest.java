package tests;
import org.junit.jupiter.api.Test;
import io.restassured.RestAssured;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class HeadersTest {


        @Test
        public void testHeader() {

            RestAssured.baseURI = "https://playground.learnqa.ru/api/homework_header";

            given().get("homework_header")
                    .then().assertThat()
                    .header("Header-Name", equalTo("Header-Value"));
        }
    }

