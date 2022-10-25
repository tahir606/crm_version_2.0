package objects;

public class Keyword {
    private int code;
    private String keywordName;

    public Keyword() {
    }

    public Keyword(String keywordName) {
        this.keywordName = keywordName;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getKeywordName() {
        return keywordName;
    }

    public void setKeywordName(String keywordName) {
        this.keywordName = keywordName;
    }

    @Override
    public String toString() {
        return keywordName;
    }
}
