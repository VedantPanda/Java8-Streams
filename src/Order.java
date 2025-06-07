import java.time.LocalDateTime;
import java.util.List;

public class Order {

    private final String orderId;

    private final double totalValue;

    private final List<Product> products;

    private final LocalDateTime orderTime;

    public Order(String orderId, double totalValue, LocalDateTime orderTime, List<Product> products) {
        this.orderId = orderId;
        this.totalValue = totalValue;
        this.orderTime = orderTime;
        this.products = products;
    }

    public String getOrderId() {
        return orderId;
    }

    public double getTotalValue() {
        return totalValue;
    }

    public LocalDateTime getOrderTime() {
        return orderTime;
    }

    public List<Product> getProducts() {
        return products;
    }
}
