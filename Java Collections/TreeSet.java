import java.util.TreeSet;

public class TreeSetExample {
    public static void main(String[] args) {
        TreeSet<Double> prices = new TreeSet<>();

        prices.add(999.99);
        prices.add(499.99);
        prices.add(1499.99);
        prices.add(799.99);
        prices.add(999.99);

        System.out.println(prices);

        System.out.println("Lowest Price: " + prices.first());
        System.out.println("Highest Price: " + prices.last());

        System.out.println("Greater Than 800: " + prices.higher(800.0));
        System.out.println("Less Than 800: " + prices.lower(800.0));
    }
}