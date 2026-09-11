import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.*;
import java.util.stream.*;

public class FunctionalInterfacesFAANGDemo {

    @FunctionalInterface
    interface IdGenerator {
        String generate();
    }

    @FunctionalInterface
    interface UserValidator {
        boolean validate(User user);
    }

    @FunctionalInterface
    interface Transformer<T, R> {
        R transform(T input);
    }

    @FunctionalInterface
    interface TriFunction<A, B, C, R> {
        R apply(A a, B b, C c);
    }

    @FunctionalInterface
    interface CheckedFunction<T, R> {
        R apply(T input) throws Exception;
    }

    @FunctionalInterface
    interface RetryableOperation<T, R> {
        R execute(T input) throws Exception;
    }

    @FunctionalInterface
    interface AuditLogger {
        void log(String event, Object data);
    }

    @FunctionalInterface
    interface CacheLoader<K, V> {
        V load(K key);
    }

    static class User {
        private final String id;
        private final String name;
        private final String email;
        private final int age;
        private final String department;
        private final double salary;
        private final boolean active;
        private final List<String> skills;

        public User(
                String id,
                String name,
                String email,
                int age,
                String department,
                double salary,
                boolean active,
                List<String> skills
        ) {
            this.id = id;
            this.name = name;
            this.email = email;
            this.age = age;
            this.department = department;
            this.salary = salary;
            this.active = active;
            this.skills = new ArrayList<>(skills);
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getEmail() {
            return email;
        }

        public int getAge() {
            return age;
        }

        public String getDepartment() {
            return department;
        }

        public double getSalary() {
            return salary;
        }

        public boolean isActive() {
            return active;
        }

        public List<String> getSkills() {
            return Collections.unmodifiableList(skills);
        }

        @Override
        public String toString() {
            return "User{" +
                    "id='" + id + '\'' +
                    ", name='" + name + '\'' +
                    ", email='" + email + '\'' +
                    ", age=" + age +
                    ", department='" + department + '\'' +
                    ", salary=" + salary +
                    ", active=" + active +
                    ", skills=" + skills +
                    '}';
        }
    }

    static class UserDTO {
        private final String id;
        private final String displayName;
        private final String department;
        private final double salary;

        public UserDTO(
                String id,
                String displayName,
                String department,
                double salary
        ) {
            this.id = id;
            this.displayName = displayName;
            this.department = department;
            this.salary = salary;
        }

        public String getId() {
            return id;
        }

        public String getDisplayName() {
            return displayName;
        }

        public String getDepartment() {
            return department;
        }

        public double getSalary() {
            return salary;
        }

        @Override
        public String toString() {
            return "UserDTO{" +
                    "id='" + id + '\'' +
                    ", displayName='" + displayName + '\'' +
                    ", department='" + department + '\'' +
                    ", salary=" + salary +
                    '}';
        }
    }

    static class UserRepository {
        private final Map<String, User> database = new ConcurrentHashMap<>();

        public void save(User user) {
            database.put(user.getId(), user);
        }

        public Optional<User> findById(String id) {
            return Optional.ofNullable(database.get(id));
        }

        public List<User> findAll() {
            return new ArrayList<>(database.values());
        }

        public List<User> findByPredicate(Predicate<User> predicate) {
            return database.values()
                    .stream()
                    .filter(predicate)
                    .collect(Collectors.toList());
        }

        public long count(Predicate<User> predicate) {
            return database.values()
                    .stream()
                    .filter(predicate)
                    .count();
        }
    }

    static class UserService {
        private final UserRepository repository;
        private final IdGenerator idGenerator;
        private final AuditLogger auditLogger;

        public UserService(
                UserRepository repository,
                IdGenerator idGenerator,
                AuditLogger auditLogger
        ) {
            this.repository = repository;
            this.idGenerator = idGenerator;
            this.auditLogger = auditLogger;
        }

        public User createUser(
                String name,
                String email,
                int age,
                String department,
                double salary,
                List<String> skills
        ) {
            String id = idGenerator.generate();

            User user = new User(
                    id,
                    name,
                    email,
                    age,
                    department,
                    salary,
                    true,
                    skills
            );

            repository.save(user);

            auditLogger.log("USER_CREATED", user);

            return user;
        }

        public Optional<User> getUser(String id) {
            return repository.findById(id);
        }

        public List<User> search(Predicate<User> predicate) {
            return repository.findByPredicate(predicate);
        }
    }

    static class Cache<K, V> {
        private final Map<K, V> cache = new ConcurrentHashMap<>();
        private final CacheLoader<K, V> loader;

        public Cache(CacheLoader<K, V> loader) {
            this.loader = loader;
        }

        public V get(K key) {
            return cache.computeIfAbsent(key, loader::load);
        }

        public void invalidate(K key) {
            cache.remove(key);
        }

        public void clear() {
            cache.clear();
        }
    }

    static class RetryExecutor {

        public <T, R> R execute(
                T input,
                RetryableOperation<T, R> operation,
                int maxAttempts
        ) {
            Exception lastException = null;

            for (int attempt = 1; attempt <= maxAttempts; attempt++) {
                try {
                    return operation.execute(input);
                } catch (Exception e) {
                    lastException = e;

                    if (attempt == maxAttempts) {
                        throw new RuntimeException(lastException);
                    }
                }
            }

            throw new RuntimeException(lastException);
        }
    }

    static class Metrics {

        private final Map<String, Long> counters = new ConcurrentHashMap<>();

        public void increment(String name) {
            counters.merge(name, 1L, Long::sum);
        }

        public long get(String name) {
            return counters.getOrDefault(name, 0L);
        }

        public Map<String, Long> snapshot() {
            return new HashMap<>(counters);
        }
    }

    public static void main(String[] args) throws Exception {

        Supplier<String> randomIdSupplier =
                () -> UUID.randomUUID().toString();

        Supplier<LocalDateTime> clockSupplier =
                LocalDateTime::now;

        Supplier<List<String>> listSupplier =
                ArrayList::new;

        String generatedId = randomIdSupplier.get();
        LocalDateTime currentTime = clockSupplier.get();
        List<String> emptyList = listSupplier.get();

        System.out.println(generatedId);
        System.out.println(currentTime);
        System.out.println(emptyList);

        Predicate<String> nonEmpty =
                value -> value != null && !value.isBlank();

        Predicate<String> minimumLength =
                value -> value.length() >= 5;

        Predicate<String> startsWithA =
                value -> value.startsWith("A");

        Predicate<String> validUsername =
                nonEmpty
                        .and(minimumLength)
                        .and(startsWithA);

        System.out.println(validUsername.test("Andrew"));
        System.out.println(validUsername.test("Bob"));

        Predicate<Integer> positive =
                value -> value > 0;

        Predicate<Integer> even =
                value -> value % 2 == 0;

        Predicate<Integer> divisibleByThree =
                value -> value % 3 == 0;

        Predicate<Integer> complexNumberPredicate =
                positive
                        .and(even)
                        .or(divisibleByThree);

        System.out.println(complexNumberPredicate.test(12));
        System.out.println(complexNumberPredicate.test(9));

        Function<String, String> trim =
                String::trim;

        Function<String, String> uppercase =
                String::toUpperCase;

        Function<String, String> normalize =
                trim.andThen(uppercase);

        System.out.println(normalize.apply("   hello world   "));

        Function<Integer, Integer> square =
                value -> value * value;

        Function<Integer, Integer> doubleValue =
                value -> value * 2;

        Function<Integer, Integer> pipeline =
                square
                        .andThen(doubleValue);

        System.out.println(pipeline.apply(5));

        Function<String, Integer> stringLength =
                String::length;

        Function<String, Boolean> longString =
                stringLength
                        .andThen(length -> length > 10);

        System.out.println(longString.apply("functional programming"));

        BiFunction<Integer, Integer, Integer> add =
                Integer::sum;

        BiFunction<Integer, Integer, Integer> multiply =
                (a, b) -> a * b;

        BiFunction<Double, Double, Double> calculateSalary =
                (salary, bonus) -> salary + bonus;

        System.out.println(add.apply(10, 20));
        System.out.println(multiply.apply(10, 20));
        System.out.println(calculateSalary.apply(100000.0, 15000.0));

        UnaryOperator<String> sanitize =
                value -> value
                        .trim()
                        .toLowerCase()
                        .replaceAll("\\s+", " ");

        UnaryOperator<Integer> increment =
                value -> value + 1;

        System.out.println(sanitize.apply("   JOHN    DOE   "));
        System.out.println(increment.apply(100));

        BinaryOperator<Integer> max =
                Integer::max;

        BinaryOperator<Integer> sum =
                Integer::sum;

        System.out.println(max.apply(100, 200));
        System.out.println(sum.apply(100, 200));

        Consumer<String> printer =
                System.out::println;

        Consumer<User> userPrinter =
                System.out::println;

        Consumer<User> auditConsumer =
                user -> System.out.println(
                        "AUDIT: " + user.getId()
                );

        Consumer<User> combinedConsumer =
                userPrinter.andThen(auditConsumer);

        printer.accept("Consumer executed");

        TriFunction<Integer, Integer, Integer, Integer> sumThree =
                (a, b, c) -> a + b + c;

        System.out.println(sumThree.apply(10, 20, 30));

        Transformer<String, String> reverse =
                value -> new StringBuilder(value)
                        .reverse()
                        .toString();

        System.out.println(reverse.transform("FAANG"));

        UserValidator adultValidator =
                user -> user.getAge() >= 18;

        UserValidator salaryValidator =
                user -> user.getSalary() >= 50000;

        UserValidator activeValidator =
                User::isActive;

        UserValidator completeValidator =
                user ->
                        adultValidator.validate(user)
                                && salaryValidator.validate(user)
                                && activeValidator.validate(user);

        UserRepository repository =
                new UserRepository();

        AuditLogger logger =
                (event, data) ->
                        System.out.println(
                                LocalDateTime.now()
                                        + " | "
                                        + event
                                        + " | "
                                        + data
                        );

        IdGenerator idGenerator =
                () -> "USR-" + UUID.randomUUID();

        UserService userService =
                new UserService(
                        repository,
                        idGenerator,
                        logger
                );

        User user1 = userService.createUser(
                "Alice Johnson",
                "alice@example.com",
                28,
                "Engineering",
                150000,
                Arrays.asList("Java", "Spring", "AWS")
        );

        User user2 = userService.createUser(
                "Bob Smith",
                "bob@example.com",
                35,
                "Engineering",
                180000,
                Arrays.asList("Java", "Kubernetes", "AWS")
        );

        User user3 = userService.createUser(
                "Charlie Brown",
                "charlie@example.com",
                22,
                "Marketing",
                70000,
                Arrays.asList("SEO", "Analytics")
        );

        User user4 = userService.createUser(
                "Diana Wilson",
                "diana@example.com",
                41,
                "Engineering",
                210000,
                Arrays.asList("Java", "Distributed Systems", "Kafka")
        );

        combinedConsumer.accept(user1);

        List<User> engineeringUsers =
                userService.search(
                        user -> user.getDepartment()
                                .equalsIgnoreCase("Engineering")
                );

        engineeringUsers.forEach(
                user -> System.out.println(user.getName())
        );

        List<User> highSalaryUsers =
                userService.search(
                        user -> user.getSalary() > 150000
                );

        highSalaryUsers.forEach(
                user -> System.out.println(user.getName())
        );

        List<User> validUsers =
                userService.search(
                        completeValidator::validate
                );

        validUsers.forEach(
                user -> System.out.println(
                        "VALID: " + user.getName()
                )
        );

        List<String> names =
                repository.findAll()
                        .stream()
                        .map(User::getName)
                        .map(String::toUpperCase)
                        .collect(Collectors.toList());

        System.out.println(names);

        List<String> engineeringNames =
                repository.findAll()
                        .stream()
                        .filter(
                                user -> user.getDepartment()
                                        .equals("Engineering")
                        )
                        .map(User::getName)
                        .sorted()
                        .collect(Collectors.toList());

        System.out.println(engineeringNames);

        double averageSalary =
                repository.findAll()
                        .stream()
                        .mapToDouble(User::getSalary)
                        .average()
                        .orElse(0.0);

        System.out.println(averageSalary);

        Map<String, List<User>> usersByDepartment =
                repository.findAll()
                        .stream()
                        .collect(
                                Collectors.groupingBy(
                                        User::getDepartment
                                )
                        );

        usersByDepartment.forEach(
                (department, users) ->
                        System.out.println(
                                department + " -> " + users
                        )
        );

        Map<String, Double> salaryByDepartment =
                repository.findAll()
                        .stream()
                        .collect(
                                Collectors.groupingBy(
                                        User::getDepartment,
                                        Collectors.averagingDouble(
                                                User::getSalary
                                        )
                                )
                        );

        System.out.println(salaryByDepartment);

        Map<String, String> emailByName =
                repository.findAll()
                        .stream()
                        .collect(
                                Collectors.toMap(
                                        User::getName,
                                        User::getEmail
                                )
                        );

        System.out.println(emailByName);

        Function<User, UserDTO> userToDTO =
                user -> new UserDTO(
                        user.getId(),
                        user.getName().toUpperCase(),
                        user.getDepartment(),
                        user.getSalary()
                );

        List<UserDTO> dtos =
                repository.findAll()
                        .stream()
                        .map(userToDTO)
                        .collect(Collectors.toList());

        dtos.forEach(System.out::println);

        Function<User, String> nameExtractor =
                User::getName;

        Function<String, String> uppercaseFunction =
                String::toUpperCase;

        Function<User, String> uppercaseName =
                nameExtractor.andThen(
                        uppercaseFunction
                );

        repository.findAll()
                .stream()
                .map(uppercaseName)
                .forEach(System.out::println);

        Function<String, String> first =
                value -> value + "-A";

        Function<String, String> second =
                value -> value + "-B";

        Function<String, String> third =
                value -> value + "-C";

        Function<String, String> composed =
                first
                        .andThen(second)
                        .andThen(third);

        System.out.println(
                composed.apply("START")
        );

        Function<String, String> composedReverse =
                third
                        .compose(second)
                        .compose(first);

        System.out.println(
                composedReverse.apply("START")
        );

        Cache<String, User> userCache =
                new Cache<>(
                        id -> repository
                                .findById(id)
                                .orElseThrow(
                                        () -> new NoSuchElementException(
                                                "User not found"
                                        )
                                )
                );

        User cachedUser =
                userCache.get(user1.getId());

        System.out.println(cachedUser);

        CheckedFunction<String, Integer> parseInteger =
                Integer::parseInt;

        try {
            System.out.println(
                    parseInteger.apply("12345")
            );
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        RetryExecutor retryExecutor =
                new RetryExecutor();

        String retryResult =
                retryExecutor.execute(
                        "DATA",
                        value -> {
                            if (Math.random() < 0.7) {
                                throw new RuntimeException(
                                        "Temporary failure"
                                );
                            }
                            return value + "-SUCCESS";
                        },
                        5
                );

        System.out.println(retryResult);

        Metrics metrics =
                new Metrics();

        Consumer<String> trackedOperation =
                operation -> {
                    metrics.increment(operation);
                    System.out.println(
                            "Executed: " + operation
                    );
                };

        trackedOperation.accept("LOGIN");
        trackedOperation.accept("LOGIN");
        trackedOperation.accept("SEARCH");
        trackedOperation.accept("SEARCH");
        trackedOperation.accept("SEARCH");

        System.out.println(metrics.snapshot());

        Function<User, Predicate<User>> sameDepartment =
                referenceUser ->
                        candidate ->
                                candidate
                                        .getDepartment()
                                        .equals(
                                                referenceUser
                                                        .getDepartment()
                                        );

        Predicate<User> sameDepartmentAsAlice =
                sameDepartment.apply(user1);

        repository.findAll()
                .stream()
                .filter(sameDepartmentAsAlice)
                .forEach(System.out::println);

        Function<Double, Double> tax =
                salary -> salary * 0.30;

        Function<Double, Double> bonus =
                salary -> salary * 0.10;

        Function<Double, Double> netSalary =
                salary ->
                        salary
                                - tax.apply(salary)
                                + bonus.apply(salary);

        System.out.println(
                netSalary.apply(150000.0)
        );

        Comparator<User> salaryComparator =
                Comparator.comparingDouble(
                        User::getSalary
                );

        repository.findAll()
                .stream()
                .sorted(salaryComparator.reversed())
                .forEach(System.out::println);

        Comparator<User> complexComparator =
                Comparator.comparing(
                                User::getDepartment
                        )
                        .thenComparing(
                                User::getSalary,
                                Comparator.reverseOrder()
                        )
                        .thenComparing(
                                User::getName
                        );

        repository.findAll()
                .stream()
                .sorted(complexComparator)
                .forEach(System.out::println);

        Optional<User> optionalUser =
                userService.getUser(
                        user1.getId()
                );

        optionalUser
                .map(User::getName)
                .map(String::toUpperCase)
                .ifPresent(
                        System.out::println
                );

        String result =
                optionalUser
                        .map(User::getName)
                        .filter(
                                name -> name.length() > 5
                        )
                        .orElse("UNKNOWN");

        System.out.println(result);

        Map<String, Function<User, Object>> extractors =
                new HashMap<>();

        extractors.put("name", User::getName);
        extractors.put("email", User::getEmail);
        extractors.put("department", User::getDepartment);
        extractors.put("salary", User::getSalary);
        extractors.put("age", User::getAge);

        User selectedUser = user1;

        extractors.forEach(
                (key, extractor) ->
                        System.out.println(
                                key + " = "
                                        + extractor.apply(selectedUser)
                        )
        );

        List<Predicate<User>> validationRules =
                Arrays.asList(
                        user -> user.getAge() >= 18,
                        user -> user.getSalary() >= 50000,
                        user -> user.getEmail()
                                .contains("@"),
                        user -> !user.getSkills().isEmpty()
                );

        Predicate<User> allRules =
                validationRules
                        .stream()
                        .reduce(
                                user -> true,
                                Predicate::and
                        );

        repository.findAll()
                .stream()
                .filter(allRules)
                .forEach(System.out::println);

        List<Function<User, String>> transformations =
                Arrays.asList(
                        User::getName,
                        String::toUpperCase,
                        String::trim
                );

        for (Function<User, String> transformation : transformations) {
            System.out.println(
                    transformation.apply(user1)
            );
        }

        Function<User, String> namePipeline =
                user -> user.getName();

        Function<String, String> cleanName =
                String::trim;

        Function<String, String> normalizeName =
                String::toLowerCase;

        Function<User, String> finalNamePipeline =
                namePipeline
                        .andThen(cleanName)
                        .andThen(normalizeName);

        System.out.println(
                finalNamePipeline.apply(user1)
        );

        ExecutorService executor =
                Executors.newFixedThreadPool(4);

        Supplier<List<User>> userLoader =
                repository::findAll;

        CompletableFuture<List<User>> futureUsers =
                CompletableFuture.supplyAsync(
                        userLoader,
                        executor
                );

        CompletableFuture<List<String>> futureNames =
                futureUsers.thenApply(
                        users ->
                                users.stream()
                                        .map(User::getName)
                                        .collect(
                                                Collectors.toList()
                                        )
                );

        futureNames.thenAccept(
                namesResult ->
                        System.out.println(
                                "Async names: "
                                        + namesResult
                        )
        ).join();

        executor.shutdown();

        Function<String, Function<String, String>> concatenate =
                firstValue ->
                        secondValue ->
                                firstValue + secondValue;

        Function<String, String> hello =
                concatenate.apply("Hello ");

        System.out.println(
                hello.apply("World")
        );

        Function<Integer, Function<Integer, Integer>> multiplier =
                a -> b -> a * b;

        Function<Integer, Integer> multiplyByTen =
                multiplier.apply(10);

        System.out.println(
                multiplyByTen.apply(50)
        );

        Function<Integer, Function<Integer, Function<Integer, Integer>>> sumCurry =
                a -> b -> c -> a + b + c;

        System.out.println(
                sumCurry
                        .apply(10)
                        .apply(20)
                        .apply(30)
        );

        BiFunction<User, Double, UserDTO> salaryAdjustedDTO =
                (user, increase) ->
                        new UserDTO(
                                user.getId(),
                                user.getName(),
                                user.getDepartment(),
                                user.getSalary() + increase
                        );

        System.out.println(
                salaryAdjustedDTO.apply(
                        user1,
                        10000.0
                )
        );

        Function<List<User>, Map<String, Long>> departmentCounter =
                users ->
                        users.stream()
                                .collect(
                                        Collectors.groupingBy(
                                                User::getDepartment,
                                                Collectors.counting()
                                        )
                                );

        System.out.println(
                departmentCounter.apply(
                        repository.findAll()
                )
        );

        Function<List<User>, Optional<User>> highestPaid =
                users ->
                        users.stream()
                                .max(
                                        Comparator.comparingDouble(
                                                User::getSalary
                                        )
                                );

        highestPaid.apply(
                        repository.findAll()
                )
                .ifPresent(
                        user ->
                                System.out.println(
                                        "Highest paid: "
                                                + user
                                )
                );

        Function<List<User>, Double> totalSalary =
                users ->
                        users.stream()
                                .mapToDouble(
                                        User::getSalary
                                )
                                .sum();

        System.out.println(
                totalSalary.apply(
                        repository.findAll()
                )
        );

        Function<List<User>, Set<String>> allSkills =
                users ->
                        users.stream()
                                .flatMap(
                                        user ->
                                                user.getSkills()
                                                        .stream()
                                )
                                .map(String::toLowerCase)
                                .collect(
                                        Collectors.toSet()
                                );

        System.out.println(
                allSkills.apply(
                        repository.findAll()
                )
        );

        Predicate<User> engineering =
                user ->
                        user.getDepartment()
                                .equals("Engineering");

        Predicate<User> senior =
                user ->
                        user.getAge() >= 30;

        Predicate<User> highEarner =
                user ->
                        user.getSalary() >= 150000;

        Predicate<User> targetEmployee =
                engineering
                        .and(senior)
                        .and(highEarner);

        repository.findAll()
                .stream()
                .filter(targetEmployee)
                .forEach(
                        user ->
                                System.out.println(
                                        "Target: "
                                                + user
                                )
                );

        Function<User, String> profileSummary =
                user ->
                        String.format(
                                "%s | %s | %s | %.2f",
                                user.getName(),
                                user.getEmail(),
                                user.getDepartment(),
                                user.getSalary()
                        );

        repository.findAll()
                .stream()
                .map(profileSummary)
                .forEach(System.out::println);

        Consumer<List<User>> batchProcessor =
                users -> {
                    users.forEach(
                            user ->
                                    System.out.println(
                                            "Processing "
                                                    + user.getId()
                                    )
                    );
                };

        batchProcessor.accept(
                repository.findAll()
        );

        Supplier<ExecutorService> executorSupplier =
                () -> Executors.newFixedThreadPool(2);

        ExecutorService suppliedExecutor =
                executorSupplier.get();

        suppliedExecutor.submit(
                () ->
                        System.out.println(
                                "Task executed using Supplier"
                        )
        );

        suppliedExecutor.shutdown();

        System.out.println(
                "Functional interface demonstration completed"
        );
    }
}