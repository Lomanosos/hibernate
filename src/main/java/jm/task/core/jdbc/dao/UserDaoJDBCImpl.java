package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    Connection cunt = Util.getConnection();
    User user = new User();
    public UserDaoJDBCImpl() throws SQLException {

    }

    public void createUsersTable() throws SQLException {

        PreparedStatement preparedStatement = null;
        String sql = "CREATE TABLE IF NOT EXSISTS users (Id INT PRIMARY KEY AUTO_INCREMENT, Name VARCHAR(20), Lastname VARCHAR(30), Age TINYINT);";
        try {
            preparedStatement = cunt.prepareStatement(sql);
            preparedStatement.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (cunt != null) {
                cunt.close();
            }
        }
    }

    public void dropUsersTable() throws SQLException {
        PreparedStatement preparedStatement = null;
        String sql = "DROP TABLE IF EXSISTS users;";

        try {
            preparedStatement = cunt.prepareStatement(sql);
            preparedStatement.execute();

        } catch(SQLException e){

        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (cunt != null) {
                cunt.close();
            }
        }
    }

    public void saveUser(String name, String lastName, byte age) throws SQLException {
        PreparedStatement preparedStatement = null;
        String sql = "INSERT INTO users (name, lastName, age) VALUES (?, ?, ?);";

        try {
            preparedStatement = cunt.prepareStatement(sql);

            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setByte(3, user.getAge());

            preparedStatement.execute();
        } catch (SQLException e) {

        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (cunt != null) {
                cunt.close();
            }
        }
    }

    public void removeUserById(long id) throws SQLException {
        PreparedStatement preparedStatement = null;
        String sql = "DELETE FROM users WHERE Id = ?;";


        try {
            preparedStatement = cunt.prepareStatement(sql);
            preparedStatement.setLong(1, id);
            preparedStatement.execute();

        } catch(SQLException e){

        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (cunt != null) {
                cunt.close();
            }
        }

    }

    public List<User> getAllUsers() throws SQLException {
        List<User> listuser = new ArrayList<>();

        String sql = "SELECT * FROM users;";

        PreparedStatement statement = null;

        try {
            statement = cunt.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery(sql);

            while(resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("Id"));
                user.setName(resultSet.getString("Name"));
                user.setLastName(resultSet.getString("Lastname"));
                user.setAge(resultSet.getByte("Age"));
                listuser.add(user);
            }
        } catch (SQLException e) {

        } finally {
            if (statement != null) {
                statement.close();
            }
            if (cunt != null) {
                cunt.close();
            }
        }
        return listuser;
    }

    public void cleanUsersTable() throws SQLException {
        PreparedStatement preparedStatement = null;
        String sql = "TRUNCATE TABLE users;";

        try {
            preparedStatement = cunt.prepareStatement(sql);
            preparedStatement.execute();

        } catch(SQLException e){

        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (cunt != null) {
                cunt.close();
            }
        }
    }
}
