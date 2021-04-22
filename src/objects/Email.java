package objects;

import JCode.CommonTasks;

import java.io.Serializable;
import java.text.ParseException;
import java.util.List;

public class Email implements Serializable {

    private int code, messageNo, freeze = 0, manualEmail = 0, sent, ticketNo,userCode,esno,isAllocatedBy;
    private String subject, timestamp, body,  type, status,upload_Attach,lockedTime,solvedTime,duration;
    private Users users;
    List<String> toAddress, fromAddress, ccAddress, bccAddress, attachment;
    private boolean sendAsEmail = false;
    private List<Note> eNoteList;
    public static boolean isEmailTypeSent = false;
    private List<History> history;


    public Email() {
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

    public int getFreeze() {
        return freeze;
    }

    public void setFreeze(int freeze) {
        this.freeze = freeze;
    }

    public String getDuration() throws ParseException {
        return CommonTasks.getTimeDuration(lockedTime,solvedTime);
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public int getManualEmail() {
        return manualEmail;
    }

    public void setManualEmail(int manualEmail) {
        this.manualEmail = manualEmail;
    }

    public int getSent() {
        return sent;
    }

    public void setSent(int sent) {
        this.sent = sent;
    }

    public int getTicketNo() {
        return ticketNo;
    }

    public void setTicketNo(int ticketNo) {
        this.ticketNo = ticketNo;
    }

    public int getUserCode() {
        return userCode;
    }

    public void setUserCode(int userCode) {
        this.userCode = userCode;
    }

    public int getEsno() {
        return esno;
    }

    public void setEsno(int esno) {
        this.esno = esno;
    }

    public int getIsAllocatedBy() {
        return isAllocatedBy;
    }

    public void setIsAllocatedBy(int isAllocatedBy) {
        this.isAllocatedBy = isAllocatedBy;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTimestamp() {
        return CommonTasks.getDateTimeFormat(timestamp);
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUpload_Attach() {
        return upload_Attach;
    }

    public void setUpload_Attach(String upload_Attach) {
        this.upload_Attach = upload_Attach;
    }

    public String getLockedTime() {
        return CommonTasks.getDateTimeFormat(lockedTime);
    }

    public void setLockedTime(String lockedTime) {
        this.lockedTime = lockedTime;
    }

    public String getSolvedTime() {
        return CommonTasks.getDateTimeFormat(solvedTime);
    }

    public void setSolvedTime(String solvedTime) {
        this.solvedTime = solvedTime;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
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

    public List<String> getAttachment() {
        return attachment;
    }

    public void setAttachment(List<String> attachment) {
        this.attachment = attachment;
    }

    public boolean isSendAsEmail() {
        return sendAsEmail;
    }

    public void setSendAsEmail(boolean sendAsEmail) {
        this.sendAsEmail = sendAsEmail;
    }

    public List<Note> geteNoteList() {
        return eNoteList;
    }

    public void seteNoteList(List<Note> eNoteList) {
        this.eNoteList = eNoteList;
    }

    public static boolean isIsEmailTypeSent() {
        return isEmailTypeSent;
    }

    public static void setIsEmailTypeSent(boolean isEmailTypeSent) {
        Email.isEmailTypeSent = isEmailTypeSent;
    }

    public List<History> getHistory() {
        return history;
    }

    public void setHistory(List<History> history) {
        this.history = history;
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
