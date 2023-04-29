package praktikum;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class StepsUser extends Client {
    private static final String CREATE_USER = "/api/auth/register";
    private static final String LOGIN_USER = "/api/auth/login ";
    private static final String USER_USER = "/api/auth/user";

    @Step("Создание пользователя")
    public ValidatableResponse createUser(User user) {
        return given()
                .spec(getSpecification())
                .body(user)
                .when()
                .post(CREATE_USER)
                .then().log().all();
    }

    @Step("Авторизация пользователя")
    public ValidatableResponse loginUser(UserLogin UserLogin) {
        return given()
                .spec(getSpecification())
                .body(UserLogin)
                .when()
                .post(LOGIN_USER)
                .then().log().all();
    }

    @Step("Удаление пользователя")
    public ValidatableResponse deleteUser(String accessToken) {
        return given()
                .spec(getSpecification())
                .header("Authorization", accessToken)
                .when()
                .delete(USER_USER)
                .then().log().all();

    }

    @Step("Обновление пользователя")
    public ValidatableResponse updateUser(String accessToken, User user) {
        return given()
                .spec(getSpecification())
                .header("Authorization", accessToken)
                .body(user)
                .when()
                .patch(USER_USER)
                .then().log().all();
    }

    @Step("Получение данных пользователя")
    public ValidatableResponse getDataUser(String accessToken) {
        return given()
                .spec(getSpecification())
                .header("Authorization", accessToken)
                .when()
                .get(USER_USER)
                .then().log().all();
    }
}
