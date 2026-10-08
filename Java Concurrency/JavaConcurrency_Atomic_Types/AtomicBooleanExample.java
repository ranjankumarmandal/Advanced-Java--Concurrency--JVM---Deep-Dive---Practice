import java.util.concurrent.atomic.AtomicBoolean;

public class AtomicBooleanExample {

    public static void main(String[] args) {
        basicOperations();
        System.out.println("\n--- Get and Set ---");
        getAndSet();
        System.out.println("\n--- Compare and Set ---");
        compareAndSet();
        System.out.println("\n--- Lazy Set ---");
        lazySet();
    }

    private static void basicOperations() {
        AtomicBoolean flag = new AtomicBoolean(false);

        System.out.println("Initial: " + flag.get());

        flag.set(true);
        System.out.println("After set(true): " + flag.get());

        flag.set(false);
        System.out.println("After set(false): " + flag.get());
    }

    private static void getAndSet() {
        AtomicBoolean flag = new AtomicBoolean(false);

        boolean oldValue = flag.getAndSet(true);
        System.out.println("getAndSet(true) returned: " + oldValue);
        System.out.println("Current value: " + flag.get());

        oldValue = flag.getAndSet(false);
        System.out.println("getAndSet(false) returned: " + oldValue);
        System.out.println("Current value: " + flag.get());
    }

    private static void compareAndSet() {
        AtomicBoolean flag = new AtomicBoolean(false);

        boolean result = flag.compareAndSet(false, true);
        System.out.println("compareAndSet(false, true): " + result);
        System.out.println("Current value: " + flag.get());

        result = flag.compareAndSet(false, true);
        System.out.println("compareAndSet(false, true) again: " + result);
        System.out.println("Current value: " + flag.get());

        result = flag.compareAndSet(true, false);
        System.out.println("compareAndSet(true, false): " + result);
        System.out.println("Current value: " + flag.get());
    }

    private static void lazySet() {
        AtomicBoolean flag = new AtomicBoolean(false);

        flag.lazySet(true);
        System.out.println("After lazySet(true): " + flag.get());

        flag.lazySet(false);
        System.out.println("After lazySet(false): " + flag.get());
    }
}
