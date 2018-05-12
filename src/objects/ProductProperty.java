package objects;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class ProductProperty {

    SimpleIntegerProperty code, price, status, type, createdBy, priority;
    SimpleStringProperty name, desc, startedtimeStmp, createdOn;
    SimpleBooleanProperty freeze;

    public ProductProperty() {
        code = new SimpleIntegerProperty();
        price = new SimpleIntegerProperty();
        status = new SimpleIntegerProperty();
        type = new SimpleIntegerProperty();
        createdBy = new SimpleIntegerProperty();
        priority = new SimpleIntegerProperty();

        name = new SimpleStringProperty();
        desc = new SimpleStringProperty();
        startedtimeStmp = new SimpleStringProperty();
        createdOn = new SimpleStringProperty();

        freeze = new SimpleBooleanProperty();
    }

    public ProductProperty(SimpleIntegerProperty code, SimpleIntegerProperty price, SimpleIntegerProperty status, SimpleIntegerProperty type, SimpleIntegerProperty createdBy, SimpleIntegerProperty priority,
                           SimpleStringProperty name, SimpleStringProperty desc, SimpleStringProperty startedtimeStmp, SimpleStringProperty createdOn, SimpleBooleanProperty freeze) {
        this.code = code;
        this.price = price;
        this.status = status;
        this.type = type;
        this.createdBy = createdBy;
        this.priority = priority;
        this.name = name;
        this.desc = desc;
        this.startedtimeStmp = startedtimeStmp;
        this.createdOn = createdOn;
        this.freeze = freeze;
    }

    @Override
    public String toString() {
        return "ProductProperty{" +
                "code=" + code +
                ", name=" + name +
                '}';
    }

    public int getCode() {
        return code.get();
    }

    public SimpleIntegerProperty codeProperty() {
        return code;
    }

    public void setCode(int code) {
        this.code.set(code);
    }

    public int getPrice() {
        return price.get();
    }

    public SimpleIntegerProperty priceProperty() {
        return price;
    }

    public void setPrice(int price) {
        this.price.set(price);
    }

    public int getStatus() {
        return status.get();
    }

    public SimpleIntegerProperty statusProperty() {
        return status;
    }

    public void setStatus(int status) {
        this.status.set(status);
    }

    public int getType() {
        return type.get();
    }

    public SimpleIntegerProperty typeProperty() {
        return type;
    }

    public void setType(int type) {
        this.type.set(type);
    }

    public int getCreatedBy() {
        return createdBy.get();
    }

    public SimpleIntegerProperty createdByProperty() {
        return createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy.set(createdBy);
    }

    public int getPriority() {
        return priority.get();
    }

    public SimpleIntegerProperty priorityProperty() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority.set(priority);
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getDesc() {
        return desc.get();
    }

    public SimpleStringProperty descProperty() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc.set(desc);
    }

    public String getStartedtimeStmp() {
        return startedtimeStmp.get();
    }

    public SimpleStringProperty startedtimeStmpProperty() {
        return startedtimeStmp;
    }

    public void setStartedtimeStmp(String startedtimeStmp) {
        this.startedtimeStmp.set(startedtimeStmp);
    }

    public String getCreatedOn() {
        return createdOn.get();
    }

    public SimpleStringProperty createdOnProperty() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn.set(createdOn);
    }

    public boolean isFreeze() {
        return freeze.get();
    }

    public SimpleBooleanProperty freezeProperty() {
        return freeze;
    }

    public void setFreeze(boolean freeze) {
        this.freeze.set(freeze);
    }
}
