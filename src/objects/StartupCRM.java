package objects;

import ApiHandler.RequestHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StartupCRM {
    public boolean checkAndCreateUser() {
        if (!checkForUsers()) {
            createFirstUser();
            return false;
        } else
            return true;
    }

    private boolean checkForUsers() {
        List<Users> usersList = new ArrayList<>();
        try {
            usersList = RequestHandler.listRequestHandler(RequestHandler.run("users/getALlUsers"), Users.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (usersList.isEmpty()) {
            return false;
        } else {
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
        List<RightsList> rightsLists = new ArrayList<>();
        try {
            rightsLists = RequestHandler.listRequestHandler(RequestHandler.run("rights_list/getRightList"), RightsList.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (rightsLists.isEmpty()) {
            return false;
        } else {
            return true;
        }

    }

    private void populateUserRights() {
        String[] rights = new String[]{"Email Viewer", "Clients", "Products", "Leads", "Activity", "Reports", "Documents", "General Setting"};
        int c = 1;
        List<RightsList> rightsLists = new ArrayList<>();
        for (String r : rights) {
            RightsList rightsList = new RightsList();
            rightsList.setName(r);
            rightsList.setFreeze(0);
            rightsList.setRightsCode(c);
            rightsLists.add(rightsList);
            c++;
        }
        try {
            RequestHandler.post("rights_list/addRights",RequestHandler.writeJSONRightListList(rightsLists));

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
