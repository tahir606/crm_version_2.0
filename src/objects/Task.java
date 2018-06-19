package objects;

import JCode.CommonTasks;

public class Task {
    
    private int code, createdByCode, contact, client, lead, product;
    private String subject, dueDate, desc, createdOn, createdBy, contactName, clientName, leadName, productName;
    private boolean isRepeat, status;   //Status: 0 is open ? 1 is closed
    
    public Task() {
    }
    
    @Override
    public String toString() {
        return "Task{" +
                "code=" + code +
                ", subject='" + subject + '\'' +
                ", dueDate='" + dueDate + '\'' +
                ", desc='" + desc + '\'' +
                ", createdOn='" + createdOn + '\'' +
                ", createdBy='" + createdBy + '\'' +
                ", isRepeat=" + isRepeat +
                '}';
    }
    
    public int getCode() {
        return code;
    }
    
    public void setCode(int code) {
        this.code = code;
    }
    
    public int getCreatedByCode() {
        return createdByCode;
    }
    
    public void setCreatedByCode(int createdByCode) {
        this.createdByCode = createdByCode;
    }
    
    public String getSubject() {
        return subject;
    }
    
    public void setSubject(String subject) {
        this.subject = subject;
    }
    
    public String getDueDate() {
        return dueDate;
    }
    
    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }
    
    public String getDesc() {
        return desc;
    }
    
    public void setDesc(String desc) {
        this.desc = desc;
    }
    
    public String getCreatedOn() {
        return createdOn;
    }
    
    public void setCreatedOn(String createdOn) {
        this.createdOn = CommonTasks.getTimeFormatted(createdOn);
    }
    
    public String getCreatedBy() {
        return createdBy;
    }
    
    public void setCreatedBy(String createdBy) {
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

    public int getLead() {
        return lead;
    }

    public void setLead(int lead) {
        this.lead = lead;
    }

    public int getProduct() {
        return product;
    }

    public void setProduct(int product) {
        this.product = product;
    }

    public String getContactName() {
        return contactName;
    }
    
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }
    
    public String getClientName() {
        return clientName;
    }
    
    public void setClientName(String clientName) {
        this.clientName = clientName;
    }
    
    public String getLeadName() {
        return leadName;
    }
    
    public void setLeadName(String leadName) {
        this.leadName = leadName;
    }
    
    public String getProductName() {
        return productName;
    }
    
    public void setProductName(String productName) {
        this.productName = productName;
    }
    
    public boolean isRepeat() {
        return isRepeat;
    }
    
    public void setRepeat(boolean repeat) {
        isRepeat = repeat;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
