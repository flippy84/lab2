import java.sql.*;

public class ConnectFourServer {
    public static void main(String[] args) {
        Connection connection;

        try {
            connection = DriverManager.getConnection("jdbc:sqlserver://hitsql-db.hb.se:56077;databaseName=oomuht1608;user=oomuht1608;password=spad66;");
            Statement statement = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
            ResultSet result = statement.executeQuery("SELECT [port], [ipAddress] FROM [dbo].[ConnectFourServer] WHERE [groupId] = 8");
            if (result.next()) {
                int port = result.getInt("port");
                String address = result.getString("ipAddress");
                System.out.println(address + ":" + port);
                result.updateInt("port", 55);
                result.updateRow();
            } else {
                statement.executeUpdate("");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }
    }
}
