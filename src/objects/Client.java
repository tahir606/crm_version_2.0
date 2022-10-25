package objects;

import java.io.Serializable;
import java.util.List;

public class Client implements Serializable {
    private int type, createdBy, clientID;
    private String name, owner, email, phoneNo, address, city, country,website;
    private String joinDate, bicycle,createdOn;
    private List<EmailList> clEmailLists;
    private List<PhoneList> clPhoneLists;
    private List<Note> clNoteList;
    private List<Task> clTaskList;
    private List<Event> clEventList;

    private Users users;
    private Integer fromLead;
    private int countOfEmail;
    private long availableCount;
    private Lead leadsClientList;
    public long getAvailableCount() {
        return availableCount;
    }

    public void setAvailableCount(long availableCount) {
        this.availableCount = availableCount;
    }

    public int getCountOfEmail() {
        if (clEmailLists.isEmpty()){
            return 0;
        }else{
           return clEmailLists.size();
        }
    }

    public Integer getFromLead() {
        return fromLead;
    }

    public void setFromLead(Integer fromLead) {
        this.fromLead = fromLead;
    }

    public Lead getLeadsClientList() {
        return leadsClientList;
    }

    public void setLeadsClientList(Lead leadsClientList) {
        this.leadsClientList = leadsClientList;
    }

    public List<Task> getClTaskList() {
        return clTaskList;
    }

    public void setClTaskList(List<Task> clTaskList) {
        this.clTaskList = clTaskList;
    }

    public List<Event> getClEventList() {
        return clEventList;
    }

    public void setClEventList(List<Event> clEventList) {
        this.clEventList = clEventList;
    }

    public List<Note> getClNoteList() {
        return clNoteList;
    }

    public void setClNoteList(List<Note> noteOldList) {
        this.clNoteList = noteOldList;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }


    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    public int getClientID() {
        return clientID;
    }

    public void setClientID(int clientID) {
        this.clientID = clientID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(String joinDate) {
        this.joinDate = joinDate;
    }

    public String getBicycle() {
        return bicycle;
    }

    public void setBicycle(String bicycle) {
        this.bicycle = bicycle;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public List<EmailList> getClEmailLists() {
        return clEmailLists;
    }

    public void setClEmailLists(List<EmailList> clEmailLists) {
        this.clEmailLists = clEmailLists;
    }

    public List<PhoneList> getClPhoneLists() {
        return clPhoneLists;
    }

    public void setClPhoneLists(List<PhoneList> clPhoneLists) {
        this.clPhoneLists = clPhoneLists;
    }

    @Override
    public String toString() {
        return  name ;
    }
}
