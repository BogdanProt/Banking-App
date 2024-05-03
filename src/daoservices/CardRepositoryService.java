package daoservices;

import dao.*;
import model.card.*;
import model.account.*;
import model.user.*;
import utils.*;

import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

public class CardRepositoryService {

    private MasterCardDAO masterCardDAO = MasterCardDAO.getInstance();
    private VisaDAO visaDAO = VisaDAO.getInstance();

    public CardRepositoryService() throws SQLException {}

    public List<MasterCard> getMasterCards() throws SQLException {
        List<MasterCard> mastercards = masterCardDAO.read();
        if (!mastercards.isEmpty())
            for (MasterCard card : mastercards)
                System.out.println(card.getName() + " : " + card.getCardID());
        else
            System.out.println("No cards registered!");

        return mastercards;
    }

    public List<Visa> getVisaCards() throws SQLException {
        List<Visa> visacards = visaDAO.read();
        if (!visacards.isEmpty())
            for (Visa card : visacards)
                System.out.println(card.getName() + " : " + card.getCardID());
        else
            System.out.println("No cards registered!");

        return visacards;
    }

    public MasterCard getMasterCardByID(int cardID) throws SQLException{
        List<MasterCard> masterCards = getMasterCards();
        for (MasterCard card : masterCards)
            if (cardID == card.getCardID())
                return card;

        return null;
    }

    public Visa getVisaByID(int cardID) throws SQLException{
        List<Visa> visaCards = visaDAO.read();
        for (Visa card : visaCards)
            if (cardID == card.getCardID())
                return card;

        return null;
    }

    public void addCard(Card card) throws SQLException{
        if (card != null) {
            switch (card) {
                case MasterCard masterCard -> masterCardDAO.create(masterCard);
                case Visa visa -> visaDAO.create(visa);
                default -> throw new IllegalStateException("Unexpected value: " + card);
            }
        }

        System.out.println("Added " + card);
    }

    public void removeCard(String typeOfCard, int cardID) throws SQLException{
        Card card = getCard(typeOfCard, cardID);
        if (card == null) return;

        switch (card) {
            case MasterCard masterCard -> masterCardDAO.delete(masterCard);
            case Visa visa -> visaDAO.delete(visa);
            default -> throw new IllegalStateException("Unexpected value: " + card);
        }

        System.out.println("Removed " + card);
    }

    public Card getCard(String typeOfCard, int cardID) throws SQLException{
        Card card = null;
        if (typeOfCard.toLowerCase().equals("mastercard")) {
            card = getMasterCardByID(cardID);
        } else if (typeOfCard.toLowerCase().equals("visa")) {
            card = getVisaByID(cardID);
        }

        if (card == null) {
            System.out.println("No card of given type having id " + cardID);
            return null;
        }

        return card;
    }

    public void updateCard(Card card) throws SQLException {
        if (card != null) {
            switch (card) {
                case MasterCard masterCard -> masterCardDAO.update(masterCard);
                case Visa visa -> visaDAO.update(visa);
                default -> throw new IllegalStateException("Unexpected value: " + card);
            }
        }
    }
}
