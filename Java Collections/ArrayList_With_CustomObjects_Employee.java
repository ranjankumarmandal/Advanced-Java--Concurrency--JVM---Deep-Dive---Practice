import java.util.ArrayList;

public class ArrayListEmployeeExample {
    public static void main(String[] args) {
        ArrayList<Employee> employees = new ArrayList<>();

        employees.add(new Employee(101, "Rahul", 50000));
        employees.add(new Employee(102, "Priya", 65000));
        employees.add(new Employee(103, "Arun", 45000));

        for (Employee employee : employees) {
            System.out.println(employee);
        }
    }
}

public class Employee {
    private int id;
    private String name;
    private double salary;

    public Employee(int id, String name, double salary) {
        this.id = id;
        this.name = name;
        this.salary = salary;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getSalary() {
        return salary;
    }

    @Override
    public String toString() {
        return id + " " + name + " " + salary;
    }
}