package dao;

import model.user.*;
import java.util.List;
import java.util.ArrayList;

public class UserDAO {

    private static List<User> users = new ArrayList<>();

    public User read(int userID) {
        if (!users.isEmpty()) {
            for (User u : users) {
                if (u.getUserID() == userID) {
                    return u;
                }
            }
        }
        return null;
    }

    public void delete(User u) {
        users.remove(u);
    }

    public void create(User u) {
        users.add(u);
    }
}
