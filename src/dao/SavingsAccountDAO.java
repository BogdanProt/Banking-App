package dao;

import model.account.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import daoservices.DatabaseConnection;
import java.sql.*;

public class SavingsAccountDAO implements DaoInterface<SavingsAccount>{

    private static SavingsAccountDAO savingsAccountDAO;

    private Connection connection = DatabaseConnection.getConnection();

    private SavingsAccountDAO() throws SQLException {}


    public static SavingsAccountDAO getInstance() throws SQLException {
        if (savingsAccountDAO == null)
            savingsAccountDAO = new SavingsAccountDAO();
        return savingsAccountDAO;
    }

    @Override
    public void create(SavingsAccount account) throws SQLException {
        String sql = "INSERT INTO Banking.SavingsAccount (IBAN, swift, balance, name, userID, startDate, endDate, interestRate) VALUES (?, ?, ?, ?, ?, ?, ?, ?);";

        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, account.getIBAN());
            statement.setString(2, account.getSwift());
            statement.setDouble(3, account.getBalance());
            statement.setString(4, account.getName());
            statement.setInt(5, account.getUserID());
            statement.setString(6, (new SimpleDateFormat("yyyy-MM-dd")).format(account.getStartDate()));
            statement.setString(7, (new SimpleDateFormat("yyyy-MM-dd")).format(account.getEndDate()));
            statement.setInt(8, account.getInterestRate());
            statement.executeUpdate();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    @Override
    public List<SavingsAccount> read() throws SQLException {
        List<SavingsAccount> accounts = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM Banking.SavingsAccount");
            while(result.next()) {
                SavingsAccount acc = new SavingsAccount(result);
                accounts.add(acc);
            }
            statement.close();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return accounts;
    }
    @Override
    public void update(SavingsAccount newAccount) throws SQLException {
        String sql = "UPDATE Banking.SavingsAccount SET balance = ?, name = ?, userID = ?, startDate = ?, endDate = ?, interestRate = ? WHERE IBAN = ? AND swift = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setDouble(1, newAccount.getBalance());
            statement.setString(2, newAccount.getName());
            statement.setInt(3, newAccount.getUserID());
            statement.setString(4, (new SimpleDateFormat("yyyy-MM-dd")).format(newAccount.getStartDate()));
            statement.setString(5, (new SimpleDateFormat("yyyy-MM-dd")).format(newAccount.getEndDate()));
            statement.setInt(6, newAccount.getInterestRate());
            statement.setString(7, newAccount.getIBAN());
            statement.setString(8, newAccount.getSwift());
            statement.executeUpdate();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    @Override
    public void delete(SavingsAccount account) throws SQLException {
        String sql = "DELETE FROM Banking.SavingsAccount WHERE IBAN = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, account.getIBAN());
            statement.executeUpdate();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

}
