package models.dao;

import models.infrastructure.ConnectionDB;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;



public abstract class GenericDAO<T>  extends ConnectionDB{
    private final Connection connection;

    protected GenericDAO() {
        this.connection = super.getConnection();
    }

    public void create(T object) {
        try {
            String sql = buildInsertQuery(object);
            PreparedStatement statement = connection.prepareStatement(sql);
            setParameters(statement, object);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            super.closeConnection(connection);
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
            super.closeConnection(connection);
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
            super.closeConnection(connection);
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
            super.closeConnection(connection);
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
            super.closeConnection(connection);
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
