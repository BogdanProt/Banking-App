package model.account;

import model.card.*;
import utils.CardSeparation;

import java.sql.*;
import java.util.*;

public class Account {
    protected String IBAN, swift;
    protected double balance;
    protected String name;
    protected int userID;

    public Account(String IBAN, String swift, double balance, String name, int userID) {
        this.IBAN = IBAN;
        this.swift = swift;
        this.balance = balance;
        this.name = name;
        this.userID = userID;
    }

    public Account(String name, int userID, int uniqueID) {
        this.IBAN = "RO67" + "RZBR" + "R" + uniqueID;
        this.swift = "SWIFT" + "RZBR" + "R";
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

    public void setIBAN(String IBAN) { this.IBAN = IBAN; }

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
