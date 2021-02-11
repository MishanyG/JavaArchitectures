package homework_7;

import java.sql.*;

public class Product {
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

class ProductView {
    public void printProductDetails(String name, Long purchasePrice, Long price) {
        System.out.println("Product: " + "\n" +
                "Name: " + name + "\n" +
                "Purchase price: " + purchasePrice + "\n" +
                "Sale price: " + price);
    }
}

class ProductController {
    private Product model;
    private ProductView view;
    private ProductDB productDB;

    public ProductController(int idProduct, Product model, ProductView view, ProductDB productDB) {
        this.view = view;
        this.productDB = productDB;
        this.model = model;
        this.model = findById(idProduct);
    }

    public String getProductName() {
        return model.getName();
    }

    public void setProductName(String name) {
        model.setName(name);
    }

    public Long getProductPurchasePrice() {
        return model.getPurchasePrice();
    }

    public void setProductPurchasePrice(Long purchasePrice) {
        model.setPurchasePrice(purchasePrice);
    }

    public Long getProductPrice() {
        return model.getPrice();
    }

    public void setProductPrice(Long price) {
        model.setPrice(price);
    }

    public void updateView() {
        view.printProductDetails(model.getName(), model.getPurchasePrice(), model.getPrice());
    }

    public void insert() {
        productDB.insert(model);
    }

    public void update() {
        productDB.update(model);
    }

    public Product findById(int idProduct) {
        return productDB.findById(idProduct);
    }
}

class MainMVC {
    public static void main(String[] args) throws SQLException {
        Driver driver = new com.mysql.cj.jdbc.Driver();
        DriverManager.registerDriver(driver);
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/product", "root", "mikha");
        ProductDB productDB = new ProductDB(connection);

        Product model = new Product();
        ProductView view = new ProductView();
        ProductController controller = new ProductController(2, model, view, productDB);

        controller.updateView();
        controller.setProductName("LG");
        controller.setProductPurchasePrice(3560L);
        controller.setProductPrice(5780L);
        controller.update();
        controller.updateView();
    }
}

class ProductDB {
    private final Connection connection;

    ProductDB(Connection connection) {
        this.connection = connection;
    }

    public Product findById(int idProduct) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT `idProduct`, `name`, `purchasePrice`, `price` FROM `products` where `idProduct`=?");
            statement.setInt(1, idProduct);
            ResultSet rs = statement.executeQuery();

            while(rs.next()) {
                Product product = new Product();
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