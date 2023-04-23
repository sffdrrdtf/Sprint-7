package ru.checkapi.pojo;

public class CourierCreds {

    private final String login;
    private final String password;

    public CourierCreds(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public static CourierCreds credsFrom(CreateCourier courier) {
        return new CourierCreds(courier.getLogin(), courier.getPassword());
    }
}
