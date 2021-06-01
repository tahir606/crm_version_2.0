package objects;

import java.io.Serializable;

public class ModuleLocking implements Serializable {
    private int moduleId, createdBy, state;
    private String lockedTime, unLockedTime, description;
    private Integer pmID;
    private Product productMModuleLockingList;

    private Users users;

    public ModuleLocking() {
    }

    public ModuleLocking(int pmID, int createdBy, String lockedTime, int state, String description) {
        this.pmID = pmID;
        this.createdBy = createdBy;
        this.lockedTime = lockedTime;
        this.state = state;
        this.description = description;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getModuleId() {
        return moduleId;
    }

    public void setModuleId(int moduleId) {
        this.moduleId = moduleId;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    public String getLockedTime() {
        return lockedTime;
    }

    public void setLockedTime(String lockedTime) {
        this.lockedTime = lockedTime;
    }

    public String getUnLockedTime() {
        return unLockedTime;
    }

    public void setUnLockedTime(String unLockedTime) {
        this.unLockedTime = unLockedTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPmID() {
        return pmID;
    }

    public void setPmID(Integer pmID) {
        this.pmID = pmID;
    }


    public Product getProductMModuleLockingList() {
        return productMModuleLockingList;
    }

    public void setProductMModuleLockingList(Product productMModuleLockingList) {
        this.productMModuleLockingList = productMModuleLockingList;
    }


    @Override
    public String toString() {
        return "ModuleLocking{" +
                "moduleId=" + moduleId +
                ", userCode=" + createdBy +
                ", lockedTime='" + lockedTime + '\'' +
                ", unLockedTime='" + unLockedTime + '\'' +
                ", description='" + description + '\'' +
                ", pmID=" + pmID +
                ", productMModuleLockingList=" + productMModuleLockingList +
                '}';
    }
}
