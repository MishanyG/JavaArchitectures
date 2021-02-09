package homework_6;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class Product {
    private int idProduct;
    private String name;
    private Long purchasePrice;
    private Long price;

    public Product() {
    }

    public Product(int idProduct, String name, Long purchasePrice, Long price) {
        this.idProduct = idProduct;
        this.name = name;
        this.purchasePrice = purchasePrice;
        this.price = price;
    }

    public int getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(Long purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }
}

class ProductMapper {
    private final Connection connection;

    ProductMapper(Connection connection) {
        this.connection = connection;
    }

    public Product findById(int idProduct) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT `idProduct`, `name`, `purchasePrice`, `price` FROM `products` where `idProduct`=?");
            statement.setInt(1, idProduct);
            ResultSet rs = statement.executeQuery();

            while(rs.next()) {
                Product product = new Product();
                product.setIdProduct(1);
                product.setName(rs.getString(2));
                product.setPurchasePrice(rs.getLong(3));
                product.setPrice(rs.getLong(4));
                return product;
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void insert(Product product) {
        try {
            PreparedStatement dbStatement = connection.prepareStatement("INSERT INTO `products` (`idProduct`, `name`, `purchasePrice`, `price`) VALUES (?, ?, ?, ?)");
            dbStatement.setInt(1, product.getIdProduct());
            dbStatement.setString(2, product.getName());
            dbStatement.setLong(3, product.getPurchasePrice());
            dbStatement.setLong(4, product.getPrice());
            dbStatement.executeUpdate();

        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Product product) {
        try {
            PreparedStatement dbStatement = connection.prepareStatement("UPDATE `products` SET `name`=?, `purchasePrice`=?, `price`=? where `idProduct`=?");
            dbStatement.setString(1, product.getName());
            dbStatement.setLong(2, product.getPurchasePrice());
            dbStatement.setLong(3, product.getPrice());
            dbStatement.setInt(4, product.getIdProduct());
            dbStatement.executeUpdate();

        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(Product product) {
        try {
            PreparedStatement dbStatement = connection.prepareStatement("DELETE FROM `products` where `idProduct`=?");
            dbStatement.setInt(1, product.getIdProduct());
            dbStatement.executeUpdate();

        } catch(SQLException e) {
            e.printStackTrace();
        }
    }
}

class MainProduct {
    public static void main(String[] args) throws SQLException {
        Driver driver = new com.mysql.cj.jdbc.Driver();
        DriverManager.registerDriver(driver);
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/product", "root", "mikha");
        ProductMapper productMapper = new ProductMapper(connection);
        Product product = productMapper.findById(1);
        System.out.println(product.getName() + "\n" + product.getPurchasePrice() + "\n" + product.getPrice());
        Product product1 = new Product(2, "LG", 8450L, 9360L);
        productMapper.insert(product1);
        product1.setName("SAMSUNG");
        productMapper.update(product1);
        productMapper.delete(product);
        IdentityMap im = new IdentityMap();
        im.addProduct(product);
        im.addProduct(product1);
        System.out.println(im.getProductFinder(2).getName() + "\n"
                + im.getProductFinder(2).getPurchasePrice() + "\n"
                + im.getProductFinder(2).getPrice());
    }
}

class IdentityMap {
    private static Map <Integer, Product> productMap = new HashMap <>();

    public static void addProduct(Product product) {
        productMap.put(product.getIdProduct(), product);
    }

    public Product getProductFinder(int idProduct) {
        return productMap.get(idProduct);
    }
}