package homework_5;

import java.util.ArrayList;
import java.util.List;

public interface Payment {
    void pay(int amount);
}

class Card implements Payment {

    private String name;
    private String cardNumber;
    private String cvv;
    private String expiryDate;

    public Card(String name, String cardNumber, String cvv, String expiryDate) {
        this.name = name;
        this.cardNumber = cardNumber;
        this.cvv = cvv;
        this.expiryDate = expiryDate;
    }

    @Override
    public void pay(int amount) {
        System.out.println(amount + " :pay credit card");
    }

    public String getName() {
        return name;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getCvv() {
        return cvv;
    }

    public String getExpiryDate() {
        return expiryDate;
    }
}

class PayPal implements Payment {

    private String email;
    private String password;

    public PayPal(String email, String password) {
        this.email = email;
        this.password = password;
    }

    @Override
    public void pay(int amount) {
        System.out.println(amount + " :pay PayPal");
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}

class Product {

    private int idProduct;
    private int price;

    public Product(int idProduct, int price) {
        this.idProduct = idProduct;
        this.price = price;
    }

    public int getIdProduct() {
        return idProduct;
    }

    public int getPrice() {
        return price;
    }

}

class Cart {

    List <Product> products;

    public Cart() {
        products = new ArrayList <>();
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public void removeProduct(Product product) {
        products.remove(product);
    }

    public int total() {
        int i = 0;
        for(Product product : products) {
            i += product.getPrice();
        }
        return i;
    }

    public void pay(Payment payment) {
        int amount = total();
        payment.pay(amount);
    }
}

class Client {

    public static void main(String[] args) {

        Cart cart = new Cart();

        Product product_1 = new Product(5, 1500);
        Product product_2 = new Product(12, 3210);
        Product product_3 = new Product(8, 700);
        Product product_4 = new Product(23, 810);

        cart.addProduct(product_1);
        cart.addProduct(product_2);

        cart.pay(new PayPal("petrov@mail.ru", "password"));

        cart.removeProduct(product_1);
        cart.removeProduct(product_2);

        cart.addProduct(product_3);
        cart.addProduct(product_4);

        cart.pay(new Card("Petrov Petr", "5000-1245-8956-5467", "321", "03/23"));
    }

}