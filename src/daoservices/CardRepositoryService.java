package daoservices;

import dao.*;
import model.card.*;
import model.account.*;
import model.user.*;
import utils.*;

public class CardRepositoryService {

    private MasterCardDAO masterCardDAO;
    private VisaDAO visaDAO;

    public CardRepositoryService() {
        this.masterCardDAO = new MasterCardDAO();
        this.visaDAO = new VisaDAO();
    }

    public MasterCard getMasterCardByID(int cardID) {
        MasterCard masterCard = masterCardDAO.read(cardID);
        if (masterCard != null) {
            System.out.println(masterCard);
        } else {
            System.out.println("No mastercard having this ID!");
        }

        return masterCard;
    }

    public Visa getVisaByID(int cardID) {
        Visa visa = visaDAO.read(cardID);
        if (visa != null) {
            System.out.println(visa);
        } else {
            System.out.println("No visa having this ID!");
        }

        return visa;
    }

    public void addCard(Card card) {
        if (card != null) {
            switch (card) {
                case MasterCard masterCard -> masterCardDAO.create(masterCard);
                case Visa visa -> visaDAO.create(visa);
                default -> throw new IllegalStateException("Unexpected value: " + card);
            }
        }

        System.out.println("Added " + card);
    }

    public void removeCard(String typeOfCard, int cardID) {
        Card card = getCard(typeOfCard, cardID);
        if (card == null) return;

        switch (card) {
            case MasterCard masterCard -> masterCardDAO.delete(masterCard);
            case Visa visa -> visaDAO.delete(visa);
            default -> throw new IllegalStateException("Unexpected value: " + card);
        }

        System.out.println("Removed " + card);
    }

    public Card getCard(String typeOfCard, int cardID) {
        Card card;
        if (typeOfCard.toLowerCase().equals("mastercard")) {
            card = getMasterCardByID(cardID);
        } else if (typeOfCard.toLowerCase().equals("visa")) {
            card = getVisaByID(cardID);
        }

        if (card == null) {
            System.out.println("No card having id " + cardID);
            return null;
        }

        return card;
    }
}
