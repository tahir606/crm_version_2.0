package JCode.mysql;

import objects.Document;

import java.io.*;
import java.sql.*;
import java.util.List;

public class DocumentQueries {

    Connection static_con;

    public DocumentQueries(Connection static_con) {
        this.static_con = static_con;
    }

    public void insertDocument(Document document) {

        String query = "INSERT INTO DOCUMENT_STORE (DCODE, DNAME, DFILE) " +
                " SELECT IFNULL(MAX(DCODE),0)+1,?,? FROM DOCUMENT_STORE";

        //saving the image
        PreparedStatement statement = null;
        try {
            statement = static_con.prepareStatement(query);
            statement.setString(1, document.getName());
            File f = new File(document.getPath());
            statement.setBlob(2, new FileInputStream(f), f.length());
            statement.executeUpdate();
            System.out.println("Image saved in DB");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public List<Document> getAllDocuments() {

        String query = "SELECT DCODE, DNAME, DFILE FROM DOCUMENT_STORE WHERE 1";

        //retrieving it
        Statement statement;
        try {
            statement = static_con.createStatement();
            ResultSet rs = statement.executeQuery(query);
            int count = 0;
            while (rs.next()) {
                InputStream is = rs.getBinaryStream("DFILE");
                BufferedImage image = ImageIO.read(is); //the image is read in
                //store it back again as a file
                ImageIO.write(image, & quot; jpg & quot;,new FileOutputStream( & quot; recived & quot;
                +count + & quot;.jpg & quot;));
                count++;
            }
            System.out.println(count + & quot; images saved on disk&quot;);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}
