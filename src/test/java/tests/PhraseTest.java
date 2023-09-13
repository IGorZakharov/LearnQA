package tests;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PhraseTest {
        @Test
        public void testStringLength() {
            String myString = "Текст длинной в 15 символов";
            assertTrue(myString.length() > 15,
                    "Длина строки больше, чем 15 символов");
        }
    }

