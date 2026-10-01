import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class AtomicLongExample {
    private static final int THREAD_COUNT = 10;
    private static final int INCREMENTS_PER_THREAD = 1000;

    public static void main(String[] args) throws InterruptedException {
        basicOperations();
        System.out.println("\n--- Multithreading Example ---");
        multithreadingExample();
        System.out.println("\n--- Compare-And-Set Pattern ---");
        compareAndSetPattern();
        System.out.println("\n--- Functional Update ---");
        functionalUpdate();
        System.out.println("\n--- Accumulate Pattern ---");
        accumulatePattern();
        System.out.println("\n--- Lazy Initialization ---");
        lazyInitialization();
        System.out.println("\n--- Max/Min Tracking ---");
        maxMinTracking();
        System.out.println("\n--- Check-Then-Act Correct Pattern ---");
        checkThenActCorrect();
    }

    private static void basicOperations() {
        AtomicLong counter = new AtomicLong(0);

        counter.incrementAndGet();
        counter.incrementAndGet();
        System.out.println("Simple counter: " + counter.get());
    }

    private static void multithreadingExample() throws InterruptedException {
        AtomicLong atomicCounter = new AtomicLong(0);

        ExecutorService executor = Executors.newFixedThreadPool(THREAD_COUNT);

        for (int i = 0; i < THREAD_COUNT; i++) {
            executor.submit(() -> {
                for (int j = 0; j < INCREMENTS_PER_THREAD; j++) {
                    atomicCounter.incrementAndGet();
                }
            });
        }

        executor.shutdown();
        executor.awaitTermination(5, TimeUnit.SECONDS);

        System.out.println("Expected: " + (THREAD_COUNT * INCREMENTS_PER_THREAD));
        System.out.println("Atomic counter: " + atomicCounter.get());
    }

    private static void compareAndSetPattern() {
        AtomicLong balance = new AtomicLong(100);

        long current = balance.get();
        long newBalance = current - 50;
        if (balance.compareAndSet(current, newBalance)) {
            System.out.println("Withdrawal successful");
        } else {
            System.out.println("Withdrawal failed");
        }

        System.out.println("Balance: " + balance.get());
    }

    private static void functionalUpdate() {
        AtomicLong value = new AtomicLong(10);

        long result = value.updateAndGet(x -> x * 2);
        System.out.println("After updateAndGet: " + value.get() + ", result: " + result);

        long oldResult = value.getAndUpdate(x -> x + 5);
        System.out.println("After getAndUpdate: " + value.get() + ", old: " + oldResult);
    }

    private static void accumulatePattern() {
        AtomicLong sum = new AtomicLong(0);

        sum.accumulateAndGet(5, Long::sum);
        sum.accumulateAndGet(10, Long::sum);
        sum.accumulateAndGet(3, Long::sum);

        System.out.println("Sum: " + sum.get());
    }

    private static void lazyInitialization() {
        AtomicLong cachedValue = new AtomicLong(-1);

        long cached = cachedValue.get();
        if (cached == -1) {
            cached = 42;
            cachedValue.compareAndSet(-1, cached);
        }

        System.out.println("Cached value: " + cachedValue.get());
    }

    private static void maxMinTracking() {
        AtomicLong maxValue = new AtomicLong(Long.MIN_VALUE);
        AtomicLong minValue = new AtomicLong(Long.MAX_VALUE);

        maxValue.updateAndGet(x -> Math.max(x, 10));
        maxValue.updateAndGet(x -> Math.max(x, 5));
        maxValue.updateAndGet(x -> Math.max(x, 20));

        minValue.updateAndGet(x -> Math.min(x, 10));
        minValue.updateAndGet(x -> Math.min(x, 5));
        minValue.updateAndGet(x -> Math.min(x, 20));

        System.out.println("Max: " + maxValue.get());
        System.out.println("Min: " + minValue.get());
    }

    private static void checkThenActCorrect() {
        AtomicLong atomic = new AtomicLong(10);

        while (true) {
            long current = atomic.get();
            if (current <= 0) break;
            if (atomic.compareAndSet(current, current - 1)) break;
        }

        System.out.println("After decrement: " + atomic.get());
    }
}
