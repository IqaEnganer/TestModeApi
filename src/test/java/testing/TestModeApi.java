package testing;

import com.codeborne.selenide.Configuration;
import data.DataGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Locale;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class TestModeApi {
    @BeforeEach
    public void setUp() {
        open("http://localhost:9999/");
        Configuration.headless = true;
    }

    @Test
    public void shouldSuccessfulAuthorization() {
        $("[data-test-id='login']").setValue(DataGenerator.getUserAllStatus("Active").getLogin());
        $("[data-test-id='password']").setValue(DataGenerator.getUserAllStatus("Active").getPassword());
        $("[data-test-id='action-login']").click();
    }
}
