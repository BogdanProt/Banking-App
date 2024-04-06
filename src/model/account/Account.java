package model.account;

import model.card.*;
import utils.CardSeparation;
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

    public void setBalance(double balance) { this.balance = balance; }

    public String getIBAN() { return IBAN; }

    public String getSwift() { return swift; }

    public double getBalance() { return balance; }

    public String getName() { return name; }

    public int getUserID() { return userID; }

    public List<Card> getCards() { return cards; }


    public int compare(Transaction transaction1, Transaction transaction2){
        return transaction1.getIssuedDate().compareTo(transaction2.getIssuedDate());
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
