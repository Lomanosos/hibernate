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

        String sql = "CREATE TABLE IF NOT EXSISTS users (Id INT PRIMARY KEY AUTO_INCREMENT, Name VARCHAR(20), Lastname VARCHAR(30), Age TINYINT);";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){

            preparedStatement.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

    public void dropUsersTable() throws SQLException {

        String sql = "DROP TABLE IF EXSISTS users;";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.execute();

        } catch(SQLException e){

        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

    public void saveUser(String name, String lastName, byte age) throws SQLException {

        String sql = "INSERT INTO users (name, lastName, age) VALUES (?, ?, ?);";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {


            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setByte(3, user.getAge());

            preparedStatement.execute();


        } catch (SQLException e) {

        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

    public void removeUserById(long id) throws SQLException {

        String sql = "DELETE FROM users WHERE Id = ?;";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);) {

            preparedStatement.setLong(1, id);
            preparedStatement.execute();

        } catch(SQLException e){

        } finally {

            if (connection != null) {
                connection.close();
            }
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
            if (connection != null) {
                connection.close();
            }
        }
        return listuser;
    }

    public void cleanUsersTable() throws SQLException {

        String sql = "TRUNCATE TABLE users;";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.execute();

        } catch(SQLException e){

        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }
}
