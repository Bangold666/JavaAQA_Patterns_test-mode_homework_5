package ru.netology.testmode.test;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.netology.testmode.data.DataGenerator;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static ru.netology.testmode.data.DataGenerator.Registration.getRegisteredUser;
import static ru.netology.testmode.data.DataGenerator.Registration.getUser;
import static ru.netology.testmode.data.DataGenerator.getRandomLogin;
import static ru.netology.testmode.data.DataGenerator.getRandomPassword;

class AuthTest {

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    @DisplayName("Should successfully login with active registered user")
    void shouldAddUserAndAuthTest() {
        DataGenerator.User user = DataGenerator.Registration.getRegisteredUser("active");
        SelenideElement form = $(".form");

        form.$("[data-test-id='login'] input").setValue(user.getLogin());
        form.$("[data-test-id='password'] input").setValue(user.getPassword());
        form.$(".button.button").click();

        $(".heading_theme_alfa-on-white").shouldHave(Condition.text("Личный кабинет"));
    }

    @Test
    @DisplayName("Should get error message if login with not registered user")
    void shouldGetErrorIfNotRegisteredUser() {
        var notRegisteredUser = getUser("active");
        SelenideElement form = $(".form");

        form.$("[data-test-id='login'] input").setValue(notRegisteredUser.getLogin());
        form.$("[data-test-id='password'] input").setValue(notRegisteredUser.getPassword());
        form.$(".button.button").click();

        $(".notification .notification__content")
                .shouldHave(Condition.text("Неверно указан логин или пароль"))
                .shouldBe(Condition.visible);

        // TODO: добавить логику теста в рамках которого будет выполнена попытка входа в личный кабинет
        //  незарегистрированного пользователя, для заполнения полей формы используйте пользователя notRegisteredUser
    }

    @Test
    @DisplayName("Should get error message if login with blocked registered user")
    void shouldGetErrorIfBlockedUser() {
        var blockedUser = getRegisteredUser("blocked");
        SelenideElement form = $(".form");

        form.$("[data-test-id='login'] input").setValue(blockedUser.getLogin());
        form.$("[data-test-id='password'] input").setValue(blockedUser.getPassword());
        form.$(".button.button").click();

        $(".notification .notification__content")
                .shouldHave(Condition.text("Ошибка! Пользователь заблокирован"))
                .shouldBe(Condition.visible);

        // TODO: добавить логику теста в рамках которого будет выполнена попытка входа в личный кабинет,
        //  заблокированного пользователя, для заполнения полей формы используйте пользователя blockedUser
    }

    @Test
    @DisplayName("Should get error message if login with wrong login")
    void shouldGetErrorIfWrongLogin() {
        var registeredUser = getRegisteredUser("active");
        var wrongLogin = getRandomLogin();
        SelenideElement form = $(".form");

        form.$("[data-test-id='login'] input").setValue(wrongLogin);
        form.$("[data-test-id='password'] input").setValue(registeredUser.getPassword());
        form.$(".button.button").click();

        $(".notification .notification__content")
                .shouldHave(Condition.text("Ошибка! Неверно указан логин или пароль"))
                .shouldBe(Condition.visible);

        // TODO: добавить логику теста в рамках которого будет выполнена попытка входа в личный кабинет с неверным
        //  логином, для заполнения поля формы "Логин" используйте переменную wrongLogin,
        //  "Пароль" - пользователя registeredUser
    }

    @Test
    @DisplayName("Should get error message if login with wrong password")
    void shouldGetErrorIfWrongPassword() {
        var registeredUser = getRegisteredUser("active");
        var wrongPassword = getRandomPassword();

        SelenideElement form = $(".form");

        form.$("[data-test-id='login'] input").setValue(registeredUser.getLogin());
        form.$("[data-test-id='password'] input").setValue(wrongPassword);
        form.$(".button.button").click();

        $(".notification .notification__content")
                .shouldHave(Condition.text("Ошибка! Неверно указан логин или пароль"))
                .shouldBe(Condition.visible);

        // TODO: добавить логику теста в рамках которого будет выполнена попытка входа в личный кабинет с неверным
        //  паролем, для заполнения поля формы "Логин" используйте пользователя registeredUser,
        //  "Пароль" - переменную wrongPassword
    }
}
