package objects;

import java.io.Serializable;

public class PhoneList implements Serializable {
    private int phoneID,  leadsId;
    private Integer clientID,contactID,userCode;
    private Client clientPhoneList;
    private String number;
    private Contact contactPhoneList;
    private Users users;
    public PhoneList(String mobile) {
        this.number=mobile;
    }
    public PhoneList(int phoneID,String mobile,int userCode,int clientID) {
        this.phoneID=phoneID;
        this.number=mobile;
        this.userCode = userCode;
        this.clientID = clientID;
    }
    public PhoneList(String mobile,int userCode,int clientID) {
        this.number=mobile;
        this.userCode = userCode;
        this.clientID = clientID;
    }
    public PhoneList(String mobile,int userCode,int clientID,int contactID) {
        this.number=mobile;
        this.userCode = userCode;
        this.clientID = clientID;
        this.contactID=contactID;
    }
    public PhoneList(int phoneID,String mobile,int userCode,int clientID,int contactID) {
        this.phoneID=phoneID;
        this.number=mobile;
        this.userCode = userCode;
        this.clientID = clientID;
        this.contactID=contactID;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public Contact getContactPhoneList() {
        return contactPhoneList;
    }

    public void setContactPhoneList(Contact contactPhoneList) {
        this.contactPhoneList = contactPhoneList;
    }

    public int getPhoneID() {
        return phoneID;
    }

    public void setPhoneID(int phoneID) {
        this.phoneID = phoneID;
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

    public int getLeadsId() {
        return leadsId;
    }

    public void setLeadsId(int leadsId) {
        this.leadsId = leadsId;
    }

    public Client getClientPhoneList() {
        return clientPhoneList;
    }

    public void setClientPhoneList(Client clientPhoneList) {
        this.clientPhoneList = clientPhoneList;
    }



    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "PhoneList{" +
                "phoneID=" + phoneID +
                ", clientID=" + clientID +
                ", userCode=" + userCode +
                ", contactID=" + contactID +
                ", leadsId=" + leadsId +
                ", client=" + clientPhoneList +
                ", number='" + number + '\'' +
                ", contact=" + contactPhoneList +
                ", users=" + users +
                '}';
    }
}
