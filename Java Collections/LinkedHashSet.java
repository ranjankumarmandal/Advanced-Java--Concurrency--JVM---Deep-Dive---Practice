import java.util.LinkedHashSet;

public class LinkedHashSetExample {
    public static void main(String[] args) {
        LinkedHashSet<String> categories = new LinkedHashSet<>();

        categories.add("Electronics");
        categories.add("Clothing");
        categories.add("Books");
        categories.add("Electronics");
        categories.add("Shoes");

        for (String category : categories) {
            System.out.println(category);
        }
    }
}