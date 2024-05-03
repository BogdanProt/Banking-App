package daoservices;

import dao.*;
import model.card.*;
import model.account.*;
import model.user.*;
import utils.*;

import java.io.File;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TransactionRepositoryService {

    private TransactionDAO transactionDAO = TransactionDAO.getInstance();

    public TransactionRepositoryService() throws SQLException {}

    public List<Transaction> getTransactions() throws SQLException {
        List<Transaction> transactions = transactionDAO.read();

        if (!transactions.isEmpty()) {
            for (Transaction trans : transactions)
                System.out.println(trans.getFromIBAN() + " -> " + trans.getToIBAN() + " on " + trans.getIssuedDate());
        }
        else {
            System.out.println("No transactions registered!");
        }
        return null;
    }

    public List<Transaction> getTransactionsByDate(Date date) throws SQLException{
        List<Transaction> transactions = transactionDAO.read();
        List<Transaction> filtered = new ArrayList<>();

        if (!transactions.isEmpty()) {
            for (Transaction trans : transactions) {
                if (trans.getIssuedDate() == date)
                    filtered.add(trans);
            }
            return filtered;
        }
        return null;
    }

    public List<Transaction> getTransactionsByUser(String IBAN) throws SQLException {
        List<Transaction> transactions = transactionDAO.read();
        List<Transaction> filtered = new ArrayList<>();

        if (!transactions.isEmpty()) {
            for (Transaction trans : transactions) {
                if (trans.getFromIBAN().equals(IBAN))
                    filtered.add(trans);
            }
            return filtered;
        }
        return null;
    }

    public void addTransaction(Transaction transaction) throws SQLException{
        if (transaction != null) {
            transactionDAO.create(transaction);
        } else {
            throw new IllegalStateException("Unexpected value: " + transaction);
        }

        System.out.println("Added " + transaction);
    }

    public void removeTransaction(Transaction transaction) throws SQLException{
        if (transaction != null) {
            transactionDAO.delete(transaction);
        } else {
            throw new IllegalStateException("Unexpected value: " + transaction);
        }

        System.out.println("Removed: " + transaction);
    }

    public void updateTransaction(Transaction transaction) throws SQLException {
        if (transaction != null)
            transactionDAO.update(transaction);
        else throw new IllegalStateException("Unexpected value: " + transaction);
    }

}
