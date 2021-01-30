package homework_3;

abstract class Product {

    Long purchasePrice;
    Long price;
    String description;

    public abstract Long PurchasePrice();

    public abstract Long Price();

    public abstract String Description();
}

abstract class Creator {

    public abstract Product FactoryMethod(Long purchasePrice, Long price, String description);
}

class PhoneCreator extends Creator {

    @Override
    public Product FactoryMethod(Long purchasePrice, Long price, String description) {
        return new Phone(purchasePrice, price, description);
    }
}

class Phone extends Product {

    public Phone(Long purchasePrice, Long price, String description) {
        this.purchasePrice = purchasePrice;
        this.price = price;
        this.description = description;
    }

    @Override
    public Long PurchasePrice() {
        return purchasePrice;
    }

    @Override
    public Long Price() {
        return price;
    }

    @Override
    public String Description() {
        return description;
    }
}

class Example {
    public static void main(String[] args) {
        Creator creators = new PhoneCreator();
        Product product = creators.FactoryMethod(500L, 700L, "LG");
        System.out.println("Закупочная цена: " + product.PurchasePrice() + "\n" + "Цена продажи: " + product.Price() + "\n" + "Описание: " + product.Description());
    }
}
