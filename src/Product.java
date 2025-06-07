public class Product {

    private final String productId;

    private final String name;

    private final String category;

    private final int quantity;

    private final boolean inStock;

    private final double price;

    public Product(String productId, String name, String category, int quantity, boolean inStock, double price) {
        this.productId = productId;
        this.name = name;
        this.category = category;
        this.quantity = quantity;
        this.inStock = inStock;
        this.price = price;
    }

    public String getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public int getQuantity() {
        return quantity;
    }

    public boolean isInStock() {
        return inStock;
    }

    public double getPrice() {
        return price;
    }
}
