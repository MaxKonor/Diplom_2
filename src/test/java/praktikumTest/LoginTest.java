package praktikumTest;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import praktikum.*;

public class LoginTest {
    private User user;
    private StepsUser stepsUser;
    private StepsOrder stepsOrder;
    private UserLogin UserLogin;
    private Methods methods;
    private String accessToken;
    private int code;
    private boolean status;

    @Before
    public void setUser() {
        user = UserGenerator.random();
        stepsUser = new StepsUser();
        stepsOrder = new StepsOrder();
        methods = new Methods();
        UserLogin = new UserLogin(user);

    }

    @After
    public void cleanUp() {
        if (accessToken != null) {
            stepsUser.deleteUser(accessToken);
        }
    }

    @Test
    @DisplayName("Тестирование авторизации")
    public void authorizationLoginTest() {
        stepsUser.createUser(user);
        ValidatableResponse response = stepsUser.loginUser(UserLogin);
        accessToken = response.extract().path("accessToken").toString();
        methods.checkCreateUserResponse(response, code, status);

    }

    @Test
    @DisplayName("Тестирование авторизации без логина")
    public void authorizationWithoutLoginTest() {
        user.setEmail("");
        ValidatableResponse response = stepsUser.loginUser(UserLogin);
        methods.checkEmailOrPasswordIncorrect(response);

    }

    @Test
    @DisplayName("Тестирование авторизации без пароля")
    public void authorizationWithoutPasswordTest() {
        user.setPassword("");
        ValidatableResponse response = stepsUser.loginUser(UserLogin);
        methods.checkEmailOrPasswordIncorrect(response);

    }
}
