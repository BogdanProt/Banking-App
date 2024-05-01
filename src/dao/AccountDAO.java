package dao;

import model.account.*;
import java.util.ArrayList;
import java.util.List;
import daoservices.DatabaseConnection;
import java.sql.*;



public class AccountDAO implements DaoInterface<Account>{
    private static AccountDAO accountDAO;

    private Connection connection = DatabaseConnection.getConnection();

    private AccountDAO() throws SQLException {}

    public static AccountDAO getInstance() throws SQLException {
        if (accountDAO == null)
            accountDAO = new AccountDAO();
        return accountDAO;
    }

    @Override
    public void create(Account account) throws SQLException {
        String sql = "INSERT INTO Banking.Account (IBAN, swift, balance, name, userID) VALUES (?, ?, ?, ?, ?);";

        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, account.getIBAN());
            statement.setString(2, account.getSwift());
            statement.setDouble(3, account.getBalance());
            statement.setString(4, account.getName());
            statement.setInt(5, account.getUserID());
            statement.executeUpdate();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    @Override
    public List<Account> read() throws SQLException {
        List<Account> accounts = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM Banking.Account");
            while(result.next()) {
                Account acc = new Account(result);
                accounts.add(acc);
            }
            statement.close();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return accounts;
    }
    @Override
    public void update(Account newAccount) throws SQLException {
        String sql = "UPDATE Banking.Account SET balance = ?, name = ?, userID = ? WHERE IBAN = ? AND swift = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setDouble(1, newAccount.getBalance());
            statement.setString(2, newAccount.getName());
            statement.setInt(3, newAccount.getUserID());
            statement.setString(4, newAccount.getIBAN());
            statement.setString(5, newAccount.getSwift());
            statement.executeUpdate();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    @Override
    public void delete(Account account) throws SQLException {
        String sql = "DELETE FROM Banking.Account WHERE IBAN = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, account.getIBAN());
            statement.executeUpdate();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }



}
