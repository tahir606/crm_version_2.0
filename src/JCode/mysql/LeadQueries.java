package JCode.mysql;

import JCode.CommonTasks;
import JCode.fileHelper;
import objects.Lead;
import objects.ProductModule;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class LeadQueries {

    private Connection static_con;
    private fileHelper fHelper;

    public LeadQueries(Connection static_con, fileHelper fHelper) {
        this.static_con = static_con;
        this.fHelper = fHelper;
    }

    public void insertLead(Lead lead) {

        String query = "INSERT INTO LEAD_STORE(LS_ID, LS_FAME ,LS_LNAME ,LS_CNAME ,LS_WEBSITE ,PS_TYPE , " +
                "PS_STARTED ,PS_PRIORITY , CREATEDON, CREATEDBY) " +
                " SELECT IFNULL(max(PS_ID),0)+1,?,?,?,?,?,?,?,?,? from PRODUCT_STORE";

        // Connection con = getConnection();
        PreparedStatement statement = null;

        try {
//            statement = static_con.prepareStatement(query);
//            statement.setString(1, product.getName());
//            statement.setInt(2, product.getPrice());
//            statement.setString(3, product.getDesc());
//            statement.setInt(4, product.getStatus());
//            statement.setInt(5, product.getType());
//            statement.setString(6, product.getStartedtimeStmp());
//            statement.setInt(7, product.getPriority());
//            statement.setString(8, CommonTasks.getCurrentTimeStamp());
//            statement.setInt(9, fHelper.ReadUserDetails().getUCODE());

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
}
