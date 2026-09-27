import java.util.concurrent.atomic.AtomicLong;

public class AtomicLongExample {
    public static void main(String[] args) {
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
}
