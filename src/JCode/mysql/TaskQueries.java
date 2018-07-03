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

public class TaskQueries {

    private Connection static_con;
    private fileHelper fHelper;

    public TaskQueries(Connection static_con, fileHelper fHelper) {
        this.static_con = static_con;
        this.fHelper = fHelper;
    }

    public void addTask(Task task) {
        String query = "INSERT INTO TASK_STORE(TS_ID, TS_SUBJECT, TS_DDATE, TS_REPEAT, TS_DESC, CS_ID, CL_ID, PS_ID, " +
                "LS_ID, CREATEDON, CREATEDBY) " +
                " SELECT IFNULL(max(TS_ID),0)+1,?,?,?,?,?,?,?,?,?,? from TASK_STORE";

        PreparedStatement statement = null;

        try {
            statement = static_con.prepareStatement(query);
            statement.setString(1, task.getSubject());
            statement.setString(2, task.getDueDate());
            statement.setBoolean(3, task.isRepeat());
            statement.setString(4, task.getDesc());
            statement.setInt(5, task.getContact());
            statement.setInt(6, task.getClient());
            statement.setInt(7, task.getProduct());
            statement.setInt(8, task.getLead());
            statement.setString(9, CommonTasks.getCurrentTimeStamp());
            statement.setInt(10, fHelper.ReadUserDetails().getUCODE());

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Task getSpecificTask(Task where) {
        String query = "SELECT TS_ID, TS_SUBJECT, TS_DESC, TS_DDATE, TS_REPEAT, NS.CREATEDON AS CREATEDON, FNAME " +
                " FROM TASK_STORE AS NS, USERS AS US " +
                " WHERE NS.CREATEDBY = US.UCODE " +
                " AND NS.FREZE = 0";

        try {
            PreparedStatement statement = static_con.prepareStatement(query);
            ResultSet set = statement.executeQuery();
            //-------------Creating Task-------------
            while (set.next()) {
                Task task = new Task();
                task.setCode(set.getInt("TS_ID"));
                task.setSubject(set.getString("TS_SUBJECT"));
                task.setDesc(set.getString("TS_DESC"));
                task.setDueDate(set.getString("TS_DDATE"));
                task.setCreatedBy(set.getString("FNAME"));
                task.setRepeat(set.getBoolean("TS_REPEAT"));
                task.setCreatedOn(set.getString("CREATEDON"));
                return task;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public List<Task> getAlLTasks(String where) {
        String query = "SELECT TS_ID, TS_SUBJECT, TS_DESC, TS_DDATE, TS_REPEAT, NS.CREATEDON AS CREATEDON, FNAME,  " +
                " NS.CS_ID, (SELECT CONCAT(CS_FNAME,' ',CS_LNAME) FROM contact_store as CS WHERE CS.CS_ID = NS.CS_ID) AS CSNAME , " +
                " NS.CL_ID, (SELECT CL.CL_NAME FROM client_store as CL WHERE CL.CL_ID = NS.CL_ID) AS CLNAME , " +
                " NS.LS_ID, (SELECT CONCAT(LS.LS_FNAME,' ',LS.LS_LNAME) FROM lead_store as LS WHERE LS.LS_ID = NS.LS_ID) AS LSNAME , " +
                " NS.PS_ID, (SELECT PS.PS_NAME FROM product_store as PS WHERE PS.PS_ID = NS.PS_ID) AS PSNAME  " +
                " FROM TASK_STORE AS NS, USERS AS US " +
                " WHERE NS.CREATEDBY = US.UCODE                " +
                " AND NS.FREZE = 0";

        if (where == null || where.equals(""))
            query = query + "";
        else
            query = query + where;

        List<Task> tasks = new ArrayList<>();

        try {
            PreparedStatement statement = static_con.prepareStatement(query);
            ResultSet set = statement.executeQuery();
            //-------------Creating Task-------------
            while (set.next()) {
                Task task = new Task();
                task.setCode(set.getInt("TS_ID"));
                task.setSubject(set.getString("TS_SUBJECT"));
                task.setDesc(set.getString("TS_DESC"));
                task.setDueDate(set.getString("TS_DDATE"));
                task.setCreatedBy(set.getString("FNAME"));
                task.setRepeat(set.getBoolean("TS_REPEAT"));
                task.setCreatedOn(set.getString("CREATEDON"));
                task.setClientName(set.getString("CLNAME"));
                task.setLeadName(set.getString("LSNAME"));
                task.setProductName(set.getString("PSNAME"));
                tasks.add(task);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return tasks;
    }

    public List<Task> getTasks(ContactProperty contact) {
        String query = "SELECT TS_ID, TS_SUBJECT, TS_DESC, TS_DDATE, TS_REPEAT, NS.CREATEDON AS CREATEDON, FNAME, " +
                "CS_FNAME " +
                " FROM TASK_STORE AS NS, CONTACT_STORE AS CS, USERS AS US " +
                " WHERE NS.CS_ID = ? " +
                " AND NS.CS_ID = CS.CS_ID " +
                " AND NS.CREATEDBY = US.UCODE " +
                " AND FREZE = 0";

        List<Task> tasks = new ArrayList<>();

        try {
            PreparedStatement statement = static_con.prepareStatement(query);
            statement.setInt(1, contact.getCode());
            ResultSet set = statement.executeQuery();
            //-------------Creating Task-------------
            while (set.next()) {
                Task task = new Task();
                task.setCode(set.getInt("TS_ID"));
                task.setSubject(set.getString("TS_SUBJECT"));
                task.setDesc(set.getString("TS_DESC"));
                task.setDueDate(set.getString("TS_DDATE"));
                task.setRepeat(set.getBoolean("TS_REPEAT"));
                task.setContactName(set.getString("CS_FNAME"));
                task.setCreatedBy(set.getString("FNAME"));
                task.setCreatedOn(set.getString("CREATEDON"));
                tasks.add(task);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return tasks;
    }

    public List<Task> getTasks(ClientProperty client) {
        String query = "SELECT TS_ID, TS_SUBJECT, TS_DESC, TS_DDATE, TS_REPEAT, TS_STATUS, NS.CREATEDON AS CREATEDON, FNAME, CL_NAME " +
                " FROM TASK_STORE AS NS, CLIENT_STORE AS CS, USERS AS US " +
                " WHERE NS.CL_ID = ? " +
                " AND NS.CL_ID = CS.CL_ID " +
                " AND NS.CREATEDBY = US.UCODE " +
                " AND NS.FREZE = 0 ";

        List<Task> tasks = new ArrayList<>();

        try {
            PreparedStatement statement = static_con.prepareStatement(query);
            statement.setInt(1, client.getCode());
            ResultSet set = statement.executeQuery();
            //-------------Creating Task-------------
            while (set.next()) {
                Task task = new Task();
                task.setCode(set.getInt("TS_ID"));
                task.setSubject(set.getString("TS_SUBJECT"));
                task.setDesc(set.getString("TS_DESC"));
                task.setDueDate(set.getString("TS_DDATE"));
                task.setRepeat(set.getBoolean("TS_REPEAT"));
                task.setStatus(set.getBoolean("TS_STATUS"));
                task.setClientName(set.getString("CL_NAME"));
                task.setCreatedBy(set.getString("FNAME"));
                task.setCreatedOn(set.getString("CREATEDON"));
                tasks.add(task);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return tasks;
    }

    public List<Task> getTasks(Lead lead) {
        String query = "SELECT TS_ID, TS_SUBJECT, TS_DESC, TS_DDATE, TS_REPEAT, TS_STATUS, NS.CREATEDON AS CREATEDON, FNAME, LS_CNAME " +
                " FROM TASK_STORE AS NS, LEAD_STORE AS CS, USERS AS US " +
                " WHERE NS.LS_ID = ? " +
                " AND NS.LS_ID = CS.LS_ID " +
                " AND NS.CREATEDBY = US.UCODE " +
                " AND NS.FREZE = 0 ";

        List<Task> tasks = new ArrayList<>();

        try {
            PreparedStatement statement = static_con.prepareStatement(query);
            statement.setInt(1, lead.getCode());
            ResultSet set = statement.executeQuery();
            //-------------Creating Task-------------
            while (set.next()) {
                Task task = new Task();
                task.setCode(set.getInt("TS_ID"));
                task.setSubject(set.getString("TS_SUBJECT"));
                task.setDesc(set.getString("TS_DESC"));
                task.setDueDate(set.getString("TS_DDATE"));
                task.setRepeat(set.getBoolean("TS_REPEAT"));
                task.setStatus(set.getBoolean("TS_STATUS"));
                task.setClientName(set.getString("LS_CNAME"));
                task.setCreatedBy(set.getString("FNAME"));
                task.setCreatedOn(set.getString("CREATEDON"));
                tasks.add(task);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return tasks;
    }

    public List<Task> getTasks(ProductProperty product) {
        String query = "SELECT TS_ID, TS_SUBJECT, TS_DESC, TS_DDATE, TS_REPEAT, TS_STATUS, NS.CREATEDON AS CREATEDON, FNAME, PS_NAME " +
                " FROM TASK_STORE AS NS, PRODUCT_STORE AS CS, USERS AS US " +
                " WHERE NS.PS_ID = ? " +
                " AND NS.PS_ID = CS.PS_ID " +
                " AND NS.CREATEDBY = US.UCODE " +
                " AND NS.FREZE = 0 ";

        List<Task> tasks = new ArrayList<>();

        try {
            PreparedStatement statement = static_con.prepareStatement(query);
            statement.setInt(1, product.getCode());
            ResultSet set = statement.executeQuery();
            //-------------Creating Task-------------
            while (set.next()) {
                Task task = new Task();
                task.setCode(set.getInt("TS_ID"));
                task.setSubject(set.getString("TS_SUBJECT"));
                task.setDesc(set.getString("TS_DESC"));
                task.setDueDate(set.getString("TS_DDATE"));
                task.setRepeat(set.getBoolean("TS_REPEAT"));
                task.setStatus(set.getBoolean("TS_STATUS"));
                task.setClientName(set.getString("PS_NAME"));
                task.setCreatedBy(set.getString("FNAME"));
                task.setCreatedOn(set.getString("CREATEDON"));
                tasks.add(task);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return tasks;
    }

    public void updateTask(Task task) {
        String query = " UPDATE TASK_STORE SET " +
                " TS_SUBJECT = ?, TS_DESC = ?, TS_DDATE = ?, TS_REPEAT = ? " +
                " WHERE TS_ID = ? ";

        PreparedStatement statement = null;
        try {
            statement = static_con.prepareStatement(query);
            statement.setString(1, task.getSubject());
            statement.setString(2, task.getDesc());
            statement.setString(3, task.getDueDate());
            statement.setBoolean(4, task.isRepeat());
            statement.setInt(5, task.getCode());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void closeTask(Task task) {
        String query = "UPDATE TASK_STORE SET TS_STATUS = ?, TS_CLOSEDON = ? " +
                " WHERE TS_ID = ? ";

        PreparedStatement statement = null;
        try {
            statement = static_con.prepareStatement(query);
            statement.setBoolean(1, true);
            statement.setString(2, CommonTasks.getCurrentTimeStamp());
            statement.setInt(3, task.getCode());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void freezeTask(Task task) {
        String query = "UPDATE TASK_STORE SET FREZE = 1 " +
                " WHERE TS_ID = ? ";

        PreparedStatement statement = null;
        try {
            statement = static_con.prepareStatement(query);
            statement.setInt(1, task.getCode());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
