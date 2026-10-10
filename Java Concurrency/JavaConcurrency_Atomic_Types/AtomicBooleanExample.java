import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class AtomicBooleanExample {

    public static void main(String[] args) {
        basicOperations();
        System.out.println("\n--- Get and Set ---");
        getAndSet();
        System.out.println("\n--- Compare and Set ---");
        compareAndSet();
        System.out.println("\n--- Lazy Set ---");
        lazySet();
        System.out.println("\n--- Shutdown Flag ---");
        shutdownFlag();
        System.out.println("\n--- One-Time Initialization ---");
        oneTimeInitialization();
        System.out.println("\n--- Feature Toggle ---");
        featureToggle();
        System.out.println("\n--- Simple Lock ---");
        simpleLock();
        System.out.println("\n--- Toggle Switch ---");
        toggleSwitch();
        System.out.println("\n--- Circuit Breaker ---");
        circuitBreaker();
        System.out.println("\n--- Guarded Suspension ---");
        guardedSuspension();
        System.out.println("\n--- Toggle with Count ---");
        toggleWithCount();
        System.out.println("\n--- State Machine ---");
        stateMachine();
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

    private static void shutdownFlag() {
        AtomicBoolean running = new AtomicBoolean(true);

        System.out.println("Running: " + running.get());

        running.set(false);
        System.out.println("After shutdown: " + running.get());
    }

    private static void oneTimeInitialization() {
        AtomicBoolean initialized = new AtomicBoolean(false);

        boolean success = initialized.compareAndSet(false, true);
        System.out.println("First initialization: " + success);

        success = initialized.compareAndSet(false, true);
        System.out.println("Second initialization attempt: " + success);

        System.out.println("Initialized: " + initialized.get());
    }

    private static void featureToggle() {
        AtomicBoolean featureEnabled = new AtomicBoolean(false);

        System.out.println("Feature enabled: " + featureEnabled.get());

        featureEnabled.set(true);
        System.out.println("After enable: " + featureEnabled.get());

        if (featureEnabled.get()) {
            System.out.println("Using new feature logic");
        }

        featureEnabled.set(false);
        System.out.println("After disable: " + featureEnabled.get());
    }

    private static void simpleLock() {
        AtomicBoolean locked = new AtomicBoolean(false);

        boolean acquired = locked.compareAndSet(false, true);
        System.out.println("Lock acquired: " + acquired);
        System.out.println("Locked: " + locked.get());

        locked.set(false);
        System.out.println("After unlock: " + locked.get());

        acquired = locked.compareAndSet(false, true);
        System.out.println("Lock acquired again: " + acquired);
    }

    private static void toggleSwitch() {
        AtomicBoolean state = new AtomicBoolean(false);

        System.out.println("Initial state: " + state.get());

        state.set(!state.get());
        System.out.println("After toggle: " + state.get());

        state.set(!state.get());
        System.out.println("After toggle: " + state.get());
    }

    private static void circuitBreaker() {
        AtomicBoolean circuitOpen = new AtomicBoolean(false);
        AtomicInteger failureCount = new AtomicInteger(0);

        System.out.println("Circuit open: " + circuitOpen.get());
        System.out.println("Failures: " + failureCount.get());

        failureCount.incrementAndGet();
        if (failureCount.get() >= 3) {
            circuitOpen.set(true);
        }

        System.out.println("After failures - Circuit open: " + circuitOpen.get());
        System.out.println("Request allowed: " + !circuitOpen.get());

        circuitOpen.set(false);
        failureCount.set(0);
        System.out.println("Circuit reset - Request allowed: " + !circuitOpen.get());
    }

    private static void guardedSuspension() {
        AtomicBoolean ready = new AtomicBoolean(false);
        String result = null;

        System.out.println("Ready: " + ready.get());

        result = "Data loaded";
        ready.set(true);

        System.out.println("After set ready: " + ready.get());

        if (ready.get()) {
            System.out.println("Result: " + result);
        }
    }

    private static void toggleWithCount() {
        AtomicBoolean state = new AtomicBoolean(false);
        AtomicInteger toggleCount = new AtomicInteger(0);

        System.out.println("Initial state: " + state.get());
        System.out.println("Toggle count: " + toggleCount.get());

        boolean oldState = state.getAndSet(!state.get());
        if (oldState != state.get()) {
            toggleCount.incrementAndGet();
        }

        System.out.println("After toggle 1 - State: " + state.get());
        System.out.println("Toggle count: " + toggleCount.get());

        oldState = state.getAndSet(!state.get());
        if (oldState != state.get()) {
            toggleCount.incrementAndGet();
        }

        System.out.println("After toggle 2 - State: " + state.get());
        System.out.println("Toggle count: " + toggleCount.get());
    }

    private static void stateMachine() {
        AtomicBoolean stateA = new AtomicBoolean(true);
        AtomicBoolean stateB = new AtomicBoolean(false);

        System.out.println("State A: " + stateA.get());
        System.out.println("State B: " + stateB.get());

        stateA.set(false);
        stateB.set(true);

        System.out.println("After transition - State A: " + stateA.get());
        System.out.println("After transition - State B: " + stateB.get());

        stateB.set(false);
        stateA.set(true);

        System.out.println("After transition back - State A: " + stateA.get());
        System.out.println("After transition back - State B: " + stateB.get());
    }
}
