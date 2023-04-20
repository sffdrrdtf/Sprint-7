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
    private CourierClient loginInSystem;
    private CreateCourier courierInSystem;
    private CourierClient createCourierWithoutPassword;
    private CreateCourier create;
    private CourierClient loginCourier;
    private CreateCourier login;
    private CourierClient loginCourierWithoutPassword;
    private CreateCourier password;
    private CourierClient createCourierWithoutFieldPassword;
    private CreateCourier withoutPassword;
    private CourierClient loginCourierWithoutFieldPassword;
    private CreateCourier withoutFieldPassword;
    private String courierId;
        @Before
            public void setUp() {
                createCourier = new CourierClient();
                courier = randomCreateCourier();
                loginInSystem = new CourierClient();
                courierInSystem = randomCreateCourierInSystem();
                createCourierWithoutPassword = new CourierClient();
                create = randomCreateCourierWithoutPassword();
                loginCourier = new CourierClient();
                login= anyRandomLoginCourier();
                loginCourierWithoutPassword = new CourierClient();
                password = randomLoginCourierWithoutPassword();
                createCourierWithoutFieldPassword = new CourierClient();
                withoutPassword = randomCreateCourierWithoutFieldPassword();
                loginCourierWithoutFieldPassword = new CourierClient();
                withoutFieldPassword = randomLoginCourierWithoutFieldPassword();
            }
        @Test
        @DisplayName("Создание курьера, создание логина курьера: эндпоинт создания api/v1/courier и логина api/v1/courier/login")
        @Description("Проверка ожидаемого результата: statusCode и body")
            public void createCourier() {
            ValidatableResponse response = createCourier.requestCreateCourier(courier);
            response.assertThat().statusCode(HttpStatus.SC_CREATED).body("ok", is(true));

            ValidatableResponse loginResponse = createCourier.requestCreateLogin(credsFrom(courier));
            courierId = loginResponse.extract().path("id").toString();
            loginResponse.assertThat().statusCode(HttpStatus.SC_OK).body("id", notNullValue());
        }
        @Test
        @DisplayName("Создание курьера, курьера с тем же логином и паролем: эндпоинт api/v1/courier")
        @Description("Проверка ожидаемого результата: statusCode и body")
            public void loginCourierInSystem() {
            ValidatableResponse system = loginInSystem.requestCreateCourierInSystem(courierInSystem);
            system.assertThat().statusCode(HttpStatus.SC_CREATED).body("ok", is(true));

            ValidatableResponse createResponse = loginInSystem.requestCreateCourierInSystem(courierInSystem);
            createResponse.assertThat().statusCode(HttpStatus.SC_CONFLICT).body("message",
                    is("Этот логин уже используется. Попробуйте другой."));

        }
        @Test
        @DisplayName("Создание курьера без логина или пароля: эндпоинт логина api/v1/courier")
        @Description("Проверка ожидаемого результата: statusCode и body")
            public void requestWithoutLoginOrPasswordTest(){
                ValidatableResponse loginResponse = createCourierWithoutPassword.requestCreateWithoutLoginOrPassword(create);
                loginResponse.assertThat().statusCode(HttpStatus.SC_BAD_REQUEST).body("message",
                    is("Недостаточно данных для создания учетной записи"));
        }
    @Test
    @DisplayName("Создание курьера без поля пароля: эндпоинт логина api/v1/courier")
    @Description("Проверка ожидаемого результата: statusCode и body")
    public void requestWithoutFieldPasswordTest(){
        ValidatableResponse loginResponse = createCourierWithoutFieldPassword.requestCreateWithoutFieldPassword(withoutPassword);
        loginResponse.assertThat().statusCode(HttpStatus.SC_BAD_REQUEST).body("message",
                is("Недостаточно данных для создания учетной записи"));
    }

        @Test
        @DisplayName("Создание логина курьера в системе, с не существующей парой логин/пароль: эндпоинт логина api/v1/courier/login")
        @Description("Проверка ожидаемого результата: statusCode и body")
            public void requestCreateLoginWithNonExistentLoginPasswordTest() {
                ValidatableResponse loginResponse = loginCourier.requestCreateLoginWithNonExistentLoginPassword(login);
                loginResponse.assertThat().statusCode(HttpStatus.SC_NOT_FOUND).body("message",
                    is("Учетная запись не найдена"));
            }
        @Test
        @DisplayName("Создание логина курьера в системе, без логина или пароля: эндпоинт логина api/v1/courier/login")
        @Description("Проверка ожидаемого результата: statusCode и body")
            public void requestCreateLoginWithoutLoginOrPasswordTest(){
                ValidatableResponse loginResponse = loginCourierWithoutPassword.requestCreateLoginWithoutLoginOrPassword(password);
                loginResponse.assertThat().statusCode(HttpStatus.SC_BAD_REQUEST).body("message",
                    is("Недостаточно данных для входа"));
            }
    @Test
    @DisplayName("Создание логина курьера в системе, без поля пароля: эндпоинт логина api/v1/courier/login")
    @Description("Проверка ожидаемого результата: statusCode")
    public void requestCreateLoginWithoutFieldPasswordTest(){
        ValidatableResponse loginResponse = loginCourierWithoutFieldPassword.requestCreateLoginWithoutFieldPassword(withoutFieldPassword);
        loginResponse.assertThat().statusCode(HttpStatus.SC_GATEWAY_TIMEOUT);
    }
        @After
       public void tearDown()
        {
            createCourier.courierDelete(courierId);
        }
}
