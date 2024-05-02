package model.card;
import model.card.Card;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class MasterCard extends Card {
    public MasterCard(int cardID, String IBAN, String name){
        super(cardID, IBAN, name);
    }

    public MasterCard(ResultSet in) throws SQLException{
        super(in);
    }
}
