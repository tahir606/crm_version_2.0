package objects;

import java.io.Serializable;
import java.util.List;

public class ProductModule implements Serializable {

    private int pmID,createdBy;
    private String name, description, createdOn;

    private Integer psID;
    private Product productModuleList;
    private List<ModuleLocking> pmModuleLockingList;
    public ProductModule() {
    }

    public List<ModuleLocking> getPmModuleLockingList() {
        return pmModuleLockingList;
    }

    public void setPmModuleLockingList(List<ModuleLocking> pmModuleLockingList) {
        this.pmModuleLockingList = pmModuleLockingList;
    }

    public int getPmID() {
        return pmID;
    }

    public void setPmID(int pmID) {
        this.pmID = pmID;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public Integer getPsID() {
        return psID;
    }

    public void setPsID(Integer psID) {
        this.psID = psID;
    }

    public Product getProductModuleList() {
        return productModuleList;
    }

    public void setProductModuleList(Product productModuleList) {
        this.productModuleList = productModuleList;
    }

    @Override
    public String toString() {
        return "ProductModule{" +
                "pmID=" + pmID +
                ", createdBy=" + createdBy +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", createdOn='" + createdOn + '\'' +
                ", psID=" + psID +
                ", productModuleList=" + productModuleList +
                '}';
    }
}
