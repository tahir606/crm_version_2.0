package objects;

import JCode.CommonTasks;

import java.io.Serializable;
import java.util.List;

public class Product implements Serializable {
    private int psID, price, status, type, property, createdBy;
    private String description, started, name, createdOn;

    private Users users;

    private List<Note> pdNoteList;

    private List<ProductModule> pdProductModule;

    private List<Task> pdTaskList;

    private List<Event> pdEventList;


    public Product() {
    }

    public Product(String name, List<ProductModule> productModuleList) {
        this.name=name;
        this.pdProductModule=productModuleList;
    }



    public int getPsID() {
        return psID;
    }

    public void setPsID(int psID) {
        this.psID = psID;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getProperty() {
        return property;
    }

    public void setProperty(int property) {
        this.property = property;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStarted() {
        return CommonTasks.getSimpleDate(started);
    }

    public void setStarted(String started) {
        this.started = started;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public List<Note> getPdNoteList() {
        return pdNoteList;
    }

    public void setPdNoteList(List<Note> pdNoteList) {
        this.pdNoteList = pdNoteList;
    }

    public List<ProductModule> getPdProductModule() {
        return pdProductModule;
    }

    public void setPdProductModule(List<ProductModule> pdProductModuleOld) {
        this.pdProductModule = pdProductModuleOld;
    }

    public List<Task> getPdTaskList() {
        return pdTaskList;
    }

    public void setPdTaskList(List<Task> pdTaskList) {
        this.pdTaskList = pdTaskList;
    }

    public List<Event> getPdEventList() {
        return pdEventList;
    }

    public void setPdEventList(List<Event> pdEventList) {
        this.pdEventList = pdEventList;
    }

    @Override
    public String toString() {
        return "Product{" +
                "psID=" + psID +
                ", price=" + price +
                ", status=" + status +
                ", type=" + type +
                ", property=" + property +
                ", createdBy=" + createdBy +
                ", description='" + description + '\'' +
                ", started='" + started + '\'' +
                ", name='" + name + '\'' +
                ", createdOn='" + createdOn + '\'' +
                ", users=" + users +
                ", pdNoteList=" + pdNoteList +
                ", pdProductModule=" + pdProductModule +
                ", pdTaskList=" + pdTaskList +
                ", pdEventList=" + pdEventList +
                '}';
    }
}
