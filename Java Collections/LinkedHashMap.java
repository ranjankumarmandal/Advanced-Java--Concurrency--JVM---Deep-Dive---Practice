import java.util.LinkedHashMap;

public class LinkedHashMapExample {
    public static void main(String[] args) {
        LinkedHashMap<Integer, String> employees = new LinkedHashMap<>();

        employees.put(101, "Rahul");
        employees.put(102, "Priya");
        employees.put(103, "Arun");
        employees.put(104, "Sneha");

        for (Integer id : employees.keySet()) {
            System.out.println(id + " = " + employees.get(id));
        }
    }
}