package br.com.ecommerce.models.dao;

import br.com.ecommerce.controllers.Product;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductDAO extends GenericDAO<Product> {

    public ProductDAO() {
        super();
    }

    @Override
    public String buildInsertQuery(Product object) {
        return "INSERT INTO products (name, price, quantity) VALUES (?, ?, ?)";
    }

    @Override
    public void setParametersInsert(PreparedStatement statement, Product object) throws SQLException {
        statement.setString(1, object.getName());
        statement.setBigDecimal(2, object.getPrice());
        statement.setInt(3, object.getQuantity());
    }

    @Override
    public void setParametersUpdate(PreparedStatement statement, Product object) throws SQLException {
        statement.setString(1, object.getName());
        statement.setBigDecimal(2, object.getPrice());
        statement.setInt(3, object.getQuantity());
        statement.setInt(4, object.getId());
    }

    @Override
    public Product buildObject(ResultSet resultSet) throws SQLException {
        var id = resultSet.getInt("id");
        var name = resultSet.getString("name");
        var price = resultSet.getBigDecimal("price");
        var quantity = resultSet.getInt("quantity");

        return new Product(id, name, price, quantity);
    }

    @Override
    public String buildUpdateQuery(Product object) {
        return "UPDATE products SET name = ?, price = ?, quantity = ? WHERE id = ?";
    }

    @Override
    public String buildSelectByIdQuery() {
        return "SELECT * FROM products WHERE id = ?";
    }

    @Override
    public String buildDeleteQuery() {
        return "DELETE FROM products WHERE id = ?";
    }

    @Override
    public String buildListQuery() {
        return "SELECT * FROM products";
    }


}
