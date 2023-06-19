package br.com.ecommerce.models.dao;

import br.com.ecommerce.controllers.Cart;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;



import static br.com.ecommerce.views.utils.Constants.getErrorMessage;

public class CartDAO extends GenericDAO<Cart> {
    @Override
    protected String buildInsertQuery(Cart cart) {
        return "INSERT INTO carts (paid) VALUES (?)";
    }

    @Override
    protected void setParametersInsert(PreparedStatement statement, Cart cart) throws SQLException {
        statement.setBoolean(1, cart.isPaid());
    }

    @Override
    protected void setParametersUpdate(PreparedStatement statement, Cart cart) throws SQLException {
        statement.setBoolean(1, cart.isPaid());
        statement.setInt(2, cart.getId());
    }

    @Override
    protected Cart buildObject(ResultSet resultSet) throws SQLException {
        Cart cart = new Cart();
        cart.setId(resultSet.getInt("id"));
        cart.setPaid(resultSet.getBoolean("paid"));

        var cp = new CartProductDAO();
        cart.setCartProducts(cp.list(cart));
        return cart;
    }

    @Override
    protected String buildUpdateQuery(Cart cart) {
        return "UPDATE carts SET paid = ? WHERE id = ?";
    }

    @Override
    protected String buildSelectByIdQuery() {
        return "SELECT * FROM carts WHERE id = ?";
    }

    @Override
    protected String buildDeleteQuery() {
        return "DELETE FROM carts WHERE id = ?";
    }

    @Override
    protected String buildListQuery() {
        return "SELECT * FROM carts";
    }

    public Cart findOpenCart(){
        Cart object = null;
        PreparedStatement statement = null;
        Connection connection = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement("SELECT * FROM carts WHERE paid = 0 LIMIT 1");
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

    public Cart openCartSelect(){
        var cart = this.findOpenCart();
        if(cart == null){
            var tempCart = new Cart();
            tempCart.setPaid(false);
            this.create(tempCart);

            return this.findOpenCart();
        } else{
            return cart;
        }
    }

    public String closeCart(Cart cart){
        cart.setPaid(true);
        update(cart);
        return "Purchase completed! A new cart will be opened.";
    }




}

