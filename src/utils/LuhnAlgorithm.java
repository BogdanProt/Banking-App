package utils;

import java.util.Random;

public class LuhnAlgorithm {

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
}
