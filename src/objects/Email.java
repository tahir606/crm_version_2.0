package objects;


import javax.mail.Address;
import java.io.File;
import java.io.Serializable;
import java.util.List;

public class Email implements Serializable {

    private int code, messageNo, locked = 0, solvedBy, freeze = 0, emailStoreNo, manualEmail = 0, sent, ticketNo;
    private String toAddress, fromAddress, ccAddress, bccAddress;
    private String subject, timestamp, timeFormatted, body, attachment, userCode, type, uploadedDocumentsString, lockedByName, disclaimer, solvedByName, createdBy, lockTime, solveTime, status;
    private Users users;
    private Address[] toAddresses, fromAddresses, ccAddresses, bccAddresses;
    private List<File> attachments;
    private List<Document> documents;
    private List<ContactProperty> relatedContacts;
    private List<ClientProperty> relatedClients;
    private char solved = 'N';
    public static boolean isEmailTypeSent = false;
    private String rawContent;
    private List<Note> notes;
    private List<History> history;

    public Email() {
    }

    public Address[] getToAddresses() {
        return toAddresses;
    }

    public String getToAddressString() {
        String s = "";
        for (Address ad : toAddresses) {
            if (ad != null) // my change
                s = s + "^" + ad;
        }
        return s;
    }

    public int getTicketNo() {
        return ticketNo;
    }

    public void setTicketNo(int ticketNo) {
        this.ticketNo = ticketNo;
    }

    public void setToAddresses(Address[] toAddresses) {
        this.toAddresses = toAddresses;
    }

    public Address[] getFromAddresses() {
        return fromAddresses;
    }

    public void setFromAddresses(Address[] fromAddresses) {
        this.fromAddresses = fromAddresses;
    }

    public Address[] getCcAddresses() {
        return ccAddresses;
    }

    public String getCcAddressString() {
        String s = "";
        for (Address ad : ccAddresses) {
            if (ad != null) // my change
                s = s + "^" + ad;
        }
        return s;
    }

    public void setCcAddresses(Address[] ccAddresses) {
        this.ccAddresses = ccAddresses;
    }

    public Address[] getBccAddresses() {
        return bccAddresses;
    }

    public String getBccAddressString() {
        String s = "";
        for (Address ad : bccAddresses) {
            if (ad != null) // my change
                s = s + "^" + ad;
        }
        return s;
    }

    public void setBccAddresses(Address[] bccAddresses) {
        this.bccAddresses = bccAddresses;
    }

    public List<History> getHistory() {
        return history;
    }

    public void setHistory(List<History> history) {
        this.history = history;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getToAddress() {
        return toAddress;
    }

    public void setToAddress(String toAddress) {
        this.toAddress = toAddress;
    }

    public String getFromAddress() {
        return fromAddress;
    }

    public void setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
    }

    public String getCcAddress() {
        return ccAddress;
    }

    public void setCcAddress(String ccAddress) {
        this.ccAddress = ccAddress;
    }

    public String getBccAddress() {
        return bccAddress;
    }

    public void setBccAddress(String bccAddress) {
        this.bccAddress = bccAddress;
    }

    public String getType() {
        return type;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getMessageNo() {
        return messageNo;
    }

    public void setMessageNo(int messageNo) {
        this.messageNo = messageNo;
    }


    public List<ContactProperty> getRelatedContacts() {
        return relatedContacts;
    }

    public void setRelatedContacts(List<ContactProperty> relatedContacts) {
        this.relatedContacts = relatedContacts;
    }

    public List<ClientProperty> getRelatedClients() {
        return relatedClients;
    }

    public void setRelatedClients(List<ClientProperty> relatedClients) {
        this.relatedClients = relatedClients;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }


    public String getTimeFormatted() {
        return timeFormatted;
    }

    public void setTimeFormatted(String timeFormatted) {
        this.timeFormatted = timeFormatted;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getDisclaimer() {
        return disclaimer;
    }

    public void setDisclaimer(String disclaimer) {
        this.disclaimer = disclaimer;
    }

    public List<Document> getDocuments() {
        return documents;
    }

    public void setDocuments(List<Document> documents) {
        this.documents = documents;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public String getUploadedDocumentsString() {
        return uploadedDocumentsString;
    }

    public void setUploadedDocumentsString(String uploadedDocumentsString) {
        this.uploadedDocumentsString = uploadedDocumentsString;
    }

    public List<File> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<File> attachments) {
        this.attachments = attachments;
    }


    public char getSolved() {
        return solved;
    }

    public void setSolved(char solved) {
        this.solved = solved;
    }

    public int getLocked() {
        return locked;
    }

    public void setLocked(int locked) {
        this.locked = locked;
    }

    public String getLockedByName() {
        return lockedByName;
    }

    public void setLockedByName(String lockedByName) {
        this.lockedByName = lockedByName;
    }

    public String getLockTime() {
        return lockTime;
    }

    public void setLockTime(String lockTime) {
        this.lockTime = lockTime;
    }

    public String getSolveTime() {
        return solveTime;
    }

    public void setSolveTime(String solveTime) {
        this.solveTime = solveTime;
    }

    public int getManualEmail() {
        return manualEmail;
    }

    public void setManualEmail(int manualEmail) {
        this.manualEmail = manualEmail;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public int getSolvedBy() {
        return solvedBy;
    }

    public void setSolvedBy(int solvedBy) {
        this.solvedBy = solvedBy;
    }

    public String getSolvedByName() {
        return solvedByName;
    }

    public void setSolvedByName(String solvedByName) {
        this.solvedByName = solvedByName;
    }

    public int getEmailStoreNo() {
        return emailStoreNo;
    }

    public void setEmailStoreNo(int emailStoreNo) {
        this.emailStoreNo = emailStoreNo;
    }

    public List<Note> getNotes() {
        return notes;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }

    public int getFreeze() {
        return freeze;
    }

    public void setFreeze(int freeze) {
        this.freeze = freeze;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public int getSent() {
        return sent;
    }

    public void setSent(int sent) {
        this.sent = sent;
    }

    public String getRawContent() {
        return rawContent;
    }

    public void setRawContent(String rawContent) {
        this.rawContent = rawContent;
    }


    @Override
    public String toString() {

        String e = "";
        if (!isEmailTypeSent) {
            if (ticketNo == 0) {
                e = code + " - ";
            } else {
                e = ticketNo + " - ";
            }

            e = e + fromAddress;
        } else {
            e = code + " - ";
            e = e + toAddress;
        }

        e = e + "\n" +
                getTimestamp() + "\n" +
                getSubject();
        return e;
    }
}
