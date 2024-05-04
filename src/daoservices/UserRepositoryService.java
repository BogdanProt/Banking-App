package daoservices;

import dao.*;
import model.card.*;
import model.account.*;
import model.user.*;
import utils.*;

import java.sql.SQLException;
import java.util.List;

public class UserRepositoryService {

    private UserDAO userDAO = UserDAO.getInstance();

    public UserRepositoryService() throws SQLException {}

    public User getUserByID(int userID) throws SQLException{
        List<User> users = userDAO.read();

        if(!users.isEmpty()) {
            for (User user : users) {
                if (user.getUserID() == userID)
                    return user;
            }
        }

        System.out.println("No user having id " + userID);
        return null;
    }

    public void addUser(User user) throws SQLException{
        if (user != null) {
            userDAO.create(user);
        } else {
            throw new IllegalStateException("Unexpected value: " + user);
        }

        System.out.println("Added " + user);
    }

    public void removeUser(User user) throws SQLException{
        if (user != null) {
            userDAO.delete(user);
        } else {
            throw new IllegalStateException("Unexpected value: " + user);
        }

        System.out.println("Removed " + user);
    }

    public void updateUser(User newUser) throws SQLException {
        if (newUser != null) {
            userDAO.update(newUser);
        } else {
            throw new IllegalStateException("Unexpected value: " + newUser)
        }
    }

    public int getNumberOfUsers() throws SQLException{
        List<User> users = userDAO.read();

        if (!users.isEmpty()) {
            return users.size();
        } else {
            System.out.println("No users registered!");
            return 0;
        }
    }
}
