import java.util.Stack;

public class StackExample {
    public static void main(String[] args) {
        Stack<String> browserHistory = new Stack<>();

        browserHistory.push("google.com");
        browserHistory.push("youtube.com");
        browserHistory.push("github.com");
        browserHistory.push("stackoverflow.com");

        System.out.println(browserHistory);

        System.out.println("Current Page: " + browserHistory.peek());

        System.out.println("Going Back From: " + browserHistory.pop());

        System.out.println("Current Page: " + browserHistory.peek());

        System.out.println(browserHistory);
    }
}