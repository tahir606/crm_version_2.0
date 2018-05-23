package objects;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Lead {
    
    private int code;
    private String firstName, lastName, company, title, website, desc;
//    private SimpleIntegerProperty codeProperty;
//    private SimpleStringProperty firstNameProperty;
    
    public Lead() {
    }
    
//    public final String getFirstName()
//    {
//        if (firstName == null)
//            return firstName;
//        else
//            return firstNameProperty.get();
//    }
//    public final void setFirstName(String value)
//    {
//        if (firstNameProperty == null)
//            firstName = value;
//        else
//            firstNameProperty.set(value);
//    }
//    public final StringProperty firstNameProperty()
//    {
//        if (firstNameProperty == null)
//            firstNameProperty = new SimpleStringProperty(
//                    this, "firstName", firstName);
//        return firstNameProperty;
//    }
    
    
    public int getCode() {
        return code;
    }
    
    public void setCode(int code) {
        this.code = code;
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public String getCompany() {
        return company;
    }
    
    public void setCompany(String company) {
        this.company = company;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getWebsite() {
        return website;
    }
    
    public void setWebsite(String website) {
        this.website = website;
    }
    
    public String getDesc() {
        return desc;
    }
    
    public void setDesc(String desc) {
        this.desc = desc;
    }
}
