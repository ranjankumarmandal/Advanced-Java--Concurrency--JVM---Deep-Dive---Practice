import java.util.TreeMap;

public class TreeMapExample {
    public static void main(String[] args) {
        TreeMap<Integer, String> employees = new TreeMap<>();

        employees.put(104, "Sneha");
        employees.put(101, "Rahul");
        employees.put(103, "Arun");
        employees.put(102, "Priya");

        System.out.println(employees);

        System.out.println("First Employee: " + employees.firstEntry());
        System.out.println("Last Employee: " + employees.lastEntry());

        System.out.println("Employees Before 103: " + employees.headMap(103));
        System.out.println("Employees From 102: " + employees.tailMap(102));
    }
}