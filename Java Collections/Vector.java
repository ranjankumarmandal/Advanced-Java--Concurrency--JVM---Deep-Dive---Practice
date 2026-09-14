// Legacy Synchronized implementation for ArrayList
import java.util.Vector;

public class VectorExample {
    public static void main(String[] args) {
        Vector<String> products = new Vector<>();

        products.add("Laptop");
        products.add("Mobile");
        products.add("Tablet");

        System.out.println(products);

        products.addElement("Monitor");

        System.out.println(products);

        products.remove("Tablet");

        System.out.println(products);

        System.out.println("Size: " + products.size());
        System.out.println("Capacity: " + products.capacity());
    }
}