import java.util.LinkedList;

public class LinkedListExample {
    public static void main(String[] args) {
        LinkedList<String> customers = new LinkedList<>();

        customers.add("Rahul");
        customers.add("Priya");
        customers.add("Arun");

        customers.addFirst("Sneha");
        customers.addLast("Kiran");

        System.out.println(customers);

        System.out.println("First Customer: " + customers.getFirst());
        System.out.println("Last Customer: " + customers.getLast());

        customers.removeFirst();
        customers.removeLast();

        System.out.println(customers);
    }
}