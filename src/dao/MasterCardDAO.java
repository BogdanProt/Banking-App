package dao;
import model.card.*;
import java.util.ArrayList;
import java.util.List;

public class MasterCardDAO {

    private static List<MasterCard> masterCards = new ArrayList<>();

    public MasterCard read(int cardID) {
        if (!masterCards.isEmpty()) {
            for (MasterCard mc : masterCards) {
                if (mc.getCardID() == cardID) {
                    return mc;
                }
            }
        }
        return null;
    }

    public void delete(MasterCard mc) {
        masterCards.remove(mc);
    }

    public void create(MasterCard mc) {
        masterCards.add(mc);
    }

}
