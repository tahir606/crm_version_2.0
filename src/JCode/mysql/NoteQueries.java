package JCode.mysql;

import JCode.CommonTasks;
import JCode.fileHelper;
import objects.ContactProperty;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class NoteQueries {
    
    private Connection static_con;
    private fileHelper fHelper;
    
    public NoteQueries(Connection static_con, fileHelper fHelper) {
        this.static_con = static_con;
        this.fHelper = fHelper;
    }
    
    public void addNewNote(String text, ContactProperty contact) {
        String query = "INSERT INTO NOTE_STORE(N_ID, N_TEXT, CS_ID, CREATEDON, CREATEDBY) " +
                " SELECT IFNULL(max(CS_ID),0)+1,?,?,?,? from NOTE_STORE WHERE CS_ID =?";
        
        // Connection con = getConnection();
        PreparedStatement statement = null;
        
        try {
            statement = static_con.prepareStatement(query);
            statement.setString(1, text);
            statement.setInt(2, contact.getCode());
            statement.setString(3, CommonTasks.getCurrentTimeStamp());
            statement.setInt(4, fHelper.ReadUserDetails().getUCODE());
            statement.setInt(5, contact.getCode());
            
            statement.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
//    public
}
