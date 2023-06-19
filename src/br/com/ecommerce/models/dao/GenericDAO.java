package br.com.ecommerce.models.dao;


import br.com.ecommerce.models.infrastructure.ConnectionDB;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static br.com.ecommerce.views.utils.Constants.getErrorMessage;


public abstract class GenericDAO<T>  extends ConnectionDB{
    private Connection connection = getConnection();

    public void create(T object) {
        try {
            connection = getConnection();
            String sql = buildInsertQuery(object);
            PreparedStatement statement = connection.prepareStatement(sql);
            setParametersInsert(statement, object);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(getErrorMessage() +e.getMessage());
        } finally {
            super.closeConnection(connection);
        }
    }

    public T findById(int id) {
        T object = null;
        PreparedStatement statement = null;
        try {
            connection = getConnection();
            String sql = buildSelectByIdQuery();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
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

    public void update(T object) {
        try {
            connection = getConnection();
            String sql = buildUpdateQuery(object);
            PreparedStatement statement = connection.prepareStatement(sql);
            setParametersUpdate(statement, object);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error: "+e.getMessage());
        } finally {
            super.closeConnection(connection);
        }
    }

    public void delete(int id) {
        PreparedStatement statement = null;
        try {
            connection = getConnection();
            String sql = buildDeleteQuery();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error: "+e.getMessage());
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
    }

    public List<T> list() {
        List<T> objects = new ArrayList<>();
        PreparedStatement statement = null;
        try {
            connection = getConnection();
            String sql = buildListQuery();
            statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                T object = buildObject(resultSet);
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

    protected abstract String buildInsertQuery(T object);

    protected abstract void setParametersInsert(PreparedStatement statement, T object) throws SQLException ;

    protected abstract void setParametersUpdate(PreparedStatement statement, T object) throws SQLException ;

    protected abstract T buildObject(ResultSet resultSet) throws SQLException;

    protected abstract String buildUpdateQuery(T object);

    protected abstract String buildSelectByIdQuery();

    protected abstract String buildDeleteQuery();

    protected abstract String buildListQuery();
}

