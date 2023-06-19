package br.com.ecommerce.models.infrastructure;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionDB {
    private static final String URL = "jdbc:mysql://localhost:3306/";
    private static final String DATABASE_NAME = "ecommerce";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

    protected Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL + DATABASE_NAME, USERNAME, PASSWORD);
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL driver not found.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return connection;
    }

    protected void closeConnection(Connection connection) {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void checkAndCreateDatabase() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            Statement statement = connection.createStatement();
            boolean databaseExists = false;

            String showDatabasesQuery = "SHOW DATABASES";
            String createDatabaseQuery = "CREATE DATABASE " + DATABASE_NAME;
            String createTableProducts = "CREATE TABLE products(id int(11) NOT NULL AUTO_INCREMENT, name varchar(100)" +
                    " DEFAULT NULL, price decimal(10,2) DEFAULT NULL, quantity int(11) DEFAULT NULL, PRIMARY KEY (id))";

            String createTableCarts = "CREATE TABLE carts (id int(11) NOT NULL AUTO_INCREMENT,paid boolean DEFAULT " +
                    "NULL,PRIMARY KEY (`id`))";

            String createTableCartProducts = "CREATE TABLE cart_products (id int(11) NOT NULL AUTO_INCREMENT," +
                    "  product_id int(11) DEFAULT NULL, cart_id int(11) DEFAULT NULL, quantity int(11) DEFAULT NULL," +
                    "  PRIMARY KEY (id), KEY fk_products (product_id), KEY fk_cart (cart_id)," +
                    "  CONSTRAINT fk_cart FOREIGN KEY (cart_id) REFERENCES carts (id)," +
                    "  CONSTRAINT fk_products FOREIGN KEY (product_id) REFERENCES products (id)" +
                    ")";

            String insertIntoProducts = "INSERT INTO products (id, name, price, quantity) VALUES" +
                    "(1, 'Coke', 4.00, 10)," +
                    "(2, 'Diet Coke', 4.00, 10)," +
                    "(3, 'Sprite', 3.50, 10)," +
                    "(4, 'Pepsi', 3.50, 10)," +
                    "(5, 'Dr Pepper', 3.00, 10)," +
                    "(6, 'Fanta', 3.00, 5)," +
                    "(7, 'Mountain Dew', 2.50, 3)";

            String insertIntoCarts = "INSERT INTO carts (id, paid) VALUES" +
                    "(1, 1)," +
                    "(2, 1)," +
                    "(3, 0)";

            statement.execute(showDatabasesQuery);
            var resultSet = statement.getResultSet();

            while (resultSet.next()) {
                String databaseName = resultSet.getString(1);
                if (databaseName.equalsIgnoreCase(DATABASE_NAME)) {
                    databaseExists = true;
                    break;
                }
            }

            resultSet.close();
            statement.close();

            if (!databaseExists) {
                statement = connection.createStatement();
                statement.executeUpdate(createDatabaseQuery);
                statement.close();

                connection = DriverManager.getConnection(URL + DATABASE_NAME, USERNAME, PASSWORD);
                statement = connection.createStatement();

                statement.executeUpdate(createTableProducts);
                statement.executeUpdate(createTableCarts);
                statement.executeUpdate(createTableCartProducts);
                statement.executeUpdate(insertIntoProducts);
                statement.executeUpdate(insertIntoCarts);

                statement.close();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            closeConnection(connection);
        }
    }
}
