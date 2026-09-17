import java.util.ArrayList;
import java.util.List;

class Order {
    private int orderId;
    private String customerName;
    private double amount;

    public Order(int orderId, String customerName, double amount) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.amount = amount;
    }

    public int getOrderId() {
        return orderId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public double getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return "Order ID: " + orderId +
                ", Customer: " + customerName +
                ", Amount: " + amount;
    }
}

class OrderProcessor extends Thread {

    private Order order;

    public OrderProcessor(Order order) {
        this.order = order;
    }

    @Override
    public void run() {
        System.out.println(
                Thread.currentThread().getName() +
                        " started processing " +
                        order
        );

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println(
                Thread.currentThread().getName() +
                        " completed order " +
                        order.getOrderId()
        );
    }
}

class PaymentProcessor implements Runnable {

    private Order order;

    public PaymentProcessor(Order order) {
        this.order = order;
    }

    @Override
    public void run() {
        System.out.println(
                Thread.currentThread().getName() +
                        " started payment for Order " +
                        order.getOrderId()
        );

        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println(
                Thread.currentThread().getName() +
                        " payment completed for Order " +
                        order.getOrderId()
        );
    }
}

class InventoryManager implements Runnable {

    private Order order;

    public InventoryManager(Order order) {
        this.order = order;
    }

    @Override
    public void run() {
        System.out.println(
                Thread.currentThread().getName() +
                        " checking inventory for Order " +
                        order.getOrderId()
        );

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println(
                Thread.currentThread().getName() +
                        " inventory reserved for Order " +
                        order.getOrderId()
        );
    }
}

class SalesCounter {

    private int totalOrders;
    private double totalRevenue;

    public synchronized void processSale(Order order) {
        totalOrders++;
        totalRevenue += order.getAmount();

        System.out.println(
                Thread.currentThread().getName() +
                        " recorded sale: Order " +
                        order.getOrderId() +
                        ", Amount: " +
                        order.getAmount()
        );
    }

    public synchronized void printSummary() {
        System.out.println();
        System.out.println("========== SALES SUMMARY ==========");
        System.out.println("Total Orders  : " + totalOrders);
        System.out.println("Total Revenue : " + totalRevenue);
        System.out.println("===================================");
    }
}

class SalesTask implements Runnable {

    private Order order;
    private SalesCounter salesCounter;

    public SalesTask(Order order, SalesCounter salesCounter) {
        this.order = order;
        this.salesCounter = salesCounter;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(800);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        salesCounter.processSale(order);
    }
}

public class BusinessConcurrencyDemo {

    public static void main(String[] args) {

        List<Order> orders = new ArrayList<>();

        orders.add(new Order(101, "Rahul", 2500));
        orders.add(new Order(102, "Priya", 4500));
        orders.add(new Order(103, "Amit", 3200));
        orders.add(new Order(104, "Sneha", 1800));
        orders.add(new Order(105, "Vikram", 5200));

        SalesCounter salesCounter = new SalesCounter();

        Thread orderThread1 =
                new OrderProcessor(orders.get(0));

        Thread orderThread2 =
                new OrderProcessor(orders.get(1));

        Thread paymentThread1 =
                new Thread(
                        new PaymentProcessor(orders.get(0)),
                        "Payment-Thread-1"
                );

        Thread paymentThread2 =
                new Thread(
                        new PaymentProcessor(orders.get(1)),
                        "Payment-Thread-2"
                );

        Thread inventoryThread1 =
                new Thread(
                        new InventoryManager(orders.get(0)),
                        "Inventory-Thread-1"
                );

        Thread inventoryThread2 =
                new Thread(
                        new InventoryManager(orders.get(1)),
                        "Inventory-Thread-2"
                );

        List<Thread> salesThreads = new ArrayList<>();

        for (Order order : orders) {
            Thread thread = new Thread(
                    new SalesTask(order, salesCounter),
                    "Sales-Thread-" + order.getOrderId()
            );

            salesThreads.add(thread);
        }

        System.out.println("Business process started");
        System.out.println();

        orderThread1.start();
        orderThread2.start();

        paymentThread1.start();
        paymentThread2.start();

        inventoryThread1.start();
        inventoryThread2.start();

        for (Thread thread : salesThreads) {
            thread.start();
        }

        try {
            orderThread1.join();
            orderThread2.join();

            paymentThread1.join();
            paymentThread2.join();

            inventoryThread1.join();
            inventoryThread2.join();

            for (Thread thread : salesThreads) {
                thread.join();
            }

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        salesCounter.printSummary();

        System.out.println();
        System.out.println("All business operations completed");
    }
}