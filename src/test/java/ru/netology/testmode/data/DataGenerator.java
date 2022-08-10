package ru.netology.testmode.data;

import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import lombok.Value;
import lombok.experimental.UtilityClass;

import java.util.Locale;

import static io.restassured.RestAssured.given;

public class DataGenerator {
    private static final RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();
    private static final Faker faker = new Faker(new Locale("en"));

    private DataGenerator() {
    }

    private static void sendRequest(User user) {
        // TODO: отправить запрос на указанный в требованиях path, передав в body запроса объект user
        //  и не забудьте передать подготовленную спецификацию requestSpec.
        //  Пример реализации метода показан в условии к задаче.
        // запрос на добавление пользователя в тестовом режиме
        given() // "дано"
                // указываем, какую спецификацию используем
                .spec(requestSpec)
                // передаём в теле объект, который будет преобразован в JSON
                .body(user)
                // "когда"
                .when()
                // на какой путь, относительно BaseUri отправляем запрос
                .post("/api/system/users")
                // "тогда ожидаем"
                .then()
                // код 200 OKx
                .statusCode(200);
    }

    public static String getRandomLogin() {
        // TODO: добавить логику для объявления переменной login и задания её значения, для генерации
        //  случайного логина используйте faker
        return faker.name().username();
    }

    public static String getRandomPassword() {
        // TODO: добавить логику для объявления переменной password и задания её значения, для генерации
        //  случайного пароля используйте faker
        return faker.internet().password();
    }
    @UtilityClass
    public static class Registration {
        public static User getUser(String status) {
            // TODO: создать пользователя user используя методы getRandomLogin(), getRandomPassword() и параметр status
            return new User (getRandomLogin(), getRandomPassword(), status);
        }

        public static User getRegisteredUser(String status) {
            // TODO: объявить переменную registeredUser и присвоить ей значение возвращённое getUser(status).
            // Послать запрос на регистрацию пользователя с помощью вызова sendRequest(registeredUser)
            User registeredUser = getUser(status);
            sendRequest(registeredUser);
            return registeredUser;
        }
    }

    @Value
    public static class User {
        String login;
        String password;
        String status;
    }
}
