package ru.checkapi.order;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import ru.checkapi.pojo.CreateOrder;
import static io.restassured.RestAssured.given;
import static ru.checkapi.client.CourierClient.PATH_ORDER;

public class ListOrder {
    public ValidatableResponse requestGetListOrders(CreateOrder order) {
        return given()
                .contentType(ContentType.JSON)
                .and()
                .body(order)
                .when()
                .post(PATH_ORDER)
                .then();
    }

    public void deleteTrack(int trackId) {
        given()
                .contentType(ContentType.JSON)
                .and()
                .delete(PATH_ORDER + trackId)
                .then();
    }
}
