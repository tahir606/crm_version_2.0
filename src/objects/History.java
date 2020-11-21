package objects;

public class History {

    private int hCode,userId,code;
    private  String status;
    private String time;
    private Users users;

    public History(int hCode, int userId, int code, String status, String time, Users users) {
        this.hCode = hCode;
        this.userId = userId;
        this.code = code;
        this.status = status;
        this.time = time;
        this.users = users;
    }

    public int gethCode() {
        return hCode;
    }

    public void sethCode(int hCode) {
        this.hCode = hCode;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "History{" +
                "hCode=" + hCode +
                ", userId=" + userId +
                ", code=" + code +
                ", status='" + status + '\'' +
                ", time=" + time +
                ", users=" + users +
                '}';
    }
}
