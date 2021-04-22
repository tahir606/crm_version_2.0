package objects;

public class Domain {
    private int code;
    private String name;
    private int whiteBlackList ;

    public Domain() {
    }

    public Domain(String name,int whiteBlackList) {
        this.name = name;
        this.whiteBlackList=whiteBlackList;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWhiteBlackList() {
        return whiteBlackList;
    }

    public void setWhiteBlackList(int whiteBlackList) {
        this.whiteBlackList = whiteBlackList;
    }

    @Override
    public String toString() {
        return  name ;
    }
}
