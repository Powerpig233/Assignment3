
import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.Comparator;


public class database {
    PreparedStatement insertStatement;
    Connection connection;

    public database() throws SQLException
    {

         String dbURL = "jdbc:derby://localhost:1527/Labyrinth";
         connection = DriverManager.getConnection(dbURL);
         String insertQuery = "INSERT INTO Labyrinth (NAME,POINT) VALUES (?,?)";
         insertStatement = connection.prepareStatement(insertQuery);

    }

    public ArrayList<data> getHighScores() throws SQLException {
        String query = "SELECT * FROM Labyrinth";
        ArrayList<data> Points = new ArrayList<>();
        Statement stmt = connection.createStatement();
        ResultSet results = stmt.executeQuery(query);
        while (results.next()) {
            String name = results.getString("NAME");
            int point = results.getInt("POINT");
            
            Points.add(new data(name, point));
        }
        sortPoints(Points);
        return Points;
    }

      private void sortPoints(ArrayList<data> Points) {
        Collections.sort(Points, new Comparator<data>() {

            @Override
            
            public int compare(data t, data t1) {
                return t1.getPoints() - t.getPoints();
            }
        });
    }

      
      public void insertScore(String name ,int point) throws SQLException {
        //Timestamp ts = new Timestamp(System.currentTimeMillis());
        insertStatement.setString(1, name);
        insertStatement.setInt(2, point);
        insertStatement.executeUpdate();
    }
}
