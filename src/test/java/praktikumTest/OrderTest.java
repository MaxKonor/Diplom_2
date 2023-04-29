package praktikumTest;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import praktikum.*;

public class OrderTest {
    private StepsUser stepsUser;
    private StepsOrder stepsOrder;
    private User user;
    private Methods methods;
    private UserLogin UserLogin;

    private String accessToken;
    private int code;
    private boolean status;

    @Before
    public void setOrder() {
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
    @DisplayName("Создание заказа без авторизации")
    public void createOrderWithoutAuthorizationTest() {
        ValidatableResponse response = stepsOrder.createOrderUnauthorizedUser();
        Methods.checkCreateOrderResponse(response, code, status);
    }

    @Test
    @DisplayName("Создание заказа с неверным хешем")
    public void createOrderWithInvalidHashTest() {
        stepsUser.createUser(user);
        ValidatableResponse response = stepsUser.loginUser(UserLogin);
        accessToken = response.extract().path("accessToken").toString();
        stepsOrder.createOrderAuthorizedUserWithInvalidHash(accessToken);
    }

    @Test
    @DisplayName("Создание заказа с авторизацией и ингридиентами")
    public void createOrderWithAuthorizationIngredientsTest() {
        stepsUser.createUser(user);
        ValidatableResponse response = stepsUser.loginUser(UserLogin);
        accessToken = response.extract().path("accessToken").toString();
        stepsOrder.createOrderAuthorizedUserWithoutIngredients(accessToken);
        Methods.checkCreateOrderResponse(response, code, status);
    }

    @Test
    @DisplayName("Создание заказа с авторизацией и без ингридиентов")
    public void createOrderWithAuthorizationAndWithoutIngredientTest() {
        stepsUser.createUser(user);
        ValidatableResponse response = stepsUser.loginUser(UserLogin);
        accessToken = response.extract().path("accessToken").toString();
        stepsOrder.createOrderAuthorizedUserWithoutIngredients(accessToken);

    }
}
