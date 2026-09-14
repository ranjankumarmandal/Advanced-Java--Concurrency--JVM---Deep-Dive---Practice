import java.util.*;

class Student {
    int id;
    String name;

    Student(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public String toString() {
        return id + " " + name;
    }
}

public class Main {
    public static void main(String[] args) {
        List<Student> students = new ArrayList<>();
        students.add(new Student(3, "John"));
        students.add(new Student(1, "Alex"));
        students.add(new Student(2, "Sam"));

        students.sort(Comparator.comparing(s -> s.name));

        System.out.println(students);
    }
}