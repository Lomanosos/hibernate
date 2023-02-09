package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private Connection connection = Util.getConnection();
    private User user = new User();
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() throws SQLException {

        //String sql = "create table IF NOT EXSISTS users (Id INT PRIMARY KEY AUTO_INCREMENT, Name VARCHAR(20), Lastname VARCHAR(30), Age TINYINT);";

        try (PreparedStatement preparedStatement = connection.prepareStatement("create table if not exists users (id int primary key auto_increment, name varchar(30), lastname varchar(30), age int);")){

            preparedStatement.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void dropUsersTable() throws SQLException {

        String sql = "drop table IF EXISTS users;";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.execute();

        } catch(SQLException e){

        }
    }

    public void saveUser(String name, String lastName, byte age) throws SQLException {

        String sql = "INSERT INTO users (name, lastName, age) VALUES (?, ?, ?);";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.execute();


        } catch (SQLException e) {

        }
    }

    public void removeUserById(long id) throws SQLException {

        String sql = String.format("DELETE FROM users WHERE Id = %s;", id);

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);) {

            preparedStatement.execute();

        } catch(SQLException e){

        }
    }

    public List<User> getAllUsers() throws SQLException {
        List<User> listuser = new ArrayList<>();
        User user = null;
        String sql = "SELECT * FROM users;";

        try (Statement preparedStatement = connection.createStatement()) {

            ResultSet resultSet = preparedStatement.executeQuery(sql);

            while(resultSet.next()) {
                user = new User();
                user.setId(resultSet.getLong("Id"));
                user.setName(resultSet.getString("Name"));
                user.setLastName(resultSet.getString("Lastname"));
                user.setAge(resultSet.getByte("Age"));
                listuser.add(user);

            }
        } catch (SQLException e) {

        }
        return listuser;
    }

    public void cleanUsersTable() throws SQLException { //работает

        String sql = "TRUNCATE TABLE users;";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.execute();

        } catch(SQLException e){

        }
    }
}
