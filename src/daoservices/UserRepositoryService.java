package daoservices;

import dao.*;
import model.card.*;
import model.account.*;
import model.user.*;
import utils.*;

import java.sql.SQLException;

public class UserRepositoryService {

    private UserDAO userDAO = UserDAO.getInstance();

    public UserRepositoryService() throws SQLException {}

    public User getUserByID(int userID) {
        User user = userDAO.read(userID);

        if (user != null) {
            System.out.println(user);
        } else {
            System.out.println("No user having this ID!");
        }

        return user;
    }

    public void addUser(User user) {
        if (user != null) {
            userDAO.create(user);
        } else {
            throw new IllegalStateException("Unexpected value: " + user);
        }

        System.out.println("Added " + user);
    }

    public void removeUser(User user) {
        if (user != null) {
            userDAO.delete(user);
        } else {
            throw new IllegalStateException("Unexpected value: " + user);
        }

        System.out.println("Removed " + user);
    }

    public int getNumberOfUsers() {
        if (userDAO.getSize() < 1) {
            return 0;
        } else {
            return userDAO.getSize();
        }
    }
}
