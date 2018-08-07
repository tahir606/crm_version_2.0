package objects;

import JCode.CommonTasks;

public class Event {

    private int code, client, lead, createdByCode;
    private String title, location, desc, fromDate, toDate, fromTime, toTime, relationName, createdOn, createdBy, fromDateTime, toDateTime, statusString;
    private boolean isAllDay, status;

    public Event() {
    }

    @Override
    public String toString() {
        return "Event{" +
                "code=" + code +
                ", client=" + client +
                ", lead=" + lead +
                ", createdBy=" + createdBy +
                ", title='" + title + '\'' +
                ", location='" + location + '\'' +
                ", desc='" + desc + '\'' +
                ", fromDate='" + fromDate + '\'' +
                ", toDate='" + toDate + '\'' +
                ", relationName='" + relationName + '\'' +
                ", createdOn='" + createdOn + '\'' +
                ", isAllDay=" + isAllDay +
                '}';
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
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

    public int getCreatedByCode() {
        return createdByCode;
    }

    public void setCreatedByCode(int createdByCode) {
        this.createdByCode = createdByCode;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public String getFromTime() {
        return fromTime;
    }

    public void setFromTime(String fromTime) {
        this.fromTime = fromTime;
    }

    public String getToTime() {
        return toTime;
    }

    public void setToTime(String toTime) {
        this.toTime = toTime;
    }

    public String getRelationName() {
        return relationName;
    }

    public void setRelationName(String relationName) {
        this.relationName = relationName;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = CommonTasks.getTimeFormatted(createdOn);
    }

    public boolean isAllDay() {
        return isAllDay;
    }

    public void setAllDay(boolean allDay) {
        isAllDay = allDay;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
        if (status)
            statusString = "Closed";
        else
            statusString = "Open";
    }

    public String getStatusString() {
        return statusString;
    }
}
