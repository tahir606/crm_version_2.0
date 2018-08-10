package JCode.mysql;

import objects.Users;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReportQueries {

    private Connection static_con;

    public ReportQueries(Connection static_con) {
        this.static_con = static_con;
    }

    public List<Users> ticketsSolvedByUser() {
        String query = "SELECT US.UCODE, UNAME, FNAME, (SELECT COUNT(EMNO) FROM email_store AS ES WHERE ES.SOLVBY = US.UCODE) AS MAXEMNO FROM USERS AS US WHERE FREZE = 'N'";
        List<Users> users = new ArrayList<>();
        try {
            PreparedStatement statement = static_con.prepareStatement(query);
            ResultSet set = statement.executeQuery();
            while (set.next()) {
                Users user = new Users();
                user.setUCODE(set.getInt("UCODE"));
                user.setUNAME(set.getString("UNAME"));
                user.setFNAME(set.getString("FNAME"));
                user.setSolved(set.getInt("MAXEMNO"));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }
}
