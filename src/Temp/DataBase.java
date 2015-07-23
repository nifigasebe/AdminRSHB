import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created by Chizhov-AS on 06.02.2015.
 */
public class DataBase {

    private Connection connection = null;
    private Statement statement = null;
    private ResultSet resultSet = null;

    public DataBase(){                                                                                                         //Creating DB and Tables.

        try {

            Class.forName("org.sqlite.JDBC"); // load the sqlite-JDBC driver using the current class loader

            connection = DriverManager.getConnection("jdbc:sqlite:Users.db");
            System.out.println("Connection OK !");

            statement = connection.createStatement();

            statement.executeUpdate("DROP TABLE IF EXISTS Users;");
            statement.executeUpdate("DROP TABLE IF EXISTS Workstations;");
            statement.executeUpdate("CREATE TABLE Users (UserID INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE NOT NULL, Login CHAR (50), FIO CHAR (100), Email CHAR (50))");
            statement.executeUpdate("CREATE TABLE Workstations (WSID INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE NOT NULL, UserID INTEGER REFERENCES Users (UserID) ON DELETE SET NULL ON UPDATE SET NULL, Hostname CHAR (20), IP CHAR (20))");

        }

        catch (Exception ex) {
            System.out.println("!Error creating database.");
            System.err.println( ex.getClass().getName() + ": " + ex.getMessage() );
            System.exit(0);
        }
    }

    public void closeConnection() {

        try {
            if (statement != null) {
                statement.close();
                System.out.println("Statement closed");
            }
            if (connection != null) {
                connection.close();
                System.out.println("Connection closed");
            }
        } catch (Exception ex) {
            System.err.println(ex.getClass().getName() + ": " + ex.getMessage());
            System.exit(0);
        }
    }

    public void insertInDB (String sqlQuery) {
        try {

            statement.executeUpdate(sqlQuery);
        }

        catch (Exception ex) {
            System.out.println("!Error creating database.");
            System.err.println( ex.getClass().getName() + ": " + ex.getMessage() );
            System.exit(0);
        }
    }
}