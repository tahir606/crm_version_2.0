package JCode.mysql;

import JCode.fileHelper;
import objects.ESetting;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EmailSettingsQueries {
    
    private Connection static_con;
    private fileHelper fHelper;
    
    public ESetting getEmailSettings() {
        
        String query = "SELECT HOST, EMAIL, PASS, FSPATH, AUTOCHK, DISCCHK, SOLVCHK, AUTOTXT, DISCTXT, SOLVTXT FROM " +
                "EMAIL_SETTINGS " +
                "WHERE 1";
//        // Connection con = getConnection();
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
}
