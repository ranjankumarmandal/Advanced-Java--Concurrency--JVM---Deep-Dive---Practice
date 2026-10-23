import java.util.concurrent.atomic.AtomicReference;

public class AtomicReferenceExample {

    public static void main(String[] args) {
        basicOperations();
        System.out.println("\n--- Get and Set ---");
        getAndSet();
        System.out.println("\n--- Compare and Set ---");
        compareAndSet();
        System.out.println("\n--- Lazy Set ---");
        lazySet();
        System.out.println("\n--- Functional Update ---");
        functionalUpdate();
    }

    private static void basicOperations() {
        AtomicReference<String> ref = new AtomicReference<>("Hello");

        System.out.println("Initial: " + ref.get());

        ref.set("World");
        System.out.println("After set(World): " + ref.get());

        ref.set("Java");
        System.out.println("After set(Java): " + ref.get());
    }

    private static void getAndSet() {
        AtomicReference<String> ref = new AtomicReference<>("Hello");

        String oldValue = ref.getAndSet("World");
        System.out.println("getAndSet(World) returned: " + oldValue);
        System.out.println("Current value: " + ref.get());

        oldValue = ref.getAndSet("Java");
        System.out.println("getAndSet(Java) returned: " + oldValue);
        System.out.println("Current value: " + ref.get());
    }

    private static void compareAndSet() {
        AtomicReference<String> ref = new AtomicReference<>("Hello");

        boolean result = ref.compareAndSet("Hello", "World");
        System.out.println("compareAndSet(Hello, World): " + result);
        System.out.println("Current value: " + ref.get());

        result = ref.compareAndSet("Hello", "Java");
        System.out.println("compareAndSet(Hello, Java) again: " + result);
        System.out.println("Current value: " + ref.get());

        result = ref.compareAndSet("World", "Java");
        System.out.println("compareAndSet(World, Java): " + result);
        System.out.println("Current value: " + ref.get());
    }

    private static void lazySet() {
        AtomicReference<String> ref = new AtomicReference<>("Hello");

        ref.lazySet("World");
        System.out.println("After lazySet(World): " + ref.get());

        ref.lazySet("Java");
        System.out.println("After lazySet(Java): " + ref.get());
    }

    private static void functionalUpdate() {
        AtomicReference<String> ref = new AtomicReference<>("Hello");

        String result = ref.updateAndGet(s -> s.toUpperCase());
        System.out.println("updateAndGet(toUpperCase) returned: " + result);
        System.out.println("Current value: " + ref.get());

        String oldResult = ref.getAndUpdate(s -> s + " World");
        System.out.println("getAndUpdate(concat) returned: " + oldResult);
        System.out.println("Current value: " + ref.get());
    }
}
