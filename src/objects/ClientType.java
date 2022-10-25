package objects;

import java.io.Serializable;

public class ClientType implements Serializable {
    private int ctCode;
    private String name;

    public int getCtCode() {
        return ctCode;
    }

    public void setCtCode(int ctCode) {
        this.ctCode = ctCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name ;
    }
}
