public class RunnableCompleteGuide {

    public static void main(String[] args) throws InterruptedException {

        System.out.println("========== 1. BASIC RUNNABLE ==========");

        Runnable task = new Runnable() {
            @Override
            public void run() {
                System.out.println("Task is running");
            }
        };

        Thread thread = new Thread(task);
        thread.start();

        thread.join();


        System.out.println("\n========== 2. RUNNABLE WITH LAMBDA ==========");

        Runnable lambdaTask = () -> {
            System.out.println("Lambda task is running");
        };

        Thread lambdaThread = new Thread(lambdaTask);
        lambdaThread.start();

        lambdaThread.join();


        System.out.println("\n========== 3. RUNNABLE RUNS ON A DIFFERENT THREAD ==========");

        Runnable threadTask = () -> {
            System.out.println("Inside run()");
            System.out.println("Current thread: "
                    + Thread.currentThread().getName());
        };

        Thread t1 = new Thread(threadTask, "Worker-1");

        System.out.println("Before start(): "
                + Thread.currentThread().getName());

        t1.start();

        t1.join();

        System.out.println("After thread finished: "
                + Thread.currentThread().getName());


        System.out.println("\n========== 4. MULTIPLE RUNNABLES ==========");

        Runnable task1 = () -> {
            for (int i = 1; i <= 5; i++) {
                System.out.println(
                        Thread.currentThread().getName()
                                + " -> Task 1: " + i
                );
            }
        };

        Runnable task2 = () -> {
            for (int i = 1; i <= 5; i++) {
                System.out.println(
                        Thread.currentThread().getName()
                                + " -> Task 2: " + i
                );
            }
        };

        Thread worker1 = new Thread(task1, "Worker-1");
        Thread worker2 = new Thread(task2, "Worker-2");

        worker1.start();
        worker2.start();

        worker1.join();
        worker2.join();


        System.out.println("\n========== 5. SAME RUNNABLE, MULTIPLE THREADS ==========");

        Runnable sameTask = () -> {
            System.out.println(
                    "Running on: "
                            + Thread.currentThread().getName()
            );
        };

        Thread a = new Thread(sameTask, "Thread-A");
        Thread b = new Thread(sameTask, "Thread-B");
        Thread c = new Thread(sameTask, "Thread-C");

        a.start();
        b.start();
        c.start();

        a.join();
        b.join();
        c.join();


        System.out.println("\n========== 6. RUNNABLE WITH SHARED DATA ==========");

        Counter counter = new Counter();

        Runnable incrementTask = () -> {

            for (int i = 0; i < 1000; i++) {
                counter.increment();
            }

        };

        Thread counterThread1 =
                new Thread(incrementTask, "Counter-1");

        Thread counterThread2 =
                new Thread(incrementTask, "Counter-2");

        counterThread1.start();
        counterThread2.start();

        counterThread1.join();
        counterThread2.join();

        System.out.println("Final counter value: "
                + counter.getValue());


        System.out.println("\n========== 7. THREAD SLEEP ==========");

        Runnable sleepingTask = () -> {

            for (int i = 1; i <= 3; i++) {

                System.out.println(
                        Thread.currentThread().getName()
                                + " -> " + i
                );

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();

                    System.out.println(
                            "Thread was interrupted"
                    );

                    return;
                }
            }
        };

        Thread sleepingThread =
                new Thread(sleepingTask, "Sleeping-Thread");

        sleepingThread.start();

        sleepingThread.join();


        System.out.println("\n========== 8. INTERRUPTING A RUNNABLE ==========");

        Runnable interruptibleTask = () -> {

            try {

                for (int i = 1; i <= 10; i++) {

                    System.out.println(
                            "Working... " + i
                    );

                    Thread.sleep(500);
                }

            } catch (InterruptedException e) {

                System.out.println(
                        "Runnable was interrupted!"
                );

                Thread.currentThread().interrupt();
            }
        };

        Thread interruptibleThread =
                new Thread(
                        interruptibleTask,
                        "Interruptible-Thread"
                );

        interruptibleThread.start();

        Thread.sleep(1500);

        interruptibleThread.interrupt();

        interruptibleThread.join();


        System.out.println("\n========== 9. THREAD STATE ==========");

        Runnable stateTask = () -> {

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        };

        Thread stateThread =
                new Thread(stateTask, "State-Thread");

        System.out.println("Before start: "
                + stateThread.getState());

        stateThread.start();

        Thread.sleep(100);

        System.out.println("While running/sleeping: "
                + stateThread.getState());

        stateThread.join();

        System.out.println("After completion: "
                + stateThread.getState());


        System.out.println("\n========== 10. RUNNABLE IS NOT A THREAD ==========");

        Runnable onlyTask = () -> {
            System.out.println(
                    "Runnable contains the work"
            );
        };

        Thread actualThread =
                new Thread(onlyTask);

        actualThread.start();

        actualThread.join();

        System.out.println(
                "Thread executes the Runnable"
        );


        System.out.println("\n========== PROGRAM FINISHED ==========");
    }


    // Shared object
    static class Counter {

        private int value = 0;

        public synchronized void increment() {
            value++;
        }

        public int getValue() {
            return value;
        }
    }
}
