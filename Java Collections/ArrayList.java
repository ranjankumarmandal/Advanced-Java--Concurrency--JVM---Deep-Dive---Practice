import java.util.ArrayList;

public class ArrayListExample {
    public static void main(String[] args) {
        ArrayList<String> employees = new ArrayList<>();

        employees.add("Rahul");
        employees.add("Priya");
        employees.add("Arun");
        employees.add("Sneha");

        System.out.println(employees);

        System.out.println(employees.get(1));

        employees.set(2, "Kiran");

        employees.remove("Rahul");

        System.out.println(employees);

        System.out.println(employees.contains("Priya"));

        System.out.println(employees.size());
    }
}