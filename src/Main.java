import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {

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

        List<Order> orders = Arrays.asList(o1, o2, o3, o4, o5);

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

}
