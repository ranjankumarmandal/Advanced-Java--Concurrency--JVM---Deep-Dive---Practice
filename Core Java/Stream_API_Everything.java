import java.util.*;
import java.util.function.*;
import java.util.stream.*;

public class StreamApiComplete {

    static class Employee {
        private final int id;
        private final String name;
        private final String department;
        private final double salary;
        private final int age;
        private final List<String> skills;

        Employee(int id, String name, String department, double salary, int age, List<String> skills) {
            this.id = id;
            this.name = name;
            this.department = department;
            this.salary = salary;
            this.age = age;
            this.skills = skills;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getDepartment() {
            return department;
        }

        public double getSalary() {
            return salary;
        }

        public int getAge() {
            return age;
        }

        public List<String> getSkills() {
            return skills;
        }

        @Override
        public String toString() {
            return id + " " + name + " " + department + " " + salary;
        }
    }

    public static void main(String[] args) {

        List<Employee> employees = Arrays.asList(
                new Employee(1, "Alice", "IT", 90000, 30, Arrays.asList("Java", "Spring", "SQL")),
                new Employee(2, "Bob", "HR", 60000, 35, Arrays.asList("Excel", "Communication")),
                new Employee(3, "Charlie", "IT", 120000, 40, Arrays.asList("Java", "AWS", "Docker")),
                new Employee(4, "David", "Finance", 80000, 28, Arrays.asList("Excel", "SQL")),
                new Employee(5, "Eva", "IT", 110000, 32, Arrays.asList("Java", "Spring", "AWS")),
                new Employee(6, "Frank", "Finance", 75000, 45, Arrays.asList("SQL", "Excel")),
                new Employee(7, "Grace", "HR", 70000, 29, Arrays.asList("Communication", "Recruitment"))
        );

        List<String> names = Arrays.asList(
                "Alice", "Bob", "Charlie", "David", "Eva", "Frank", "Grace"
        );

        List<Integer> numbers = Arrays.asList(
                10, 20, 30, 40, 50, 60, 70, 80, 90
        );

        List<Integer> duplicateNumbers = Arrays.asList(
                10, 20, 20, 30, 30, 30, 40, 50
        );

        basicStreamCreation();
        filter(employees);
        map(employees);
        flatMap(employees);
        distinct(duplicateNumbers);
        sorted(numbers);
        limitAndSkip(numbers);
        peek(employees);
        mapToPrimitive(employees);
        reduce(numbers);
        matchOperations(employees);
        findOperations(employees);
        count(numbers);
        minMax(numbers);
        collectToCollections(employees);
        joining(names);
        grouping(employees);
        groupingWithDownstreamCollectors(employees);
        partitioning(employees);
        countingAndStatistics(employees);
        mappingCollector(employees);
        collectingAndThen(employees);
        toMap(employees);
        summarizing(employees);
        averaging(employees);
        primitiveStreams();
        optionalWithStreams(employees);
        streamFromArrays();
        streamFromMap();
        streamGenerate();
        streamIterate();
        concatStreams();
        takeDropOperations(numbers);
        sortingObjects(employees);
        multiLevelSorting(employees);
        duplicateHandling(duplicateNumbers);
        firstCharacter(names);
        frequency(numbers);
        topNSalaries(employees);
        nthHighestSalary(employees);
        secondHighestSalary(employees);
        departmentWiseHighestSalary(employees);
        departmentWiseAverageSalary(employees);
        departmentWiseEmployeeCount(employees);
        employeesAboveDepartmentAverage(employees);
        commonSkills(employees);
        employeesWithSkill(employees);
        longestName(names);
        shortestName(names);
        stringProcessing(names);
        numericProcessing(numbers);
        customCollector(numbers);
        parallelStream(employees);
        streamReuse();
        lazyEvaluation(numbers);
        shortCircuiting(numbers);
        nullSafeStream();
    }

    static void basicStreamCreation() {

        Stream<Integer> s1 = Stream.of(1, 2, 3, 4, 5);

        Stream<String> s2 = Stream.of("A", "B", "C");

        Stream<Integer> s3 = Arrays.asList(1, 2, 3).stream();

        Stream<Integer> s4 = Arrays.stream(new Integer[]{1, 2, 3});

        Stream<String> s5 = Stream.empty();

        Stream<Integer> s6 = Stream.ofNullable(10);

        long count = s1.count();

        System.out.println(count);
    }

    static void filter(List<Employee> employees) {

        List<Employee> result = employees.stream()
                .filter(e -> e.getSalary() > 80000)
                .collect(Collectors.toList());

        System.out.println(result);
    }

    static void map(List<Employee> employees) {

        List<String> result = employees.stream()
                .map(Employee::getName)
                .collect(Collectors.toList());

        System.out.println(result);
    }

    static void flatMap(List<Employee> employees) {

        List<String> skills = employees.stream()
                .flatMap(e -> e.getSkills().stream())
                .distinct()
                .collect(Collectors.toList());

        System.out.println(skills);
    }

    static void distinct(List<Integer> numbers) {

        List<Integer> result = numbers.stream()
                .distinct()
                .collect(Collectors.toList());

        System.out.println(result);
    }

    static void sorted(List<Integer> numbers) {

        List<Integer> ascending = numbers.stream()
                .sorted()
                .collect(Collectors.toList());

        List<Integer> descending = numbers.stream()
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());

        System.out.println(ascending);
        System.out.println(descending);
    }

    static void limitAndSkip(List<Integer> numbers) {

        List<Integer> firstFive = numbers.stream()
                .limit(5)
                .collect(Collectors.toList());

        List<Integer> afterThree = numbers.stream()
                .skip(3)
                .collect(Collectors.toList());

        System.out.println(firstFive);
        System.out.println(afterThree);
    }

    static void peek(List<Employee> employees) {

        List<String> result = employees.stream()
                .filter(e -> e.getSalary() > 80000)
                .peek(System.out::println)
                .map(Employee::getName)
                .collect(Collectors.toList());

        System.out.println(result);
    }

    static void mapToPrimitive(List<Employee> employees) {

        int totalAge = employees.stream()
                .mapToInt(Employee::getAge)
                .sum();

        double averageSalary = employees.stream()
                .mapToDouble(Employee::getSalary)
                .average()
                .orElse(0);

        long totalSalary = employees.stream()
                .mapToLong(e -> (long) e.getSalary())
                .sum();

        System.out.println(totalAge);
        System.out.println(averageSalary);
        System.out.println(totalSalary);
    }

    static void reduce(List<Integer> numbers) {

        int sum = numbers.stream()
                .reduce(0, Integer::sum);

        int product = numbers.stream()
                .reduce(1, (a, b) -> a * b);

        Optional<Integer> maximum = numbers.stream()
                .reduce(Integer::max);

        Optional<Integer> minimum = numbers.stream()
                .reduce(Integer::min);

        System.out.println(sum);
        System.out.println(product);
        System.out.println(maximum.orElse(0));
        System.out.println(minimum.orElse(0));
    }

    static void matchOperations(List<Employee> employees) {

        boolean allAbove50000 = employees.stream()
                .allMatch(e -> e.getSalary() > 50000);

        boolean anyAbove100000 = employees.stream()
                .anyMatch(e -> e.getSalary() > 100000);

        boolean noneBelow30000 = employees.stream()
                .noneMatch(e -> e.getSalary() < 30000);

        System.out.println(allAbove50000);
        System.out.println(anyAbove100000);
        System.out.println(noneBelow30000);
    }

    static void findOperations(List<Employee> employees) {

        Optional<Employee> first = employees.stream()
                .findFirst();

        Optional<Employee> any = employees.stream()
                .findAny();

        first.ifPresent(System.out::println);
        any.ifPresent(System.out::println);
    }

    static void count(List<Integer> numbers) {

        long count = numbers.stream()
                .filter(n -> n > 40)
                .count();

        System.out.println(count);
    }

    static void minMax(List<Integer> numbers) {

        Optional<Integer> min = numbers.stream()
                .min(Integer::compareTo);

        Optional<Integer> max = numbers.stream()
                .max(Integer::compareTo);

        System.out.println(min.orElse(0));
        System.out.println(max.orElse(0));
    }

    static void collectToCollections(List<Employee> employees) {

        List<String> list = employees.stream()
                .map(Employee::getName)
                .collect(Collectors.toList());

        Set<String> set = employees.stream()
                .map(Employee::getDepartment)
                .collect(Collectors.toSet());

        LinkedList<String> linkedList = employees.stream()
                .map(Employee::getName)
                .collect(Collectors.toCollection(LinkedList::new));

        System.out.println(list);
        System.out.println(set);
        System.out.println(linkedList);
    }

    static void joining(List<String> names) {

        String result1 = names.stream()
                .collect(Collectors.joining());

        String result2 = names.stream()
                .collect(Collectors.joining(", "));

        String result3 = names.stream()
                .collect(Collectors.joining(", ", "[", "]"));

        System.out.println(result1);
        System.out.println(result2);
        System.out.println(result3);
    }

    static void grouping(List<Employee> employees) {

        Map<String, List<Employee>> byDepartment = employees.stream()
                .collect(Collectors.groupingBy(Employee::getDepartment));

        System.out.println(byDepartment);
    }

    static void groupingWithDownstreamCollectors(List<Employee> employees) {

        Map<String, List<String>> namesByDepartment = employees.stream()
                .collect(Collectors.groupingBy(
                        Employee::getDepartment,
                        Collectors.mapping(
                                Employee::getName,
                                Collectors.toList()
                        )
                ));

        Map<String, Long> countByDepartment = employees.stream()
                .collect(Collectors.groupingBy(
                        Employee::getDepartment,
                        Collectors.counting()
                ));

        Map<String, Double> averageSalary = employees.stream()
                .collect(Collectors.groupingBy(
                        Employee::getDepartment,
                        Collectors.averagingDouble(Employee::getSalary)
                ));

        Map<String, DoubleSummaryStatistics> statistics = employees.stream()
                .collect(Collectors.groupingBy(
                        Employee::getDepartment,
                        Collectors.summarizingDouble(Employee::getSalary)
                ));

        System.out.println(namesByDepartment);
        System.out.println(countByDepartment);
        System.out.println(averageSalary);
        System.out.println(statistics);
    }

    static void partitioning(List<Employee> employees) {

        Map<Boolean, List<Employee>> partitioned = employees.stream()
                .collect(Collectors.partitioningBy(
                        e -> e.getSalary() > 80000
                ));

        System.out.println(partitioned);
    }

    static void countingAndStatistics(List<Employee> employees) {

        long count = employees.stream()
                .collect(Collectors.counting());

        DoubleSummaryStatistics stats = employees.stream()
                .collect(Collectors.summarizingDouble(Employee::getSalary));

        System.out.println(count);
        System.out.println(stats.getCount());
        System.out.println(stats.getSum());
        System.out.println(stats.getMin());
        System.out.println(stats.getMax());
        System.out.println(stats.getAverage());
    }

    static void mappingCollector(List<Employee> employees) {

        Set<String> departments = employees.stream()
                .collect(Collectors.mapping(
                        Employee::getDepartment,
                        Collectors.toSet()
                ));

        System.out.println(departments);
    }

    static void collectingAndThen(List<Employee> employees) {

        List<String> names = employees.stream()
                .collect(Collectors.collectingAndThen(
                        Collectors.mapping(
                                Employee::getName,
                                Collectors.toList()
                        ),
                        Collections::unmodifiableList
                ));

        System.out.println(names);
    }

    static void toMap(List<Employee> employees) {

        Map<Integer, String> employeeMap = employees.stream()
                .collect(Collectors.toMap(
                        Employee::getId,
                        Employee::getName
                ));

        Map<String, Double> salaryMap = employees.stream()
                .collect(Collectors.toMap(
                        Employee::getName,
                        Employee::getSalary
                ));

        System.out.println(employeeMap);
        System.out.println(salaryMap);
    }

    static void summarizing(List<Employee> employees) {

        IntSummaryStatistics ages = employees.stream()
                .collect(Collectors.summarizingInt(Employee::getAge));

        LongSummaryStatistics ids = employees.stream()
                .collect(Collectors.summarizingLong(Employee::getId));

        DoubleSummaryStatistics salaries = employees.stream()
                .collect(Collectors.summarizingDouble(Employee::getSalary));

        System.out.println(ages);
        System.out.println(ids);
        System.out.println(salaries);
    }

    static void averaging(List<Employee> employees) {

        double averageAge = employees.stream()
                .collect(Collectors.averagingInt(Employee::getAge));

        double averageSalary = employees.stream()
                .collect(Collectors.averagingDouble(Employee::getSalary));

        System.out.println(averageAge);
        System.out.println(averageSalary);
    }

    static void primitiveStreams() {

        IntStream.range(1, 10)
                .forEach(System.out::println);

        IntStream.rangeClosed(1, 10)
                .forEach(System.out::println);

        int sum = IntStream.rangeClosed(1, 100)
                .sum();

        OptionalDouble average = IntStream.rangeClosed(1, 100)
                .average();

        int max = IntStream.of(10, 20, 30)
                .max()
                .orElse(0);

        int min = IntStream.of(10, 20, 30)
                .min()
                .orElse(0);

        System.out.println(sum);
        System.out.println(average.orElse(0));
        System.out.println(max);
        System.out.println(min);
    }

    static void optionalWithStreams(List<Employee> employees) {

        Optional<Employee> employee = employees.stream()
                .filter(e -> e.getSalary() > 200000)
                .findFirst();

        employee.ifPresent(System.out::println);

        String name = employee
                .map(Employee::getName)
                .orElse("Not Found");

        System.out.println(name);
    }

    static void streamFromArrays() {

        int[] numbers = {1, 2, 3, 4, 5};

        int sum = Arrays.stream(numbers)
                .sum();

        String[] names = {"A", "B", "C"};

        List<String> result = Arrays.stream(names)
                .collect(Collectors.toList());

        System.out.println(sum);
        System.out.println(result);
    }

    static void streamFromMap() {

        Map<Integer, String> map = new HashMap<>();

        map.put(1, "A");
        map.put(2, "B");
        map.put(3, "C");

        map.entrySet()
                .stream()
                .forEach(System.out::println);

        map.keySet()
                .stream()
                .forEach(System.out::println);

        map.values()
                .stream()
                .forEach(System.out::println);

        Map<Integer, String> filtered = map.entrySet()
                .stream()
                .filter(e -> e.getKey() > 1)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue
                ));

        System.out.println(filtered);
    }

    static void streamGenerate() {

        List<Double> randomNumbers = Stream.generate(Math::random)
                .limit(5)
                .collect(Collectors.toList());

        System.out.println(randomNumbers);
    }

    static void streamIterate() {

        List<Integer> numbers = Stream.iterate(
                        1,
                        n -> n <= 100,
                        n -> n + 2
                )
                .collect(Collectors.toList());

        System.out.println(numbers);
    }

    static void concatStreams() {

        Stream<Integer> first = Stream.of(1, 2, 3);
        Stream<Integer> second = Stream.of(4, 5, 6);

        List<Integer> result = Stream.concat(first, second)
                .collect(Collectors.toList());

        System.out.println(result);
    }

    static void takeDropOperations(List<Integer> numbers) {

        List<Integer> taken = numbers.stream()
                .takeWhile(n -> n < 60)
                .collect(Collectors.toList());

        List<Integer> dropped = numbers.stream()
                .dropWhile(n -> n < 60)
                .collect(Collectors.toList());

        System.out.println(taken);
        System.out.println(dropped);
    }

    static void sortingObjects(List<Employee> employees) {

        List<Employee> result = employees.stream()
                .sorted(Comparator.comparing(Employee::getSalary))
                .collect(Collectors.toList());

        List<Employee> result2 = employees.stream()
                .sorted(
                        Comparator.comparing(
                                Employee::getSalary,
                                Comparator.reverseOrder()
                        )
                )
                .collect(Collectors.toList());

        System.out.println(result);
        System.out.println(result2);
    }

    static void multiLevelSorting(List<Employee> employees) {

        List<Employee> result = employees.stream()
                .sorted(
                        Comparator.comparing(Employee::getDepartment)
                                .thenComparing(Employee::getSalary)
                                .thenComparing(Employee::getName)
                )
                .collect(Collectors.toList());

        System.out.println(result);
    }

    static void duplicateHandling(List<Integer> numbers) {

        Map<Integer, Long> frequency = numbers.stream()
                .collect(Collectors.groupingBy(
                        Function.identity(),
                        Collectors.counting()
                ));

        List<Integer> duplicates = frequency.entrySet()
                .stream()
                .filter(e -> e.getValue() > 1)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        List<Integer> unique = frequency.entrySet()
                .stream()
                .filter(e -> e.getValue() == 1)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        System.out.println(frequency);
        System.out.println(duplicates);
        System.out.println(unique);
    }

    static void firstCharacter(List<String> names) {

        Map<Character, List<String>> result = names.stream()
                .collect(Collectors.groupingBy(
                        name -> name.charAt(0)
                ));

        System.out.println(result);
    }

    static void frequency(List<Integer> numbers) {

        Map<Integer, Long> result = numbers.stream()
                .collect(Collectors.groupingBy(
                        Function.identity(),
                        LinkedHashMap::new,
                        Collectors.counting()
                ));

        System.out.println(result);
    }

    static void topNSalaries(List<Employee> employees) {

        List<Employee> topThree = employees.stream()
                .sorted(
                        Comparator.comparing(
                                Employee::getSalary
                        ).reversed()
                )
                .limit(3)
                .collect(Collectors.toList());

        System.out.println(topThree);
    }

    static void nthHighestSalary(List<Employee> employees) {

        Optional<Double> thirdHighest = employees.stream()
                .map(Employee::getSalary)
                .distinct()
                .sorted(Comparator.reverseOrder())
                .skip(2)
                .findFirst();

        System.out.println(thirdHighest.orElse(0.0));
    }

    static void secondHighestSalary(List<Employee> employees) {

        Optional<Employee> result = employees.stream()
                .sorted(
                        Comparator.comparing(
                                Employee::getSalary
                        ).reversed()
                )
                .skip(1)
                .findFirst();

        result.ifPresent(System.out::println);
    }

    static void departmentWiseHighestSalary(List<Employee> employees) {

        Map<String, Optional<Employee>> result = employees.stream()
                .collect(Collectors.groupingBy(
                        Employee::getDepartment,
                        Collectors.maxBy(
                                Comparator.comparing(Employee::getSalary)
                        )
                ));

        System.out.println(result);
    }

    static void departmentWiseAverageSalary(List<Employee> employees) {

        Map<String, Double> result = employees.stream()
                .collect(Collectors.groupingBy(
                        Employee::getDepartment,
                        Collectors.averagingDouble(Employee::getSalary)
                ));

        System.out.println(result);
    }

    static void departmentWiseEmployeeCount(List<Employee> employees) {

        Map<String, Long> result = employees.stream()
                .collect(Collectors.groupingBy(
                        Employee::getDepartment,
                        Collectors.counting()
                ));

        System.out.println(result);
    }

    static void employeesAboveDepartmentAverage(List<Employee> employees) {

        Map<String, Double> averageSalary = employees.stream()
                .collect(Collectors.groupingBy(
                        Employee::getDepartment,
                        Collectors.averagingDouble(Employee::getSalary)
                ));

        List<Employee> result = employees.stream()
                .filter(e ->
                        e.getSalary() >
                                averageSalary.get(e.getDepartment())
                )
                .collect(Collectors.toList());

        System.out.println(result);
    }

    static void commonSkills(List<Employee> employees) {

        Set<String> skills = employees.stream()
                .flatMap(e -> e.getSkills().stream())
                .collect(Collectors.toSet());

        System.out.println(skills);
    }

    static void employeesWithSkill(List<Employee> employees) {

        List<Employee> result = employees.stream()
                .filter(e ->
                        e.getSkills()
                                .stream()
                                .anyMatch(skill ->
                                        skill.equalsIgnoreCase("Java")
                                )
                )
                .collect(Collectors.toList());

        System.out.println(result);
    }

    static void longestName(List<String> names) {

        Optional<String> result = names.stream()
                .max(Comparator.comparingInt(String::length));

        System.out.println(result.orElse(""));
    }

    static void shortestName(List<String> names) {

        Optional<String> result = names.stream()
                .min(Comparator.comparingInt(String::length));

        System.out.println(result.orElse(""));
    }

    static void stringProcessing(List<String> names) {

        String result = names.stream()
                .filter(name -> name.length() > 3)
                .map(String::toUpperCase)
                .sorted()
                .collect(Collectors.joining(", "));

        System.out.println(result);
    }

    static void numericProcessing(List<Integer> numbers) {

        int sumOfEvenSquares = numbers.stream()
                .filter(n -> n % 2 == 0)
                .map(n -> n * n)
                .reduce(0, Integer::sum);

        System.out.println(sumOfEvenSquares);
    }

    static void customCollector(List<Integer> numbers) {

        String result = numbers.stream()
                .collect(
                        Collector.of(
                                StringBuilder::new,
                                (builder, value) ->
                                        builder.append(value).append(","),
                                (left, right) ->
                                        left.append(right),
                                StringBuilder::toString
                        )
                );

        System.out.println(result);
    }

    static void parallelStream(List<Employee> employees) {

        List<String> names = employees.parallelStream()
                .filter(e -> e.getSalary() > 70000)
                .map(Employee::getName)
                .collect(Collectors.toList());

        System.out.println(names);

        double total = employees.parallelStream()
                .mapToDouble(Employee::getSalary)
                .sum();

        System.out.println(total);
    }

    static void streamReuse() {

        Stream<Integer> stream = Stream.of(1, 2, 3, 4, 5);

        long count = stream.count();

        System.out.println(count);

        Stream<Integer> newStream = Stream.of(1, 2, 3, 4, 5);

        List<Integer> result = newStream
                .filter(n -> n > 2)
                .collect(Collectors.toList());

        System.out.println(result);
    }

    static void lazyEvaluation(List<Integer> numbers) {

        Stream<Integer> stream = numbers.stream()
                .filter(n -> {
                    System.out.println("filter: " + n);
                    return n > 30;
                })
                .map(n -> {
                    System.out.println("map: " + n);
                    return n * 2;
                });

        System.out.println("Before terminal operation");

        List<Integer> result = stream.collect(Collectors.toList());

        System.out.println(result);
    }

    static void shortCircuiting(List<Integer> numbers) {

        Optional<Integer> result = numbers.stream()
                .filter(n -> n > 30)
                .findFirst();

        System.out.println(result.orElse(0));

        boolean exists = numbers.stream()
                .anyMatch(n -> n > 80);

        System.out.println(exists);

        List<Integer> result2 = numbers.stream()
                .filter(n -> n > 20)
                .limit(3)
                .collect(Collectors.toList());

        System.out.println(result2);
    }

    static void nullSafeStream() {

        List<String> list = null;

        List<String> result = Optional.ofNullable(list)
                .orElse(Collections.emptyList())
                .stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        System.out.println(result);

        Stream<String> stream = Stream.ofNullable(null);

        System.out.println(stream.count());
    }
}