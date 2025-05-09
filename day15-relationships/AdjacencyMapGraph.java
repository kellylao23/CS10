import java.util.HashMap;
import java.util.Map;

/**
 * Adjacency Map implementation of the Graph interface
 * Edge labels are stored in nested maps: { v1 -> { v2 -> edge } }
 * Inspired by and loosely based on Goodrich & Tamassia
 *
 * @author Chris Bailey-Kellogg, Dartmouth CS 10, Spring 2015
 * @author Tim Pierson, Dartmouth CS10, provided for Winter 2025
 */

public class AdjacencyMapGraph<V,E> implements Graph<V,E> {
    protected Map<V, Map<V, E>> out;		// from v1 to v2: { v1 -> { v2 -> edge } }
    protected Map<V, Map<V, E>> in;		// to v1 from v2: { v1 -> { v2 -> edge } }

    /**
     * Default constructor, creating an empty graph
     */
    public AdjacencyMapGraph() {
        in = new HashMap<V, Map<V, E>>();
        out = new HashMap<V, Map<V, E>>();
    }

    public int numVertices() {
        return out.keySet().size();
    }

    public int numEdges() {
        // Don't actually keep an edge list around, so compute this on the fly
        int n = 0;
        for (V v : vertices()) n += outDegree(v);
        return n;
    }

    public Iterable<V> vertices() {
        return out.keySet();
    }

    public boolean hasVertex(V v) {
        return out.keySet().contains(v);
    }

    public int outDegree(V v) {
        return out.get(v).size();
    }

    public int inDegree(V v) {
        return in.get(v).size();
    }

    public Iterable<V> outNeighbors(V v) {
        return out.get(v).keySet();
    }

    public Iterable<V> inNeighbors(V v) {
        return in.get(v).keySet();
    }

    public boolean hasEdge(V u, V v) {
        return out.get(u).containsKey(v);
    }

    public E getLabel(V u, V v) {
        return out.get(u).get(v);
    }

    public void insertVertex(V v) {
        if (!out.keySet().contains(v)) {
            out.put(v, new HashMap<V, E>());	// edges from v
            in.put(v, new HashMap<V, E>());		// edges to v
        }
    }

    public void insertDirected(V u, V v, E e) {
        out.get(u).put(v, e); //out from u to v
        in.get(v).put(u, e); //reversed for in, from v to u
    }

    public void insertUndirected(V u, V v, E e) {
        // insert in both directions
        insertDirected(u, v, e);
        insertDirected(v, u, e);
    }

    public void removeVertex(V v) {
        if (!out.keySet().contains(v)) return;
        //remove all edges to and from v
        // remove all in edges to v
        for (V u : inNeighbors(v)) { // u has an out edge to v
            out.get(u).remove(v);
        }
        //remove all out edges from v
        for (V w : outNeighbors(v)) { // w has an in edge from v
            in.get(w).remove(v);
        }
        //remove node from outer map
        in.remove(v);
        out.remove(v);
    }

    public void removeDirected(V u, V v) {
        //remove edge from u to v in both in and out maps
        in.get(v).remove(u); //remove from in to v
        out.get(u).remove(v);  //remove from out of u
    }

    public void removeUndirected(V u, V v) {
        // remove in both directions
        removeDirected(u, v);
        removeDirected(v, u);
    }

    /**
     * Returns a string representation of the vertex and edge lists.
     */
    public String toString() {
        return "Vertices: " + out.keySet().toString() + "\nOut edges: " + out.toString();
    }
}
