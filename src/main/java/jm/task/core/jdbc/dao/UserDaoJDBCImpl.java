package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private Connection connection = Util.getConnection();
    private User user = new User();
    public UserDaoJDBCImpl() throws SQLException {

    }

    public void createUsersTable() throws SQLException {

        //String sql = "create table IF NOT EXSISTS users (Id INT PRIMARY KEY AUTO_INCREMENT, Name VARCHAR(20), Lastname VARCHAR(30), Age TINYINT);";

        try (PreparedStatement preparedStatement = connection.prepareStatement("create table if not exists users (id int primary key auto_increment, name varchar(30), lastname varchar(30), age tinyint null);")){

            preparedStatement.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {

        }
    }

    public void dropUsersTable() throws SQLException {

        String sql = "drop table IF EXISTS users;";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.execute();

        } catch(SQLException e){

        } finally {

        }
    }

    public void saveUser(String name, String lastName, byte age) throws SQLException {

        String sql = String.format("INSERT INTO users (name, lastName, age) VALUES (%s, %s, %s);", name, lastName, age);

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.execute();


        } catch (SQLException e) {

        } finally {

        }
    }

    public void removeUserById(long id) throws SQLException {

        String sql = String.format("DELETE FROM users WHERE Id = %s;", id);

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);) {

            preparedStatement.execute();

        } catch(SQLException e){

        } finally {


        }
    }

    public List<User> getAllUsers() throws SQLException {
        List<User> listuser = new ArrayList<>();

        String sql = "SELECT * FROM users;";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            ResultSet resultSet = preparedStatement.executeQuery(sql);

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

        }
        return listuser;
    }

    public void cleanUsersTable() throws SQLException {

        String sql = "TRUNCATE TABLE users;";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.execute();

        } catch(SQLException e){

        } finally {

        }
    }
}
