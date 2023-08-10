import org.junit.jupiter.api.Test;
import io.restassured.RestAssured;
import io.restassured.response.Response;
public class TestPrintln {
    @Test
    public void helloWorld() {

        System.out.println("Hello from Igor");
    }

    @Test
    public void getRequest() {
        Response response = RestAssured
                .get("https://playground.learnqa.ru/api/get_text")
                .andReturn();
        response.prettyPrint();

    }
}