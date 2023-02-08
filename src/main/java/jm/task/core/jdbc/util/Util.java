package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private static final String URL = "jdbc:mysql://localhost:3306/pp_1hmw";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    private static Connection cunt;

    public static Connection getConnection() throws SQLException{
        try{
            cunt = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            cunt.close();
        }
        return cunt;
    }


}
