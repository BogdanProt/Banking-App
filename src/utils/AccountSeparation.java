package utils;
import daoservices.DatabaseConnection;
import model.account.*;

import java.sql.*;

import daoservices.DatabaseConnection.*;

public class AccountSeparation {
    private static int uniqueID;

    private Connection connection = DatabaseConnection.getConnection();

    public int getNumberOfAccounts() throws SQLException {
        int count = 0;
        try (Statement statement = connection.createStatement();
             ResultSet result = statement.executeQuery("SELECT COUNT(*) AS total FROM Banking.Account")) {
            if (result.next()) {
                count = result.getInt("total");
            }
        }
        return count+1;
    }

    public static void addToUniqueID(int cnt) {
        uniqueID += cnt;
    }

    public Account createAccount(String name, int userID) throws SQLException{
        return new Account(name, userID, getNumberOfAccounts());
    }

    public SavingsAccount createSavingsAccount(String name, int userID) throws SQLException{
        return new SavingsAccount(name, userID, getNumberOfAccounts());
    }

    public AccountSeparation() throws SQLException {
    }
}

