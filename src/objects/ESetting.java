package objects;

public class ESetting {

    private String host,email,pass;

    public ESetting() {
    }

    public ESetting(String host, String email, String pass) {
        this.host = host;
        this.email = email;
        this.pass = pass;
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
}
