package objects;

import JCode.CommonTasks;

import java.io.Serializable;
import java.util.List;

public class Contact implements Serializable {
    private int contactID, freeze, clientID, createdBy;
    private String firstName, lastName, fullName, email, mobile, age, dateOfBirth, address, city, country, note, createdOn;
    private List<EmailList> coEmailLists;
    private List<PhoneList> coPhoneLists;
    private List<Note> coNoteList;
    private Client client12;
    private Users users;

    public Contact() {
    }

    public List<Note> getCoNoteList() {
        return coNoteList;
    }

    public void setCoNoteList(List<Note> noteOldList) {
        this.coNoteList = noteOldList;
    }

    public String getMobile() {
        if (coPhoneLists.isEmpty()){
            return "";
        }else{
            return coPhoneLists.get(0).getNumber();
        }
    }

    public String getEmail() {

        if (coEmailLists.isEmpty()) {
            return "";
        } else {
            return coEmailLists.get(0).getAddress();
        }

    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public String getAge() {
        return CommonTasks.getAge(dateOfBirth);
    }

    public List<PhoneList> getCoPhoneLists() {
        return coPhoneLists;
    }

    public void setCoPhoneLists(List<PhoneList> coPhoneLists) {
        this.coPhoneLists = coPhoneLists;
    }

    public Client getClient12() {
        return client12;
    }

    public void setClient12(Client client12) {
        this.client12 = client12;
    }

    public List<EmailList> getCoEmailLists() {
        return coEmailLists;
    }

    public void setCoEmailLists(List<EmailList> coEmailLists) {
        this.coEmailLists = coEmailLists;
    }

    public int getContactID() {
        return contactID;
    }

    public void setContactID(int contactID) {
        this.contactID = contactID;
    }

    public int getFreeze() {
        return freeze;
    }

    public void setFreeze(int freeze) {
        this.freeze = freeze;
    }

    public int getClientID() {
        return clientID;
    }

    public void setClientID(int clientID) {
        this.clientID = clientID;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "contactID=" + contactID +
                ", freeze=" + freeze +
                ", clientID=" + clientID +
                ", createdBy=" + createdBy +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", mobile='" + mobile + '\'' +
                ", age='" + age + '\'' +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", note='" + note + '\'' +
                ", createdOn='" + createdOn + '\'' +
                ", coEmailLists=" + coEmailLists +
                ", coPhoneLists=" + coPhoneLists +
                ", coNoteList=" + coNoteList +
                ", client12=" + client12 +
                ", users=" + users +
                '}';
    }
}
