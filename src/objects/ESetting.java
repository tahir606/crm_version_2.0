package objects;

public class ESetting {

    private String host, email, pass, fspath;

    public ESetting() {
    }

    public ESetting(String host, String email, String pass, String fspath) {
        this.host = host;
        this.email = email;
        this.pass = pass;
        this.fspath = fspath;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getFspath() {
        return fspath;
    }

    public void setFspath(String fspath) {
        this.fspath = fspath;
    }
}
