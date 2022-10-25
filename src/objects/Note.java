package objects;

import java.io.Serializable;

public class Note implements Serializable {
    private int noteCode, freeze;
    private Integer contactID, clientID, emailId, createdBy, psID, leadsId;
    private String text, createdOn;
    private Users users;
    private Email emailTickets;
    private Contact contactNoteList;
    private Client clientNoteList;
    private Product productNoteList;
    private Lead leadsNoteList;

    public Note() {
    }

    public Note(int noteCode, Integer clientID) {
        this.noteCode = noteCode;
        this.clientID = clientID;
    }

    public void setContactID(Integer contactID) {
        this.contactID = contactID;
    }

    public void setClientID(Integer clientID) {
        this.clientID = clientID;
    }

    public void setEmailId(Integer emailId) {
        this.emailId = emailId;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public Integer getPsID() {
        return psID;
    }

    public void setPsID(Integer psID) {
        this.psID = psID;
    }

    public Integer getLeadsId() {
        return leadsId;
    }

    public void setLeadsId(Integer leadsId) {
        this.leadsId = leadsId;
    }

    public Product getProductNoteList() {
        return productNoteList;
    }

    public void setProductNoteList(Product productNoteList) {
        this.productNoteList = productNoteList;
    }

    public Lead getLeadsNoteList() {
        return leadsNoteList;
    }

    public void setLeadsNoteList(Lead leadsNoteList) {
        this.leadsNoteList = leadsNoteList;
    }

    public int getNoteCode() {
        return noteCode;
    }

    public void setNoteCode(int noteCode) {
        this.noteCode = noteCode;
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
        if (emailTickets == null) {
            return "  " + text + "      " + createdOn + "      " + users.getFullName();
        } else {
            return "  " + text + "      " + createdOn + "      " + noteCode + "      " + emailTickets.getCode() + "    " + users.getUserCode();
        }

    }


}
