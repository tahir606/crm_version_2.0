package objects;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.ArrayList;
import java.util.List;

public class ContactProperty {

    SimpleIntegerProperty code, clID;
    SimpleStringProperty firstName, lastName, fullName, address, city, country, age, email, mobile, dob, note, clientName;
    SimpleBooleanProperty isFreeze;
    List<NoteOld> contactNoteOlds;

    public ContactProperty() {

        code = new SimpleIntegerProperty();

        firstName = new SimpleStringProperty();
        lastName = new SimpleStringProperty();
        fullName = new SimpleStringProperty();
        address = new SimpleStringProperty();
        city = new SimpleStringProperty();
        country = new SimpleStringProperty();
        email = new SimpleStringProperty();
        mobile = new SimpleStringProperty();
        dob = new SimpleStringProperty();
        age = new SimpleStringProperty();
        note = new SimpleStringProperty();
        isFreeze = new SimpleBooleanProperty();

        clientName = new SimpleStringProperty();
        clID = new SimpleIntegerProperty();
        
        contactNoteOlds = new ArrayList<>();
        
    }

    @Override
    public String toString() {
        return getFullName();
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

    public String getAddress() {
        return address.get();
    }

    public SimpleStringProperty addressProperty() {
        return address;
    }

    public void setAddress(String address) {
        this.address.set(address);
    }

    public String getNote() {
        return note.get();
    }

    public SimpleStringProperty noteProperty() {
        return note;
    }

    public void setNote(String note) {
        this.note.set(note);
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

    public String getAge() {
        return age.get();
    }

    public SimpleStringProperty ageProperty() {
        return age;
    }

    public void setAge(String age) {
        this.age.set(age);
    }

    public boolean isIsFreeze() {
        return isFreeze.get();
    }

    public SimpleBooleanProperty isFreezeProperty() {
        return isFreeze;
    }

    public void setIsFreeze(boolean isFreeze) {
        this.isFreeze.set(isFreeze);
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
    
    public List<NoteOld> getContactNotes() {
        return contactNoteOlds;
    }
    
    public void setContactNotes(List<NoteOld> contactNoteOlds) {
        this.contactNoteOlds = contactNoteOlds;
    }
}
