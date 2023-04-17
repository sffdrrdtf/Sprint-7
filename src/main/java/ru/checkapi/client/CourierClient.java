package ru.checkapi.client;

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
    public CourierClient() {
        RestAssured.baseURI = BASE_URI;
    }
    public ValidatableResponse requestCreateCourier(CreateCourier courier) {
        return given()
                .contentType(ContentType.JSON)
                .and()
                .body(courier)
                .when()
                .post(PATH)
                .then();
    }
    public ValidatableResponse requestCreateWithoutLoginOrPassword(CreateCourier courier) {
        return given()
                .contentType(ContentType.JSON)
                .and()
                .body(courier)
                .when()
                .post(PATH)
                .then();
    }
    public ValidatableResponse requestCreateWithoutFieldPassword(CreateCourier courier) {
        return given()
                .contentType(ContentType.JSON)
                .and()
                .body(courier)
                .when()
                .post(PATH)
                .then();
    }

    public ValidatableResponse requestCreateLogin(CourierCreds creds) {
        return given()
                .contentType(ContentType.JSON)
                .and()
                .body(creds)
                .when()
                .post(LOGIN_PATH)
                .then();
    }

    public ValidatableResponse requestCreateLoginWithoutLoginOrPassword(CreateCourier courier) {
        return given()
                .contentType(ContentType.JSON)
                .and()
                .body(courier)
                .when()
                .post(LOGIN_PATH)
                .then();
    }

    public ValidatableResponse requestCreateLoginWithNonExistentLoginPassword(CreateCourier courier) {
        return given()
                .contentType(ContentType.JSON)
                .and()
                .body(courier)
                .when()
                .post(LOGIN_PATH)
                .then();
    }
    public ValidatableResponse requestCreateLoginWithoutFieldPassword(CreateCourier courier) {
        return given()
                .contentType(ContentType.JSON)
                .and()
                .body(courier)
                .when()
                .post(LOGIN_PATH)
                .then();
    }
    public void delete(int courierId) {
    }
}

