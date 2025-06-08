import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {

        System.out.println(stockIssueDetector());

        productCategoryQuantity();

        groupingByExample();

        getSummaryStatistics();

        sliceStreamExample();

        stringJoinExample();

        duplicateElementsInStreamExample();

        duplicateElementsInStreamUsingGroupingBy();

        duplicateElementsInStreamUsingFrequency();

        findEmployeeByDescendingSalary();

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

    private static void sliceStreamExample() {
        List<Order> orders = getOrders();
        List<Integer> products = orders.stream().flatMap(order -> order.getProducts().stream())
                .map(Product::getQuantity).sorted().toList();
        System.out.println(products.stream().skip(1).limit(2).toList());
    }

    private static void stringJoinExample() {
        List<String> stringList = Arrays.asList("Virat", "Rohit", "Bumrah", "Sky");
        String names = stringList.stream().map(String::toUpperCase).collect(Collectors.joining(","));
        System.out.println(names);
    }

    private static void duplicateElementsInStreamExample() {
        List<Order> orders = getOrders();
        Set<String> categories = new HashSet<>();
        Set<String> duplicateCategories = orders.stream().flatMap(order -> order.getProducts().stream())
                .map(Product::getCategory)
                .filter(category -> !categories.add(category))
                .collect(Collectors.toSet());
        System.out.println(duplicateCategories);
    }

    private static void duplicateElementsInStreamUsingGroupingBy() {
        List<Order> orders = getOrders();
        Map<String, Long> categoryOccurenceMap = orders.stream().flatMap(order -> order.getProducts().stream())
                .map(Product::getCategory)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        Set<String> duplicateCategories = categoryOccurenceMap.entrySet()
                .stream()
                .filter(category -> category.getValue() > 1)
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());
        System.out.println(duplicateCategories);
    }

    private static void duplicateElementsInStreamUsingFrequency() {
        List<Order> orders = getOrders();
        List<String> categories = orders.stream().flatMap(order -> order.getProducts().stream())
                .map(Product::getCategory).toList();
        Set<String> duplicateCategories = categories.stream()
                .filter(category -> Collections.frequency(categories, category) > 1)
                .collect(Collectors.toSet());
        System.out.println(duplicateCategories);
    }

    private static void findEmployeeByDescendingSalary() {

        Employee e1 = new Employee(1, 500);
        Employee e2 = new Employee(2, 1000);
        Employee e3 = new Employee(3, 1500);
        Employee e4 = new Employee(4, 2000);
        Employee e5 = new Employee(5, 2500);
        Employee e6 = new Employee(6, 3000);
        Employee e7 = new Employee(7, 3500);

        List<Employee> employees = Arrays.asList(e1, e2, e3, e4, e5, e6, e7);

        List<Employee> employeeBySalary = employees.stream()
                .sorted((a, b) -> Math.toIntExact(b.getSalary() - a.getSalary())).skip(3).toList();

        employeeBySalary.forEach(employee -> System.out.println(employee.toString()));

    }

}
