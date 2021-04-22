package objects;

import java.io.Serializable;

public class EmailList implements Serializable {
    private int emailID;
    private String name;
    private String address;
    private Integer clientID;
    private Integer userCode;
    private Integer contactID;
    private int leadsID;
    private Client clientEmailList;
    private Contact contactEmailList;
    private Users users;

    public EmailList() {
    }

    public EmailList(String address) {
        this.address = address;
    }

    public EmailList(String address, String name) {
        this.address = address;
        this.name = name;
    }

    public EmailList(int emailID, String email, int userCode, int clientID) {
        this.emailID = emailID;
        this.address = email;
        this.userCode = userCode;
        this.clientID = clientID;
    }

    public EmailList(String email, int userCode, int clientID) {
        ;
        this.address = email;
        this.userCode = userCode;
        this.clientID = clientID;
    }

    public EmailList(String email, int userCode, int clientID, int contactID) {
        ;
        this.address = email;
        this.userCode = userCode;
        this.clientID = clientID;
        this.contactID = contactID;
    }

    public EmailList(int emailID, String email, int userCode, int clientID, int contactID) {
        this.emailID = emailID;
        this.address = email;
        this.userCode = userCode;
        this.clientID = clientID;
        this.contactID = contactID;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public Client getClientEmailList() {
        return clientEmailList;
    }

    public void setClientEmailList(Client clientEmailList) {
        this.clientEmailList = clientEmailList;
    }

    public int getEmailID() {
        return emailID;
    }

    public void setEmailID(int emailID) {
        this.emailID = emailID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getClientID() {
        return clientID;
    }

    public void setClientID(int clientID) {
        this.clientID = clientID;
    }

    public int getUserCode() {
        return userCode;
    }

    public void setUserCode(int userCode) {
        this.userCode = userCode;
    }

    public int getContactID() {
        return contactID;
    }

    public void setContactID(int contactID) {
        this.contactID = contactID;
    }

    public int getLeadsID() {
        return leadsID;
    }

    public void setLeadsID(int leadsID) {
        this.leadsID = leadsID;
    }

    public Contact getContactEmailList() {
        return contactEmailList;
    }

    public void setContactEmailList(Contact contactEmailList) {
        this.contactEmailList = contactEmailList;
    }

    @Override
    public String toString() {
        return address;
    }
}
