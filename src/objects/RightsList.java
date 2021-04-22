package objects;

import java.io.Serializable;

public class RightsList implements Serializable {
    private int rightsCode, freeze;
    private String name;

    public RightsList() {
    }

    public RightsList(int rightsCode, int freeze, String name) {
        this.rightsCode = rightsCode;
        this.freeze = freeze;
        this.name = name;
    }

    public RightsList(int rightsCode, String name) {
        this.rightsCode = rightsCode;
        this.name = name;
    }

    public int getRightsCode() {
        return rightsCode;
    }

    public void setRightsCode(int rightsCode) {
        this.rightsCode = rightsCode;
    }

    public int getFreeze() {
        return freeze;
    }

    public void setFreeze(int freeze) {
        this.freeze = freeze;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "RightsList{" +
                "rightsCode=" + rightsCode +
                ", freeze=" + freeze +
                ", name='" + name + '\'' +
                '}';
    }
}
