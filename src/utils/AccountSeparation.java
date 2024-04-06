package utils;
import model.account.*;

public class AccountSeparation {
    private static int uniqueID = 0;

    public static void addToUniqueID(int cnt) {
        uniqueID += cnt;
    }

    public Account createAccount(String name, int userID) {
        return new Account(name, userID, uniqueID++);
    }

    public SavingsAccount createSavingsAccount(String name, int userID) {
        return new SavingsAccount(name, userID, uniqueID++);
    }
}
