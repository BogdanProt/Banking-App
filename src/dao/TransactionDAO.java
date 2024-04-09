package dao;

import model.account.*;
import java.util.List;
import java.util.ArrayList;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TransactionDAO {

    private static List<Transaction> transactions = new ArrayList<>();

    public Transaction read(Date date) {
        if (!transactions.isEmpty()) {
            for (Transaction trans : transactions) {
                if (date == trans.getIssuedDate()) {
                    return trans;
                }
            }
        }
        return null;
    }

    public void create(Transaction trans) {
        transactions.add(trans);
    }

    public void delete(Transaction trans) {
        transactions.remove(trans);
    }

    public List<Transaction> getAllTransactions() { return transactions; }
}
