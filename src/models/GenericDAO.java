package models;

import models.ConnectionDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class GenericDAO<T> extends ConnectionDB{
    private final Connection connection = ConnectionDB.getConnection();

    public void create(T object) {
        try {
            String sql = buildInsertQuery(object);
            PreparedStatement statement = connection.prepareStatement(sql);
            setParameters(statement, object);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(connection);
        }
    }

    public T findById(int id) {
        T object = null;
        PreparedStatement statement = null;
        try {
            String sql = buildSelectByIdQuery();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                object = buildObject(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if(statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
            close(connection);
        }
        return object;
    }

    public void update(T object) {
        try {
            String sql = buildUpdateQuery(object);
            PreparedStatement statement = connection.prepareStatement(sql);
            setParameters(statement, object);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(connection);
        }
    }

    public void delete(int id) {
        PreparedStatement statement = null;
        try {
            String sql = buildDeleteQuery();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if(statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
            close(connection);
        }
    }

    public List<T> list() {
        List<T> objects = new ArrayList<>();
        PreparedStatement statement = null;
        try {
            String sql = buildListQuery();
            statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                T object = buildObject(resultSet);
                objects.add(object);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if(statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
            close(connection);
        }
        return objects;
    }

    protected abstract String buildInsertQuery(T object);

    protected abstract void setParameters(PreparedStatement statement, T object) throws SQLException ;

    protected abstract T buildObject(ResultSet resultSet) throws SQLException;

    protected abstract String buildUpdateQuery(T object);

    protected abstract String buildSelectByIdQuery();

    protected abstract String buildDeleteQuery();

    protected abstract String buildListQuery();
}

