package JCode.mysql;

import JCode.CommonTasks;
import JCode.fileHelper;
import objects.ContactProperty;
import objects.Task;

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
        String query = "INSERT INTO TASK_STORE(TS_ID, TS_DDATE, TS_REPEAT, TS_DESC, CS_ID, CL_ID, PS_ID, LS_ID, CREATEDON, CREATEDBY) " +
                " SELECT IFNULL(max(TS_ID),0)+1,?,?,?,?,? from TASK_STORE";

        PreparedStatement statement = null;

        try {
            statement = static_con.prepareStatement(query);
            statement.setString(1, task.getDueDate());
            statement.setBoolean(2, task.isRepeat());
            statement.setString(3, task.getDesc());
            statement.setInt(4, task.getContact());
            statement.setInt(5, task.getClient());
            statement.setInt(6, task.getProduct());
            statement.setInt(7, task.getLead());
            statement.setString(8, CommonTasks.getCurrentTimeStamp());
            statement.setInt(9, fHelper.ReadUserDetails().getUCODE());

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Task> getTasks(ContactProperty contact) {
        String query = "SELECT TS_ID, TS_DDATE, TS_REPEAT, NS.CREATEDON AS CREATEDON, FNAME, CS_FNAME " +
                " FROM TASK_STORE AS NS, CONTACT_STORE AS CS, USERS AS US " +
                " WHERE NS.CS_ID = ? " +
                " AND NS.CS_ID = CS.CS_ID " +
                " AND NS.CREATEDBY = US.UCODE";

        List<Task> tasks = new ArrayList<>();

        try {
//            System.out.println(query);
            PreparedStatement statement = static_con.prepareStatement(query);
            statement.setInt(1, contact.getCode());
            ResultSet set = statement.executeQuery();
            //-------------Creating Email-------------
            while (set.next()) {
                Task task = new Task();
                task.setCode(set.getInt("N_ID"));
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


}
