// finalize keyword was used for gc, was deprecated in java 9 and was removed in java 18.
class Can_Finalize_Run_Twice {
    static Test obj;

    @Override
    protected void finalize() {
        System.out.println("finalize()");
        obj = this;
    }

    public static void main(String[] args) throws Exception {
        Test t = new Test();

        t = null;
        System.gc();

        Thread.sleep(1000);

        System.out.println(obj);

        obj = null;
        System.gc();

        Thread.sleep(1000);
    }
}