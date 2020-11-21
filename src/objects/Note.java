package objects;

import JCode.CommonTasks;

import java.io.Serializable;

public class Note implements Serializable {
    
    private int code,noteCode, createdBy, contact,contactID,clientID,psID,leadsID, emailId, client,emailNo,freeze;
    private String text, createdOn, createdByName, contactName;
    private Users users;
    private Email emailTickets;
    public Note() {
    }



    @Override
    public String toString() {
        return "  "+text + "      " + createdOn + "      " + noteCode+ "      " + emailTickets.getCode() + "    " + users.getUserCode();
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public Email getEmail() {
        return emailTickets;
    }

    public void setEmail(Email email) {
        this.emailTickets = email;
    }

    public int getNoteCode() {
        return noteCode;
    }

    public void setNoteCode(int noteCode) {
        this.noteCode = noteCode;
    }

    public int getContactID() {
        return contactID;
    }

    public void setContactID(int contactID) {
        this.contactID = contactID;
    }

    public int getClientID() {
        return clientID;
    }

    public void setClientID(int clientID) {
        this.clientID = clientID;
    }

    public int getPsID() {
        return psID;
    }

    public void setPsID(int psID) {
        this.psID = psID;
    }

    public int getLeadsID() {
        return leadsID;
    }

    public void setLeadsID(int leadsID) {
        this.leadsID = leadsID;
    }

    public int getFreeze() {
        return freeze;
    }

    public void setFreeze(int freeze) {
        this.freeze = freeze;
    }

    public int getEmailId() {
        return emailId;
    }

    public void setEmailId(int emailId) {
        this.emailId = emailId;
    }



    public int getEmailNo() {
        return emailNo;
    }

    public void setEmailNo(int emailNo) {
        this.emailNo = emailNo;
    }

    public int getCode() {
        return code;
    }
    
    public void setCode(int code) {
        this.code = code;
    }
    
    public int getCreatedBy() {
        return createdBy;
    }
    
    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }
    
    public int getContact() {
        return contact;
    }
    
    public void setContact(int contact) {
        this.contact = contact;
    }
    
    public int getClient() {
        return client;
    }
    
    public void setClient(int client) {
        this.client = client;
    }
    
    public String getText() {
        return text;
    }
    
    public void setText(String text) {
        this.text = text;
    }
    
    public String getCreatedOn() {
        return createdOn;
    }
    
    public void setCreatedOn(String createdOn) {
        this.createdOn = CommonTasks.getTimeFormatted(createdOn);
    }
    
    public String getCreatedByName() {
        return createdByName;
    }
    
    public void setCreatedByName(String createdByName) {
        this.createdByName = createdByName;
    }
    
    public String getContactName() {
        return contactName;
    }
    
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }
}
