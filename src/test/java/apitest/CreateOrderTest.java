package apitest;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.checkapi.pojo.CreateOrder;

import java.util.List;

import static io.restassured.RestAssured.delete;
import static io.restassured.RestAssured.given;
import static ru.checkapi.client.CourierClient.BASE_URI;
@RunWith(Parameterized.class)
public class CreateOrderTest {
    private final List<String> color;
    public CreateOrderTest(List<String> color) {
        this.color = color;
    }


    @Parameterized.Parameters(name = "{index}: Заказ с цветом: {0}")
    public static Object[][] checkCreateOrderWithChoiceColor() {
        return new Object[][]{
                {List.of("GREY")},
                {List.of("BLACK")},
                {List.of("GREY", "BLACK")},
                {List.of()},
        };
    }

    @Before
    public void setUp() {
        RestAssured.baseURI = BASE_URI;
    }

    @Test
    @DisplayName("Создание заказа с выбором цвета: эндпоинт /api/v1/orders")
    @Description("Проверка, что можно создать заказ на: серый цвет, черный цвет, выбор двух цветов, без выбора цвета")
    public void ParamCreateOrderTest() {
        CreateOrder order = new CreateOrder ("Виктор", "Иванов", "Ижорская, 10", "4", "6800188888", 4, "2023-10-11", "Доставить самокат после 3 часов дня", color);
        Response response = given()
                .contentType(ContentType.JSON)
                .and()
                .body(order)
                .when()
                .post("/api/v1/orders" );
        response.then().assertThat().body("$", Matchers.allOf(Matchers.hasKey("track")))
                .statusCode(HttpStatus.SC_CREATED);
    }
    @After
    public void setDown(){
        delete("track");
    }
}

