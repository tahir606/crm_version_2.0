package JCode.mysql;

import JCode.fileHelper;
import objects.ESetting;
import objects.NotificationSettings;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SettingsQueries {

    private Connection static_con;
    private fileHelper fHelper;

    public SettingsQueries(Connection static_con, fileHelper fHelper) {
        this.static_con = static_con;
        this.fHelper = fHelper;
    }

    public ESetting getEmailSettings() {

        String query = "SELECT HOST, EMAIL, PASS, FSPATH, AUTOCHK, DISCCHK, SOLVCHK, AUTOTXT, DISCTXT, SOLVTXT FROM " +
                "EMAIL_SETTINGS " +
                "WHERE 1";
        try {
            PreparedStatement statement = static_con.prepareStatement(query);
            ResultSet set = statement.executeQuery();
            ESetting eSetting;
            while (set.next()) {
                eSetting = new ESetting(set.getString("HOST"), set.getString("EMAIL"),
                        set.getString("PASS"), set.getString("FSPATH"), set.getBoolean("AUTOCHK"),
                        set.getBoolean("DISCCHK"));
                eSetting.setSolv(set.getBoolean("SOLVCHK"));
                eSetting.setAutotext(set.getString("AUTOTXT"));
                eSetting.setDisctext(set.getString("DISCTXT"));
                eSetting.setSolvRespText(set.getString("SOLVTXT"));
                return eSetting;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }

    public void saveEmailSettings(ESetting eSetting) {

        String query = "UPDATE EMAIL_SETTINGS SET HOST = ?,EMAIL = ?, PASS = ?, FSPATH = ?," +
                " AUTOCHK = ?, DISCCHK = ?, AUTOTXT = ?, DISCTXT = ?, SOLVTXT = ?, SOLVCHK = ? WHERE ECODE = 1";

        // Connection con = getConnection();
        PreparedStatement statement = null;

        try {
            statement = static_con.prepareStatement(query);
            statement.setString(1, eSetting.getHost());
            statement.setString(2, eSetting.getEmail());
            statement.setString(3, eSetting.getPass());
            statement.setString(4, eSetting.getFspath());
            statement.setBoolean(5, eSetting.isAuto());
            statement.setBoolean(6, eSetting.isDisc());
            statement.setString(7, eSetting.getAutotext());
            statement.setString(8, eSetting.getDisctext());
            statement.setString(9, eSetting.getSolvRespText());
            statement.setBoolean(10, eSetting.isSolv());
            statement.executeUpdate();

            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        fHelper.DeleteESettings();
        fHelper.WriteESettings(getEmailSettings());

    }


    public NotificationSettings getNotificationSettings() {

        String query = "SELECT NS_ID, NS_TASK1, NS_EVENT1 FROM " +
                " NOTIFICATION_SETTINGS " +
                " WHERE 1";
        try {
            PreparedStatement statement = static_con.prepareStatement(query);
            ResultSet set = statement.executeQuery();
            NotificationSettings nSetting;
            while (set.next()) {
                nSetting = new NotificationSettings();
                nSetting.setNotifyTaskDueDate(set.getBoolean("NS_TASK1"));
                nSetting.setNotifyEventFromDate(set.getBoolean("NS_EVENT1"));
                return nSetting;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }

    public void saveNotificationSettings(NotificationSettings nSettings) {

        String query = "UPDATE NOTIFICATION_SETTINGS SET NS_TASK1 =?, NS_EVENT1 =?  " +
                " WHERE NS_ID = 1";

        PreparedStatement statement = null;

        try {
            statement = static_con.prepareStatement(query);
            statement.setBoolean(1, nSettings.isNotifyTaskDueDate());
            statement.setBoolean(2, nSettings.isNotifyEventFromDate());
            statement.executeUpdate();

            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void insertNotificationSettings(NotificationSettings nSettings) {

        String query = "INSERT INTO NOTIFICATION_SETTINGS (NS_ID, NS_TASK1, NS_EVENT1) " +
                " VALUES (1, ?, ?)   ";

        // Connection con = getConnection();
        PreparedStatement statement = null;

        try {
            statement = static_con.prepareStatement(query);
            statement.setBoolean(1, nSettings.isNotifyTaskDueDate());
            statement.setBoolean(2, nSettings.isNotifyEventFromDate());
            statement.executeUpdate();

            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void checkOnNotificationSettings() {
        if (getNotificationSettings() == null) {
            insertNotificationSettings(new NotificationSettings(true, true));
        }
    }


}
