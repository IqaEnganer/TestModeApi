package data;

import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import lombok.Value;
import org.junit.jupiter.api.BeforeAll;

import java.util.Locale;

import static io.restassured.RestAssured.given;

public class DataGenerator {

    private static final Faker faker = new Faker(new Locale("en"));

    private DataGenerator() {
    }

    // Генерация полей (Логин и пароль)
    public static String getLogin() {
        return faker.name().username();
    }

    public static String getPassword() {
        return faker.internet().password();
    }

    @Value
    public static class RegistrationInfo {
        String login;
        String password;
        String status;
    }

    /*// Активный пользователь
    public static RegistrationInfo getUserActive() {
        RegistrationInfo info = new RegistrationInfo(getLogin(), getPassword(), "active");
        sendingData(info);
        return info;
    }

    // Заблокированный пользователь
    public static RegistrationInfo getUserBlocked() {
        RegistrationInfo info = new RegistrationInfo(getLogin(), getPassword(), "blocked");
        sendingData(info);
        return info;
    }*/


        public static RegistrationInfo getUserAllStatus(String status) {
            RegistrationInfo info = new RegistrationInfo(getLogin(),getPassword(),status);
            sendingData(info);
            return info;
        }


    // спецификация нужна для того, чтобы переиспользовать настройки в разных запросах
    private static final RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    @BeforeAll
    private static void sendingData(RegistrationInfo info) {
        given() // "дано"
                .spec(requestSpec) // указываем, какую спецификацию используем
                .body(info) // передаём в теле объект, который будет преобразован в JSON
                .when() // "когда"
                .post("/api/system/users") // на какой путь, относительно BaseUri отправляем запрос
                .then() // "тогда ожидаем"
                .statusCode(200); // код 200 OK
    }
}
