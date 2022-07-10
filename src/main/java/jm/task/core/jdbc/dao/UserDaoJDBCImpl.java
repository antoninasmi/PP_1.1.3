package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private static final Connection CONNECTION = Util.getConnection();

    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        try (Statement statement = CONNECTION.createStatement()) {
            CONNECTION.setAutoCommit(false);
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS Users " +
                    "(ID BIGINT PRIMARY KEY AUTO_INCREMENT, Name VARCHAR(100), LastName VARCHAR(100), Age INT(3))");
            CONNECTION.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                CONNECTION.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void dropUsersTable() {
        try (Statement statement = CONNECTION.createStatement()) {
            CONNECTION.setAutoCommit(false);
            statement.executeUpdate("DROP TABLE IF EXISTS Users");
            CONNECTION.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                CONNECTION.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement ps = CONNECTION.prepareStatement("INSERT INTO Users (Name, LastName, Age) VALUES" +
                " (?, ?, ?)")) {
            CONNECTION.setAutoCommit(false);
            ps.setString(1, name);
            ps.setString(2, lastName);
            ps.setByte(3, age);
            ps.executeUpdate();
            CONNECTION.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                CONNECTION.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void removeUserById(long id) {
        try (PreparedStatement ps = CONNECTION.prepareStatement("DELETE FROM Users WHERE ID = ?")) {
            CONNECTION.setAutoCommit(false);
            ps.setLong(1, id);
            ps.executeUpdate();
            CONNECTION.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                CONNECTION.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (ResultSet rs = CONNECTION.createStatement().executeQuery("SELECT * FROM Users")) {
            CONNECTION.setAutoCommit(false);
            while (rs.next()) {
                User user = new User(rs.getString("Name"), rs.getString("LastName"),
                        rs.getByte("Age"));
                user.setId(rs.getLong("ID"));
                users.add(user);
                CONNECTION.commit();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                CONNECTION.rollback();
            } catch (SQLException ex) {
                e.printStackTrace();
            }
        }
        return users;
    }

    public void cleanUsersTable() {
        try (Statement statement = CONNECTION.createStatement()) {
            CONNECTION.setAutoCommit(false);
            statement.executeUpdate("TRUNCATE TABLE Users");
            CONNECTION.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                CONNECTION.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}
