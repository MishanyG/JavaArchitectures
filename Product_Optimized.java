package homework_8;

import java.sql.*;

// Найденные антипаттерны
// Poltergeist/Полтергейст
// Boat anchor/Лодочный якорь

public class Product_Optimized {
    private Connection connection;

    public Product_Optimized(Connection connection) {
        this.connection = connection;
    }

    public Product findById(int idProduct) {
        try {
            Product product = new Product();
            PreparedStatement statement = connection.prepareStatement("SELECT `idProduct`, `name`, `purchasePrice`, `price` FROM `products` where `idProduct`=?");
            statement.setInt(1, idProduct);
            ResultSet rs = statement.executeQuery();

            while(rs.next()) {
                product.setIdProduct(idProduct);
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
}

class Product {
    private int idProduct;
    private String name;
    private Long purchasePrice;
    private Long price;

    public Product(int idProduct, String name, Long purchasePrice, Long price) {
        this.idProduct = idProduct;
        this.name = name;
        this.purchasePrice = purchasePrice;
        this.price = price;
    }

    public Product() {
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

class Product_OptimizedView {
    public void printProductDetails(String name, Long purchasePrice, Long price) {
        System.out.println("Product: " + "\n" +
                "Name: " + name + "\n" +
                "Purchase price: " + purchasePrice + "\n" +
                "Sale price: " + price);
    }
}

class Product_OptimizedController {
    private Product_Optimized model;
    private Product_OptimizedView view;
    private Product product;

    public Product_OptimizedController(Product_Optimized model, Product_OptimizedView view) {
        this.view = view;
        this.model = model;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Product getProduct() {
        return product;
    }

    public void updateView() {
        view.printProductDetails(product.getName(), product.getPurchasePrice(), product.getPrice());
    }

    public void insert(Product product) {
        this.product = product;
        model.insert(product);
    }

    public void update(Product product) {
        this.model.update(product);
    }

    public Product findById(int idProduct) {
        return model.findById(idProduct);
    }
}

class Main_OptimizedMVC {
    public static void main(String[] args) throws SQLException {
        Driver driver = new com.mysql.cj.jdbc.Driver();
        DriverManager.registerDriver(driver);
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/product", "root", "mikha");

        Product_Optimized model = new Product_Optimized(connection);
        Product_OptimizedView view = new Product_OptimizedView();
        Product_OptimizedController controller = new Product_OptimizedController(model, view);

        Product product = controller.findById(2);
        controller.setProduct(product);
        controller.updateView();
        controller.getProduct().setName("LG");
        controller.update(product);
        controller.updateView();
        Product product1 = new Product(3, "ASUS", 4850L, 6760L);
        controller.insert(product1);
        controller.updateView();
    }
}