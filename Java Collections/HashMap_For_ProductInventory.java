import java.util.HashMap;

public class ProductInventoryExample {
    public static void main(String[] args) {
        HashMap<String, Integer> inventory = new HashMap<>();

        inventory.put("Laptop", 25);
        inventory.put("Mobile", 50);
        inventory.put("Tablet", 30);
        inventory.put("Monitor", 15);

        inventory.put("Laptop", inventory.get("Laptop") - 2);

        for (String product : inventory.keySet()) {
            System.out.println(product + " = " + inventory.get(product));
        }
    }
}