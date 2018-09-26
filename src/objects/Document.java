package objects;

import java.io.File;

public class Document {

    private int code;
    private String name, path;
    private File file;

    public Document() {
    }

    public Document(File file) {
        this.file = file;
        this.name = file.getName();
    }

    public Document(int code, File file) {
        this.code = code;
        this.file = file;
        this.name = file.getName();
    }

    public Document(int code, String name, String path) {
        this.code = code;
        this.name = name;
        this.path = path;
    }

    @Override
    public String toString() {
        return name;
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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }
}
