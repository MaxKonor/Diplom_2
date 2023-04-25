package praktikumTest;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import praktikum.*;

public class GetOrderTest {
    private User user;
    private Steps steps;
    private Methods methods;
    private String accessToken;
    private praktikum.UserLogin UserLogin;

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
    @DisplayName("Получение заказов без регистрации")
    public void getOrderWithoutRegistrationTest() {
        ValidatableResponse response = steps.getOrdersWithoutRegistration();
        methods.checkOrderNoUser(response);
    }

    @Test
    @DisplayName("Получение заказов зарегистрированного пользователя")
    public void getOrderWithRegistrationTest() {
        steps.createUser(user);
        ValidatableResponse response = steps.loginUser(UserLogin);
        accessToken = response.extract().path("accessToken").toString();
        steps.createOrderAuthorizedUser(accessToken);
        steps.getOrdersWithRegistration(accessToken);
    }
}
