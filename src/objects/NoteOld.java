package objects;

import JCode.CommonTasks;

import java.io.Serializable;

public class NoteOld implements Serializable {
    
    private int code,noteCode, createdBy,contactID=1,clientID=1,psID,leadsID, emailId=3,emailNo,freeze;
    private String text, createdOn, createdByName, contactName;
    private UsersOld usersOld;
    private EmailOld emailOldTickets;
    private Contact contact;
    private Client client;
    public NoteOld() {
    }



    @Override
    public String toString() {
        if (emailOldTickets ==null){
            return "  "+text + "      " + createdOn + "      " + createdByName;
        }else{
            return "  "+text + "      " + createdOn + "      " + noteCode+ "      " + emailOldTickets.getCode() + "    " + usersOld.getUserCode();
        }

    }

    public UsersOld getUsers() {
        return usersOld;
    }

    public void setUsers(UsersOld usersOld) {
        this.usersOld = usersOld;
    }

    public EmailOld getEmail() {
        return emailOldTickets;
    }

    public void setEmail(EmailOld emailOld) {
        this.emailOldTickets = emailOld;
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

    public EmailOld getEmailTickets() {
        return emailOldTickets;
    }

    public void setEmailTickets(EmailOld emailOldTickets) {
        this.emailOldTickets = emailOldTickets;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
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
