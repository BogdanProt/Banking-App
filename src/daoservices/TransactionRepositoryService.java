package daoservices;

import dao.*;
import model.card.*;
import model.account.*;
import model.user.*;
import utils.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class TransactionRepositoryService {

    private TransactionDAO transactionDAO;

    public TransactionRepositoryService() {
        this.transactionDAO = new TransactionDAO();
    }

    public Transaction getTransactionByDate(Date date) {
        Transaction transaction = transactionDAO.read(date);

        if (transaction != null) {
            System.out.println(transaction);
        } else {
            System.out.println("No transaction having this exact date. Please check the date again!");
        }

        return transaction;
    }

    public void addTransaction(Transaction transaction) {
        if (transaction != null) {
            transactionDAO.create(transaction);
        } else {
            throw new IllegalStateException("Unexpected value: " + transaction);
        }

        System.out.println("Added " + transaction);
    }

    public void removeTransaction(Transaction transaction) {
        if (transaction != null) {
            transactionDAO.delete(transaction);
        } else {
            throw new IllegalStateException("Unexpected value: " + transaction);
        }

        System.out.println("Removed: " + transaction);
    }

    public List<Transaction> getAllTransactions() {
        if (!transactionDAO.getAllTransactions().isEmpty())
            return transactionDAO.getAllTransactions();
        else
            System.out.println("No transactions registered!");
        return null;
    }
}
