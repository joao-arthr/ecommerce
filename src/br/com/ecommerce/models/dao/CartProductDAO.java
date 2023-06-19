package br.com.ecommerce.models.dao;

import br.com.ecommerce.controllers.CartProduct;
import br.com.ecommerce.controllers.Cart;
import br.com.ecommerce.controllers.Product;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static br.com.ecommerce.views.utils.Constants.getErrorMessage;

public class CartProductDAO extends GenericDAO<CartProduct> {
    @Override
    protected String buildInsertQuery(CartProduct cartProduct) {
        return "INSERT INTO cart_products (cart_id, product_id, quantity) VALUES (?, ?, ?)";
    }

    @Override
    protected void setParametersInsert(PreparedStatement statement, CartProduct cartProduct) throws SQLException {
        statement.setInt(1, cartProduct.getCart().getId());
        statement.setInt(2, cartProduct.getProduct().getId());
        statement.setInt(3, cartProduct.getQuantityInCart());
    }

    @Override
    protected void setParametersUpdate(PreparedStatement statement, CartProduct cartProduct) throws SQLException {
        // Set the parameters for the update query
        statement.setInt(1, cartProduct.getQuantityInCart());
        statement.setInt(2, cartProduct.getCart().getId());
        statement.setInt(3, cartProduct.getProduct().getId());
    }

    @Override
    protected CartProduct buildObject(ResultSet resultSet) throws SQLException {
        CartProduct cartProduct = new CartProduct();
        cartProduct.setId(resultSet.getInt("id"));
        cartProduct.setQuantityInCart(resultSet.getInt("quantity"));

        ProductDAO productDAO = new ProductDAO();
        Product product = productDAO.findById(resultSet.getInt("product_id"));
        cartProduct.setProduct(product);
        return cartProduct;
    }


    @Override
    protected String buildUpdateQuery(CartProduct cartProduct) {
        return "UPDATE cart_products SET quantity = ? WHERE cart_id = ? AND product_id = ?";
    }

    @Override
    protected String buildSelectByIdQuery() {
        return "SELECT * FROM cart_products WHERE product_id = ? AND cart_id = ?";
    }

    @Override
    protected String buildDeleteQuery() {
        return "DELETE FROM cart_products WHERE id = ?";
    }

    @Override
    protected String buildListQuery() {
        return "SELECT * FROM cart_products";
    }

    public CartProduct findById(int product_id, int cart_id) {
        CartProduct object = null;
        PreparedStatement statement = null;
        Connection connection = null;
        try {
            connection = getConnection();
            String sql = buildSelectByIdQuery();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, product_id);
            statement.setInt(2, cart_id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                object = buildObject(resultSet);
            }
        } catch (SQLException e) {
            System.out.println(getErrorMessage() +e.getMessage());
        } finally {
            if(statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
            super.closeConnection(connection);
        }
        return object;
    }
    public List<CartProduct> list(Cart cart){
        List<CartProduct> objects = new ArrayList<>();
        PreparedStatement statement = null;
        Connection connection = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement("SELECT * FROM cart_products WHERE cart_id = ?");
            statement.setInt(1, cart.getId());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                CartProduct object = buildObject(resultSet);
                objects.add(object);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            if(statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
            super.closeConnection(connection);
        }
        return objects;
    }
}
