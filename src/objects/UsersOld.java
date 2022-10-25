package objects;

import java.io.Serializable;
import java.util.ArrayList;

public class UsersOld implements Serializable {

    private int userCode, isLog, freeze, isEmail;
    private String fullName, userName, email, password, note, userRight;


    private int UCODE, solved, locked,availableCount;
    private String UNAME;
    private String FNAME, Emaill, Passwordd, uright;
    private ArrayList<uRights> uRightsList;


    private ArrayList<RightsList> userRightsList;
    private boolean freezee, isEmaill;

    public UsersOld() {
        uRightsList = new ArrayList<>();
        userRightsList = new ArrayList<>();
    }

    @Override
    public String toString() {
        return fullName;
    }

    // For Api


    public int getAvailableCount() {
        return availableCount;
    }

    public void setAvailableCount(int availableCount) {
        this.availableCount = availableCount;
    }

    public int getUserCode() {
        return userCode;
    }

    public void setUserCode(int userCode) {
        this.userCode = userCode;
    }

    public int getIsLog() {
        return isLog;
    }

    public void setIsLog(int isLog) {
        this.isLog = isLog;
    }

    public int getFreeze() {
        return freeze;
    }

    public void setFreeze(int freeze) {
        this.freeze = freeze;
    }

    public int getIsEmail() {
        return isEmail;
    }

    public void setIsEmail(int isEmail) {
        this.isEmail = isEmail;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getUserRight() {
        return userRight;
    }

    public void setUserRight(String userRight) {
        this.userRight = userRight;
    }


    // For Local
    public int getUCODE() {
        return UCODE;
    }

    public void setUCODE(int UCODE) {
        this.UCODE = UCODE;
    }

    public String getUNAME() {
        return UNAME;
    }

    public void setUNAME(String UNAME) {
        this.UNAME = UNAME;
    }

    public String getFNAME() {
        return FNAME;
    }

    public void setFNAME(String FNAME) {
        this.FNAME = FNAME;
    }



    public String getEmaill() {
        return Emaill;
    }

    public void setEmaill(boolean emaill) {
        isEmaill = emaill;
    }

    public void setEmaill(String emaill) {
        Emaill = emaill;
    }

    public String getPasswordd() {
        return Passwordd;
    }

    public void setPasswordd(String passwordd) {
        Passwordd = passwordd;
    }
    public ArrayList<uRights> getRightss() {
        return uRightsList;
    }
    public void setRightss(String[] rights) {
        for (int i = 0; i < rights.length; i++) {
            if (rights[i] != "") {
                String[] cn = rights[i].split("-");
                uRights right = new uRights(cn[0], null);
                uRightsList.add(right);
            }
        }
    }
    public ArrayList<RightsList> getRights(){
        return userRightsList;
    }
    public void setRights(String[] rights) {
        for (int i = 0; i < rights.length; i++) {
            if (rights[i] != "") {
                String[] cn = rights[i].split("-");
                RightsList right = new RightsList(Integer.parseInt(cn[0]), null);
                userRightsList.add(right);
            }
        }
    }
    public void setUserRightsList(ArrayList<RightsList> rights) {
            this.userRightsList =rights;
    }
    public ArrayList<RightsList> getUserRightsList() {
        return userRightsList;
    }



    public int getSolved() {
        return solved;
    }

    public void setSolved(int solved) {
        this.solved = solved;
    }

    public int getLocked() {
        return locked;
    }

    public void setLocked(int locked) {
        this.locked = locked;
    }

    public boolean isFreezee() {
        return freezee;
    }

    public void setFreezee(boolean freezee) {
        this.freezee = freezee;
    }

    public boolean isEmaill() {
        return isEmaill;
    }

    public void setEmailBool(boolean email) {
        isEmaill = email;
    }

    public String getUright() {
        return uright;
    }

    public void setUright(String uright) {
        this.uright = uright;
    }

    public ArrayList<uRights> getuRightsList() {
        return uRightsList;
    }

    public void setuRightsList(ArrayList<uRights> uRightsList) {
        this.uRightsList = uRightsList;
    }

    public static class uRights {
        private int RCODE;
        private String RNAME;

        public uRights(int RCODE, String RNAME) {
            this.RCODE = RCODE;
            this.RNAME = RNAME;
        }

        public uRights(String RCODE, String RNAME) { //If RCODE is String
            this.RCODE = Integer.parseInt(RCODE);
            this.RNAME = RNAME;
        }

        public uRights() {
        }

        public int getRCODE() {
            return RCODE;
        }

        public void setRCODE(int RCODE) {
            this.RCODE = RCODE;
        }

        public String getRNAME() {
            return RNAME;
        }

        public void setRNAME(String RNAME) {
            this.RNAME = RNAME;
        }
    }

}
