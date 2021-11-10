package testing;

import com.codeborne.selenide.Configuration;
import data.DataGenerator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class TestModeApi {

    @BeforeEach
    public void setUp() {
        Configuration.headless = true;
        open("http://localhost:9999/");


    }

    // Создание и авторизация активного пользователя
    @Test
    public void shouldSuccessfulAuthorization() {
        var validUser = DataGenerator.getUserAllStatus("active");
        $("[data-test-id='login'] .input__control").setValue(validUser.getLogin());
        $("[data-test-id='password'] .input__control").setValue(validUser.getPassword());
        $("[data-test-id='action-login']").click();
        $("[class='heading heading_size_l heading_theme_alfa-on-white']").shouldBe(visible, Duration.ofSeconds(11));
        $("[class='heading heading_size_l heading_theme_alfa-on-white']").shouldHave(text("  Личный кабинет"));
    }

    // Создание и авторизация заблокированного пользователя
    @Test
    public void shouldNotifyAboutBlockedUser() {
        var blockedUser = DataGenerator.getUserAllStatus("blocked");
        $("[data-test-id='login'] .input__control").setValue(blockedUser.getLogin());
        $("[data-test-id='password'] .input__control").setValue(blockedUser.getPassword());
        $("[data-test-id='action-login']").click();
        $("[data-test-id='error-notification']").shouldBe(visible, Duration.ofSeconds(11));
        $("[data-test-id='error-notification']").shouldHave(text("Пользователь заблокирован"));
    }
    // Оба поля заполнены неправильно
    @Test
    public void shouldNotifyAboutIncorrectDate() {
        $("[data-test-id='login'] .input__control").setValue(DataGenerator.getLogin());
        $("[data-test-id='password'] .input__control").setValue(DataGenerator.getPassword());
        $("[data-test-id='action-login']").click();
        $("[data-test-id='error-notification']").shouldBe(visible, Duration.ofSeconds(11));
        $("[data-test-id='error-notification']").shouldHave(text("Неверно указан логин или пароль"));
    }
    // Не валидный логин
    @Test
    public void shouldNotifyAboutIncorrectLogin() {
        var user = DataGenerator.getUserAllStatus("active").getPassword();
        $("[data-test-id='login'] .input__control").setValue(DataGenerator.getLogin());
        $("[data-test-id='password'] .input__control").setValue(user);
        $("[data-test-id='action-login']").click();
        $("[data-test-id='error-notification']").shouldBe(visible, Duration.ofSeconds(11));
        $("[data-test-id='error-notification']").shouldHave(text("Неверно указан логин или пароль"));
    }
    // Не валидный пароль
    @Test
    public void shouldNotifyAboutIncorrectPassword() {
        var user = DataGenerator.getUserAllStatus("active").getLogin();
        $("[data-test-id='login'] .input__control").setValue(user);
        $("[data-test-id='password'] .input__control").setValue(DataGenerator.getPassword());
        $("[data-test-id='action-login']").click();
        $("[data-test-id='error-notification']").shouldBe(visible, Duration.ofSeconds(11));
        $("[data-test-id='error-notification']").shouldHave(text("Неверно указан логин или пароль"));
    }


}
