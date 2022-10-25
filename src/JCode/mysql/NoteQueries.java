package JCode.mysql;

import JCode.CommonTasks;
import JCode.FileHelper;
import objects.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NoteQueries {

    private Connection static_con;
    private FileHelper fHelper;
    String  createdByQuery =    " AND CREATEDBY = ? ";
    public NoteQueries(Connection static_con, FileHelper fHelper) {
        this.static_con = static_con;
        this.fHelper = fHelper;
    }

    //Contact notes-----------------------------------------------------------------------------------------------------
    public void addNewNote(String text, Contact contact) {
        String query = "INSERT INTO NOTE_STORE(N_ID, N_TEXT, CS_ID, CREATEDON, CREATEDBY) " +
                " SELECT IFNULL(max(N_ID),0)+1,?,?,?,? from NOTE_STORE WHERE CS_ID =?";

        // Connection con = getConnection();
        PreparedStatement statement = null;

        try {
            statement = static_con.prepareStatement(query);
            statement.setString(1, text);
            statement.setInt(2, contact.getClientID());
            statement.setString(3, CommonTasks.getCurrentTimeStamp());
            statement.setInt(4, FileHelper.ReadUserApiDetails().getUserCode());
//            statement.setInt(4, fHelper.ReadUserDetails().getUCODE());
            statement.setInt(5, contact.getClientID());

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateNote(NoteOld noteOld, Contact contactProperty) {
        String query = " UPDATE NOTE_STORE SET N_TEXT = ? " +
                " WHERE CS_ID =?  " +
                " AND N_ID =? "+createdByQuery;

        // Connection con = getConnection();
        PreparedStatement statement = null;

        try {
            statement = static_con.prepareStatement(query);
            statement.setString(1, noteOld.getText());
            statement.setInt(2, contactProperty.getClientID());
            statement.setInt(3, noteOld.getCode());
            statement.setInt(4 , FileHelper.ReadUserApiDetails().getUserCode());
//            statement.setInt(4 ,fHelper.ReadUserDetails().getUCODE());
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<NoteOld> getNotes(ContactProperty contact) {
        String query = "SELECT N_ID, N_TEXT, CS_FNAME, NS.CREATEDON AS CREATEDON, FNAME,NS.CREATEDBY " +
                " FROM NOTE_STORE AS NS, CONTACT_STORE AS CS, USERS AS US " +
                " WHERE NS.CS_ID = ? " +
                " AND NS.CS_ID = CS.CS_ID " +
                " AND NS.CREATEDBY = US.UCODE";

        List<NoteOld> noteOlds = new ArrayList<>();

        try {
            PreparedStatement statement = static_con.prepareStatement(query);
            statement.setInt(1, contact.getCode());
            ResultSet set = statement.executeQuery();
            //-------------Creating Email-------------
            while (set.next()) {
                NoteOld noteOld = new NoteOld();
                noteOld.setCode(set.getInt("N_ID"));
                noteOld.setContactName(set.getString("CS_FNAME"));
                noteOld.setCreatedByName(set.getString("FNAME"));
                noteOld.setCreatedOn(set.getString("CREATEDON"));
                noteOld.setText(set.getString("N_TEXT"));
                noteOld.setCreatedBy(set.getInt("CREATEDBY"));
                noteOlds.add(noteOld);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return noteOlds;
    }

    public void deleteNote(NoteOld noteOld, Contact contact) {
        String query = "DELETE FROM NOTE_STORE " +
                " WHERE N_ID = ? " +
                " AND CS_ID = ? "+createdByQuery;

        // Connection con = getConnection();
        PreparedStatement statement = null;

        try {
            statement = static_con.prepareStatement(query);
            statement.setInt(1, noteOld.getCode());
            statement.setInt(2, contact.getClientID());
            statement.setInt(3 ,FileHelper.ReadUserApiDetails().getUserCode());
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Client Notes------------------------------------------------------------------------------------------------------
    public void addNewNote(String text, Client client) {
        String query = "INSERT INTO NOTE_STORE(N_ID, N_TEXT, CL_ID, CREATEDON, CREATEDBY) " +
                " SELECT IFNULL(max(N_ID),0)+1,?,?,?,? from NOTE_STORE WHERE CL_ID =?";

        // Connection con = getConnection();
        PreparedStatement statement = null;

        try {
            statement = static_con.prepareStatement(query);
            statement.setString(1, text);
            statement.setInt(2, client.getClientID());
            statement.setString(3, CommonTasks.getCurrentTimeStamp());
            statement.setInt(4, FileHelper.ReadUserApiDetails().getUserCode());
            statement.setInt(5, client.getCreatedBy());

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateNote(NoteOld noteOld, Client client) {
        String query = " UPDATE NOTE_STORE SET N_TEXT = ? " +
                " WHERE CL_ID =?  " +
                " AND N_ID =? "+createdByQuery;

        // Connection con = getConnection();
        PreparedStatement statement = null;

        try {
            statement = static_con.prepareStatement(query);
            statement.setString(1, noteOld.getText());
            statement.setInt(2, client.getClientID());
            statement.setInt(3, noteOld.getCode());
            statement.setInt(4 ,FileHelper.ReadUserApiDetails().getUserCode());
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<NoteOld> getNotes(ClientProperty client) {
        String query = "SELECT N_ID, N_TEXT, CL_NAME, NS.CREATEDON AS CREATEDON, FNAME ,NS.CREATEDBY " +
                " FROM NOTE_STORE AS NS, CLIENT_STORE AS CS, USERS AS US " +
                " WHERE NS.CL_ID = ? " +
                " AND NS.CL_ID = CS.CL_ID " +
                " AND NS.CREATEDBY = US.UCODE";

        List<NoteOld> noteOlds = new ArrayList<>();

        try {
            PreparedStatement statement = static_con.prepareStatement(query);
            statement.setInt(1, client.getCode());
            ResultSet set = statement.executeQuery();
            //-------------Creating Email-------------
            while (set.next()) {
                NoteOld noteOld = new NoteOld();
                noteOld.setCode(set.getInt("N_ID"));
                noteOld.setContactName(set.getString("CL_NAME"));
                noteOld.setCreatedByName(set.getString("FNAME"));
                noteOld.setCreatedOn(set.getString("CREATEDON"));
                noteOld.setText(set.getString("N_TEXT"));
                noteOld.setCreatedBy(set.getInt("CREATEDBY"));
                noteOlds.add(noteOld);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return noteOlds;
    }

    public void deleteNote(NoteOld noteOld, Client client) {
        String query = "DELETE FROM NOTE_STORE " +
                " WHERE N_ID = ? " +
                " AND CL_ID = ? "+createdByQuery;

        // Connection con = getConnection();
        PreparedStatement statement = null;

        try {
            statement = static_con.prepareStatement(query);
            statement.setInt(1, noteOld.getCode());
            statement.setInt(2, client.getClientID());
            statement.setInt(3 ,FileHelper.ReadUserApiDetails().getUserCode());
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Lead Notes------------------------------------------------------------------------------------------------------
    public void addNewNote(String text, LeadOld leadOld) {
        String query = "INSERT INTO NOTE_STORE(N_ID, N_TEXT, LS_ID, CREATEDON, CREATEDBY) " +
                " SELECT IFNULL(max(N_ID),0)+1,?,?,?,? from NOTE_STORE WHERE LS_ID =?";

        // Connection con = getConnection();
        PreparedStatement statement = null;

        try {
            statement = static_con.prepareStatement(query);
            statement.setString(1, text);
            statement.setInt(2, leadOld.getCode());
            statement.setString(3, CommonTasks.getCurrentTimeStamp());
            statement.setInt(4, FileHelper.ReadUserApiDetails().getUserCode());
            statement.setInt(5, leadOld.getCode());

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateNote(NoteOld noteOld, LeadOld leadOld) {
        String query = " UPDATE NOTE_STORE SET N_TEXT = ? " +
                " WHERE LS_ID =?  " +
                " AND N_ID =? "+createdByQuery;

        // Connection con = getConnection();
        PreparedStatement statement = null;

        try {
            statement = static_con.prepareStatement(query);
            statement.setString(1, noteOld.getText());
            statement.setInt(2, leadOld.getCode());
            statement.setInt(3, noteOld.getCode());
            statement.setInt(4 ,FileHelper.ReadUserApiDetails().getUserCode());
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Note> getNotes(LeadOld leadOld) {
        String query = "SELECT N_ID, N_TEXT, LS_CNAME, NS.CREATEDON AS CREATEDON, FNAME,NS.CREATEDBY " +
                " FROM NOTE_STORE AS NS, LEAD_STORE AS LS, USERS AS US " +
                " WHERE NS.LS_ID = ? " +
                " AND NS.LS_ID = LS.LS_ID " +
                " AND NS.CREATEDBY = US.UCODE ";

        List<Note> noteOlds = new ArrayList<>();

        try {
            PreparedStatement statement = static_con.prepareStatement(query);
            statement.setInt(1, leadOld.getCode());
            ResultSet set = statement.executeQuery();
            //-------------Creating Email-------------
            while (set.next()) {
                Note noteOld = new Note();
//                noteOld.setCode(set.getInt("N_ID"));
//                noteOld.setContactName(set.getString("LS_CNAME"));
//                noteOld.setCreatedByName(set.getString("FNAME"));
                noteOld.setCreatedOn(set.getString("CREATEDON"));
                noteOld.setText(set.getString("N_TEXT"));
                noteOld.setCreatedBy(set.getInt("CREATEDBY"));
                noteOlds.add(noteOld);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return noteOlds;
    }

    public void deleteNote(NoteOld noteOld, LeadOld leadOld) {
        String query = "DELETE FROM NOTE_STORE " +
                " WHERE N_ID = ? " +
                " AND LS_ID = ? "+createdByQuery;

        // Connection con = getConnection();
        PreparedStatement statement = null;

        try {
            statement = static_con.prepareStatement(query);
            statement.setInt(1, noteOld.getCode());
            statement.setInt(2, leadOld.getCode());
            statement.setInt(3 ,FileHelper.ReadUserApiDetails().getUserCode());
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Product Notes----------------------------------------------------------------------------------------------------
    public void addNewNote(String text, ProductProperty product) {
        String query = "INSERT INTO NOTE_STORE(N_ID, N_TEXT, PS_ID, CREATEDON, CREATEDBY) " +
                " SELECT IFNULL(max(N_ID),0)+1,?,?,?,? from NOTE_STORE WHERE PS_ID =?";

        // Connection con = getConnection();
        PreparedStatement statement = null;

        try {
            statement = static_con.prepareStatement(query);
            statement.setString(1, text);
            statement.setInt(2, product.getCode());
            statement.setString(3, CommonTasks.getCurrentTimeStamp());
            statement.setInt(4,FileHelper.ReadUserApiDetails().getUserCode());
            statement.setInt(5, product.getCode());

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateNote(NoteOld noteOld, ProductProperty product) {
        String query = " UPDATE NOTE_STORE SET N_TEXT = ? " +
                " WHERE PS_ID =?  " +
                " AND N_ID =? "+createdByQuery;

        // Connection con = getConnection();
        PreparedStatement statement = null;

        try {
            statement = static_con.prepareStatement(query);
            statement.setString(1, noteOld.getText());
            statement.setInt(2, product.getCode());
            statement.setInt(3, noteOld.getCode());
            statement.setInt(4 ,FileHelper.ReadUserApiDetails().getUserCode());
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<NoteOld> getNotes(ProductProperty product) {
        String query = "SELECT N_ID, N_TEXT, PS_NAME, NS.CREATEDON AS CREATEDON, FNAME ,NS.CREATEDBY " +
                " FROM NOTE_STORE AS NS, PRODUCT_STORE AS LS, USERS AS US " +
                " WHERE NS.PS_ID = ? " +
                " AND NS.PS_ID = LS.PS_ID " +
                " AND NS.CREATEDBY = US.UCODE ";

        List<NoteOld> noteOlds = new ArrayList<>();

        try {
            PreparedStatement statement = static_con.prepareStatement(query);
            statement.setInt(1, product.getCode());
            ResultSet set = statement.executeQuery();
            //-------------Creating Email-------------
            while (set.next()) {
                NoteOld noteOld = new NoteOld();
                noteOld.setCode(set.getInt("N_ID"));
                noteOld.setContactName(set.getString("PS_NAME"));
                noteOld.setCreatedByName(set.getString("FNAME"));
                noteOld.setCreatedOn(set.getString("CREATEDON"));
                noteOld.setText(set.getString("N_TEXT"));
                noteOld.setCreatedBy(set.getInt("CREATEDBY"));
                noteOlds.add(noteOld);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return noteOlds;
    }

    public void deleteNote(NoteOld noteOld, ProductProperty product) {
        String query = "DELETE FROM NOTE_STORE " +
                " WHERE N_ID = ? " +
                " AND PS_ID = ? "+createdByQuery;

        // Connection con = getConnection();
        PreparedStatement statement = null;

        try {
            statement = static_con.prepareStatement(query);
            statement.setInt(1, noteOld.getCode());
            statement.setInt(2, product.getCode());
            statement.setInt(3 ,FileHelper.ReadUserApiDetails().getUserCode());
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<NoteOld> getNotes(EmailOld emailOld) {

        String query = "SELECT N_ID, N_TEXT, ES.EMNO , NS.CREATEDON AS CREATEDON, FNAME,NS.CREATEDBY " +
                "                 FROM NOTE_STORE AS NS,EMAIL_STORE AS ES,  USERS AS US " +
                "                 WHERE NS.EMNO = ? " +
                "                 AND NS.EMNO = ES.EMNO" +
                "                AND NS.CREATEDBY = US.UCODE ";

        List<NoteOld> noteOlds = new ArrayList<>();

        try {
            PreparedStatement statement = static_con.prepareStatement(query);

//            statement.setInt(1, email.getCode());
            ResultSet set = statement.executeQuery();
            //-------------Creating Email-------------
            while (set.next()) {
                NoteOld noteOld = new NoteOld();
                noteOld.setCode(set.getInt("N_ID"));
                noteOld.setEmailNo(set.getInt("EMNO"));
                noteOld.setCreatedByName(set.getString("FNAME"));
                noteOld.setCreatedOn(set.getString("CREATEDON"));
                noteOld.setText(set.getString("N_TEXT"));
                noteOld.setCreatedBy(set.getInt("CREATEDBY"));
                noteOlds.add(noteOld);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return noteOlds;
    }

    public void addNewNote(String note, EmailOld emailOld) {
        String query = "INSERT INTO NOTE_STORE(N_ID, N_TEXT, EMNO, CREATEDON, CREATEDBY) " +
                " SELECT IFNULL(max(N_ID),0)+1,?,?,?,? from NOTE_STORE WHERE EMNO =?";

        // Connection con = getConnection();
        PreparedStatement statement = null;

        try {
            statement = static_con.prepareStatement(query);
            statement.setString(1, note);
//            statement.setInt(2, email.getCode());
            statement.setString(3, CommonTasks.getCurrentTimeStamp());
            statement.setInt(4, FileHelper.ReadUserApiDetails().getUserCode());
//            statement.setInt(5,email.getCode());

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteNote(NoteOld noteOld, EmailOld emailOld) {
        String query = "DELETE FROM NOTE_STORE WHERE N_ID = ? AND EMNO = ? " + createdByQuery;


        // Connection con = getConnection();
        PreparedStatement statement = null;

        try {
            statement = static_con.prepareStatement(query);
            statement.setInt(1, noteOld.getCode());
            statement.setInt(2, noteOld.getEmailNo());
            statement.setInt(3 ,FileHelper.ReadUserApiDetails().getUserCode());
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateNote(NoteOld noteOld, EmailOld emailOld) {
        String query = " UPDATE NOTE_STORE SET N_TEXT = ? " +
                " WHERE EMNO =?  " +
                " AND N_ID =? "+createdByQuery;

        // Connection con = getConnection();
        PreparedStatement statement = null;

        try {
            statement = static_con.prepareStatement(query);
            statement.setString(1, noteOld.getText());
//            statement.setInt(2, email.getCode());
            statement.setInt(3, noteOld.getCode());
            statement.setInt(4 ,FileHelper.ReadUserApiDetails().getUserCode());
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
