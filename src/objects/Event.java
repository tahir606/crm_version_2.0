package objects;

import java.io.Serializable;

public class Event implements Serializable {
    private int eventID, eventAllDay, notified, leadsId, productID, createdBy, freeze, status, clientID;
    private String tittle, location, createdOn, closedOn, from, to, description;
    private Client clientEventList;
    private Users users;

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public int getEventID() {
        return eventID;
    }

    public void setEventID(int eventID) {
        this.eventID = eventID;
    }

    public int getEventAllDay() {
        return eventAllDay;
    }

    public void setEventAllDay(int eventAllDay) {
        this.eventAllDay = eventAllDay;
    }

    public int getNotified() {
        return notified;
    }

    public void setNotified(int notified) {
        this.notified = notified;
    }

    public int getLeadsId() {
        return leadsId;
    }

    public void setLeadsId(int leadsId) {
        this.leadsId = leadsId;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getClientID() {
        return clientID;
    }

    public void setClientID(int clientID) {
        this.clientID = clientID;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public String getClosedOn() {
        return closedOn;
    }

    public void setClosedOn(String closedOn) {
        this.closedOn = closedOn;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Client getClientEventList() {
        return clientEventList;
    }

    public void setClientEventList(Client clientEventList) {
        this.clientEventList = clientEventList;
    }

    @Override
    public String toString() {
        return "Event{" +
                "eventID=" + eventID +
                ", eventAllDay=" + eventAllDay +
                ", notified=" + notified +
                ", leadsId=" + leadsId +
                ", productID=" + productID +
                ", createdBy=" + createdBy +
                ", freeze=" + freeze +
                ", status=" + status +
                ", clientID=" + clientID +
                ", tittle='" + tittle + '\'' +
                ", location='" + location + '\'' +
                ", createdOn='" + createdOn + '\'' +
                ", closedOn='" + closedOn + '\'' +
                ", from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", description='" + description + '\'' +
                ", clientEventList=" + clientEventList +
                '}';
    }
}
