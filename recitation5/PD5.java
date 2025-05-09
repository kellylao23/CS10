import java.util.*;
/**
 * Provided code for PD-5
 * Find all neighbors <= k edges from each graph vertex
 * Store results in a Map with a vertex as key and a Set of neighboring verticies
 * as the Map entry's value
 * TODO: complete kNearestNeighbors method
 *
 * @author Tim Pierson, Dartmouth CS10, Fall 2024
 */
public class PD5<V,E> {
    AdjacencyMapGraph<V,E> G;
    /**
     * Helper class to keep track of vertex and its distance from start
     */
    private class VertexDistance<V> {
        V vertex; //vertex in graph
        int distance; //number of edges away from start
        public VertexDistance(V vertex, int distance) {
            this.vertex = vertex;
            this.distance = distance;
        }
    }
    /**
     * Constructor
     */
    public PD5() {
        G = new AdjacencyMapGraph<V,E>();
    }
    /**
     * Getter for graph
     * @return graph G
     */
    public AdjacencyMapGraph<V,E> getGraph() {
        return G;
    }
    /**
     * Find all neighbors <= k edges away from each node
     * @param k - number of steps away from start node
     * @return - Map with a vertex as key and a Set of neighbors <= k edges away
    from the vertex
     * @throws Exception - invalid k
     */
    public Map<V,Set<V>> kNearestNeighbors(int k) throws Exception {
//safety checks
        if (G == null || k < 0) {
            throw new Exception("Invalid parameters");
        }
//store neighbors in map with vertex as key and set of neighbors as value
        Map<V, Set<V>> neighbors = new HashMap<V, Set<V>>();
//loop over each vertex in graph G
        for (V start : G.vertices()) {
            Queue<VertexDistance<V>> queue = new LinkedList<VertexDistance<V>>(); //queue to implement BFS
            int dist = 0; //distance from start
//TODO: find k nearest neighbors
//Do a modified BFS
            Set<V> visited = new HashSet<>();
            Set<V> result = new HashSet<>();

            queue.add(new VertexDistance<>(start, 0));
            visited.add(start);

            while (!queue.isEmpty()) {
                VertexDistance<V> current = queue.poll();
                V currentVertex = current.vertex;
                dist = current.distance;

                if (dist <= k && dist > 0) {
                    result.add(currentVertex);
                }

                if (dist < k) {
                    for (V neighbor : G.outNeighbors(currentVertex)) {
                        if (!visited.contains(neighbor)) {
                            visited.add(neighbor);
                            queue.add(new VertexDistance<>(neighbor, dist + 1));
                        }
                    }
                }
            }
            neighbors.put(start, result);
        }
        return neighbors;
    }
    public static void main(String[] args) throws Exception {
        PD5<String,String> neighbors = new PD5<>();
//get graph and add vertices and edges from in-class example
        AdjacencyMapGraph<String,String> g = neighbors.getGraph();
        g.insertVertex("Alice");
        g.insertVertex("Bob");
        g.insertVertex("Charlie");
        g.insertVertex("Dartmouth");
        g.insertVertex("Elvis");
        g.insertDirected("Alice", "Dartmouth", "follower");
        g.insertDirected("Bob", "Dartmouth", "follower");
        g.insertDirected("Charlie", "Dartmouth", "follower");
        g.insertDirected("Elvis", "Dartmouth", "follower");
        g.insertUndirected("Alice", "Bob", "friend"); // symmetric, undirected edge
        g.insertDirected("Alice", "Elvis", "friend"); // not symmetric, directed edge!
        g.insertDirected("Charlie", "Elvis", "follower");
//find nearest neighbors
        Map<String,Set<String>> nearest = neighbors.kNearestNeighbors(2);
        System.out.println(nearest);
    }
}
