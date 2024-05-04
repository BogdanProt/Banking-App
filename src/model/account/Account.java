package model.account;

import model.card.*;
import utils.CardSeparation;

import java.sql.*;
import java.util.*;

public class Account implements Comparator<Transaction>{
    protected String IBAN, swift;
    protected double balance;
    protected String name;
    protected int userID;
    protected List<Card> cards = new ArrayList<>();

    private final CardSeparation cardSeparation = new CardSeparation();

    public Account(String IBAN, String swift, double balance, String name, int userID) {
        this.IBAN = IBAN;
        this.swift = swift;
        this.balance = balance;
        this.name = name;
        this.userID = userID;
    }

    public Account(String name, int userID, int uniqueID) {
        this.IBAN = this.IBANGenerator(uniqueID);
        this.swift = this.SWIFTGenerator();
        this.balance = 0;
        this.name = name;
        this.userID = userID;
    }

    public Account(ResultSet in) throws SQLException {
        this.IBAN = in.getString("IBAN");
        this.swift = in.getString("swift");
        this.balance = in.getDouble("balance");
        this.name = in.getString("name");
        this.userID = in.getInt("userID");
    }

    public void setBalance(double balance) { this.balance = balance; }

    public String getIBAN() { return IBAN; }

    public String getSwift() { return swift; }

    public double getBalance() { return balance; }

    public String getName() { return name; }

    public int getUserID() { return userID; }

    public List<Card> getCards() { return cards; }


    public List<Transaction> getTransactionsPerAccount(List<Transaction> allTransactions) {
        List<Transaction> transactions = new ArrayList<>();

        for (Transaction transaction : allTransactions) {
            if (transaction.getFromIBAN().equals(this.IBAN)) {
                transactions.add(transaction);
            }
        }

        return transactions;
    }
    public List<Transaction> getTransactionsPerAccountByYear(List<Transaction> allTransactions, int year) {
        List<Transaction> transactions = new ArrayList<>();

        for(Transaction transaction : allTransactions) {
            if (transaction.getFromIBAN().equals(this.IBAN) && transaction.getIssuedDate().getYear() == year) {
                transactions.add(transaction);
            }
        }

        return transactions;
    }

    public void addCard(String name) {
        Card card = cardSeparation.addCard(this.IBAN, name);
        cards.add(card);
    }

    private String IBANGenerator(int uniqueID) {
        String bankCode = "RZBR";
        return "RO67" + bankCode + "R" + uniqueID;
    }

    private String SWIFTGenerator() {
        String bankCode = "RZBR";
        return "SWIFT" + bankCode + "R";
    }

    @Override
    public String toString() {
        return "Account{" +
                "IBAN='" + IBAN + '\'' +
                ", swift='" + swift + '\'' +
                ", balance=" + balance +
                ", name='" + name + '\'' +
                ", userID=" + userID +
                '}';
    }
}
