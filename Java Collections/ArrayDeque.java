import java.util.ArrayDeque;

public class ArrayDequeExample {
    public static void main(String[] args) {
        ArrayDeque<String> tasks = new ArrayDeque<>();

        tasks.addLast("Email");
        tasks.addLast("Meeting");
        tasks.addLast("Report");

        System.out.println(tasks);

        System.out.println("Next Task: " + tasks.removeFirst());

        tasks.addFirst("Urgent Task");

        System.out.println(tasks);
    }
}