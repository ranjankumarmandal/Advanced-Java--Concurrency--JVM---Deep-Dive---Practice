import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Phaser;
import java.util.concurrent.Exchanger;
import java.util.concurrent.locks.LockSupport;

public class AtomicIntegerExample {
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
        System.out.println("\n--- Producer-Consumer with Atomic ---");
        producerConsumerAtomic();
        System.out.println("\n--- Work Queue with Atomic Tracking ---");
        workQueueAtomic();
        System.out.println("\n--- Backoff Strategy ---");
        backoffStrategy();
        System.out.println("\n--- Spin Wait with LockSupport ---");
        spinWaitLockSupport();
        System.out.println("\n--- Atomic Counter with Threshold ---");
        counterWithThreshold();
        System.out.println("\n--- Thread Pool with Atomic Stats ---");
        threadPoolAtomicStats();
    }

    private static void basicOperations() {
        AtomicInteger counter = new AtomicInteger(0);

        counter.incrementAndGet();
        counter.incrementAndGet();
        System.out.println("Simple counter: " + counter.get());
    }

    private static void multithreadingExample() throws InterruptedException {
        AtomicInteger atomicCounter = new AtomicInteger(0);

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
        AtomicInteger balance = new AtomicInteger(100);

        int current = balance.get();
        int newBalance = current - 50;
        if (balance.compareAndSet(current, newBalance)) {
            System.out.println("Withdrawal successful");
        } else {
            System.out.println("Withdrawal failed");
        }

        System.out.println("Balance: " + balance.get());
    }

    private static void functionalUpdate() {
        AtomicInteger value = new AtomicInteger(10);

        int result = value.updateAndGet(x -> x * 2);
        System.out.println("After updateAndGet: " + value.get() + ", result: " + result);

        int oldResult = value.getAndUpdate(x -> x + 5);
        System.out.println("After getAndUpdate: " + value.get() + ", old: " + oldResult);
    }

    private static void accumulatePattern() {
        AtomicInteger sum = new AtomicInteger(0);

        sum.accumulateAndGet(5, Integer::sum);
        sum.accumulateAndGet(10, Integer::sum);
        sum.accumulateAndGet(3, Integer::sum);

        System.out.println("Sum: " + sum.get());
    }

    private static void lazyInitialization() {
        AtomicInteger cachedValue = new AtomicInteger(-1);

        int cached = cachedValue.get();
        if (cached == -1) {
            cached = 42;
            cachedValue.compareAndSet(-1, cached);
        }

        System.out.println("Cached value: " + cachedValue.get());
    }

    private static void maxMinTracking() {
        AtomicInteger maxValue = new AtomicInteger(Integer.MIN_VALUE);
        AtomicInteger minValue = new AtomicInteger(Integer.MAX_VALUE);

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
        AtomicInteger atomic = new AtomicInteger(10);

        while (true) {
            int current = atomic.get();
            if (current <= 0) break;
            if (atomic.compareAndSet(current, current - 1)) break;
        }

        System.out.println("After decrement: " + atomic.get());
    }

    private static void producerConsumerAtomic() throws InterruptedException {
        AtomicInteger itemCount = new AtomicInteger(0);
        AtomicInteger consumedCount = new AtomicInteger(0);
        BlockingQueue<Integer> queue = new LinkedBlockingQueue<>(10);
        AtomicInteger producersDone = new AtomicInteger(0);

        ExecutorService executor = Executors.newFixedThreadPool(4);

        for (int i = 0; i < 2; i++) {
            executor.submit(() -> {
                for (int j = 0; j < 5; j++) {
                    try {
                        queue.put(j);
                        itemCount.incrementAndGet();
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
                producersDone.incrementAndGet();
            });
        }

        for (int i = 0; i < 2; i++) {
            executor.submit(() -> {
                while (true) {
                    try {
                        Integer item = queue.poll(100, TimeUnit.MILLISECONDS);
                        if (item == null && producersDone.get() == 2) {
                            break;
                        }
                        if (item != null) {
                            consumedCount.incrementAndGet();
                        }
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        break;
                    }
                }
            });
        }

        executor.shutdown();
        executor.awaitTermination(5, TimeUnit.SECONDS);

        System.out.println("Produced: " + itemCount.get());
        System.out.println("Consumed: " + consumedCount.get());
    }

    private static void workQueueAtomic() throws InterruptedException {
        AtomicInteger pendingTasks = new AtomicInteger(0);
        AtomicInteger completedTasks = new AtomicInteger(0);
        AtomicInteger failedTasks = new AtomicInteger(0);
        BlockingQueue<Runnable> taskQueue = new LinkedBlockingQueue<>();

        for (int i = 0; i < 10; i++) {
            final int taskId = i;
            taskQueue.add(() -> {
                try {
                    Thread.sleep(50);
                    completedTasks.incrementAndGet();
                } catch (InterruptedException e) {
                    failedTasks.incrementAndGet();
                }
            });
            pendingTasks.incrementAndGet();
        }

        ExecutorService executor = Executors.newFixedThreadPool(3);

        for (int i = 0; i < 3; i++) {
            executor.submit(() -> {
                while (true) {
                    Runnable task = taskQueue.poll();
                    if (task == null) break;
                    task.run();
                }
            });
        }

        executor.shutdown();
        executor.awaitTermination(5, TimeUnit.SECONDS);

        System.out.println("Pending: " + pendingTasks.get());
        System.out.println("Completed: " + completedTasks.get());
        System.out.println("Failed: " + failedTasks.get());
    }

    private static void backoffStrategy() throws InterruptedException {
        AtomicInteger counter = new AtomicInteger(0);
        AtomicInteger contentionCount = new AtomicInteger(0);

        ExecutorService executor = Executors.newFixedThreadPool(10);

        for (int i = 0; i < 10; i++) {
            executor.submit(() -> {
                for (int j = 0; j < 100; j++) {
                    int attempts = 0;
                    while (true) {
                        int current = counter.get();
                        int newValue = current + 1;
                        if (counter.compareAndSet(current, newValue)) {
                            break;
                        }
                        attempts++;
                        contentionCount.incrementAndGet();
                        if (attempts > 3) {
                            LockSupport.parkNanos(1000);
                        }
                    }
                }
            });
        }

        executor.shutdown();
        executor.awaitTermination(5, TimeUnit.SECONDS);

        System.out.println("Counter: " + counter.get());
        System.out.println("Contention events: " + contentionCount.get());
    }

    private static void spinWaitLockSupport() throws InterruptedException {
        AtomicInteger flag = new AtomicInteger(0);
        AtomicInteger waitingThreads = new AtomicInteger(0);

        Thread waiter = new Thread(() -> {
            waitingThreads.incrementAndGet();
            while (flag.get() == 0) {
                LockSupport.parkNanos(100000);
            }
            System.out.println("Thread woke up!");
        });

        waiter.start();
        Thread.sleep(100);

        flag.set(1);
        waiter.join();

        System.out.println("Waiting threads: " + waitingThreads.get());
    }

    private static void counterWithThreshold() throws InterruptedException {
        AtomicInteger counter = new AtomicInteger(0);
        AtomicInteger thresholdHits = new AtomicInteger(0);
        final int THRESHOLD = 50;

        ExecutorService executor = Executors.newFixedThreadPool(5);

        for (int i = 0; i < 5; i++) {
            executor.submit(() -> {
                for (int j = 0; j < 20; j++) {
                    while (true) {
                        int current = counter.get();
                        if (current >= THRESHOLD) {
                            thresholdHits.incrementAndGet();
                            break;
                        }
                        if (counter.compareAndSet(current, current + 1)) {
                            break;
                        }
                    }
                }
            });
        }

        executor.shutdown();
        executor.awaitTermination(5, TimeUnit.SECONDS);

        System.out.println("Counter: " + counter.get());
        System.out.println("Threshold hits: " + thresholdHits.get());
    }

    private static void threadPoolAtomicStats() throws InterruptedException {
        AtomicInteger activeThreads = new AtomicInteger(0);
        AtomicInteger totalTasks = new AtomicInteger(0);
        AtomicInteger successfulTasks = new AtomicInteger(0);
        AtomicInteger failedTasks = new AtomicInteger(0);
        AtomicInteger totalProcessingTime = new AtomicInteger(0);

        ExecutorService executor = Executors.newFixedThreadPool(5);

        for (int i = 0; i < 20; i++) {
            final int taskId = i;
            executor.submit(() -> {
                activeThreads.incrementAndGet();
                totalTasks.incrementAndGet();
                long startTime = System.currentTimeMillis();

                try {
                    Thread.sleep(50 + (int)(Math.random() * 50));
                    successfulTasks.incrementAndGet();
                } catch (InterruptedException e) {
                    failedTasks.incrementAndGet();
                } finally {
                    long processingTime = (int)(System.currentTimeMillis() - startTime);
                    totalProcessingTime.addAndGet(processingTime);
                    activeThreads.decrementAndGet();
                }
            });
        }

        executor.shutdown();
        executor.awaitTermination(10, TimeUnit.SECONDS);

        System.out.println("Total tasks: " + totalTasks.get());
        System.out.println("Successful: " + successfulTasks.get());
        System.out.println("Failed: " + failedTasks.get());
        System.out.println("Active threads: " + activeThreads.get());
        System.out.println("Total processing time: " + totalProcessingTime.get() + "ms");
        System.out.println("Avg processing time: " + (totalProcessingTime.get() / totalTasks.get()) + "ms");
    }
}
