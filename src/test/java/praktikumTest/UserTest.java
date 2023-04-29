package praktikumTest;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import praktikum.*;

public class UserTest {
    private User user;
    private StepsUser stepsUser;
    private StepsOrder stepsOrder;
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

    }

    @After
    public void cleanUp() {
        if (accessToken != null) {
            stepsUser.deleteUser(accessToken);
        }
    }

    @Test
    @DisplayName("Проверка создание пользователя")
    public void checkCreationUserTest() {
        ValidatableResponse response = stepsUser.createUser(user);
        accessToken = response.extract().path("accessToken").toString();
        methods.checkCreateUserResponse(response, code, status);

    }

    @Test
    @DisplayName("Проверка создание пользователя без имени")
    public void checkCreationUserWithoutNameTest() {
        user.setName("");
        ValidatableResponse response = stepsUser.createUser(user);
        methods.checkCorrectCreateUserResponse(response);

    }

    @Test
    @DisplayName("Проверка создание пользователя без email")
    public void checkCreationUserWithoutEmailTest() {
        user.setEmail("");
        ValidatableResponse response = stepsUser.createUser(user);
        methods.checkCorrectCreateUserResponse(response);

    }

    @Test
    @DisplayName("Проверка создание пользователя без пароля")
    public void checkCreationUserWithoutPasswordTest() {
        user.setPassword("");
        ValidatableResponse response = stepsUser.createUser(user);
        methods.checkCorrectCreateUserResponse(response);
    }

    @Test
    @DisplayName("Проверка создание пользователя с одинаковым логином")
    public void checkCreationUserWithTheSameloginTest() {
        stepsUser.createUser(user);
        ValidatableResponse response = stepsUser.createUser(user);
        methods.checkCorrectUserDoubleResponse(response);

    }
}
