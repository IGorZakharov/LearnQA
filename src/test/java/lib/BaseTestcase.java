package lib;

import io.restassured.http.Headers;
import io.restassured.response.Response;

import java.util.Map;

import static org.hamcrest.Matchers.hasKey;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BaseTestcase {
    protected String getHeader(Response Response, String name){
        Headers headers = Response.getHeaders();

        assertTrue(headers.hasHeaderWithName(name), "В Ответе нет headers с именем" + name);
        return headers.getValue(name);

/** Убеждаемся, что в соответсвующие поля пришли значения и выводим их, если в полях значений нет, то тест падает */
    }

    protected String getCookie (Response Response, String name){
        Map<String, String> cookies = Response.getCookies();

        assertTrue(cookies.containsKey(name), "В Ответе нет cookies" + name);
        return cookies.get(name);

    }

    protected int getIntFromJson (Response Response, String name) {
        Response.then().assertThat().body("$", hasKey(name));
        return Response.jsonPath().getInt(name);
    }
}
