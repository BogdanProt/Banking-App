package model.card;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;


public class Card {
    final private int cardID, CVV;
    private String cardNumber;
    private String name;
    private String IBAN;
    final private Date expirationDate;

    static private final Set<String> checkCardNumber = new HashSet<>();

    public Card(int cardID, String IBAN, String name) {
        this.cardID = cardID;
        this.IBAN = IBAN;
        this.name = name;
        this.cardNumber = cardNumberGenerator();

        while(checkCardNumber.contains(cardNumber)){
            this.cardNumber = cardNumberGenerator();
        }
        checkCardNumber.add(cardNumber);

        this.CVV = generateCVV();

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

    public String cardNumberGenerator() {
        Random random = new Random();
        int firstDigit = random.nextInt(9) + 1; // Cannot start with 1

        StringBuilder cardNumberBuilder = new StringBuilder();
        cardNumberBuilder.append(firstDigit);
        for (int i = 0; i < 15; i++) {
            cardNumberBuilder.append(random.nextInt(10));
        }

        // Luhn's algorithm
        String cardNumber = cardNumberBuilder.toString();
        int sum = 0;
        boolean alternate = false;
        for (int i = cardNumber.length() - 1; i >= 0; i--) {
            int digit = Integer.parseInt(cardNumber.substring(i, i + 1));
            if (alternate) {
                digit *= 2;
                if (digit > 9) {
                    digit -= 9;
                }
            }
            sum += digit;
            alternate = !alternate;
        }
        int checksum = (sum % 10 == 0) ? 0 : 10 - (sum % 10);

        // Append the checksum to the generated number
        cardNumber += checksum;

        return cardNumber;
    }

    public int generateCVV() {
        Random random = new Random();
        return random.nextInt(900) + 100;
    }

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
