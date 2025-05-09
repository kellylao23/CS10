import java.util.List;

public class GraphLibTest extends GraphLib{
    public static void main(String [] args) {
        Graph<String, String> relationships = new AdjacencyMapGraph<String, String>();

        relationships.insertVertex("A");
        relationships.insertVertex("B");
        relationships.insertVertex("C");
        relationships.insertVertex("D");
        relationships.insertVertex("E");
        relationships.insertDirected("A", "B", "follower");
        relationships.insertDirected("A", "C", "follower");
        relationships.insertDirected("A", "D", "follower");
        relationships.insertDirected("A", "E", "follower");
        relationships.insertDirected("B", "A", "follower");
        relationships.insertDirected("B", "C", "follower");
        relationships.insertDirected("C", "A", "follower");
        relationships.insertDirected("C", "B", "follower");
        relationships.insertDirected("C", "D", "follower");
        relationships.insertDirected("E", "B", "follower");
        relationships.insertDirected("E", "C", "follower");

        System.out.println("The graph:");
        System.out.println(relationships);

        List<String> walk = GraphLib.randomWalk(relationships, "B", 4);
        List<String> walk1 = GraphLib.randomWalk(relationships, "A", 10);
        List<String> walk2 = GraphLib.randomWalk(relationships, "E", 17);
        List<String> sorted = GraphLib.verticesByInDegree(relationships);
        System.out.println(walk);
        System.out.println(walk1);
        System.out.println(walk2);
        System.out.println(sorted);
    }
}
