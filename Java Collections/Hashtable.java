// HashTable is legacy implementation of HashMap, it doesn't allow null key or null values

import java.util.Hashtable;

public class HashtableExample {
    public static void main(String[] args) {
        Hashtable<Integer, String> employees = new Hashtable<>();

        employees.put(101, "Rahul");
        employees.put(102, "Priya");
        employees.put(103, "Arun");

        System.out.println(employees);

        System.out.println(employees.get(102));

        employees.remove(103);

        System.out.println(employees);
    }
}