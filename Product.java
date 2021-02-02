package homework_4;

abstract class Product {
    public String name;

    public String getInfo() {
        return name;
    }

    public abstract int getPrice();
}

abstract class Decorator extends Product {
    public abstract String getInfo();
}

class ASUS extends Product {
    public ASUS() {
        name = "ASUS ROG Crosshair VIII Impact";
    }

    public int getPrice() {
        return 30000;
    }
}

class MSI extends Product {
    public MSI() {
        name = "MSI B350i Pro AC";
    }

    public int getPrice() {
        return 25000;
    }
}

class WIFI extends Decorator {
    Product Product;

    public WIFI(Product Product) {
        this.Product = Product;
    }

    public String getInfo() {
        return Product.getInfo() + " + WI-FI module";
    }

    public int getPrice() {
        return Product.getPrice() + 3500;
    }

}

class RAM extends Decorator {
    Product Product;

    public RAM(Product Product) {
        this.Product = Product;
    }

    public String getInfo() {
        return Product.getInfo() + " + RAM bar";
    }

    public int getPrice() {
        return Product.getPrice() + 1500;
    }
}

class SSD extends Decorator {
    Product Product;

    public SSD(Product Product) {
        this.Product = Product;
    }

    public String getInfo() {
        return Product.getInfo() + " + SSD disk";
    }

    public int getPrice() {
        return Product.getPrice() + 4800;
    }
}

class Client {
    public static void main(String[] args) {
        Product PC1 = new ASUS();
        print(PC1.getInfo(), PC1.getPrice());

        PC1 = new WIFI(PC1);
        print(PC1.getInfo(), PC1.getPrice());

        PC1 = new RAM(PC1);
        print(PC1.getInfo(), PC1.getPrice());

        Product PC2 = new RAM(new WIFI(new MSI()));
        print(PC2.getInfo(), PC2.getPrice());

        PC2 = new SSD(PC2);
        print(PC2.getInfo(), PC2.getPrice());
    }

    public static void print (String info, int price) {
        System.out.println("Product: " + info + "\n" + "Price: " + price);
    }
}