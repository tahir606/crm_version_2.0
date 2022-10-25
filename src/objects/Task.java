package objects;

import JCode.CommonTasks;

import java.io.Serializable;

public class Task implements Serializable {
    private int taskID, repeat, status,notified,  createdBy, freeze;
    private String subject, entryDate, dueDate,closedOn,description,createdOn;
    private Users users;
    private Client clientTaskList;
    private Lead leadsTaskList;
    private Product productTaskList;
    Integer contactID, psID,leadsId,clientID;

    public Integer getContactID() {
        return contactID;
    }

    public void setContactID(Integer contactID) {
        this.contactID = contactID;
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

    public Integer getClientID() {
        return clientID;
    }

    public void setClientID(Integer clientID) {
        this.clientID = clientID;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public int getTaskID() {
        return taskID;
    }

    public void setTaskID(int taskID) {
        this.taskID = taskID;
    }

    public int getRepeat() {
        return repeat;
    }

    public void setRepeat(int repeat) {
        this.repeat = repeat;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getNotified() {
        return notified;
    }

    public void setNotified(int notified) {
        this.notified = notified;
    }



    public Lead getLeadsTaskList() {
        return leadsTaskList;
    }

    public void setLeadsTaskList(Lead leadsTaskList) {
        this.leadsTaskList = leadsTaskList;
    }

    public Product getProductTaskList() {
        return productTaskList;
    }

    public void setProductTaskList(Product productTaskList) {
        this.productTaskList = productTaskList;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    public int getFreeze() {
        return freeze;
    }

    public void setFreeze(int freeze) {
        this.freeze = freeze;
    }



    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getEntryDate() {
        return CommonTasks.convertFormatWithOutTimeZone(entryDate);
    }

    public void setEntryDate(String entryDate) {
        this.entryDate = entryDate;
    }

    public String getDueDate() {
        return CommonTasks.convertFormatWithOutTimeZone(dueDate);
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getClosedOn() {
        return closedOn;
    }

    public void setClosedOn(String closedOn) {
        this.closedOn = closedOn;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreatedOn() {
        return CommonTasks.convertFormatWithOutTimeZone(createdOn);
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public Client getClientTaskList() {
        return clientTaskList;
    }

    public void setClientTaskList(Client clientTaskList) {
        this.clientTaskList = clientTaskList;
    }

    @Override
    public String toString() {
        return "Task{" +
                "taskID=" + taskID +
                ", repeat=" + repeat +
                ", status=" + status +
                ", notified=" + notified +
                ", contactID=" + contactID +
                ", psID=" + psID +
                ", leadsId=" + leadsId +
                ", createdBy=" + createdBy +
                ", freeze=" + freeze +
                ", clientID=" + clientID +
                ", subject='" + subject + '\'' +
                ", entryDate='" + entryDate + '\'' +
                ", dueDate='" + dueDate + '\'' +
                ", closedOn='" + closedOn + '\'' +
                ", description='" + description + '\'' +
                ", createdOn='" + createdOn + '\'' +
                ", users=" + users +
                ", clientTaskList=" + clientTaskList +
                '}';
    }
}
