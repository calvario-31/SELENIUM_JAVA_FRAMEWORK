package models.test;

import com.github.javafaker.Faker;

public class UserDataModel {
    private final String firstname;
    private final String lastname;
    private final String zipcode;

    public UserDataModel() {
        Faker faker = new Faker();
        firstname = faker.name().firstName();
        lastname = faker.name().lastName();
        zipcode = faker.address().zipCode();
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getZipcode() {
        return zipcode;
    }

    @Override
    public String toString() {
        return "{" + firstname + " " + lastname + " " + zipcode + "}";
    }
}
