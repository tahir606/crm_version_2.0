package objects;

import java.io.Serializable;

public class Note implements Serializable {
    private int noteCode, psID,leadsId,freeze;
    private Integer contactID , clientID , emailId ,createdBy;
    private String text;
    private String createdOn;
    private Users users;
    private Email emailTickets;
    private Contact contactNoteList;
    private Client clientNoteList;

    public Note() {
    }

    public int getNoteCode() {
        return noteCode;
    }

    public void setNoteCode(int noteCode) {
        this.noteCode = noteCode;
    }

    public int getPsID() {
        return psID;
    }

    public void setPsID(int psID) {
        this.psID = psID;
    }

    public int getLeadsId() {
        return leadsId;
    }

    public void setLeadsId(int leadsId) {
        this.leadsId = leadsId;
    }

    public int getFreeze() {
        return freeze;
    }

    public void setFreeze(int freeze) {
        this.freeze = freeze;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
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

    public int getEmailId() {
        return emailId;
    }

    public void setEmailId(int emailId) {
        this.emailId = emailId;
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
        this.createdOn = createdOn;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public Email getEmailTickets() {
        return emailTickets;
    }

    public void setEmailTickets(Email emailTickets) {
        this.emailTickets = emailTickets;
    }

    public Contact getContactNoteList() {
        return contactNoteList;
    }

    public void setContactNoteList(Contact contactNoteList) {
        this.contactNoteList = contactNoteList;
    }

    public Client getClientNoteList() {
        return clientNoteList;
    }

    public void setClientNoteList(Client clientNoteList) {
        this.clientNoteList = clientNoteList;
    }


    @Override
    public String toString() {
        if (emailTickets ==null){
            return "  "+text + "      " + createdOn + "      " + users.getFullName();
        }else{
            return "  "+text + "      " + createdOn + "      " + noteCode+ "      " + emailTickets.getCode() + "    " + users.getUserCode();
        }

    }
}
