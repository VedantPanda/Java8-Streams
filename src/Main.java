import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {

        System.out.println(stockIssueDetector());

        productCategoryQuantity();

        groupingByExample();

        getSummaryStatistics();

    }

    private static List<String> stockIssueDetector() {

        Map<String, Integer> productStock = new HashMap<>();
        productStock.put("P001", 100);
        productStock.put("P002", 50);
        productStock.put("P003", 200);

        List<Product> p1 = Arrays.asList(new Product("P001", "iPhone", "Electronics",
                        30, true, 200),
                new Product("P002", "Usb cable", "Electronics", 60, true, 200)
        );
        Order o1 = new Order("1", 1500, LocalDateTime.now().minusHours(3), p1);

        List<Product> p2 = Arrays.asList(new Product("P003", "iPhone", "Electronics",
                        50, true, 200),
                new Product("P002", "Usb cable", "Electronics", 40, true, 200)
        );
        Order o2 = new Order("2", 1500, LocalDateTime.now().minusHours(3), p2);

        List<Product> p3 = Arrays.asList(new Product("P003", "iPhone", "Electronics",
                        30, true, 200),
                new Product("P001", "Usb cable", "Electronics", 120, true, 200)
        );
        Order o3 = new Order("3", 1500, LocalDateTime.now().minusHours(3), p3);

        List<Order> orders = Arrays.asList(o1, o2, o3);

        return orders.stream()
                .filter(order -> order.getProducts()
                        .stream()
                        .anyMatch(product -> productStock.get(product.getProductId()) < product.getQuantity()))
                .map(Order::getOrderId)
                .toList();


    }

    private static void productCategoryQuantity() {

        List<Order> orders = getOrders();

        Map<String, Integer> productCategoryQuantityMap = orders.stream()
                .filter(order -> order.getTotalValue() > 500)
                .filter(order -> order.getOrderTime().isAfter(LocalDateTime.now().minusHours(24)))
                .flatMap(order -> order.getProducts().stream())
                .filter(Product::isInStock)
                .collect(Collectors.groupingBy(Product::getCategory, Collectors.summingInt(Product::getQuantity)))
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (a, b) -> a, LinkedHashMap::new));

        System.out.println(productCategoryQuantityMap);

    }

    private static List<Order> getOrders() {
        List<Product> p1 = Arrays.asList(new Product("1", "iPhone", "Electronics", 2, true, 200),
                new Product("2", "Usb cable", "Electronics", 5, true, 200),
                new Product("3", "Head phones", "Electronics", 0, false, 200)
        );

        Order o1 = new Order("1", 1500, LocalDateTime.now().minusHours(3), p1);

        List<Product> p2 = Arrays.asList(new Product("1", "T-shirt", "Clothing", 3, true, 200),
                new Product("2", "Jeans", "Clothing", 2, true, 200)
        );

        Order o2 = new Order("2", 600, LocalDateTime.now().minusHours(5), p2);

        List<Product> p3 = Arrays.asList(new Product("1", "Sandal", "Footwear", 1, true, 200),
                new Product("2", "Sneaker", "Footwear", 1, false, 200)
        );

        Order o3 = new Order("3", 400, LocalDateTime.now().minusHours(2), p3);

        List<Product> p4 = Arrays.asList(new Product("1", "Cookware", "Home & Kitchen", 1, true, 200),
                new Product("2", "Knife", "Home & Kitchen", 2, true, 200)
        );

        Order o4 = new Order("3", 1000, LocalDateTime.now().minusHours(26), p4);

        List<Product> p5 = Arrays.asList(new Product("1", "iphone", "Electronics", 1, true, 200),
                new Product("2", "T-shirt", "Clothing", 1, false, 200)
        );

        Order o5 = new Order("3", 700, LocalDateTime.now().minusHours(1), p5);

        return Arrays.asList(o1, o2, o3, o4, o5);

    }

    private static void groupingByExample() {

        List<Order> orders = getOrders();

        Map<String, List<Product>> categoryProductMap = orders.stream()
                .flatMap(order -> order.getProducts().stream())
                .collect(Collectors.groupingBy(Product::getCategory));

        System.out.println(categoryProductMap);

    }

    private static void getSummaryStatistics() {
        List<Order> orders = getOrders();
        IntSummaryStatistics summaryStatistics = orders.stream().flatMap(order -> order.getProducts().stream())
                .mapToInt(Product::getQuantity).summaryStatistics();
        System.out.println(summaryStatistics.getMax());
        System.out.println(summaryStatistics.getAverage());
        System.out.println(summaryStatistics.getCount());
        System.out.println(summaryStatistics.getMin());
        System.out.println(summaryStatistics.getSum());
    }

}
