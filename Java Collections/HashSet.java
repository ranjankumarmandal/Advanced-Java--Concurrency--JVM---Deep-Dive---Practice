import java.util.HashSet;

public class HashSetExample {
    public static void main(String[] args) {
        HashSet<String> emails = new HashSet<>();

        emails.add("rahul@gmail.com");
        emails.add("priya@gmail.com");
        emails.add("arun@gmail.com");
        emails.add("rahul@gmail.com");

        System.out.println(emails);

        System.out.println(emails.contains("priya@gmail.com"));

        emails.remove("arun@gmail.com");

        System.out.println(emails);
        System.out.println("Total Unique Emails: " + emails.size());
    }
}