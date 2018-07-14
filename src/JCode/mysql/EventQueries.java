package JCode.mysql;

import JCode.CommonTasks;
import JCode.fileHelper;
import objects.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EventQueries {

    private Connection static_con;
    private fileHelper fHelper;

    public EventQueries(Connection static_con, fileHelper fHelper) {
        this.static_con = static_con;
        this.fHelper = fHelper;
    }

    public void addEvent(Event event) {
        String query = "INSERT INTO EVENT_STORE(ES_ID, ES_TITLE, ES_DESC, ES_LOCATION, ES_ALLDAY, CL_ID, " +
                "LS_ID, CREATEDON, CREATEDBY) " +
                " SELECT IFNULL(max(ES_ID),0)+1,?,?,?,?,?,?,?,? from EVENT_STORE";

        PreparedStatement statement = null;

        try {
            statement = static_con.prepareStatement(query);
            statement.setString(1, event.getTitle());
            statement.setString(2, event.getDesc());
            statement.setString(3, event.getLocation());
            statement.setBoolean(4, event.isAllDay());
            statement.setInt(5, event.getClient());
            statement.setInt(6, event.getLead());
            statement.setString(7, CommonTasks.getCurrentTimeStamp());
            statement.setInt(8, fHelper.ReadUserDetails().getUCODE());

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

//    public Task getSpecificEvent(Event where) {
//        String query = "SELECT TS_ID, TS_SUBJECT, TS_DESC, TS_DDATE, TS_REPEAT, NS.CREATEDON AS CREATEDON, FNAME " +
//                " FROM TASK_STORE AS NS, USERS AS US " +
//                " WHERE NS.CREATEDBY = US.UCODE " +
//                " AND NS.FREZE = 0";
//
//        try {
//            PreparedStatement statement = static_con.prepareStatement(query);
//            ResultSet set = statement.executeQuery();
//            //-------------Creating Task-------------
//            while (set.next()) {
//                Task task = new Task();
//                task.setCode(set.getInt("TS_ID"));
//                task.setSubject(set.getString("TS_SUBJECT"));
//                task.setDesc(set.getString("TS_DESC"));
//                task.setDueDate(set.getString("TS_DDATE"));
//                task.setCreatedBy(set.getString("FNAME"));
//                task.setRepeat(set.getBoolean("TS_REPEAT"));
//                task.setCreatedOn(set.getString("CREATEDON"));
//                return task;
//            }
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//        }
//        return null;
//    }

//    public List<Event> getAlLEvents(String where) {
//        String query = "SELECT TS_ID, TS_SUBJECT, TS_DESC, TS_DDATE, TS_REPEAT, NS.CREATEDON AS CREATEDON, FNAME, " +
//                " TS_STATUS,  " +
//                " NS.CS_ID, (SELECT CONCAT(CS_FNAME,' ',CS_LNAME) FROM contact_store as CS WHERE CS.CS_ID = NS.CS_ID) AS CSNAME , " +
//                " NS.CL_ID, (SELECT CL.CL_NAME FROM client_store as CL WHERE CL.CL_ID = NS.CL_ID) AS CLNAME , " +
//                " NS.LS_ID, (SELECT CONCAT(LS.LS_FNAME,' ',LS.LS_LNAME) FROM lead_store as LS WHERE LS.LS_ID = NS.LS_ID) AS LSNAME , " +
//                " NS.PS_ID, (SELECT PS.PS_NAME FROM product_store as PS WHERE PS.PS_ID = NS.PS_ID) AS PSNAME  " +
//                " FROM TASK_STORE AS NS, USERS AS US " +
//                " WHERE NS.CREATEDBY = US.UCODE                " +
//                " AND NS.FREZE = 0";
//
//        if (where == null || where.equals(""))
//            query = query + "";
//        else
//            query = query + where;
//
//        List<Event> events = new ArrayList<>();
//
//        try {
//            PreparedStatement statement = static_con.prepareStatement(query);
//            ResultSet set = statement.executeQuery();
//            //-------------Creating Task-------------
//            while (set.next()) {
//                Event event = new Event();
//                task.setCode(set.getInt("TS_ID"));
//                task.setSubject(set.getString("TS_SUBJECT"));
//                task.setDesc(set.getString("TS_DESC"));
//                task.setDueDate(set.getString("TS_DDATE"));
//                task.setCreatedBy(set.getString("FNAME"));
//                task.setRepeat(set.getBoolean("TS_REPEAT"));
//                task.setCreatedOn(set.getString("CREATEDON"));
//                task.setStatus(set.getBoolean("TS_STATUS"));
//
//                task.setClient(set.getInt("CL_ID"));
//                task.setClientName(set.getString("CLNAME"));
//                task.setLead(set.getInt("LS_ID"));
//                task.setLeadName(set.getString("LSNAME"));
//                task.setProduct(set.getInt("PS_ID"));
//                task.setProductName(set.getString("PSNAME"));
//                events.add(event);
//            }
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//        }
//
//        return events;
//    }

    public List<Event> getTasks(ClientProperty client) {
        String query = "SELECT ES_ID, ES_TITLE, ES_DESC, ES_LOCATION, ES_ALLDAY, FNAME, CL_NAME " +
                " FROM EVENT_STORE AS NS, CLIENT_STORE AS CS, USERS AS US " +
                " WHERE NS.CL_ID = ? " +
                " AND NS.CL_ID = CS.CL_ID " +
                " AND NS.CREATEDBY = US.UCODE " +
                " AND NS.FREZE = 0 ";

        List<Event> events = new ArrayList<>();

        try {
            PreparedStatement statement = static_con.prepareStatement(query);
            statement.setInt(1, client.getCode());
            ResultSet set = statement.executeQuery();
            //-------------Creating Task-------------
            while (set.next()) {
                Event event = new Event();
                event.setCode(set.getInt("ES_ID"));
                event.setTitle(set.getString("ES_TITLE"));
                event.setDesc(set.getString("ES_DESC"));
                event.setAllDay(set.getBoolean("ES_ALLDAY"));
                event.setRelationName(set.getString("CL_NAME"));
                event.setCreatedBy(set.getString("FNAME"));
                event.setCreatedOn(set.getString("CREATEDON"));
                events.add(event);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return events;
    }
//
//    public List<Task> getTasks(Lead lead) {
//        String query = "SELECT TS_ID, TS_SUBJECT, TS_DESC, TS_DDATE, TS_REPEAT, TS_STATUS, NS.CREATEDON AS CREATEDON, FNAME, LS_CNAME " +
//                " FROM TASK_STORE AS NS, LEAD_STORE AS CS, USERS AS US " +
//                " WHERE NS.LS_ID = ? " +
//                " AND NS.LS_ID = CS.LS_ID " +
//                " AND NS.CREATEDBY = US.UCODE " +
//                " AND NS.FREZE = 0 ";
//
//        List<Task> tasks = new ArrayList<>();
//
//        try {
//            PreparedStatement statement = static_con.prepareStatement(query);
//            statement.setInt(1, lead.getCode());
//            ResultSet set = statement.executeQuery();
//            //-------------Creating Task-------------
//            while (set.next()) {
//                Task task = new Task();
//                task.setCode(set.getInt("TS_ID"));
//                task.setSubject(set.getString("TS_SUBJECT"));
//                task.setDesc(set.getString("TS_DESC"));
//                task.setDueDate(set.getString("TS_DDATE"));
//                task.setRepeat(set.getBoolean("TS_REPEAT"));
//                task.setStatus(set.getBoolean("TS_STATUS"));
//                task.setClientName(set.getString("LS_CNAME"));
//                task.setCreatedBy(set.getString("FNAME"));
//                task.setCreatedOn(set.getString("CREATEDON"));
//                tasks.add(task);
//            }
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//        }
//
//        return tasks;
//    }

//    public void updateEvent(Event event) {
//        String query = " UPDATE TASK_STORE SET " +
//                " TS_SUBJECT = ?, TS_DESC = ?, TS_DDATE = ?, TS_REPEAT = ? " +
//                " WHERE TS_ID = ? ";
//
//        PreparedStatement statement = null;
//        try {
//            statement = static_con.prepareStatement(query);
//            statement.setString(1, task.getSubject());
//            statement.setString(2, task.getDesc());
//            statement.setString(3, task.getDueDate());
//            statement.setBoolean(4, task.isRepeat());
//            statement.setInt(5, task.getCode());
//            statement.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void closeTask(Task task) {
//        String query = "UPDATE TASK_STORE SET TS_STATUS = ?, TS_CLOSEDON = ? " +
//                " WHERE TS_ID = ? ";
//
//        PreparedStatement statement = null;
//        try {
//            statement = static_con.prepareStatement(query);
//            statement.setBoolean(1, true);
//            statement.setString(2, CommonTasks.getCurrentTimeStamp());
//            statement.setInt(3, task.getCode());
//            statement.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void archiveTask(Task task) {
//        String query = "UPDATE TASK_STORE SET FREZE = 1 " +
//                " WHERE TS_ID = ? ";
//
//        PreparedStatement statement = null;
//        try {
//            statement = static_con.prepareStatement(query);
//            statement.setInt(1, task.getCode());
//            statement.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }


}
