package algorithm.jung;

import algorithm.jung.transformer.LossTransformer;
import algorithm.jung.transformer.PriorityTransformer;
import edu.uci.ics.jung.algorithms.shortestpath.DijkstraShortestPath;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author: Kled
 * @version: TestJung2.java, v0.1 2020-10-14 10:27 Kled
 */

public class TestJung2 {

//    public static void main(String[] args) {
//        MyGraph<Node, Link> graph = new MyGraph<>();
//
//        Node nodeA = new Node("nodeA");
//        Node nodeB = new Node("nodeB");
//        Node nodeC = new Node("nodeC");
//
//        Link linkA2B = new Link("linkA2B");
//        Link linkB2C = new Link("linkB2C");
//        Link linkA2C = new Link("linkA2C");
//
//        graph.addEdge(linkA2B, nodeA, nodeB);
//        graph.addEdge(linkB2C, nodeB, nodeC);
//        graph.addEdge(linkA2C, nodeA, nodeC);
//
//        DijkstraShortestPath<Node, Link> dijkstraShortestPath1 = new DijkstraShortestPath<>(graph);
//
//        List<Link> linkList1 = dijkstraShortestPath1.getPath(nodeA, nodeC);
//        System.out.println(linkList1);
//
//        linkA2B.setPriority(1);
//        linkB2C.setPriority(1);
//        linkA2C.setPriority(3);
//        DijkstraShortestPath<Node, Link> dijkstraShortestPath2 = new DijkstraShortestPath<>(graph, new PriorityTransformer());
//        List<Link> linkList2 = dijkstraShortestPath2.getPath(nodeA, nodeC);
//        System.out.println(linkList2);
//    }

    public static void main(String[] args) {
        test1();
    }

    public static void test1(){
        // A-B-C vs A-C
        MyGraph<Node, Link> graph = new MyGraph<>();

        Node nodeA = new Node("nodeA");
        Node nodeB = new Node("nodeB");
        Node nodeC = new Node("nodeC");

        Link linkA2B = new Link("linkA2B");
        Link linkB2C = new Link("linkB2C");
        Link linkA2C = new Link("linkA2C");
        Link leasedLinkA2C = new Link("leasedLinkA2C");

        graph.addEdge(linkA2B, nodeA, nodeB);
        graph.addEdge(linkB2C, nodeB, nodeC);
        graph.addEdge(linkA2C, nodeA, nodeC);
        graph.addEdge(leasedLinkA2C, nodeA, nodeC);

        MyDijkstraShortestPath<Node, Link> dijkstraShortestPath1 = new MyDijkstraShortestPath<>(graph);

        List<Link> linkList1 = dijkstraShortestPath1.getPath(nodeA, nodeC);
        System.out.println(linkList1);

        linkA2B.setPriority(1);
        linkB2C.setPriority(1);
        linkA2C.setPriority(3);
        leasedLinkA2C.setPriority(1);
        MyDijkstraShortestPath<Node, Link> dijkstraShortestPath2 = new MyDijkstraShortestPath<>(graph, new PriorityTransformer());
        List<Link> linkList2 = dijkstraShortestPath2.getPath(nodeA, nodeC);
        System.out.println(linkList2);
    }

    public static void test2(){
        MyGraph<Node, Link> graph = new MyGraph<>();

        Node nodeA = new Node("nodeA");
        Node nodeB = new Node("nodeB");
        Node nodeC = new Node("nodeC");
        Node nodeD = new Node("nodeD");

        Link linkA2B = new Link("linkA2B");
        Link linkB2C = new Link("linkB2C");
        Link linkC2D = new Link("linkC2D");
        Link linkA2C = new Link("linkA2C");
        Link linkB2D = new Link("linkB2D");

        graph.addEdge(linkA2B, nodeA, nodeB);
        graph.addEdge(linkB2C, nodeB, nodeC);
        graph.addEdge(linkA2C, nodeA, nodeC);
        graph.addEdge(linkC2D, nodeC, nodeD);
        graph.addEdge(linkB2D, nodeB, nodeD);

        MyDijkstraShortestPath<Node, Link> dijkstraShortestPath1 = new MyDijkstraShortestPath<>(graph);

        List<Link> linkList1 = dijkstraShortestPath1.getPath(nodeA, nodeD);
        System.out.println(linkList1);

        linkA2B.setPriority(3);
        linkB2C.setPriority(1);
        linkA2C.setPriority(1);
        linkC2D.setPriority(3);
        linkB2D.setPriority(1);
        MyDijkstraShortestPath<Node, Link> dijkstraShortestPath2 = new MyDijkstraShortestPath<>(graph, new PriorityTransformer());
        List<Link> linkList2 = dijkstraShortestPath2.getPath(nodeA, nodeD);
        System.out.println(linkList2);
    }

    public static void test3(){
        MyGraph<Node, Link> graph = new MyGraph<>();

        Node nodeA = new Node("nodeA");
        Node nodeB = new Node("nodeB");
        Node nodeC = new Node("nodeC");

        Link linkA2B = new Link("linkA2B");
        Link linkB2C = new Link("linkB2C");
        Link linkA2C = new Link("linkA2C");

        graph.addEdge(linkA2B, nodeA, nodeB);
        graph.addEdge(linkB2C, nodeB, nodeC);
        graph.addEdge(linkA2C, nodeA, nodeC);

        linkA2B.setLoss(new BigDecimal("0.8"));
        linkB2C.setLoss(new BigDecimal("0.8"));
        linkA2C.setLoss(new BigDecimal("0.98"));
        MyDijkstraShortestPath<Node, Link> dijkstraShortestPath1 = new MyDijkstraShortestPath<>(graph, new LossTransformer());
        List<Link> linkList1 = dijkstraShortestPath1.getPath(nodeA, nodeC);
        System.out.println(linkList1);
    }
}
