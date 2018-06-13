package JCode.mysql;

import JCode.fileHelper;
import objects.Task;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TaskQueries {
    
    private Connection static_con;
    private fileHelper fHelper;
    
    public TaskQueries(Connection static_con, fileHelper fHelper) {
        this.static_con = static_con;
        this.fHelper = fHelper;
    }
    
    public void addTask(Task task) {
        String query = "";
        
        PreparedStatement statement;
        try {
            statement = static_con.prepareStatement(query);
            statement.executeUpdate();
            
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
    }
    
    
}
