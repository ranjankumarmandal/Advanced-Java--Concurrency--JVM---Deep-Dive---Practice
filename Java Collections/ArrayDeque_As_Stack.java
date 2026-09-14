import java.util.ArrayDeque;

public class ArrayDequeStackExample {
    public static void main(String[] args) {
        ArrayDeque<String> pages = new ArrayDeque<>();

        pages.push("Home");
        pages.push("Products");
        pages.push("Checkout");

        System.out.println(pages);

        System.out.println("Current Page: " + pages.peek());

        pages.pop();

        System.out.println("Current Page: " + pages.peek());
    }
}