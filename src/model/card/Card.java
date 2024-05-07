package model.card;

import utils.LuhnAlgorithm;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;


public class Card {
    private int cardID, CVV;
    private String cardNumber;
    private String name;
    private String IBAN;
    final private Date expirationDate;

    LuhnAlgorithm luhnAlgo = new LuhnAlgorithm();

    public Card(int cardID, String IBAN, String name) {
        this.cardID = cardID;
        this.IBAN = IBAN;
        this.name = name;
        this.cardNumber = luhnAlgo.cardNumberGenerator();


        Random random = new Random();
        this.CVV = random.nextInt(900) + 100;

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.YEAR, 4);
        this.expirationDate = calendar.getTime();
    }

    public Card(ResultSet in) throws SQLException {
        this.cardID = in.getInt("cardID");
        this.CVV = in.getInt("CVV");
        this.cardNumber = in.getString("cardNumber");
        this.name = in.getString("name");
        this.IBAN = in.getString("IBAN");
        this.expirationDate = in.getDate("expirationDate");
    }

    public int getCardID() { return cardID; }

    public int getCVV() { return CVV; }

    public String getCardNumber() { return cardNumber; }

    public String getName() { return name; }

    public String getIBAN() { return IBAN; }

    public Date getExpirationDate() { return expirationDate; }

    public void setName(String name) { this.name = name; }

    public void setIBAN(String IBAN) { this.IBAN = IBAN; }

    public void setCardID(int cardID) { this.cardID = cardID; }

    @Override
    public String toString() {
        return "Card{" +
                "ID='" + cardID + '\'' +
                ", CVV='" + CVV + '\'' +
                ", Number=" + cardNumber +
                ", Name='" + name + '\'' +
                ", IBAN=" + IBAN +
                ", Expiration Date=" + (new SimpleDateFormat("yyyy-MM-dd+HH:mm:ss")).format(expirationDate) +
                '}';
    }

}
