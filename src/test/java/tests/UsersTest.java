package tests;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;


public class UsersTest {

        /** Функция для проверки одного User Agent */
        public void checkUserAgent(String userAgent, String expectedPlatform, String expectedBrowser, String expectedDevice) throws IOException {
            String url = "https://playground.learnqa.ru/ajax/api/user_agent_check";
            URL obj = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("User-Agent", userAgent);

            int responseCode = connection.getResponseCode();
            if (responseCode != 200) {
                throw new IOException("Ошибка ответа");
            }

            String[] headers = connection.getHeaderFields().get("User-Agent-Check").get(0).split(";");

            String actualPlatform = "Unknown";
            String actualBrowser = "Unknown";
            String actualDevice = "Unknown";

            for (String header : headers) {
                if (header.contains("platform")) {
                    actualPlatform = header.split(": ")[1];
                } else if (header.contains("browser")) {
                    actualBrowser = header.split(": ")[1];
                } else if (header.contains("device")) {
                    actualDevice = header.split(": ")[1];
                }
            }

            assertEquals(expectedPlatform, actualPlatform);
            assertEquals(expectedBrowser, actualBrowser);
            assertEquals(expectedDevice, actualDevice);
        }

        @Test
        public void testUserAgentCheck() throws IOException {
            checkUserAgent("Mozilla/5.0 (Linux; U; Android 4.0.2; en-us; Galaxy Nexus Build/ICL53F) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30",
                    "Mobile", "No", "Android");

            checkUserAgent("Mozilla/5.0 (iPad; CPU OS 13_2 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) CriOS/91.0.4472.77 Mobile/15E148 Safari/604.1",
                    "Mobile", "Chrome", "iOS");

            checkUserAgent("Mozilla/5.0 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)",
                    "Googlebot", "Unknown", "Unknown");

            checkUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.77 Safari/537.36 Edg/91.0.100.0",
                    "Web", "Chrome", "No");

            checkUserAgent("Mozilla/5.0 (iPad; CPU iPhone OS 13_2_3 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/13.0.3 Mobile/15E148 Safari/604.1",
                    "Mobile", "No", "iPhone");
        }
    }
