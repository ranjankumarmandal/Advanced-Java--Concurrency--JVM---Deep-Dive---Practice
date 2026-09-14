import java.util.*;

public class Main {
    public static void main(String[] args) {
        List<List<Integer>> numbers = new ArrayList<>();

        numbers.add(Arrays.asList(1, 2, 3));
        numbers.add(Arrays.asList(4, 5, 6));
        numbers.add(Arrays.asList(7, 8, 9));

        for (List<Integer> row : numbers) {
            System.out.println(row);
        }
    }
}