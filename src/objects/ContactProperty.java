package objects;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class ContactProperty {

    SimpleIntegerProperty code, age, clID;
    SimpleStringProperty firstName, lastName, fullName, city, country, email, mobile, dob, clientName;

    public ContactProperty() {

        code = new SimpleIntegerProperty();

        firstName = new SimpleStringProperty();
        lastName = new SimpleStringProperty();
        fullName = new SimpleStringProperty();
        city = new SimpleStringProperty();
        country = new SimpleStringProperty();
        email = new SimpleStringProperty();
        mobile = new SimpleStringProperty();
        dob = new SimpleStringProperty();
        age = new SimpleIntegerProperty();

        clientName = new SimpleStringProperty();

    }

    @Override
    public String toString() {
        return "ContactProperty{" +
                "code=" + code +
                ", firstName=" + firstName +
                ", lastName=" + lastName +
                ", fullName=" + fullName +
                ", city=" + city +
                ", country=" + country +
                ", email=" + email +
                ", mobile=" + mobile +
                ", dob=" + dob +
                ", age=" + age +
                '}';
    }

    public int getCode() {
        return code.get();
    }

    public SimpleIntegerProperty codeProperty() {
        return code;
    }

    public void setCode(int code) {
        this.code.set(code);
    }

    public String getFirstName() {
        return firstName.get();
    }

    public SimpleStringProperty firstNameProperty() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public String getLastName() {
        return lastName.get();
    }

    public SimpleStringProperty lastNameProperty() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    public String getFullName() {
        return firstName.get() + " " + lastName.get();
    }

    public String getCity() {
        return city.get();
    }

    public SimpleStringProperty cityProperty() {
        return city;
    }

    public void setCity(String city) {
        this.city.set(city);
    }

    public String getCountry() {
        return country.get();
    }

    public SimpleStringProperty countryProperty() {
        return country;
    }

    public void setCountry(String country) {
        this.country.set(country);
    }

    public String getEmail() {
        return email.get();
    }

    public SimpleStringProperty emailProperty() {
        return email;
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public String getMobile() {
        return mobile.get();
    }

    public SimpleStringProperty mobileProperty() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile.set(mobile);
    }

    public String getDob() {
        return dob.get();
    }

    public SimpleStringProperty dobProperty() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob.set(dob);
    }

    public int getAge() {
        return age.get();
    }

    public SimpleIntegerProperty ageProperty() {
        return age;
    }

    public void setAge(int age) {
        this.age.set(age);
    }

    public int getClID() {
        return clID.get();
    }

    public SimpleIntegerProperty clIDProperty() {
        return clID;
    }

    public void setClID(int clID) {
        this.clID.set(clID);
    }

    public String getClientName() {
        return clientName.get();
    }

    public SimpleStringProperty clientNameProperty() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName.set(clientName);
    }
}
