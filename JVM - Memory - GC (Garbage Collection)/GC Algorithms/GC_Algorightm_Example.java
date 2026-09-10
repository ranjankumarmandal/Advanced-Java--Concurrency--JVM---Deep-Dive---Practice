import java.util.*;

public class Main {

    static class ObjectNode {
        int id;
        List<ObjectNode> references = new ArrayList<>();
        boolean marked;

        ObjectNode(int id) {
            this.id = id;
        }

        void addReference(ObjectNode object) {
            references.add(object);
        }

        public String toString() {
            return "Object-" + id;
        }
    }

    static class Heap {
        List<ObjectNode> objects = new ArrayList<>();
        Set<ObjectNode> roots = new HashSet<>();
        int nextId = 1;

        ObjectNode allocate() {
            ObjectNode object = new ObjectNode(nextId++);
            objects.add(object);
            return object;
        }

        void addRoot(ObjectNode object) {
            roots.add(object);
        }

        void removeRoot(ObjectNode object) {
            roots.remove(object);
        }

        void mark() {
            Set<ObjectNode> visited = new HashSet<>();
            Deque<ObjectNode> stack = new ArrayDeque<>(roots);

            while (!stack.isEmpty()) {
                ObjectNode current = stack.pop();

                if (!visited.add(current)) {
                    continue;
                }

                current.marked = true;

                for (ObjectNode reference : current.references) {
                    stack.push(reference);
                }
            }
        }

        void sweep() {
            Iterator<ObjectNode> iterator = objects.iterator();

            while (iterator.hasNext()) {
                ObjectNode object = iterator.next();

                if (!object.marked) {
                    iterator.remove();
                } else {
                    object.marked = false;
                }
            }
        }

        void gc() {
            mark();
            sweep();
        }

        void printHeap() {
            for (ObjectNode object : objects) {
                System.out.print(object + " -> ");

                for (ObjectNode reference : object.references) {
                    System.out.print(reference + " ");
                }

                System.out.println();
            }
        }
    }

    public static void main(String[] args) {
        Heap heap = new Heap();

        ObjectNode a = heap.allocate();
        ObjectNode b = heap.allocate();
        ObjectNode c = heap.allocate();
        ObjectNode d = heap.allocate();
        ObjectNode e = heap.allocate();

        a.addReference(b);
        b.addReference(c);

        d.addReference(e);

        heap.addRoot(a);

        System.out.println("Before GC:");
        heap.printHeap();

        heap.gc();

        System.out.println();
        System.out.println("After GC:");
        heap.printHeap();
    }
}