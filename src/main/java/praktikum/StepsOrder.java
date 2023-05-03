package praktikum;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class StepsOrder extends Client {
    private static final String CREATE_ORDER = "/api/orders";
    private static final String INGREDIENTS = "/api/ingredients";
    private static final String GET_ORDER = "/api/orders";

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
                .then().log().all();

    }

    @Step("Создание заказа авторизированного пользователя без ингридиентов")
    public ValidatableResponse createOrderAuthorizedUserWithoutIngredients(String accessToken) {
        return given()
                .spec(getSpecification())
                .header("Authorization", accessToken)
                .when()
                .post(CREATE_ORDER)
                .then().log().all();
    }


    @Step("Получение заказов с регистрацией")
    public ValidatableResponse getOrdersWithRegistration(String accessToken) {
        return given()
                .spec(getSpecification())
                .header("Authorization", accessToken)
                .when().get(GET_ORDER)
                .then().log().all();

    }

    @Step("Получение ингридиентов")
    public ValidatableResponse getIngredients() {
        return given()
                .spec(getSpecification())
                .when()
                .get(INGREDIENTS)
                .then().log().all();
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
