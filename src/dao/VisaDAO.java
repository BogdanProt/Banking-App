package dao;

import model.card.*;
import java.util.ArrayList;
import java.util.List;


public class VisaDAO {
    private static List<Visa> visaCards = new ArrayList<>();

    public Visa read(int cardID) {
        if (!visaCards.isEmpty()) {
            for (Visa vc : visaCards) {
                if (vc.getCardID() == cardID) {
                    return vc;
                }
            }
        }
        return null;
    }

    public void delete(Visa vc) {
        visaCards.remove(vc);
    }

    public void create(Visa vc) {
        visaCards.add(vc);
    }
}
