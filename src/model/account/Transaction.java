package model.account;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Transaction {
    final private String fromIBAN, toIBAN;
    final private String description;
    final private double amount;
    final private Date issuedDate;

    public Transaction(String fromIBAN, String toIBAN, String description, double amount) throws Exception {
        if (amount <= 0)
            throw new Exception("The issued amount is too low!");

        this.fromIBAN = fromIBAN;
        this.toIBAN = toIBAN;
        this.description = description;
        this.amount = amount;
        this.issuedDate = new Date();
    }

    public Transaction(String fromIBAN, String toIBAN, String description, double amount, Date issuedDate) throws Exception{
        if (amount <= 0)
            throw new Exception("The issued amount is too low!");

        this.fromIBAN = fromIBAN;
        this.toIBAN = toIBAN;
        this.description = description;
        this.amount = amount;
        this.issuedDate = issuedDate;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "from=" + fromIBAN +
                ", to=" + toIBAN +
                ", description=" + description + '\'' +
                ", amount='" + amount +
                ", issuedDate=" + (new SimpleDateFormat("yyyy-MM-dd+HH:mm:ss")).format(issuedDate) +
                '}';
    }

    public String getFromIBAN() { return fromIBAN; }

    public String getToIBAN() { return toIBAN; }

    public String getDescription() { return description; }

    public double getAmount() { return amount; }

    public Date getIssuedDate() { return issuedDate; }


}
