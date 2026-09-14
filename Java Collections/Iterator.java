import java.util.*;

public class Main {
    public static void main(String[] args) {
        List<String> names = Arrays.asList("A", "B", "C");

        Iterator<String> it = names.iterator();

        while (it.hasNext()) {
            System.out.println(it.next());
        }
    }
}