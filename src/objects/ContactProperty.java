package objects;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;

public class ContactProperty {

    SimpleBooleanProperty isSelected;
    SimpleStringProperty firstName, lastName;

    public ContactProperty() {

        isSelected = new SimpleBooleanProperty();
        firstName = new SimpleStringProperty();
        lastName = new SimpleStringProperty();

    }

    public boolean isIsSelected() {
        return isSelected.get();
    }

    public SimpleBooleanProperty isSelectedProperty() {
        return isSelected;
    }

    public void setIsSelected(boolean isSelected) {
        this.isSelected.set(isSelected);
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
