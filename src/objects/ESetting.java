package objects;

public class ESetting {

    private String hostt, emailt, passt, fspatht, autotextt, disctextt, solvRespTextt, generated_reply_emailt;
    private boolean autot, disct, solvt;

    private int code, autoCheck, disclaimerCheck, solveCheck;
    private String host, email, password, filePath, generaEmail, autoText, disclaimerText, solveText, replacementKeyword;


    public ESetting() {
    }

    public ESetting(String host, String email, String password, String filePath) {
        this.host = host;
        this.email = email;
        this.password = password;
        this.filePath = filePath;
    }

    public ESetting(String hostt, String emailt, String passt, String fspatht, boolean autot, boolean disct) {
        this.hostt = hostt;
        this.emailt = emailt;
        this.passt = passt;
        this.fspatht = fspatht;
        this.autot = autot;
        this.disct = disct;
    }

//    @Override
//    public String toString() {
//        return hostt + "\n" + emailt + "\n" + passt + "\n" + fspatht + "\n" + autot + "\n" + disct;
//    }

    @Override
    public String toString() {
        return "ESetting{" +
                "hostt='" + hostt + '\'' +
                ", emailt='" + emailt + '\'' +
                ", passt='" + passt + '\'' +
                ", fspatht='" + fspatht + '\'' +
                ", autotextt='" + autotextt + '\'' +
                ", disctextt='" + disctextt + '\'' +
                ", solvRespTextt='" + solvRespTextt + '\'' +
                ", generated_reply_emailt='" + generated_reply_emailt + '\'' +
                ", autot=" + autot +
                ", disct=" + disct +
                ", solvt=" + solvt +
                ", code=" + code +
                ", autoCheck=" + autoCheck +
                ", disclaimerCheck=" + disclaimerCheck +
                ", solveCheck=" + solveCheck +
                ", host='" + host + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", filePath='" + filePath + '\'' +
                ", generaEmail='" + generaEmail + '\'' +
                ", autoText='" + autoText + '\'' +
                ", disclaimerText='" + disclaimerText + '\'' +
                ", solveText='" + solveText + '\'' +
                ", replacementKeyword='" + replacementKeyword + '\'' +
                '}';
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getAutoCheck() {
        return autoCheck;
    }

    public void setAutoCheck(int autoCheck) {
        this.autoCheck = autoCheck;
    }

    public int getDisclaimerCheck() {
        return disclaimerCheck;
    }

    public void setDisclaimerCheck(int disclaimerCheck) {
        this.disclaimerCheck = disclaimerCheck;
    }

    public int getSolveCheck() {
        return solveCheck;
    }

    public void setSolveCheck(int solveCheck) {
        this.solveCheck = solveCheck;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getGeneraEmail() {
        return generaEmail;
    }

    public void setGeneraEmail(String generaEmail) {
        this.generaEmail = generaEmail;
    }

    public String getAutoText() {
        return autoText;
    }

    public void setAutoText(String autoText) {
        this.autoText = autoText;
    }

    public String getDisclaimerText() {
        return disclaimerText;
    }

    public void setDisclaimerText(String disclaimerText) {
        this.disclaimerText = disclaimerText;
    }

    public String getSolveText() {
        return solveText;
    }

    public void setSolveText(String solveText) {
        this.solveText = solveText;
    }

    public String getReplacementKeyword() {
        return replacementKeyword;
    }

    public void setReplacementKeyword(String replacementKeyword) {
        this.replacementKeyword = replacementKeyword;
    }

    public String getHostt() {
        return hostt;
    }

    public void setHostt(String hostt) {
        this.hostt = hostt;
    }

    public String getEmailt() {
        return emailt;
    }

    public void setEmailt(String emailt) {
        this.emailt = emailt;
    }

    public String getPasst() {
        return passt;
    }

    public void setPasst(String passt) {
        this.passt = passt;
    }

    public String getFspatht() {
        return fspatht;
    }

    public void setFspatht(String fspatht) {
        this.fspatht = fspatht;
    }

    public boolean isAutot() {
        return autot;
    }

    public void setAutot(boolean autot) {
        this.autot = autot;
    }

    public boolean isDisct() {
        return disct;
    }

    public void setDisct(boolean disct) {
        this.disct = disct;
    }

    public boolean isSolvt() {
        return solvt;
    }

    public void setSolvt(boolean solvt) {
        this.solvt = solvt;
    }

    public String getAutotextt() {
        return autotextt;
    }

    public void setAutotextt(String autotextt) {
        this.autotextt = autotextt;
    }

    public String getDisctextt() {
        return disctextt;
    }

    public void setDisctextt(String disctextt) {
        this.disctextt = disctextt;
    }

    public String getSolvRespTextt() {
        return solvRespTextt;
    }

    public void setSolvRespTextt(String solvRespTextt) {
        this.solvRespTextt = solvRespTextt;
    }

    public String getGenerated_reply_emailt() {
        return generated_reply_emailt;
    }

    public void setGenerated_reply_emailt(String generated_reply_emailt) {
        this.generated_reply_emailt = generated_reply_emailt;
    }
}
