import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class PsetGraphEC {

    /** Implements a BFS returning a graph */
    public static <V, E> Graph<V, E> bfs(Graph<V, E> g, V source) {
        Graph<V, E> tree = new AdjacencyMapGraph<>();
        tree.insertVertex(source);
        Queue<V> start = new LinkedList<>();
        Set<V> visited = new HashSet<>();
        start.add(source);
        visited.add(source);
        while (!start.isEmpty()) {
            V u = start.remove();
            for (V v : g.outNeighbors(u)) {
                if (!visited.contains(v)) {
                    visited.add(v);
                    start.add(v);
                    tree.insertVertex(v);
                    tree.insertDirected(v, u, g.getLabel(u, v));
                }
            }
        }
        return tree;
    }

    /** Gets the shortest path from a given vertex to the root */
    public static <V, E> List<V> getPath(Graph<V, E> tree, V v) {
        List<V> path = new ArrayList<>();
        path.add(v);
        V curr = v;
        while (tree.outDegree(curr) > 0) {
            for (V vertex : tree.outNeighbors(curr)) {
                path.add(vertex);
                curr = vertex;
            }
        }
        return path;
    }

    /** Finds any vertices not present in the graph */
    public static <V, E> Set<V> missingVertices(Graph<V, E> graph, Graph<V, E> subgraph) {
        Set<V> missing = new HashSet<>();
        for (V v : graph.vertices()) {
            if (!subgraph.hasVertex(v)) {
                missing.add(v);
            }
        }
        return missing;
    }

    public static <V, E> double averageSeparation(Graph<V, E> tree, V root) {
        double[] total = {0};
        averageSeparationHelper(tree, root, 0, total);
        return (total[0]) / ((double) tree.numVertices());
    }

    /** Helper for average separation */
    public static <V, E> void averageSeparationHelper(Graph<V, E> tree, V currentVertex, int pathDistance, double[] total) {
        total[0] += pathDistance;
        if (tree.inDegree(currentVertex) > 0) {
            for (V u : tree.inNeighbors(currentVertex)) {
                averageSeparationHelper(tree, u, pathDistance + 1, total);
            }
        }
    }

    /** Parses the CSV and builds the graph */
    public static Graph<String, Set<String>> buildGraphFromCSV(String csvFile) throws IOException {
        Graph<String, Set<String>> graph = new AdjacencyMapGraph<>();
        Map<String, Set<String>> filmToActors = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(","); // Use comma as delimiter
                if (parts.length < 7) continue; // Ensure proper format

                String actor = parts[0];
                String film = parts[2];

                // Insert actor as vertex
                graph.insertVertex(actor);

                // Record the film-to-actor relationship
                filmToActors.computeIfAbsent(film, k -> new HashSet<>()).add(actor);
            }

            // Create edges between all actors in each film
            for (Map.Entry<String, Set<String>> entry : filmToActors.entrySet()) {
                String film = entry.getKey();
                Set<String> actors = entry.getValue();
                for (String actor1 : actors) {
                    for (String actor2 : actors) {
                        if (!actor1.equals(actor2)) {
                            // Use film name to label the connection between actors
                            graph.insertUndirected(actor1, actor2, new HashSet<>(List.of(film)));
                        }
                    }
                }
            }
        }
        return graph;
    }

    /** Displays function outputs to the console. */
    public static void testFunctions(Graph<String, Set<String>> graph) {
        String kb = "Kevin Bacon";
        if (!graph.hasVertex(kb)) {
            System.out.println("Kevin Bacon not found in the graph.");
            return;
        }
        System.out.println("Original Graph:");
        System.out.println(graph);

        Graph<String, Set<String>> bfsTree = bfs(graph, kb);
        System.out.println("BFS Tree:");
        System.out.println(bfsTree);

        String targetActor = "Frank Morgan"; // Change this to an actor that is likely in your graph
        if (bfsTree.hasVertex(targetActor)) {
            System.out.println("Shortest Path to " + targetActor + ":");
            System.out.println(getPath(bfsTree, targetActor));
        } else {
            System.out.println(targetActor + " not found in the BFS tree from Kevin Bacon.");
        }

        System.out.println("Missing vertices from BFS:");
        System.out.println(missingVertices(graph, bfsTree));

        System.out.println("Average Separation from Kevin Bacon:");
        System.out.println(averageSeparation(bfsTree, kb));
    }

    public static void main(String[] args) {
        try {
            String csvFile = "C:\\Users\\siwat\\IdeaProjects\\CS010\\Problem Sets\\PS-4\\actorfilms.csv";
            Graph<String, Set<String>> graph = buildGraphFromCSV(csvFile);
            testFunctions(graph);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}