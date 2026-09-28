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
        System.out.println("\n--- Advanced Operations ---");
        advancedOperations();
    }

    private static void basicOperations() {
        AtomicLong atomicLong = new AtomicLong(0);

        System.out.println("Initial value: " + atomicLong.get());

        atomicLong.set(10);
        System.out.println("After set(10): " + atomicLong.get());

        long currentValue = atomicLong.get();
        System.out.println("Current value: " + currentValue);

        long newValue = atomicLong.getAndSet(20);
        System.out.println("getAndSet(20) returned: " + newValue);
        System.out.println("After getAndSet(20): " + atomicLong.get());

        newValue = atomicLong.getAndAdd(5);
        System.out.println("getAndAdd(5) returned: " + newValue);
        System.out.println("After getAndAdd(5): " + atomicLong.get());

        newValue = atomicLong.addAndGet(5);
        System.out.println("addAndGet(5) returned: " + newValue);
        System.out.println("After addAndGet(5): " + atomicLong.get());

        newValue = atomicLong.getAndIncrement();
        System.out.println("getAndIncrement() returned: " + newValue);
        System.out.println("After getAndIncrement(): " + atomicLong.get());

        newValue = atomicLong.incrementAndGet();
        System.out.println("incrementAndGet() returned: " + newValue);
        System.out.println("After incrementAndGet(): " + atomicLong.get());

        newValue = atomicLong.getAndDecrement();
        System.out.println("getAndDecrement() returned: " + newValue);
        System.out.println("After getAndDecrement(): " + atomicLong.get());

        newValue = atomicLong.decrementAndGet();
        System.out.println("decrementAndGet() returned: " + newValue);
        System.out.println("After decrementAndGet(): " + atomicLong.get());

        boolean wasUpdated = atomicLong.compareAndSet(24, 100);
        System.out.println("compareAndSet(24, 100): " + wasUpdated);
        System.out.println("After compareAndSet(24, 100): " + atomicLong.get());

        wasUpdated = atomicLong.compareAndSet(100, 200);
        System.out.println("compareAndSet(100, 200): " + wasUpdated);
        System.out.println("After compareAndSet(100, 200): " + atomicLong.get());

        System.out.println("Final value: " + atomicLong.get());
    }

    private static void multithreadingExample() throws InterruptedException {
        AtomicLong atomicCounter = new AtomicLong(0);
        long regularCounter = 0;

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

        System.out.println("Expected increments: " + (THREAD_COUNT * INCREMENTS_PER_THREAD));
        System.out.println("Atomic counter result: " + atomicCounter.get());

        ThreadSafeCounter threadSafeCounter = new ThreadSafeCounter();
        ExecutorService executor2 = Executors.newFixedThreadPool(THREAD_COUNT);

        for (int i = 0; i < THREAD_COUNT; i++) {
            executor2.submit(() -> {
                for (int j = 0; j < INCREMENTS_PER_THREAD; j++) {
                    threadSafeCounter.increment();
                }
            });
        }

        executor2.shutdown();
        executor2.awaitTermination(5, TimeUnit.SECONDS);

        System.out.println("ThreadSafeCounter result: " + threadSafeCounter.get());
    }

    private static void advancedOperations() {
        AtomicLong atomicLong = new AtomicLong(10);

        long oldValue = atomicLong.getAndUpdate(x -> x * 2);
        System.out.println("getAndUpdate(x -> x * 2) returned: " + oldValue);
        System.out.println("After getAndUpdate: " + atomicLong.get());

        long newValue = atomicLong.updateAndGet(x -> x + 5);
        System.out.println("updateAndGet(x -> x + 5) returned: " + newValue);
        System.out.println("After updateAndGet: " + atomicLong.get());

        newValue = atomicLong.accumulateAndGet(5, (x, y) -> x * y);
        System.out.println("accumulateAndGet(5, (x, y) -> x * y) returned: " + newValue);
        System.out.println("After accumulateAndGet: " + atomicLong.get());

        oldValue = atomicLong.getAndAccumulate(2, (x, y) -> x + y);
        System.out.println("getAndAccumulate(2, (x, y) -> x + y) returned: " + oldValue);
        System.out.println("After getAndAccumulate: " + atomicLong.get());
    }

    static class ThreadSafeCounter {
        private final AtomicLong counter = new AtomicLong(0);

        public void increment() {
            counter.incrementAndGet();
        }

        public void add(long value) {
            counter.addAndGet(value);
        }

        public long get() {
            return counter.get();
        }
    }
}
