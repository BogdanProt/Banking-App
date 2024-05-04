package dao;

import model.user.*;
import java.util.List;
import java.util.ArrayList;
import daoservices.DatabaseConnection;
import java.sql.*;

public class UserDAO implements DaoInterface<User>{

    private static UserDAO userDAO;

    Connection connection = DatabaseConnection.getConnection();

    private UserDAO() throws SQLException {}

    public static UserDAO getInstance() throws SQLException {
        if (userDAO == null)
            userDAO = new UserDAO();
        return userDAO;
    }

    @Override
    public void create(User user) throws SQLException {
        String sql = "INSERT INTO Banking.User (userID, firstName, lastName, CNP, emailAddress, phoneNumber, country, county, city, street, postalCode) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, user.getUserID());
            statement.setString(2, user.getFirstName());
            statement.setString(3, user.getLastName());
            statement.setString(4, user.getCNP());
            statement.setString(5, user.getEmailAddress());
            statement.setString(6, user.getPhoneNumber());
            statement.setString(7, user.getAddress().getCountry());
            statement.setString(8, user.getAddress().getCounty());
            statement.setString(9, user.getAddress().getCity());
            statement.setString(10, user.getAddress().getStreet());
            statement.setInt(11, user.getAddress().getPostalCode());
            statement.executeUpdate();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    @Override
    public List<User> read() throws SQLException {
        List<User> users = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM Banking.User");
            while (result.next()) {
                User user = new User(result);
                users.add(user);
            }
            statement.close();
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        return users;
    }

    @Override
    public void update(User newUser) throws SQLException {
        String sql = "UPDATE Banking.User SET firstName = ?, lastName = ?, CNP = ?, emailAddress = ?, phoneNumber = ?, country = ?, county = ?, city = ?, street = ?, postalCode = ? WHERE userID = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, newUser.getFirstName());
            statement.setString(2, newUser.getLastName());
            statement.setString(3, newUser.getCNP());
            statement.setString(4, newUser.getEmailAddress());
            statement.setString(5, newUser.getPhoneNumber());
            statement.setString(6, newUser.getAddress().getCountry());
            statement.setString(7, newUser.getAddress().getCounty());
            statement.setString(8, newUser.getAddress().getCity());
            statement.setString(9, newUser.getAddress().getStreet());
            statement.setInt(10, newUser.getAddress().getPostalCode());
            statement.setInt(11, newUser.getUserID());
            statement.executeUpdate();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    @Override
    public void delete(User user) throws SQLException {
        String sql = "DELETE FROM Banking.User WHERE userID = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, user.getUserID());
            statement.executeUpdate();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
}
