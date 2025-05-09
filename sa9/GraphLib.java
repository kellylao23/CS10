import java.util.*;

/**
 * Library for graph analysis
 *
 * @author Chris Bailey-Kellogg, Dartmouth CS 10, Fall 2016
 * @author Tim Pierson, Dartmouth CS10, provided for Winter 2025
 *
 */
public class GraphLib {
    /**
     * Takes a random walk from a vertex, up to a given number of steps
     * So a 0-step path only includes start, while a 1-step path includes start and one of its out-neighbors,
     * and a 2-step path includes start, an out-neighbor, and one of the out-neighbor's out-neighbors
     * Stops earlier if no step can be taken (i.e., reach a vertex with no out-edge)
     * @param g		graph to walk on
     * @param start	initial vertex (assumed to be in graph)
     * @param steps	max number of steps
     * @return		a list of vertices starting with start, each with an edge to the sequentially next in the list;
     * 			    null if start isn't in graph
     */
    public static <V,E> List<V> randomWalk(Graph<V,E> g, V start, int steps) {
        List<V> walk = new ArrayList<V>();
        walk.add(start);
        int totalSteps = 0;
        V curr = start;
        while (g.outDegree(curr) > 0 && totalSteps < steps){
            totalSteps ++;
            int randInt = (int) (Math.random() * g.outDegree(curr));
            Iterator<V> neighbors = g.outNeighbors(curr).iterator();
            for (int i = 0; i <= randInt; i++){
                curr = neighbors.next();
            }
            walk.add(curr);
        }
        return walk;
    }

    /**
     * Orders vertices in decreasing order by their in-degree
     * @param g		graph
     * @return		list of vertices sorted by in-degree, decreasing (i.e., largest at index 0)
     */
    public static <V,E> List<V> verticesByInDegree(Graph<V,E> g) {
        Iterable<V> vertices = g.vertices();
        List<V> sorted = new ArrayList<>();
        for (V v : vertices){
            sorted.add(v);
        }
        sorted.sort((v1, v2) -> {
            int first = g.inDegree(v1); // Get in-degree of v1
            int second = g.inDegree(v2); // Get in-degree of v2
            return Integer.compare(second, first); // Compare in-degrees for descending order
        });
        return sorted;
    }
}
