package dao;
import model.card.MasterCard;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import daoservices.DatabaseConnection;
import java.sql.*;

public class MasterCardDAO implements DaoInterface<MasterCard>{

    private static MasterCardDAO masterCardDAO;

    private Connection connection = DatabaseConnection.getConnection();

    private MasterCardDAO() throws SQLException {}

    public static MasterCardDAO getInstance() throws SQLException {
        if (masterCardDAO == null)
            masterCardDAO = new MasterCardDAO();
        return masterCardDAO;
    }

    @Override
    public void create(MasterCard masterCard) throws SQLException {
        String sql = "INSERT INTO Banking.MasterCard (CVV, cardNumber, name, IBAN, expirationDate) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, masterCard.getCVV());
            statement.setString(2, masterCard.getCardNumber());
            statement.setString(3, masterCard.getName());
            statement.setString(4, masterCard.getIBAN());
            statement.setString(5, (new SimpleDateFormat("yyyy-MM-dd")).format(masterCard.getExpirationDate()));
            statement.executeUpdate();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    masterCard.setCardID(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Failed.");
                }
            }

        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    @Override
    public List<MasterCard> read() throws SQLException {
        List<MasterCard> masterCards = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM Banking.MasterCard");
            while (result.next()) {
                MasterCard mc = new MasterCard(result);
                masterCards.add(mc);
            }
            statement.close();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return masterCards;
    }
    @Override
    public void update(MasterCard newMasterCard) throws SQLException {
        String sql = "UPDATE Banking.MasterCard SET CVV = ?, cardNumber = ?, name = ?, IBAN = ?, expirationDate = ? WHERE cardID = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, newMasterCard.getCVV());
            statement.setString(2, newMasterCard.getCardNumber());
            statement.setString(3, newMasterCard.getName());
            statement.setString(4, newMasterCard.getIBAN());
            statement.setString(5, (new SimpleDateFormat("yyyy-MM-dd")).format(newMasterCard.getExpirationDate()));
            statement.setInt(6, newMasterCard.getCardID());
            statement.executeUpdate();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    @Override
    public void delete(MasterCard masterCard) throws SQLException {
        String sql = "DELETE FROM Banking.MasterCard WHERE cardID = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, masterCard.getCardID());
            statement.executeUpdate();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
}
