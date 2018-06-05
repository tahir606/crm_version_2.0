package objects;

import client.newClient.newClientController;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.ArrayList;
import java.util.List;

public class ClientProperty {

    private SimpleIntegerProperty Code, Type;
    private SimpleStringProperty Name, owner, Addr, City, Country, Website, JoinDate, note;
    private String[] emails = new String[newClientController.noOfFields],
            phones = new String[newClientController.noOfFields];
    private List<Note> notes;

    public ClientProperty() {

        Code = new SimpleIntegerProperty();
        Type = new SimpleIntegerProperty();

        Name = new SimpleStringProperty();
        owner = new SimpleStringProperty();
        Addr = new SimpleStringProperty();
        City = new SimpleStringProperty();
        Country = new SimpleStringProperty();
        Website = new SimpleStringProperty();
        JoinDate = new SimpleStringProperty();
        note = new SimpleStringProperty();

        notes = new ArrayList<>();
    }

    @Override
    public String toString() {
        return Name.get();
    }

    public int getCode() {
        return Code.get();
    }

    public SimpleIntegerProperty codeProperty() {
        return Code;
    }

    public void setCode(int code) {
        this.Code.set(code);
    }

    public int getType() {
        return Type.get();
    }

    public SimpleIntegerProperty typeProperty() {
        return Type;
    }

    public void setType(int type) {
        this.Type.set(type);
    }

    public String getName() {
        return Name.get();
    }

    public SimpleStringProperty nameProperty() {
        return Name;
    }

    public void setName(String name) {
        this.Name.set(name);
    }

    public String getOwner() {
        return owner.get();
    }

    public SimpleStringProperty ownerProperty() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner.set(owner);
    }

    public String getAddr() {
        return Addr.get();
    }

    public SimpleStringProperty addrProperty() {
        return Addr;
    }

    public void setAddr(String addr) {
        this.Addr.set(addr);
    }

    public String getCity() {
        return City.get();
    }

    public SimpleStringProperty cityProperty() {
        return City;
    }

    public void setCity(String city) {
        this.City.set(city);
    }

    public String getCountry() {
        return Country.get();
    }

    public SimpleStringProperty countryProperty() {
        return Country;
    }

    public void setCountry(String country) {
        this.Country.set(country);
    }

    public String getWebsite() {
        return Website.get();
    }

    public SimpleStringProperty websiteProperty() {
        return Website;
    }

    public void setWebsite(String website) {
        this.Website.set(website);
    }

    public String getJoinDate() {
        return JoinDate.get();
    }

    public SimpleStringProperty joinDateProperty() {
        return JoinDate;
    }

    public void setJoinDate(String joinDate) {
        this.JoinDate.set(joinDate);
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

    public String[] getEmails() {
        return emails;
    }

    public void setEmails(String[] emails) {
        this.emails = emails;
    }

    public String getEmailsString() {
        String st = "";
        for (String s : emails) {
            if (s != null)
                st = st + s + ",";
        }
        return st;
    }

    public String[] getPhones() {
        return phones;
    }

    public void setPhones(String[] phones) {
        this.phones = phones;
    }

    public List<Note> getNotes() {
        return notes;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }
}
