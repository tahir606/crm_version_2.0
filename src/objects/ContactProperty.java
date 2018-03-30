package objects;

import javafx.beans.property.SimpleStringProperty;

public class ContactProperty {

    SimpleStringProperty firstName, lastName;

    public ContactProperty() {

        firstName = new SimpleStringProperty();
        lastName = new SimpleStringProperty();

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
}
