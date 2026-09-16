import java.util.ArrayList;
import java.util.List;

public class ThreadConcurrencyDemo {

    private static volatile boolean running = true;

    public static void main(String[] args) throws Exception {

        System.out.println("Main Thread: " + Thread.currentThread().getName());
        System.out.println("Main Thread Priority: " + Thread.currentThread().getPriority());

        Thread.currentThread().setName("Main-Thread");

        Thread thread1 = new Thread(() -> {
            for (int i = 1; i <= 5; i++) {
                System.out.println(Thread.currentThread().getName() + " -> " + i);
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    System.out.println(Thread.currentThread().getName() + " interrupted");
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        });

        thread1.setName("Worker-1");
        thread1.setPriority(Thread.NORM_PRIORITY);
        thread1.start();

        Thread thread2 = new Thread(new MyRunnable(), "Worker-2");
        thread2.setPriority(Thread.MAX_PRIORITY);
        thread2.start();

        Thread thread3 = new Thread(() -> {
            for (int i = 1; i <= 5; i++) {
                System.out.println(Thread.currentThread().getName() + " running");
                Thread.yield();
            }
        }, "Yield-Thread");

        thread3.start();

        System.out.println("Thread 1 Alive: " + thread1.isAlive());
        System.out.println("Thread 2 Alive: " + thread2.isAlive());
        System.out.println("Thread 3 Alive: " + thread3.isAlive());

        thread1.join();
        thread2.join();
        thread3.join();

        System.out.println("Basic threads completed");

        Thread sleepingThread = new Thread(() -> {
            try {
                System.out.println("Sleeping thread started");
                Thread.sleep(5000);
                System.out.println("Sleeping thread completed");
            } catch (InterruptedException e) {
                System.out.println("Sleeping thread interrupted");
            }
        }, "Sleeping-Thread");

        sleepingThread.start();

        Thread.sleep(500);
        sleepingThread.interrupt();
        sleepingThread.join();

        Thread daemonThread = new Thread(() -> {
            while (true) {
                System.out.println("Daemon thread running");
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    break;
                }
            }
        }, "Daemon-Thread");

        daemonThread.setDaemon(true);
        daemonThread.start();

        Thread normalThread = new Thread(() -> {
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "Normal-Thread");

        normalThread.start();
        normalThread.join();

        System.out.println("Daemon thread status: " + daemonThread.isDaemon());

        Counter counter = new Counter();

        Thread counterThread1 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                counter.increment();
            }
        }, "Counter-1");

        Thread counterThread2 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                counter.increment();
            }
        }, "Counter-2");

        counterThread1.start();
        counterThread2.start();

        counterThread1.join();
        counterThread2.join();

        System.out.println("Synchronized Counter: " + counter.getValue());

        SharedData sharedData = new SharedData();

        Thread writer = new Thread(() -> {
            for (int i = 1; i <= 5; i++) {
                sharedData.setValue(i);
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }, "Writer");

        Thread reader = new Thread(() -> {
            for (int i = 1; i <= 5; i++) {
                System.out.println("Reader sees: " + sharedData.getValue());
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }, "Reader");

        writer.start();
        reader.start();

        writer.join();
        reader.join();

        BankAccount account = new BankAccount();

        Thread depositThread = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                account.deposit(100);
            }
        }, "Deposit-Thread");

        Thread withdrawThread = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                account.withdraw(50);
            }
        }, "Withdraw-Thread");

        depositThread.start();
        withdrawThread.start();

        depositThread.join();
        withdrawThread.join();

        System.out.println("Final Bank Balance: " + account.getBalance());

        Message message = new Message();

        Thread messageReader = new Thread(() -> {
            synchronized (message) {
                try {
                    System.out.println("Reader waiting for message");
                    message.wait();
                    System.out.println("Reader received: " + message.getMessage());
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }, "Message-Reader");

        Thread messageWriter = new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            synchronized (message) {
                message.setMessage("Hello from Writer");
                message.notify();
            }
        }, "Message-Writer");

        messageReader.start();
        messageWriter.start();

        messageReader.join();
        messageWriter.join();

        Buffer buffer = new Buffer(5);

        Thread producer1 = new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                try {
                    buffer.put(i);
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }, "Producer-1");

        Thread producer2 = new Thread(() -> {
            for (int i = 11; i <= 20; i++) {
                try {
                    buffer.put(i);
                    Thread.sleep(120);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }, "Producer-2");

        Thread consumer1 = new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                try {
                    System.out.println("Consumer-1 received: " + buffer.get());
                    Thread.sleep(150);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }, "Consumer-1");

        Thread consumer2 = new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                try {
                    System.out.println("Consumer-2 received: " + buffer.get());
                    Thread.sleep(150);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }, "Consumer-2");

        producer1.start();
        producer2.start();
        consumer1.start();
        consumer2.start();

        producer1.join();
        producer2.join();
        consumer1.join();
        consumer2.join();

        Thread stateThread = new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "State-Thread");

        System.out.println("State before start: " + stateThread.getState());

        stateThread.start();

        Thread.sleep(100);

        System.out.println("State after start: " + stateThread.getState());

        stateThread.join();

        System.out.println("State after completion: " + stateThread.getState());

        Thread exceptionThread = new Thread(() -> {
            throw new RuntimeException("Example exception from thread");
        }, "Exception-Thread");

        exceptionThread.setUncaughtExceptionHandler((thread, exception) -> {
            System.out.println(
                    "Exception handled from " +
                            thread.getName() +
                            ": " +
                            exception.getMessage()
            );
        });

        exceptionThread.start();
        exceptionThread.join();

        Thread interruptCheckingThread = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                System.out.println("Interrupt checking thread working");
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }

            System.out.println("Interrupt flag detected");
        }, "Interrupt-Checking-Thread");

        interruptCheckingThread.start();

        Thread.sleep(1000);

        interruptCheckingThread.interrupt();

        interruptCheckingThread.join();

        running = true;

        Thread volatileThread = new Thread(() -> {
            while (running) {
                System.out.println("Volatile thread running");
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }

            System.out.println("Volatile thread stopped");
        }, "Volatile-Thread");

        volatileThread.start();

        Thread.sleep(1000);

        running = false;

        volatileThread.join();

        List<Thread> workers = new ArrayList<>();

        for (int i = 1; i <= 5; i++) {
            int id = i;

            Thread worker = new Thread(() -> {
                System.out.println(
                        Thread.currentThread().getName() +
                                " started with ID " +
                                id
                );

                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }

                System.out.println(
                        Thread.currentThread().getName() +
                                " finished"
                );
            }, "Pool-Like-Worker-" + i);

            workers.add(worker);
            worker.start();
        }

        for (Thread worker : workers) {
            worker.join();
        }

        Thread priorityThread = new Thread(() -> {
            System.out.println(
                    Thread.currentThread().getName() +
                            " priority = " +
                            Thread.currentThread().getPriority()
            );
        }, "Priority-Thread");

        priorityThread.setPriority(Thread.MIN_PRIORITY);
        priorityThread.start();
        priorityThread.join();

        Thread directRunThread = new Thread(() -> {
            System.out.println(
                    "Current thread executing run(): " +
                            Thread.currentThread().getName()
            );
        }, "Direct-Run-Thread");

        directRunThread.run();

        System.out.println("Direct run completed without creating a new thread");

        System.out.println("Main thread completed");
    }

    static class MyRunnable implements Runnable {

        @Override
        public void run() {
            for (int i = 1; i <= 5; i++) {
                System.out.println(
                        Thread.currentThread().getName() +
                                " -> Runnable " +
                                i
                );

                try {
                    Thread.sleep(250);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }
    }

    static class Counter {

        private int value;

        public synchronized void increment() {
            value++;
        }

        public synchronized int getValue() {
            return value;
        }
    }

    static class SharedData {

        private int value;

        public synchronized void setValue(int value) {
            this.value = value;
            System.out.println(
                    Thread.currentThread().getName() +
                            " set value to " +
                            value
            );
        }

        public synchronized int getValue() {
            return value;
        }
    }

    static class BankAccount {

        private double balance = 1000;

        public synchronized void deposit(double amount) {
            balance += amount;

            System.out.println(
                    Thread.currentThread().getName() +
                            " deposited " +
                            amount +
                            ", balance = " +
                            balance
            );
        }

        public synchronized void withdraw(double amount) {
            if (balance >= amount) {
                balance -= amount;

                System.out.println(
                        Thread.currentThread().getName() +
                                " withdrew " +
                                amount +
                                ", balance = " +
                                balance
                );
            } else {
                System.out.println("Insufficient balance");
            }
        }

        public synchronized double getBalance() {
            return balance;
        }
    }

    static class Message {

        private String message;

        public void setMessage(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }

    static class Buffer {

        private final List<Integer> queue = new ArrayList<>();
        private final int capacity;

        public Buffer(int capacity) {
            this.capacity = capacity;
        }

        public synchronized void put(int value)
                throws InterruptedException {

            while (queue.size() == capacity) {
                wait();
            }

            queue.add(value);

            System.out.println(
                    Thread.currentThread().getName() +
                            " produced: " +
                            value
            );

            notifyAll();
        }

        public synchronized int get()
                throws InterruptedException {

            while (queue.isEmpty()) {
                wait();
            }

            int value = queue.remove(0);

            notifyAll();

            return value;
        }
    }
}