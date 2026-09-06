public class Main {

    static Test instance;

    @Override
    protected void finalize() {
        System.out.println("finalize");
        instance = this;
    }

    public static void main(String[] args) throws Exception {

        Test t = new Test();

        t = null;

        System.gc();

        Thread.sleep(1000);

        System.out.println(instance != null);
    }
}