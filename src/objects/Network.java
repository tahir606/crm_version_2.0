package objects;

public class Network {

    private String host, dbname, root, pass;
    private int port;

    public Network() {
    }

    public Network(String host, int port, String dbname, String root, String pass) {
        this.host = host;
        this.port = port;
        this.dbname = dbname;
        this.root = root;
        this.pass = pass;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getDbname() {
        return dbname;
    }

    public void setDbname(String dbname) {
        this.dbname = dbname;
    }

    public String getRoot() {
        return root;
    }

    public void setRoot(String root) {
        this.root = root;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
