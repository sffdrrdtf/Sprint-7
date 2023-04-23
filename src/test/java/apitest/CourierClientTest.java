package apitest;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.checkapi.client.CourierClient;
import ru.checkapi.pojo.CreateCourier;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static ru.checkapi.data.GeneratorCourier.*;
import static ru.checkapi.pojo.CourierCreds.credsFrom;

public class CourierClientTest {
    private CourierClient createCourier;
    private CreateCourier courier;
    private CourierClient createCourierWithoutPassword;
    private CreateCourier create;
    private CourierClient loginCourier;
    private CreateCourier login;
    private CourierClient loginCourierWithoutPassword;
    private CreateCourier password;
    private CourierClient createCourierWithoutFieldPassword;
    private CreateCourier withoutPassword;
    private CreateCourier withoutFieldPassword;
    private String courierId;
        @Before
            public void setUp() {
                createCourier = new CourierClient();
                courier = randomCreateCourier();
                createCourierWithoutPassword = new CourierClient();
                create = randomCreateCourierWithoutPassword();
                loginCourier = new CourierClient();
                login= anyRandomLoginCourier();
                loginCourierWithoutPassword = new CourierClient();
                password = randomLoginCourierWithoutPassword();
                createCourierWithoutFieldPassword = new CourierClient();
                withoutPassword = randomCreateCourierWithoutFieldPassword();
                withoutFieldPassword = randomLoginCourierWithoutFieldPassword();
            }
        @Test
        @DisplayName("Создание курьера: эндпоинт создания api/v1/courier")
        @Description("Проверка ожидаемого результата: statusCode и body")
            public void createCourier() {
            ValidatableResponse response = createCourier.requestCreateCourier(courier);
            response.assertThat().statusCode(HttpStatus.SC_CREATED).body("ok", is(true));
        }
        @Test
        @DisplayName("Логин курьера в системе: эндпоинт api/v1/courier/login")
        @Description("Проверка ожидаемого результата: statusCode и body")
            public void requestLoginCourierInSystem() {
                createCourier.requestCreateCourier(courier);
                ValidatableResponse loginResponse = createCourier.requestCreateLogin(credsFrom(courier));
                courierId = loginResponse.extract().path("id").toString();
                loginResponse.assertThat().statusCode(HttpStatus.SC_OK).body("id", notNullValue());
            }

        @Test
        @DisplayName("Создание курьера с повторяющимся логином: эндпоинт api/v1/courier")
        @Description("Проверка ожидаемого результата: statusCode и body")
            public void requestCreateCourierWithRepetitiveLogin() {
                createCourier.requestCreateCourier(courier);
                ValidatableResponse createResponse = createCourier.requestCreateCourier(courier);
                createResponse.assertThat().statusCode(HttpStatus.SC_CONFLICT).body("message",
                    is("Этот логин уже используется. Попробуйте другой."));

            }
        @Test
        @DisplayName("Создание курьера без пароля: эндпоинт логина api/v1/courier")
        @Description("Проверка ожидаемого результата: statusCode и body")
            public void requestWithoutPasswordTest(){
                ValidatableResponse loginResponse = createCourierWithoutPassword.requestCreateCourier(create);
                loginResponse.assertThat().statusCode(HttpStatus.SC_BAD_REQUEST).body("message",
                    is("Недостаточно данных для создания учетной записи"));
            }
        @Test
        @DisplayName("Создание курьера без поля пароль: эндпоинт логина api/v1/courier")
        @Description("Проверка ожидаемого результата: statusCode и body")
            public void requestWithoutFieldPasswordTest(){
                ValidatableResponse loginResponse = createCourierWithoutFieldPassword.requestCreateCourier(withoutPassword);
                loginResponse.assertThat().statusCode(HttpStatus.SC_BAD_REQUEST).body("message",
                    is("Недостаточно данных для создания учетной записи"));
            }

        @Test
        @DisplayName("Создание логин курьера в системе, с не существующей парой логин/пароль: эндпоинт логина api/v1/courier/login")
        @Description("Проверка ожидаемого результата: statusCode и body")
            public void requestCreateLoginWithNonExistentLoginPasswordTest() {
                ValidatableResponse loginResponse = loginCourier.requestCreateLoginInSystem(login);
                loginResponse.assertThat().statusCode(HttpStatus.SC_NOT_FOUND).body("message",
                    is("Учетная запись не найдена"));
            }
        @Test
        @DisplayName("Создание логин курьера в системе, без логин или пароля: эндпоинт логина api/v1/courier/login")
        @Description("Проверка ожидаемого результата: statusCode и body")
            public void requestCreateLoginWithoutPasswordTest(){
                ValidatableResponse loginResponse = loginCourierWithoutPassword.requestCreateLoginInSystem(password);
                loginResponse.assertThat().statusCode(HttpStatus.SC_BAD_REQUEST).body("message",
                    is("Недостаточно данных для входа"));
            }
        @Test
        @DisplayName("Создание логин курьера в системе, без поля пароль: эндпоинт логина api/v1/courier/login")
        @Description("Проверка ожидаемого результата: statusCode")
            public void requestCreateLoginWithoutFieldPasswordTest(){
                ValidatableResponse loginResponse = loginCourier.requestCreateLoginInSystem(withoutFieldPassword);
                loginResponse.assertThat().statusCode(HttpStatus.SC_GATEWAY_TIMEOUT);
            }
        @After
       public void tearDown()
        {
            createCourier.courierDelete(courierId);
        }
}
