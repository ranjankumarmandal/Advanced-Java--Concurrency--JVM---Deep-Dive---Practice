import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;
import java.util.concurrent.atomic.AtomicMarkableReference;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

public class AtomicReferenceExample {

    public static void main(String[] args) throws InterruptedException {
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
        System.out.println("\n--- ABA Problem ---");
        abaProblem();
        System.out.println("\n--- AtomicStampedReference ---");
        atomicStampedReference();
        System.out.println("\n--- AtomicMarkableReference ---");
        atomicMarkableReference();
        System.out.println("\n--- Optimistic Locking ---");
        optimisticLocking();
        System.out.println("\n--- Concurrent Initialization ---");
        concurrentInitialization();
        System.out.println("\n--- Concurrent Config Update ---");
        concurrentConfigUpdate();
        System.out.println("\n--- Backoff Reference Update ---");
        backoffReferenceUpdate();
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

    private static void abaProblem() {
        AtomicReference<String> ref = new AtomicReference<>("A");

        System.out.println("Initial: " + ref.get());

        ref.set("B");
        System.out.println("After set B: " + ref.get());

        ref.set("A");
        System.out.println("After set A: " + ref.get());

        boolean success = ref.compareAndSet("A", "C");
        System.out.println("CAS A->C succeeded: " + success);
        System.out.println("Final value: " + ref.get());
    }

    private static void atomicStampedReference() {
        AtomicStampedReference<String> ref = new AtomicStampedReference<>("A", 0);

        int[] stampHolder = new int[1];
        String value = ref.get(stampHolder);
        System.out.println("Initial - Value: " + value + ", Stamp: " + stampHolder[0]);

        ref.compareAndSet("A", "B", 0, 1);
        value = ref.get(stampHolder);
        System.out.println("After A->B - Value: " + value + ", Stamp: " + stampHolder[0]);

        ref.compareAndSet("B", "A", 1, 2);
        value = ref.get(stampHolder);
        System.out.println("After B->A - Value: " + value + ", Stamp: " + stampHolder[0]);

        boolean success = ref.compareAndSet("A", "C", 0, 3);
        System.out.println("CAS A->C with stamp 0 succeeded: " + success);
        System.out.println("Final value: " + ref.getReference() + ", Stamp: " + ref.getStamp());
    }

    private static void atomicMarkableReference() {
        AtomicMarkableReference<String> ref = new AtomicMarkableReference<>("A", false);

        boolean[] markHolder = new boolean[1];
        String value = ref.get(markHolder);
        System.out.println("Initial - Value: " + value + ", Marked: " + markHolder[0]);

        ref.compareAndSet("A", "B", false, true);
        value = ref.get(markHolder);
        System.out.println("After A->B - Value: " + value + ", Marked: " + markHolder[0]);

        ref.compareAndSet("B", "A", true, false);
        value = ref.get(markHolder);
        System.out.println("After B->A - Value: " + value + ", Marked: " + markHolder[0]);

        boolean success = ref.compareAndSet("A", "C", false, true);
        System.out.println("CAS A->C with mark false succeeded: " + success);
        System.out.println("Final value: " + ref.getReference() + ", Marked: " + ref.isMarked());
    }

    private static void optimisticLocking() {
        class VersionedValue {
            private final String value;
            private final int version;

            VersionedValue(String value, int version) {
                this.value = value;
                this.version = version;
            }

            String getValue() {
                return value;
            }

            int getVersion() {
                return version;
            }
        }

        AtomicReference<VersionedValue> ref = new AtomicReference<>(
            new VersionedValue("initial", 0)
        );

        System.out.println("Initial value: " + ref.get().getValue() + ", version: " + ref.get().getVersion());

        VersionedValue current = ref.get();
        VersionedValue newValue = new VersionedValue("updated", current.getVersion() + 1);
        boolean success = ref.compareAndSet(current, newValue);

        System.out.println("Update succeeded: " + success);
        System.out.println("Final value: " + ref.get().getValue() + ", version: " + ref.get().getVersion());
    }

    private static void concurrentInitialization() throws InterruptedException {
        AtomicReference<String> initialized = new AtomicReference<>(null);
        AtomicInteger initCount = new AtomicInteger(0);
        AtomicInteger successCount = new AtomicInteger(0);

        ExecutorService executor = Executors.newFixedThreadPool(10);

        for (int i = 0; i < 10; i++) {
            executor.submit(() -> {
                initCount.incrementAndGet();
                if (initialized.compareAndSet(null, "Initialized by thread")) {
                    successCount.incrementAndGet();
                    System.out.println("Initialization completed");
                }
            });
        }

        executor.shutdown();
        executor.awaitTermination(2, TimeUnit.SECONDS);

        System.out.println("Initialization attempts: " + initCount.get());
        System.out.println("Successful initializations: " + successCount.get());
        System.out.println("Initialized value: " + initialized.get());
    }

    private static void concurrentConfigUpdate() throws InterruptedException {
        class Config {
            private final String name;
            private final int value;

            Config(String name, int value) {
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

        AtomicReference<Config> configRef = new AtomicReference<>(new Config("default", 0));
        AtomicInteger updateCount = new AtomicInteger(0);

        ExecutorService executor = Executors.newFixedThreadPool(5);

        for (int i = 0; i < 5; i++) {
            final int threadId = i;
            executor.submit(() -> {
                while (true) {
                    Config current = configRef.get();
                    Config newConfig = new Config("config-" + threadId, current.getValue() + 1);
                    if (configRef.compareAndSet(current, newConfig)) {
                        updateCount.incrementAndGet();
                        break;
                    }
                }
            });
        }

        executor.shutdown();
        executor.awaitTermination(2, TimeUnit.SECONDS);

        System.out.println("Successful updates: " + updateCount.get());
        System.out.println("Final config: " + configRef.get().getName() + ", value: " + configRef.get().getValue());
    }

    private static void backoffReferenceUpdate() throws InterruptedException {
        AtomicReference<String> ref = new AtomicReference<>("initial");
        AtomicInteger acquiredCount = new AtomicInteger(0);
        AtomicInteger contentionCount = new AtomicInteger(0);

        ExecutorService executor = Executors.newFixedThreadPool(5);

        for (int i = 0; i < 5; i++) {
            final int threadId = i;
            executor.submit(() -> {
                int attempts = 0;
                while (true) {
                    String current = ref.get();
                    String newValue = "thread-" + threadId;
                    if (ref.compareAndSet(current, newValue)) {
                        acquiredCount.incrementAndGet();
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        } finally {
                            ref.set("initial");
                        }
                        break;
                    }
                    attempts++;
                    contentionCount.incrementAndGet();
                    if (attempts < 3) {
                        Thread.yield();
                    } else {
                        LockSupport.parkNanos(1000000);
                    }
                }
            });
        }

        executor.shutdown();
        executor.awaitTermination(5, TimeUnit.SECONDS);

        System.out.println("Successful acquisitions: " + acquiredCount.get());
        System.out.println("Contention events: " + contentionCount.get());
    }
}
