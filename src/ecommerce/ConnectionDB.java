package ecommerce;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConnectionDB {
    private static final String URL = "jdbc:mysql://localhost:3306/ecommerce";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

    private static final Logger logger = Logger.getLogger(ConnectionDB.class.getName());

    public ConnectionDB(){
        getConnection();
    }

    public Connection getConnection(){
        Connection connection = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            logger.log(Level.INFO, "Connection to MySQL established successfully!");
        } catch (ClassNotFoundException e) {
            logger.log(Level.SEVERE, "Driver MySQL not found.", e);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
        }

        return connection;
    }

    public void close(ConnectionDB connection) {
        if (connection != null) {
            connection.close(connection);
            logger.log(Level.INFO, "Connection closed successfully!");
        }
    }

}