package praktikum;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;

public class Methods {
    @Step("Проверка кода ответа")
    public void checkCreateUserResponse(ValidatableResponse response, int code, Boolean status) {
        response
                .statusCode(SC_OK)
                .and()
                .assertThat().body("success", is(true));
    }


    @Step("Проверка кода ответа при создании одинакового пользователя")
    public void checkCorrectUserDoubleResponse(ValidatableResponse response) {
        response
                .statusCode(SC_FORBIDDEN)
                .and()
                .assertThat().body("message", equalTo("User already exists"));
    }


    @Step("Проверка кода ответа при получения заказа без пользователя")
    public void checkUpdateNoUser(ValidatableResponse response) {
        response
                .statusCode(SC_UNAUTHORIZED)
                .and()
                .assertThat().body("message", equalTo("You should be authorised"));

    }

    @Step("Проверка кода ответа при создании пользователя без email, password and name")
    public void checkCorrectCreateUserResponse(ValidatableResponse response) {
        response
                .statusCode(SC_FORBIDDEN)
                .and()
                .assertThat().body("message", equalTo("Email, password and name are required fields"));
    }

    @Step("Проверка кода ответа при создании заказа без пользователя")
    public void checkOrderNoUser(ValidatableResponse response) {
        response
                .statusCode(SC_UNAUTHORIZED)
                .and()
                .assertThat().body("message", equalTo("You should be authorised"));
    }

    @Step("Проверка кода ответа при неправильном email или password")
    public void checkEmailOrPasswordIncorrect(ValidatableResponse response) {
        response
                .statusCode(SC_UNAUTHORIZED)
                .and()
                .assertThat().body("message", equalTo("email or password are incorrect"));
    }

    @Step("Проверка кода создания заказа")
    public static void checkCreateOrderResponse(ValidatableResponse response, int code, Boolean status) {
        response
                .statusCode(SC_OK)
                .and()
                .assertThat().body("success", is(true));
    }
}
