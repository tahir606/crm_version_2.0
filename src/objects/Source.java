package objects;

import java.io.Serializable;

public class Source implements Serializable {
    private int sourceID;
    private String name, description;

    public Source(String name) {
        this.name = name;
    }


    public int getSourceID() {
        return sourceID;
    }

    public void setSourceID(int sourceID) {
        this.sourceID = sourceID;
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

    @Override
    public String toString() {
        return  name ;
    }
}
