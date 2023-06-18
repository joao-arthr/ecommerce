package br.com.ecommerce.models.dao;

import br.com.ecommerce.controllers.Product;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductDAO extends GenericDAO<Product> {

    @Override
    public String buildInsertQuery(Product object) {
        return null;
    }

    @Override
    public void setParameters(PreparedStatement statement, Product object) throws SQLException {
        //uuid
    }

    @Override
    public Product buildObject(ResultSet resultSet) throws SQLException {
        return null;
    }

    @Override
    public String buildUpdateQuery(Product object) {
        return null;
    }

    @Override
    public String buildSelectByIdQuery() {
        return null;
    }

    @Override
    public String buildDeleteQuery() {
        return null;
    }

    @Override
    public String buildListQuery() {
        return null;
    }
}
