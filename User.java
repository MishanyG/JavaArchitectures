package homework_6;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class User {
    private int idUser;
    private String email;
    private String password;

    public User() {
    }

    public User(int idUser, String email, String password) {
        this.idUser = idUser;
        this.email = email;
        this.password = password;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

class UserMapper {
    private final Connection connection;

    UserMapper(Connection connection) {
        this.connection = connection;
    }

    public User findById(int idUser) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT `idUser`, `email`, `password` FROM `users` where `idUser`=?");
            statement.setInt(1, idUser);
            ResultSet rs = statement.executeQuery();

            while(rs.next()) {
                User user = new User();
                user.setIdUser(1);
                user.setEmail(rs.getString(2));
                user.setPassword(rs.getString(3));
                return user;
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void insert(User user) {
        try {
            PreparedStatement dbStatement = connection.prepareStatement("INSERT INTO `users` (`idUser`, `email`, `password`) VALUES (?, ?, ?)");
            dbStatement.setInt(1, user.getIdUser());
            dbStatement.setString(2, user.getEmail());
            dbStatement.setString(3, user.getPassword());
            dbStatement.executeUpdate();

        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(User user) {
        try {
            PreparedStatement dbStatement = connection.prepareStatement("UPDATE `users` SET `email`=?, `password`=? where `idUser`=?");
            dbStatement.setString(1, user.getEmail());
            dbStatement.setString(2, user.getPassword());
            dbStatement.setInt(3, user.getIdUser());
            dbStatement.executeUpdate();

        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(User user) {
        try {
            PreparedStatement dbStatement = connection.prepareStatement("DELETE FROM `users` where `idUser`=?");
            dbStatement.setInt(1, user.getIdUser());
            dbStatement.executeUpdate();

        } catch(SQLException e) {
            e.printStackTrace();
        }
    }
}

class MainUser {
    public static void main(String[] args) throws SQLException {
        Driver driver = new com.mysql.cj.jdbc.Driver();
        DriverManager.registerDriver(driver);
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/product", "root", "mikha");
        UserMapper userMapper = new UserMapper(connection);
        User user = userMapper.findById(1);
        System.out.println(user.getIdUser() + "\n" + user.getEmail() + "\n");
        User user1 = new User(2, "123@mail.ru", "321");
        userMapper.insert(user1);
        user1.setEmail("mail@mail.ru");
        userMapper.update(user1);
        userMapper.delete(user);
        IdentityMapUser im = new IdentityMapUser();
        im.addUser(user);
        im.addUser(user1);
        System.out.println(im.getUserFinder(2).getEmail());
    }
}

class IdentityMapUser {
    private static Map <Integer, User> userMapper = new HashMap <>();

    public static void addUser(User user) {
        userMapper.put(user.getIdUser(), user);
    }

    public User getUserFinder(int idUser) {
        return userMapper.get(idUser);
    }
}