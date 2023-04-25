package praktikumTest;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import praktikum.Methods;
import praktikum.Steps;
import praktikum.User;
import praktikum.UserGenerator;

public class UserTest {
    private User user;
    private Steps steps;
    private Methods methods;
    private String accessToken;
    private int code;
    private boolean status;

    @Before
    public void setUser() {
        user = UserGenerator.random();
        steps = new Steps();
        methods = new Methods();

    }

    @After
    public void cleanUp() {
        if (accessToken != null) {
            steps.deleteUser(accessToken);
        }
    }

    @Test
    @DisplayName("Проверка создание пользователя")
    public void checkCreationUserTest() {
        ValidatableResponse response = steps.createUser(user);
        accessToken = response.extract().path("accessToken").toString();
        methods.checkCreateUserResponse(response, code, status);

    }

    @Test
    @DisplayName("Проверка создание пользователя без имени")
    public void checkCreationUserWithoutNameTest() {
        user.setName("");
        ValidatableResponse response = steps.createUser(user);
        methods.checkCorrectCreateUserResponse(response);

    }

    @Test
    @DisplayName("Проверка создание пользователя без email")
    public void checkCreationUserWithoutEmailTest() {
        user.setEmail("");
        ValidatableResponse response = steps.createUser(user);
        methods.checkCorrectCreateUserResponse(response);

    }

    @Test
    @DisplayName("Проверка создание пользователя без пароля")
    public void checkCreationUserWithoutPasswordTest() {
        user.setPassword("");
        ValidatableResponse response = steps.createUser(user);
        methods.checkCorrectCreateUserResponse(response);
    }

    @Test
    @DisplayName("Проверка создание пользователя с одинаковым логином")
    public void checkCreationUserWithTheSameloginTest() {
        steps.createUser(user);
        ValidatableResponse response = steps.createUser(user);
        methods.checkCorrectUserDoubleResponse(response);

    }
}
