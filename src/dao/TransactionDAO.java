package dao;

import model.account.*;
import java.util.List;
import java.util.ArrayList;
import java.text.SimpleDateFormat;
import java.util.Date;
import daoservices.DatabaseConnection;

import javax.swing.plaf.nimbus.State;
import java.sql.*;

public class TransactionDAO implements DaoInterface<Transaction> {
    private static TransactionDAO transactionDAO;

    private Connection connection = DatabaseConnection.getConnection();

    private TransactionDAO() throws SQLException {}

    public static TransactionDAO getInstance() throws SQLException {
        if (transactionDAO == null)
            transactionDAO = new TransactionDAO();
        return transactionDAO;
    }

    @Override
    public void create(Transaction transaction) throws SQLException {
        String sql = "INSERT INTO Banking.Transaction (fromIBAN, toIBAN, description, amount, issuedDate) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, transaction.getFromIBAN());
            statement.setString(2, transaction.getToIBAN());
            statement.setString(3, transaction.getDescription());
            statement.setDouble(4, transaction.getAmount());
            statement.setString(5, (new SimpleDateFormat("yyyy-MM-dd")).format(transaction.getIssuedDate()));
            statement.executeUpdate();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    @Override
    public List<Transaction> read() throws SQLException {
        List<Transaction> transactions = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM Banking.Transaction");
            while (result.next()) {
                Transaction trans = new Transaction(result);
                transactions.add(trans);
            }
            statement.close();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return transactions;
    }

    @Override
    public void update(Transaction newTransaction) throws SQLException {
        String sql = "UPDATE Banking.Transaction SET description = ?, amount = ?, issuedDate = ? WHERE fromIBAN = ? AND toIBAN = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, newTransaction.getDescription());
            statement.setDouble(2, newTransaction.getAmount());
            statement.setString(3, (new SimpleDateFormat("yyyy-MM-dd")).format(newTransaction.getIssuedDate()));
            statement.setString(4, newTransaction.getFromIBAN());
            statement.setString(5, newTransaction.getToIBAN());
            statement.executeUpdate();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    @Override
    public void delete(Transaction transaction) throws SQLException {
        String sql = "DELETE FROM Banking.Transaction WHERE fromIBAN = ? AND toIBAN = ? and issuedDate = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, transaction.getFromIBAN());
            statement.setString(2, transaction.getToIBAN());
            statement.setString(3, (new SimpleDateFormat("yyyy-MM-dd")).format(transaction.getIssuedDate()));
            statement.executeUpdate();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

}
