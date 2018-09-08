package objects;

public class ESetting {

    private String host, email, pass, fspath, autotext, disctext, solvRespText, generated_reply_email;
    private boolean auto, disc, solv;

    public ESetting() {
    }

    public ESetting(String host, String email, String pass, String fspath) {
        this.host = host;
        this.email = email;
        this.pass = pass;
        this.fspath = fspath;
    }

    public ESetting(String host, String email, String pass, String fspath, boolean auto, boolean disc) {
        this.host = host;
        this.email = email;
        this.pass = pass;
        this.fspath = fspath;
        this.auto = auto;
        this.disc = disc;
    }

    @Override
    public String toString() {
        return host + "\n" + email + "\n" + pass + "\n" + fspath + "\n" + auto + "\n" + disc;
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

    public boolean isAuto() {
        return auto;
    }

    public void setAuto(boolean auto) {
        this.auto = auto;
    }

    public boolean isDisc() {
        return disc;
    }

    public void setDisc(boolean disc) {
        this.disc = disc;
    }

    public boolean isSolv() {
        return solv;
    }

    public void setSolv(boolean solv) {
        this.solv = solv;
    }

    public String getAutotext() {
        return autotext;
    }

    public void setAutotext(String autotext) {
        this.autotext = autotext;
    }

    public String getDisctext() {
        return disctext;
    }

    public void setDisctext(String disctext) {
        this.disctext = disctext;
    }

    public String getSolvRespText() {
        return solvRespText;
    }

    public void setSolvRespText(String solvRespText) {
        this.solvRespText = solvRespText;
    }

    public String getGenerated_reply_email() {
        return generated_reply_email;
    }

    public void setGenerated_reply_email(String generated_reply_email) {
        this.generated_reply_email = generated_reply_email;
    }
}
