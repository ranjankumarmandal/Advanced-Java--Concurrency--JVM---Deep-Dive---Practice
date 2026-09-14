import java.util.HashMap;

public class HashMapExample {
    public static void main(String[] args) {
        HashMap<Integer, String> employees = new HashMap<>();

        employees.put(101, "Rahul");
        employees.put(102, "Priya");
        employees.put(103, "Arun");
        employees.put(104, "Sneha");

        System.out.println(employees);

        System.out.println(employees.get(102));

        employees.put(102, "Kiran");

        System.out.println(employees);

        employees.remove(104);

        System.out.println(employees);

        System.out.println(employees.containsKey(101));
        System.out.println(employees.containsValue("Arun"));
    }
}