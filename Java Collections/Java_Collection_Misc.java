import java.util.*;
import java.util.stream.Collectors;

class Employee {
    int id;
    String name;
    double salary;
    String department;

    Employee(int id, String name, double salary, String department) {
        this.id = id;
        this.name = name;
        this.salary = salary;
        this.department = department;
    }

    public String toString() {
        return id + " " + name + " " + salary + " " + department;
    }
}

public class Main {
    public static void main(String[] args) {

        List<Employee> employees = new ArrayList<>();

        employees.add(new Employee(101, "John", 50000, "IT"));
        employees.add(new Employee(102, "Alice", 65000, "HR"));
        employees.add(new Employee(103, "Bob", 45000, "IT"));
        employees.add(new Employee(104, "David", 75000, "Finance"));

        System.out.println("All Employees:");
        for (Employee e : employees) {
            System.out.println(e);
        }

        employees.removeIf(e -> e.id == 103);

        for (Employee e : employees) {
            if (e.id == 102) {
                e.salary = 70000;
            }
        }

        Employee search = employees.stream()
                .filter(e -> e.name.equals("John"))
                .findFirst()
                .orElse(null);

        System.out.println("\nSearch Result:");
        System.out.println(search);

        employees.sort(Comparator.comparingDouble(e -> e.salary));

        System.out.println("\nSorted by Salary:");
        employees.forEach(System.out::println);

        List<Employee> highSalary = employees.stream()
                .filter(e -> e.salary > 60000)
                .collect(Collectors.toList());

        System.out.println("\nSalary > 60000:");
        highSalary.forEach(System.out::println);

        List<String> names = employees.stream()
                .map(e -> e.name)
                .collect(Collectors.toList());

        System.out.println("\nEmployee Names:");
        System.out.println(names);

        Map<String, List<Employee>> grouped = employees.stream()
                .collect(Collectors.groupingBy(e -> e.department));

        System.out.println("\nEmployees by Department:");
        System.out.println(grouped);
    }
}