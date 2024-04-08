package dao;

import model.account.*;
import java.util.List;
import java.util.ArrayList;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TransactionDAO {

    private static List<Transaction> transactions = new ArrayList<>();

    public Transaction read(String fromIBAN) {
        if (!transactions.isEmpty()) {
            for (Transaction trans : transactions) {
                if (fromIBAN == trans.getFromIBAN()) {
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
}
