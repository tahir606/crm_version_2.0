package JCode.mysql;

import ApiHandler.RequestHandler;
import objects.Users;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StartupQueries {

    private Connection static_con;
    private UserQueries userQueries;

    public StartupQueries(Connection static_con, UserQueries userQueries) {
        this.static_con = static_con;
        this.userQueries = userQueries;
    }

    public boolean checkAndCreateUser() {
        if (!checkForUsers()) {
            createFirstUser();
            return false;
        } else
            return true;
    }

    private boolean checkForUsers() {
        List<Users> usersList =new ArrayList<>();
        try {
           usersList = RequestHandler.listRequestHandler(RequestHandler.run("users/getALlUsers"), Users.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (usersList.isEmpty()){
            return false;
        }else{
            return true;
        }
    }

    private void createFirstUser() {
        Users user = new Users();
        user.setFullName("Auto Created User");
        user.setUserName("first_user");
        user.setPassword("auto123");
        user.setUserRight("Admin");
        user.setIsEmail(1);
        try {
            RequestHandler.post("users/addUser", RequestHandler.writeJSON(user)).close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean checkAndPopulateRights() {
        if (!checkForRights()) {
            populateUserRights();
            return false;
        } else
            return true;
    }

    private boolean checkForRights() {

        String query = " SELECT RCODE " +
                " FROM RIGHTS_LIST ";

        try {
            PreparedStatement statement = static_con.prepareStatement(query);
            ResultSet set = statement.executeQuery();

            if (!set.next()) {
                return false;
            } else {
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    private void populateUserRights() {
        String[] rights = new String[]{"Email Viewer", "Clients", "Products", "Leads", "Activity", "Reports", "Documents", "General Setting"};

        String query = "INSERT INTO RIGHTS_LIST (RCODE, RNAME) VALUES (?, ?)";

        int c = 1;
        for (String r : rights) {
            try {
                PreparedStatement statement = static_con.prepareStatement(query);
                statement.setInt(1, c);
                statement.setString(2, r);
                statement.execute();
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            c++;
        }
    }
}
