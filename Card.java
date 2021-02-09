package homework_6;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class Card {
    private int idCard;
    private String name;
    private String cardNumber;
    private int cvv;
    private String expiryDate;

    public Card() {
    }

    public Card(int idCard, String name, String cardNumber, int cvv, String expiryDate) {
        this.idCard = idCard;
        this.name = name;
        this.cardNumber = cardNumber;
        this.cvv = cvv;
        this.expiryDate = expiryDate;
    }

    public int getIdCard() {
        return idCard;
    }

    public void setIdCard(int idCard) {
        this.idCard = idCard;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public int getCvv() {
        return cvv;
    }

    public void setCvv(int cvv) {
        this.cvv = cvv;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }
}

class CardMapper {
    private final Connection connection;

    CardMapper(Connection connection) {
        this.connection = connection;
    }

    public Card findById(int idCard) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT `idCard`, `name`, `cardNumber`, `cvv`, `expiryDate` FROM `cards` where `idCard`=?");
            statement.setInt(1, idCard);
            ResultSet rs = statement.executeQuery();

            while(rs.next()) {
                Card card = new Card();
                card.setIdCard(1);
                card.setName(rs.getString(2));
                card.setCardNumber(rs.getString(3));
                card.setCvv(rs.getInt(4));
                card.setExpiryDate(rs.getString(5));
                return card;
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void insert(Card card) {
        try {
            PreparedStatement dbStatement = connection.prepareStatement(
                    "INSERT INTO `cards` (`idCard`, `name`, `cardNumber`, `cvv`, `expiryDate`) VALUES (?, ?, ?, ?, ?)");
            dbStatement.setInt(1, card.getIdCard());
            dbStatement.setString(2, card.getName());
            dbStatement.setString(3, card.getCardNumber());
            dbStatement.setInt(4, card.getCvv());
            dbStatement.setString(5, card.getExpiryDate());
            dbStatement.executeUpdate();

        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Card card) {
        try {
            PreparedStatement dbStatement = connection.prepareStatement("UPDATE `cards` SET `name`=?, `cardNumber`=?, `cvv`=?, `expiryDate`=? where `idCard`=?");
            dbStatement.setString(1, card.getName());
            dbStatement.setString(2, card.getCardNumber());
            dbStatement.setInt(3, card.getCvv());
            dbStatement.setString(4, card.getExpiryDate());
            dbStatement.setInt(5, card.getIdCard());
            dbStatement.executeUpdate();

        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(Card card) {
        try {
            PreparedStatement dbStatement = connection.prepareStatement("DELETE FROM `cards` where `idCard`=?");
            dbStatement.setInt(1, card.getIdCard());
            dbStatement.executeUpdate();

        } catch(SQLException e) {
            e.printStackTrace();
        }
    }
}

class MainCard {
    public static void main(String[] args) throws SQLException {
        Driver driver = new com.mysql.cj.jdbc.Driver();
        DriverManager.registerDriver(driver);
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/product", "root", "mikha");
        CardMapper cardMapper = new CardMapper(connection);
        Card card = cardMapper.findById(1);
        System.out.println(card.getName() + "\n" + card.getCardNumber() + "\n" + card.getCvv() + "\n" + card.getExpiryDate());
        Card card1 = new Card(2, "Ivanon Ivan", "4567-2546-8956-2456", 123, "02/23");
        cardMapper.insert(card1);
        card1.setName("Petrov Petr");
        cardMapper.update(card1);
        cardMapper.delete(card);
        IdentityMapCard im = new IdentityMapCard();
        im.addCard(card);
        im.addCard(card1);
        System.out.println(im.getCardFinder(2).getName() + "\n"
                + im.getCardFinder(2).getCardNumber() + "\n"
                + im.getCardFinder(2).getCvv() + "\n"
                + im.getCardFinder(2).getExpiryDate());
    }
}

class IdentityMapCard {
    private static Map <Integer, Card> cardMapper = new HashMap <>();

    public static void addCard(Card card) {
        cardMapper.put(card.getIdCard(), card);
    }

    public Card getCardFinder(int idCard) {
        return cardMapper.get(idCard);
    }
}