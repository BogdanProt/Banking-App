package utils;
import model.card.Card;
import model.card.MasterCard;
import model.card.Visa;

public class CardSeparation {
    private static int uniqueID;

    public Card addCard(String IBAN, String name) {
        return new Card(uniqueID++, IBAN, name);
    }

    public MasterCard createMasterCard(String IBAN, String name) {
        return new MasterCard(0, IBAN, name);
    }

    public Visa createVisaCard(String IBAN, String name) {
        return new Visa(0, IBAN, name);
    }

}
