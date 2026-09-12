interface Service {

    int MAX_RETRIES = 3;

    void start();

    void stop();

    String getName();

    default void restart() {
        stop();
        start();
    }

    static void printInfo() {
        System.out.println("Service interface");
    }

    private void log(String message) {
        System.out.println("[LOG] " + message);
    }
}

class ServiceImpl implements Service {

    private boolean running;

    @Override
    public void start() {
        running = true;
        System.out.println(getName() + " started");
    }

    @Override
    public void stop() {
        running = false;
        System.out.println(getName() + " stopped");
    }

    @Override
    public String getName() {
        return "Payment Service";
    }

    public boolean isRunning() {
        return running;
    }
}

class PaymentService implements Service {

    @Override
    public void start() {
        System.out.println("Payment service started");
    }

    @Override
    public void stop() {
        System.out.println("Payment service stopped");
    }

    @Override
    public String getName() {
        return "Payment Service";
    }
}

class EmailService implements Service {

    @Override
    public void start() {
        System.out.println("Email service started");
    }

    @Override
    public void stop() {
        System.out.println("Email service stopped");
    }

    @Override
    public String getName() {
        return "Email Service";
    }
}

class NotificationService implements Service {

    @Override
    public void start() {
        System.out.println("Notification service started");
    }

    @Override
    public void stop() {
        System.out.println("Notification service stopped");
    }

    @Override
    public String getName() {
        return "Notification Service";
    }
}

interface Monitorable {

    void monitor();

    default void healthCheck() {
        System.out.println("Default health check");
    }
}

class AdvancedServiceImpl implements Service, Monitorable {

    private boolean running;

    @Override
    public void start() {
        running = true;
        System.out.println("Advanced service started");
    }

    @Override
    public void stop() {
        running = false;
        System.out.println("Advanced service stopped");
    }

    @Override
    public String getName() {
        return "Advanced Service";
    }

    @Override
    public void monitor() {
        System.out.println("Monitoring advanced service");
    }

    @Override
    public void healthCheck() {
        System.out.println("Custom health check");
    }
}

interface BasicService {
    void execute();
}

interface AdvancedService extends BasicService {
    void restart();
}

class AdvancedServiceImplementation implements AdvancedService {

    @Override
    public void execute() {
        System.out.println("Executing advanced service");
    }

    @Override
    public void restart() {
        System.out.println("Restarting advanced service");
    }
}

interface Printable {
    void print();
}

interface Loggable {
    void log();
}

class PrintLogService implements Printable, Loggable {

    @Override
    public void print() {
        System.out.println("Printing");
    }

    @Override
    public void log() {
        System.out.println("Logging");
    }
}

interface InterfaceA {

    default void show() {
        System.out.println("Interface A");
    }
}

interface InterfaceB {

    default void show() {
        System.out.println("Interface B");
    }
}

class MultipleDefaultService implements InterfaceA, InterfaceB {

    @Override
    public void show() {
        System.out.println("Service implementation");
    }

    public void showA() {
        InterfaceA.super.show();
    }

    public void showB() {
        InterfaceB.super.show();
    }
}

class ServiceFactory {

    public static Service createService() {
        return new ServiceImpl();
    }
}

class Application {

    public void run(Service service) {
        service.start();
        System.out.println(service.getName());
        service.stop();
    }
}

public class Main {

    public static void main(String[] args) {

        System.out.println("===== 1. BASIC INTERFACE =====");

        Service service = new ServiceImpl();

        service.start();
        service.stop();


        System.out.println("\n===== 2. INTERFACE CONSTANT =====");

        System.out.println(Service.MAX_RETRIES);


        System.out.println("\n===== 3. PARAMETERS AND RETURN VALUES =====");

        CalculatorService calculator = new CalculatorService();

        System.out.println(calculator.add(10, 20));
        System.out.println(calculator.multiply(5, 6));


        System.out.println("\n===== 4. POLYMORPHISM =====");

        Service payment = new PaymentService();
        Service email = new EmailService();
        Service notification = new NotificationService();

        payment.start();
        email.start();
        notification.start();


        System.out.println("\n===== 5. DEFAULT METHOD =====");

        Service defaultService = new ServiceImpl();

        defaultService.start();
        defaultService.restart();
        defaultService.stop();


        System.out.println("\n===== 6. STATIC METHOD =====");

        Service.printInfo();


        System.out.println("\n===== 7. MULTIPLE INTERFACES =====");

        AdvancedServiceImpl advanced = new AdvancedServiceImpl();

        advanced.start();
        advanced.monitor();
        advanced.healthCheck();
        advanced.stop();


        System.out.println("\n===== 8. INTERFACE EXTENDS INTERFACE =====");

        AdvancedServiceImplementation advancedImplementation =
                new AdvancedServiceImplementation();

        advancedImplementation.execute();
        advancedImplementation.restart();


        System.out.println("\n===== 9. CLASS IMPLEMENTS MULTIPLE INTERFACES =====");

        PrintLogService printLogService = new PrintLogService();

        printLogService.print();
        printLogService.log();


        System.out.println("\n===== 10. DEFAULT METHOD CONFLICT =====");

        MultipleDefaultService multipleDefaultService =
                new MultipleDefaultService();

        multipleDefaultService.show();
        multipleDefaultService.showA();
        multipleDefaultService.showB();


        System.out.println("\n===== 11. INTERFACE AS METHOD PARAMETER =====");

        Application application = new Application();

        application.run(new PaymentService());
        application.run(new EmailService());


        System.out.println("\n===== 12. INTERFACE AS RETURN TYPE =====");

        Service factoryService = ServiceFactory.createService();

        factoryService.start();
        factoryService.stop();


        System.out.println("\n===== 13. ANONYMOUS CLASS =====");

        Service anonymousService = new Service() {

            @Override
            public void start() {
                System.out.println("Anonymous service started");
            }

            @Override
            public void stop() {
                System.out.println("Anonymous service stopped");
            }

            @Override
            public String getName() {
                return "Anonymous Service";
            }
        };

        anonymousService.start();
        System.out.println(anonymousService.getName());
        anonymousService.stop();


        System.out.println("\n===== 14. INTERFACE REFERENCE VS CLASS REFERENCE =====");

        Service interfaceReference = new ServiceImpl();

        interfaceReference.start();

        ServiceImpl classReference = new ServiceImpl();

        classReference.start();

        System.out.println(classReference.isRunning());


        System.out.println("\n===== 15. PRIVATE METHOD IN INTERFACE =====");

        Service privateMethodService = new ServiceImpl();

        privateMethodService.start();
        privateMethodService.stop();


        System.out.println("\n===== COMPLETE =====");
    }
}

class CalculatorService {

    public int add(int a, int b) {
        return a + b;
    }

    public int multiply(int a, int b) {
        return a * b;
    }
}
