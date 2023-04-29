package praktikumTest;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import praktikum.*;

import static org.hamcrest.Matchers.equalTo;

public class UpdateTest {
    private User user;
    private User userForUpdate;
    private StepsUser stepsUser;
    private StepsOrder stepsOrder;
    private UserLogin UserLogin;
    private Methods methods;
    private String accessToken;

    @Before
    public void setUser() {
        user = UserGenerator.random();
        userForUpdate = UserGenerator.random();
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
    @DisplayName("Изменения данных зарегистрированного пользователя")
    public void updateDataRegisteredUserTest() {
        User initialUser = UserGenerator.random();
        User userForUpdate = initialUser.clone();
        userForUpdate.setEmail(RandomStringUtils.randomAlphabetic(6) + "@changeableness.com");

        String accessToken = stepsUser.createUser(initialUser)
                .extract()
                .header("Authorization");

        stepsUser.updateUser(accessToken, userForUpdate);
        ValidatableResponse updatedUserResponse = stepsUser.getDataUser(accessToken);

        updatedUserResponse.body("user.name", equalTo(initialUser.getName()))
                .and()
                .body("user.email", equalTo(userForUpdate.getEmail().toLowerCase()));
    }

    @Test
    @DisplayName("Изменения данных не зарегистрированного пользователя")
    public void updateDataUnregisteredUserTest() {
        userForUpdate.setEmail(RandomStringUtils.randomAlphabetic(6) + "@changeableness.com");
        ValidatableResponse response = stepsUser.updateUser("", userForUpdate);
        methods.checkUpdateNoUser(response);

    }
}
