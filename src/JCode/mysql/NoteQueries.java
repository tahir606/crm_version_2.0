package JCode.mysql;

import JCode.CommonTasks;
import JCode.fileHelper;
import objects.ContactProperty;
import objects.Note;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NoteQueries {
    
    private Connection static_con;
    private fileHelper fHelper;
    
    public NoteQueries(Connection static_con, fileHelper fHelper) {
        this.static_con = static_con;
        this.fHelper = fHelper;
    }
    
    public void addNewNote(String text, ContactProperty contact) {
        String query = "INSERT INTO NOTE_STORE(N_ID, N_TEXT, CS_ID, CREATEDON, CREATEDBY) " +
                " SELECT IFNULL(max(N_ID),0)+1,?,?,?,? from NOTE_STORE WHERE CS_ID =?";
        
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
    
    public void updateNote(Note note, ContactProperty contactProperty) {
        String query = " UPDATE NOTE_STORE SET N_TEXT = ? " +
                " WHERE CS_ID =?  " +
                " AND N_ID =? ";
        
        // Connection con = getConnection();
        PreparedStatement statement = null;
        
        try {
            statement = static_con.prepareStatement(query);
            statement.setString(1, note.getText());
            statement.setInt(2, contactProperty.getCode());
            statement.setInt(3, note.getCode());
            statement.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public List<Note> getContactNotes(ContactProperty contact) {
        String query = "SELECT N_ID, N_TEXT, CS_FNAME, NS.CREATEDON AS CREATEDON, FNAME " +
                " FROM NOTE_STORE AS NS, CONTACT_STORE AS CS, USERS AS US " +
                " WHERE NS.CS_ID = ? " +
                " AND NS.CS_ID = CS.CS_ID " +
                " AND NS.CREATEDBY = US.UCODE";
        
        List<Note> notes = new ArrayList<>();
        
        try {
            System.out.println(query);
            PreparedStatement statement = static_con.prepareStatement(query);
            statement.setInt(1, contact.getCode());
            ResultSet set = statement.executeQuery();
            //-------------Creating Email-------------
            while (set.next()) {
                Note note = new Note();
                note.setCode(set.getInt("N_ID"));
                note.setContactName(set.getString("CS_FNAME"));
                note.setCreatedByName(set.getString("FNAME"));
                note.setCreatedOn(set.getString("CREATEDON"));
                note.setText(set.getString("N_TEXT"));
                notes.add(note);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        return notes;
    }
    
    public void deleteNote(Note note, ContactProperty contact) {
        String query = "DELETE FROM NOTE_STORE " +
                " WHERE N_ID = ? " +
                " AND CS_ID = ? ";
        
        // Connection con = getConnection();
        PreparedStatement statement = null;
        
        try {
            statement = static_con.prepareStatement(query);
            statement.setInt(1, note.getCode());
            statement.setInt(2, contact.getCode());
            statement.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
