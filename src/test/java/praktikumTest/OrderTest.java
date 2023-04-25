package praktikumTest;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import praktikum.*;

public class OrderTest {
    private Steps steps;
    private User user;
    private Methods methods;
    private praktikum.UserLogin UserLogin;

    private String accessToken;
    private int code;
    private boolean status;

    @Before
    public void setOrder() {
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
    @DisplayName("Создание заказа без авторизации")
    public void createOrderWithoutAuthorizationTest() {
        ValidatableResponse response = steps.createOrderUnauthorizedUser();
        Methods.checkCreateOrderResponse(response, code, status);
    }

    @Test
    @DisplayName("Создание заказа с неверным хешем")
    public void createOrderWithInvalidHashTest() {
        steps.createUser(user);
        ValidatableResponse response = steps.loginUser(UserLogin);
        accessToken = response.extract().path("accessToken").toString();
        steps.createOrderAuthorizedUserWithInvalidHash(accessToken);
    }

    @Test
    @DisplayName("Создание заказа с авторизацией и ингридиентами")
    public void createOrderWithAuthorizationIngredientsTest() {
        steps.createUser(user);
        ValidatableResponse response = steps.loginUser(UserLogin);
        accessToken = response.extract().path("accessToken").toString();
        steps.createOrderAuthorizedUserWithoutIngredients(accessToken);
        Methods.checkCreateOrderResponse(response, code, status);
    }

    @Test
    @DisplayName("Создание заказа с авторизацией и без ингридиентов")
    public void createOrderWithAuthorizationAndWithoutIngredientTest() {
        steps.createUser(user);
        ValidatableResponse response = steps.loginUser(UserLogin);
        accessToken = response.extract().path("accessToken").toString();
        steps.createOrderAuthorizedUserWithoutIngredients(accessToken);

    }
}
