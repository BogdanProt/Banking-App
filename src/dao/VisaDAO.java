package dao;
import model.card.Visa;

import model.card.*;
import java.util.ArrayList;
import java.util.List;
import java.text.SimpleDateFormat;
import daoservices.DatabaseConnection;
import java.sql.*;


public class VisaDAO implements DaoInterface<Visa> {

    private static VisaDAO visaDAO;

    private Connection connection = DatabaseConnection.getConnection();
    private VisaDAO() throws SQLException {}

    public static VisaDAO getInstance() throws SQLException {
        if (visaDAO == null)
            visaDAO = new VisaDAO();
        return visaDAO;
    }

    @Override
    public void create(Visa visa) throws SQLException {
        String sql = "INSERT INTO Banking.Visa (CVV, cardNumber, name, IBAN, expirationDate) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, visa.getCVV());
            statement.setString(2, visa.getCardNumber());
            statement.setString(3, visa.getName());
            statement.setString(4, visa.getIBAN());
            statement.setString(5, (new SimpleDateFormat("yyyy-MM-dd")).format(visa.getExpirationDate()));
            statement.executeUpdate();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    visa.setCardID(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Failed.");
                }
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    @Override
    public List<Visa> read() throws SQLException {
        List<Visa> visaCards = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM Banking.Visa");
            while (result.next()) {
                Visa vc = new Visa(result);
                visaCards.add(vc);
            }
            statement.close();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return visaCards;
    }

    @Override
    public void update(Visa newVisaCard) throws SQLException {
        String sql = "UPDATE Banking.Visa SET CVV = ?, cardNumber = ?, name = ?, IBAN = ?, expirationDate = ? WHERE cardID = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, newVisaCard.getCVV());
            statement.setString(2, newVisaCard.getCardNumber());
            statement.setString(3, newVisaCard.getName());
            statement.setString(4, newVisaCard.getIBAN());
            statement.setString(5, (new SimpleDateFormat("yyyy-MM-dd")).format(newVisaCard.getExpirationDate()));
            statement.setInt(6, newVisaCard.getCardID());
            statement.executeUpdate();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    @Override
    public void delete(Visa visaCard) throws SQLException {
        String sql = "DELETE FROM Banking.Visa WHERE cardID = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, visaCard.getCardID());
            statement.executeUpdate();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

}
