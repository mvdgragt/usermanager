package org.example.data;

import org.example.models.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDb {
    public static List<User> findAll() throws SQLException {
//Connect & prepare query
        String query = "SELECT * FROM user;";
        Connection connection = MysqlConnector.getConnection();
    Statement stmt = connection.createStatement();
    ResultSet rs = stmt.executeQuery(query);


    // Create empty list for users
    List<User> users = new ArrayList<>();

    //get user data one row at a time
    while (rs.next()){
        User user = new User();
        user.setUserId(rs.getInt("userId"));
        user.setFirstName(rs.getString("firstName"));
        user.setLastName(rs.getString("lastName"));
        user.setEmail(rs.getString("email"));

        // Add user to list
        users.add(user);

    }
    return users;
    }

    public static void insert(User user) throws SQLException {
        String query = "INSERT INTO user (firstName, lastName, email) VALUES (?, ?, ?)";

        Connection connection = MysqlConnector.getConnection();
        PreparedStatement stmt = connection.prepareStatement(query);

        stmt.setString(1, user.getFirstName());
        stmt.setString(2, user.getLastName());
        stmt.setString(3, user.getEmail());

        stmt.executeUpdate(query);
        connection.close();
    }

    public static User findById(int userId) throws SQLException {
        String query = "SELECT * FROM user WHERE userid = ?";
        Connection connection = MysqlConnector.getConnection();
        PreparedStatement stmt = connection.prepareStatement(query);

        stmt.setInt(1, userId);

        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            User user = new User();
            user.setUserId(rs.getInt("userId"));
            user.setFirstName(rs.getString("firstName"));
            user.setLastName(rs.getString("lastName"));
            user.setEmail(rs.getString("email"));
            connection.close();
            return user;
        }
        connection.close();
        return null;
    }


    public static boolean update(User user) throws SQLException{

        String query = "UPDATE user SET firstName = ?, lastName = ?, email = ? WHERE userId = ? ";

        Connection connection = MysqlConnector.getConnection();
        PreparedStatement stmt = connection.prepareStatement(query);

        stmt.setString(1, user.getFirstName());
        stmt.setString(2, user.getLastName());
        stmt.setString(3, user.getEmail());
        stmt.setInt(4, user.getUserId());

        int rowsAffected = stmt.executeUpdate();
        connection.close();
        return rowsAffected > 0;


    }

    public static boolean delete(int userId) throws SQLException{
        String query = "DELETE FROM user WHERE userId = ?";
        Connection connection = MysqlConnector.getConnection();
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setInt(1, userId);

        int rowsAffected = stmt.executeUpdate();
        connection.close();
        return rowsAffected > 0;
    }
}

