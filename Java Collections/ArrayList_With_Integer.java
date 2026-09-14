import java.util.ArrayList;

public class ArrayListIntegerExample {
    public static void main(String[] args) {
        ArrayList<Integer> marks = new ArrayList<>();

        marks.add(85);
        marks.add(92);
        marks.add(76);
        marks.add(88);

        System.out.println(marks);

        int total = 0;

        for (int mark : marks) {
            total += mark;
        }

        double average = (double) total / marks.size();

        System.out.println("Total: " + total);
        System.out.println("Average: " + average);
    }
}