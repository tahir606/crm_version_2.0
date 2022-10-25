package objects;

import java.io.Serializable;
import java.util.List;

public class Lead implements Serializable {
    private int leadsId, sourceID, converted, createdBy, freeze;
    private String firstName, lastName, tittle, companyName, website, city, country, note, createdOn, sOther, fullName;

    private Users users;
    private Source source;
    private List<EmailList> ldEmailLists;

    private List<PhoneList> ldPhoneLists;

    private List<Note> ldNoteList;

    private List<Task> ldTaskList;

    private List<Event> ldEventList;
    private List<Client> ldClientList;

    public Lead() {
    }

    public List<Client> getLdClientList() {
        return ldClientList;
    }

    public void setLdClientList(List<Client> ldClientList) {
        this.ldClientList = ldClientList;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    public int getLeadsId() {
        return leadsId;
    }

    public void setLeadsId(int leadsId) {
        this.leadsId = leadsId;
    }

    public int getSourceID() {
        return sourceID;
    }

    public void setSourceID(int sourceID) {
        this.sourceID = sourceID;
    }

    public int getConverted() {
        return converted;
    }

    public void setConverted(int converted) {
        this.converted = converted;
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

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public String getsOther() {
        return sOther;
    }

    public void setsOther(String sOther) {
        this.sOther = sOther;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public List<EmailList> getLdEmailLists() {
        return ldEmailLists;
    }

    public void setLdEmailLists(List<EmailList> ldEmailLists) {
        this.ldEmailLists = ldEmailLists;
    }

    public List<PhoneList> getLdPhoneLists() {
        return ldPhoneLists;
    }

    public void setLdPhoneLists(List<PhoneList> ldPhoneLists) {
        this.ldPhoneLists = ldPhoneLists;
    }

    public List<Note> getLdNoteList() {
        return ldNoteList;
    }

    public void setLdNoteList(List<Note> ldNoteList) {
        this.ldNoteList = ldNoteList;
    }

    public List<Task> getLdTaskList() {
        return ldTaskList;
    }

    public void setLdTaskList(List<Task> ldTaskList) {
        this.ldTaskList = ldTaskList;
    }

    public List<Event> getLdEventList() {
        return ldEventList;
    }

    public void setLdEventList(List<Event> ldEventList) {
        this.ldEventList = ldEventList;
    }

    @Override
    public String toString() {
        return "Lead{" +
                "leadsId=" + leadsId +
                ", sId=" + sourceID +
                ", converted=" + converted +
                ", createdBy=" + createdBy +
                ", freeze=" + freeze +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", tittle='" + tittle + '\'' +
                ", companyName='" + companyName + '\'' +
                ", website='" + website + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", note='" + note + '\'' +
                ", createdOn='" + createdOn + '\'' +
                ", sOther='" + sOther + '\'' +
                ", users=" + users +
                ", ldEmailLists=" + ldEmailLists +
                ", ldPhoneLists=" + ldPhoneLists +
                ", ldNoteList=" + ldNoteList +
                ", ldTaskList=" + ldTaskList +
                ", ldEventList=" + ldEventList +
                '}';
    }
}
