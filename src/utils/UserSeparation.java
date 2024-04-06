package utils;

import model.user.*;
import java.util.Scanner;
import java.text.ParseException;

public class UserSeparation {
    private static int uniqueID = 0;

    public static void incrementUniqueId(int cnt) {
        uniqueID += cnt;
    }

    public User createUser(Scanner scanner) throws ParseException {
        return new User(uniqueID++, scanner);
    }

}
