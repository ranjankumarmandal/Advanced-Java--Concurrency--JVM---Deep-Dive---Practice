import java.util.LinkedList;
import java.util.Queue;

public class QueueExample {
    public static void main(String[] args) {
        Queue<String> customers = new LinkedList<>();

        customers.offer("Customer 1");
        customers.offer("Customer 2");
        customers.offer("Customer 3");

        System.out.println(customers);

        System.out.println("Next Customer: " + customers.peek());

        System.out.println("Served: " + customers.poll());

        System.out.println(customers);

        System.out.println("Next Customer: " + customers.peek());
    }
}