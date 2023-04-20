package ru.checkapi.client;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import ru.checkapi.pojo.CourierCreds;
import ru.checkapi.pojo.CreateCourier;

import static io.restassured.RestAssured.given;

public class CourierClient {
    public static final String BASE_URI = "https://qa-scooter.praktikum-services.ru/";
    private static final String PATH = "api/v1/courier";
    private static final String LOGIN_PATH = "api/v1/courier/login";
    public static final String PATH_ORDER = "/api/v1/orders";
    private static final String API_DELETE = "/api/v1/courier/";
    public CourierClient() {
        RestAssured.baseURI = BASE_URI;
    }
    @Step("Создание курьера")
    public ValidatableResponse requestCreateCourier(CreateCourier courier) {
        return given()
                .contentType(ContentType.JSON)
                .and()
                .body(courier)
                .when()
                .post(PATH)
                .then();
    }
    @Step("Создание курьера с тем же логином и паролем")
    public ValidatableResponse requestCreateCourierInSystem(CreateCourier courier) {
        return given()
                .contentType(ContentType.JSON)
                .and()
                .body(courier)
                .when()
                .post(PATH)
                .then();
    }
    @Step("Создание курьера без пароля")
    public ValidatableResponse requestCreateWithoutLoginOrPassword(CreateCourier courier) {
        return given()
                .contentType(ContentType.JSON)
                .and()
                .body(courier)
                .when()
                .post(PATH)
                .then();
    }
    @Step("Создание курьера без поля пароля")
    public ValidatableResponse requestCreateWithoutFieldPassword(CreateCourier courier) {
        return given()
                .contentType(ContentType.JSON)
                .and()
                .body(courier)
                .when()
                .post(PATH)
                .then();
    }
    @Step("Создание логина курьера в системе")
    public ValidatableResponse requestCreateLogin(CourierCreds creds) {
        return given()
                .contentType(ContentType.JSON)
                .and()
                .body(creds)
                .when()
                .post(LOGIN_PATH)
                .then();
    }
    @Step("Создание логина курьера в системе без пароль")
    public ValidatableResponse requestCreateLoginWithoutLoginOrPassword(CreateCourier courier) {
        return given()
                .contentType(ContentType.JSON)
                .and()
                .body(courier)
                .when()
                .post(LOGIN_PATH)
                .then();
    }
    @Step("Создание логина курьера в системе с несуществующим парой логин/пароль")
    public ValidatableResponse requestCreateLoginWithNonExistentLoginPassword(CreateCourier courier) {
        return given()
                .contentType(ContentType.JSON)
                .and()
                .body(courier)
                .when()
                .post(LOGIN_PATH)
                .then();
    }
    @Step("Создание логина курьера в системе без поля пароль")
    public ValidatableResponse requestCreateLoginWithoutFieldPassword(CreateCourier courier) {
        return given()
                .contentType(ContentType.JSON)
                .and()
                .body(courier)
                .when()
                .post(LOGIN_PATH)
                .then();
    }
    @Step("Удаление id курьера")
    public void courierDelete(String courierId) {
        given()
                .contentType(ContentType.JSON)
                .delete(API_DELETE + courierId)
                .then();
    }

}

