package algorithm.jung;

/**
 * @author: Kled
 * @version: Node.java, v0.1 2020-10-14 14:22 Kled
 */
public class Node {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Node(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Node{" +
                "name='" + name + '\'' +
                '}';
    }
}
