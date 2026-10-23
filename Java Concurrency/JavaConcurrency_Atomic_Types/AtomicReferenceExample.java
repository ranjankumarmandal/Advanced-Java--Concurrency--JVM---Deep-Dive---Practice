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
        System.out.println("\n--- Lazy Initialization ---");
        lazyInitialization();
        System.out.println("\n--- Immutable Object Update ---");
        immutableObjectUpdate();
        System.out.println("\n--- Cache Pattern ---");
        cachePattern();
        System.out.println("\n--- Latest Value Holder ---");
        latestValueHolder();
        System.out.println("\n--- Accumulate Pattern ---");
        accumulatePattern();
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

    private static void lazyInitialization() {
        AtomicReference<String> cached = new AtomicReference<>(null);

        String value = cached.get();
        if (value == null) {
            value = "Computed Value";
            cached.compareAndSet(null, value);
        }

        System.out.println("Cached value: " + cached.get());
    }

    private static void immutableObjectUpdate() {
        class ImmutableConfig {
            private final String name;
            private final int value;

            ImmutableConfig(String name, int value) {
                this.name = name;
                this.value = value;
            }

            String getName() {
                return name;
            }

            int getValue() {
                return value;
            }
        }

        AtomicReference<ImmutableConfig> configRef = new AtomicReference<>(
            new ImmutableConfig("default", 0)
        );

        System.out.println("Initial config: " + configRef.get().getName() + ", " + configRef.get().getValue());

        ImmutableConfig current = configRef.get();
        ImmutableConfig newConfig = new ImmutableConfig("new", 100);
        configRef.compareAndSet(current, newConfig);

        System.out.println("Updated config: " + configRef.get().getName() + ", " + configRef.get().getValue());
    }

    private static void cachePattern() {
        AtomicReference<String> cache = new AtomicReference<>(null);

        String value = cache.get();
        if (value == null) {
            value = "Loaded from database";
            cache.compareAndSet(null, value);
        }

        System.out.println("Cache value: " + cache.get());

        cache.set(null);
        System.out.println("After invalidate: " + cache.get());
    }

    private static void latestValueHolder() {
        AtomicReference<String> latestValue = new AtomicReference<>("initial");

        System.out.println("Latest value: " + latestValue.get());

        latestValue.set("update1");
        System.out.println("After update1: " + latestValue.get());

        latestValue.set("update2");
        System.out.println("After update2: " + latestValue.get());
    }

    private static void accumulatePattern() {
        AtomicReference<String> builder = new AtomicReference<>("");

        builder.accumulateAndGet("Hello", (a, b) -> a + b);
        System.out.println("After accumulate Hello: " + builder.get());

        builder.accumulateAndGet(" ", (a, b) -> a + b);
        System.out.println("After accumulate space: " + builder.get());

        builder.accumulateAndGet("World", (a, b) -> a + b);
        System.out.println("After accumulate World: " + builder.get());
    }
}
