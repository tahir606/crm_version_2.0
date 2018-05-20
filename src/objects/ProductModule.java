package objects;

public class ProductModule {
    
    int code, productCode, createdBy, state, lockedBy;
    String name, desc, createdOn, lockedByName;
    
    public ProductModule() {
    }
    
    @Override
    public String toString() {
        return "ProductModule{" +
                "code=" + code +
                ", productCode=" + productCode +
                ", name='" + name + '\'' +
                ", desc='" + desc + '\'' +
                '}';
    }
    
    public int getCode() {
        return code;
    }
    
    public void setCode(int code) {
        this.code = code;
    }
    
    public int getProductCode() {
        return productCode;
    }
    
    public void setProductCode(int productCode) {
        this.productCode = productCode;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getDesc() {
        return desc;
    }
    
    public void setDesc(String desc) {
        this.desc = desc;
    }
    
    public int getState() {
        return state;
    }
    
    public void setState(int state) {
        this.state = state;
    }
    
    public int getLockedBy() {
        return lockedBy;
    }
    
    public void setLockedBy(int lockedBy) {
        this.lockedBy = lockedBy;
    }
    
    public String getLockedByName() {
        return lockedByName;
    }
    
    public void setLockedByName(String lockedByName) {
        this.lockedByName = lockedByName;
    }
    
    public int getCreatedBy() {
        return createdBy;
    }
    
    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }
    
    public String getCreatedOn() {
        return createdOn;
    }
    
    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }
}
