package ru.checkapi.data;

import com.github.javafaker.Faker;
import ru.checkapi.pojo.CreateCourier;

public class GeneratorCourier {
    static Faker faker = new Faker();
    public static CreateCourier randomCreateCourier() {
        return new CreateCourier()
                .setLogin(faker.funnyName().name())
                .setPassword(faker.number().digits(4))
                .setFirstName((faker.name().firstName()));
    }

    public static CreateCourier randomCreateCourierWithoutPassword() {
        return new CreateCourier()
                .setLogin(faker.funnyName().name())
                .setPassword("")
                .setFirstName((faker.name().firstName()));
    }
    public static CreateCourier randomCreateCourierWithoutFieldPassword() {
        return new CreateCourier()
                .setLogin(faker.funnyName().name())
                .setFirstName((faker.name().firstName()));
    }
    public static CreateCourier anyRandomLoginCourier() {
        return new CreateCourier()
                .setLogin(faker.funnyName().name())
                .setPassword(faker.number().digits(4));
    }
    public static CreateCourier randomLoginCourierWithoutPassword() {
        return new CreateCourier()
                .setLogin(faker.funnyName().name())
                .setPassword("");
    }
    public static CreateCourier randomLoginCourierWithoutFieldPassword() {
        return new CreateCourier()
                .setLogin(faker.funnyName().name());
    }
}
