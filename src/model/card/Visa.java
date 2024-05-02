package model.card;
import model.card.Card;
import java.sql.ResultSet;
import java.sql.SQLException;
public class Visa extends Card{
    public Visa(int cardID, String IBAN, String name){
        super(cardID, IBAN, name);
    }

    public Visa(ResultSet in) throws SQLException{
        super(in);
    }
}
