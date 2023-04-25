package praktikum;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;

public class Steps extends Client{
    private static final String CREATE_USER = "/api/auth/register";
    private static final String LOGIN_USER = "/api/auth/login ";
    private static final String USER_USER = "/api/auth/user";
    private static final String CREATE_ORDER = "/api/orders";
    private static final String INGREDIENTS = "/api/ingredients";
    private static final String GET_ORDER = "/api/orders";

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

    @Step("Создание заказа не авторизированного пользователя")
    public ValidatableResponse createOrderUnauthorizedUser() {
        return given()
                .spec(getSpecification())
                .when()
                .body("{\n\"ingredients\": [\"61c0c5a71d1f82001bdaaa6d\"," +
                        "\"61c0c5a71d1f82001bdaaa70\",\"61c0c5a71d1f82001bdaaa73\"]\n}")
                .post(CREATE_ORDER)
                .then().log().all();
    }

    @Step("Создание заказа авторизированного пользователя")
    public void createOrderAuthorizedUser(String accessToken) {
        given()
                .spec(getSpecification())
                .header("Authorization", accessToken)
                .when()
                .body("{\n\"ingredients\": [\"61c0c5a71d1f82001bdaaa6d\"," +
                        "\"61c0c5a71d1f82001bdaaa70\",\"61c0c5a71d1f82001bdaaa73\"]\n}")
                .post(CREATE_ORDER)
                .then().log().all();
    }

    @Step("Создание заказа авторизированного пользователя c неверным хешем")
    public void createOrderAuthorizedUserWithInvalidHash(String accessToken) {
        given()
                .spec(getSpecification())
                .header("Authorization", accessToken)
                .when()
                .body("{\n\"ingredients\": [\"91c0c5a71d1f82001bdaaa6d\"," +
                        "\"91c0c5a71d1f82001bdaaa70\",\"91c0c5a71d1f82001bdaaa73\"]\n}")
                .post(CREATE_ORDER)
                .then().log().all()
                .assertThat().statusCode(400)
                .body("message", equalTo("One or more ids provided are incorrect"));
    }

    @Step("Создание заказа авторизированного пользователя без ингридиентов")
    public ValidatableResponse createOrderAuthorizedUserWithoutIngredients(String accessToken) {
        return given()
                .spec(getSpecification())
                .header("Authorization", accessToken)
                .when()
                .post(CREATE_ORDER)
                .then().log().all()
                .assertThat().statusCode(400)
                .body("message", equalTo("Ingredient ids must be provided"));
    }

    @Step("Получение ингридиентов")
    public ValidatableResponse getIngredients() {
        return given()
                .spec(getSpecification())
                .when()
                .get(INGREDIENTS)
                .then().log().all();
    }

    @Step("Получение заказов с регистрацией")
    public ValidatableResponse getOrdersWithRegistration(String accessToken) {
        return given()
                .spec(getSpecification())
                .header("Authorization", accessToken)
                .when().get(GET_ORDER)
                .then().log().all()
                .assertThat().statusCode(200)
                .body("success", is(true));
    }

    @Step("Получение заказа без регистрации")
    public ValidatableResponse getOrdersWithoutRegistration() {
        return given()
                .spec(getSpecification())
                .when()
                .get(GET_ORDER)
                .then().log().all();
    }
}
