mport java.util.PriorityQueue;

public class PriorityQueueExample {
    public static void main(String[] args) {
        PriorityQueue<Integer> emergencyLevels = new PriorityQueue<>();

        emergencyLevels.offer(5);
        emergencyLevels.offer(2);
        emergencyLevels.offer(1);
        emergencyLevels.offer(4);
        emergencyLevels.offer(3);

        while (!emergencyLevels.isEmpty()) {
            System.out.println("Processing Priority: " + emergencyLevels.poll());
        }
    }
}