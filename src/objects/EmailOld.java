package objects;


import java.io.File;
import java.io.Serializable;
import java.util.List;

import static JCode.CommonTasks.convertFormat;

public class EmailOld implements Serializable {

    private int code, messageNo, locked = 0, solvedBy, freeze = 0, emailStoreNo, manualEmail = 0, sent, ticketNo;
    private String subject, timestamp, timeFormatted, body, userCode, type, uploadedDocumentsString, lockedByName, disclaimer, solvedByName, createdBy, lockedTime, solvedTime, status;
    private Users users;

    List<String> toAddress, fromAddress, ccAddress, bccAddress, attachment;
    private List<File> attachments;
    private List<Document> documents;
    private List<ContactProperty> relatedContacts;
    private List<ClientProperty> relatedClients;
    private List<Note> eNoteList;
    public static boolean isEmailTypeSent = false;
    private boolean sendAsEmail = false;
    private List<NoteOld> noteOlds;
    private List<History> history;
    private int isAllocatedBy;

    public EmailOld() {
    }

    public List<Note> geteNoteList() {
        return eNoteList;
    }

    public void seteNoteList(List<Note> noteOldList) {
        this.eNoteList = noteOldList;
    }

    public boolean isSendAsEmail() {
        return sendAsEmail;
    }

    public void setSendAsEmail(boolean sendAsEmail) {
        this.sendAsEmail = sendAsEmail;
    }

    public List<String> getToAddress() {
        return toAddress;
    }

    public void setToAddress(List<String> toAddress) {
        this.toAddress = toAddress;
    }

    public List<String> getFromAddress() {
        return fromAddress;
    }

    public void setFromAddress(List<String> fromAddress) {
        this.fromAddress = fromAddress;
    }

    public List<String> getCcAddress() {
        return ccAddress;
    }

    public void setCcAddress(List<String> ccAddress) {
        this.ccAddress = ccAddress;
    }

    public List<String> getBccAddress() {
        return bccAddress;
    }

    public void setBccAddress(List<String> bccAddress) {
        this.bccAddress = bccAddress;
    }


    public int getTicketNo() {
        return ticketNo;
    }

    public void setTicketNo(int ticketNo) {
        this.ticketNo = ticketNo;
    }


    public int getIsAllocatedBy() {
        return isAllocatedBy;
    }

    public void setIsAllocatedBy(int isAllocatedBy) {
        this.isAllocatedBy = isAllocatedBy;
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

    public List<String> getAttachment() {
        return attachment;
    }

    public void setAttachment(List<String> attachment) {
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

    public String getLockedTime() {
        return lockedTime;
    }

    public void setLockedTime(String lockedTime) {
        this.lockedTime = lockedTime;
    }

    public String getSolvedTime() {
        return solvedTime;
    }

    public void setSolvedTime(String solvedTime) {
        this.solvedTime = solvedTime;
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

    public List<NoteOld> getNotes() {
        return noteOlds;
    }

    public void setNotes(List<NoteOld> noteOlds) {
        this.noteOlds = noteOlds;
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

//StringUtils.join(fromAddress, " ")

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
                convertFormat(getTimestamp()) + "\n" +
                getSubject();
        return e;
    }
}
