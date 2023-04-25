package praktikumTest;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import praktikum.*;

public class LoginTest {
    private User user;
    private Steps steps;
    private praktikum.UserLogin UserLogin;
    private Methods methods;
    private String accessToken;
    private int code;
    private boolean status;

    @Before
    public void setUser() {
        user = UserGenerator.random();
        steps = new Steps();
        methods = new Methods();
        UserLogin = new UserLogin(user);

    }

    @After
    public void cleanUp() {
        if (accessToken != null) {
            steps.deleteUser(accessToken);
        }
    }

    @Test
    @DisplayName("Тестирование авторизации")
    public void authorizationLoginTest() {
        steps.createUser(user);
        ValidatableResponse response = steps.loginUser(UserLogin);
        accessToken = response.extract().path("accessToken").toString();
        methods.checkCreateUserResponse(response, code, status);

    }

    @Test
    @DisplayName("Тестирование авторизации без логина")
    public void authorizationWithoutLoginTest() {
        user.setEmail("");
        ValidatableResponse response = steps.loginUser(UserLogin);
        methods.checkEmailOrPasswordIncorrect(response);

    }

    @Test
    @DisplayName("Тестирование авторизации без пароля")
    public void authorizationWithoutPasswordTest() {
        user.setPassword("");
        ValidatableResponse response = steps.loginUser(UserLogin);
        methods.checkEmailOrPasswordIncorrect(response);

    }
}
