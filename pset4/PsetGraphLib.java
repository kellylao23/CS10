import java.util.*;

public class PsetGraphLib {
    /**
     *implements a bfs returning a graph
     * @param g
     * @param source
     * @return
     * @param <V>
     * @param <E>
     */
    public static <V,E> Graph<V,E> bfs(Graph<V,E> g, V source){
        Graph<V, E> tree = new AdjacencyMapGraph<>();
        tree.insertVertex(source);

        Queue<V> start = new LinkedList<>();
        Set<V> visited = new HashSet<>();

        start.add(source);
        visited.add(source);

        while(!start.isEmpty()){
            V u = start.remove();
            for (V v: g.outNeighbors(u)){
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

    /**
     * gets the shortest path from a given vertice to the center of the graph parameter
     * @param tree
     * @param v
     * @return
     * @param <V>
     * @param <E>
     */
    public static <V,E> List<V> getPath(Graph<V,E> tree, V v){
        List<V> path = new ArrayList<>();
        path.add(v);
        V curr = v;

        while (tree.outDegree(curr) > 0){
            for (V vertex : tree.outNeighbors(curr)){
                path.add(vertex);
                curr = vertex;
            }
        }
        return path;
    }

    /**
     * find any verticies not present in the graph
     * @param graph
     * @param subgraph
     * @return
     * @param <V>
     * @param <E>
     */
    public static <V,E> Set<V> missingVertices(Graph<V,E> graph, Graph<V,E> subgraph){
        Set<V> missing = new HashSet<>();

        for (V v: graph.vertices()){
            if (!subgraph.hasVertex(v)){
                missing.add(v);
            }
        }
        return missing;
    }

    public static <V,E> double averageSeparation(Graph<V,E> tree, V root){

        double total[] = {0}; //asked chatgpt for how to create variable so recursion method updates the value properly

        averageSeparationHelper(tree, root, 0, total);

        return ((total[0]))/((double)tree.numVertices());

    }

    /**
     * For each node in the tree, this helper function updates the sum based on the vertex's separation from the root
     * and calls the itself on the node's children
     * @param tree
     * @param currentVertex
     * @param pathDistance
     * @param total
     * @param <V>
     * @param <E>
     */
    public static <V,E> void averageSeparationHelper(Graph<V,E> tree, V currentVertex, int pathDistance, double[] total){

        total[0] += pathDistance;

        if (tree.inDegree(currentVertex) > 0){
            for (V u: tree.inNeighbors(currentVertex)){
                averageSeparationHelper(tree, u, pathDistance+1, total);
            }
        }
    }

    /**
     * Manually initializes a map of actors and movies based on PS4 doc and uses it to display
     * function outputs to the console.
     */
    public static void testFunctions(){
        Graph<String, Set<String>> testGraph = new AdjacencyMapGraph<String, Set<String>>();

        String kb = "Kevin Bacon";
        String a = "Alice";
        String c = "Charlie";
        String b = "Bob";
        String d = "Dartmouth";
        String n = "Nobody";
        String nf = "Nobody's Friend";

        Set<String> kba = new HashSet<String>();
        kba.add("A Movie");
        kba.add("E Movie");

        Set<String> kbb = new HashSet<String>();
        kbb.add("A Movie");

        Set<String> ab = new HashSet<String>();
        ab.add("A Movie");

        Set<String> ac = new HashSet<String>();
        ac.add("D Movie");

        Set<String> bc = new HashSet<String>();
        bc.add("C Movie");

        Set<String> cd = new HashSet<String>();
        cd.add("B Movie");

        Set<String> nnf = new HashSet<String>();
        nnf.add("F Movie");

        testGraph.insertVertex(kb);
        testGraph.insertVertex(a);
        testGraph.insertVertex(c);
        testGraph.insertVertex(b);
        testGraph.insertVertex(d);
        testGraph.insertVertex(n);
        testGraph.insertVertex(nf);

        testGraph.insertUndirected(kb, a, kba);
        testGraph.insertUndirected(kb, b, kbb);
        testGraph.insertUndirected(a, b, ab);
        testGraph.insertUndirected(a, c, ac);
        testGraph.insertUndirected(b, c, bc);
        testGraph.insertUndirected(c, d, cd);
        testGraph.insertUndirected(n, nf, nnf);

        System.out.println((testGraph));
        System.out.println(bfs(testGraph, kb));
        System.out.println(getPath(bfs(testGraph, kb), c));
        System.out.println(missingVertices(testGraph, bfs(testGraph, kb)));
        System.out.println(averageSeparation(bfs(testGraph, kb), kb));

    }

    public static void main(String[] args){

        testFunctions();
    }

    /**
     * C:\Users\siwat\.jdks\openjdk-23.0.1\bin\java.exe "-javaagent:C:\Program Files\JetBrains\IntelliJ IDEA Community Edition 2024.3\lib\idea_rt.jar=56581:C:\Program Files\JetBrains\IntelliJ IDEA Community Edition 2024.3\bin" -Dfile.encoding=UTF-8 -Dsun.stdout.encoding=UTF-8 -Dsun.stderr.encoding=UTF-8 -classpath C:\Users\siwat\IdeaProjects\lib\libs\opencv.jar;C:\Users\siwat\IdeaProjects\lib\libs\openblas-windows-x86.jar;C:\Users\siwat\IdeaProjects\lib\libs\openblas-windows-x86_64.jar;C:\Users\siwat\IdeaProjects\lib\libs\javacv.jar;C:\Users\siwat\IdeaProjects\lib\libs\opencv-windows-x86.jar;C:\Users\siwat\IdeaProjects\lib\libs\opencv-windows-x86_64.jar;C:\Users\siwat\IdeaProjects\lib\libs\json-simple-1.1.1.jar;C:\Users\siwat\IdeaProjects\lib\libs\net-datastructures-4-0.jar;C:\Users\siwat\IdeaProjects\CS010\out\production\CS010 PsetGraphLib
     * Vertices: [Bob, Dartmouth, Alice, Charlie, Nobody, Nobody's Friend, Kevin Bacon]
     * Out edges: {Bob={Alice=[A Movie], Charlie=[C Movie], Kevin Bacon=[A Movie]}, Dartmouth={Charlie=[B Movie]}, Alice={Bob=[A Movie], Charlie=[D Movie], Kevin Bacon=[A Movie, E Movie]}, Charlie={Bob=[C Movie], Dartmouth=[B Movie], Alice=[D Movie]}, Nobody={Nobody's Friend=[F Movie]}, Nobody's Friend={Nobody=[F Movie]}, Kevin Bacon={Bob=[A Movie], Alice=[A Movie, E Movie]}}
     * Vertices: [Bob, Dartmouth, Alice, Charlie, Kevin Bacon]
     * Out edges: {Bob={Kevin Bacon=[A Movie]}, Dartmouth={Charlie=[B Movie]}, Alice={Kevin Bacon=[A Movie, E Movie]}, Charlie={Bob=[C Movie]}, Kevin Bacon={}}
     * [Charlie, Bob, Kevin Bacon]
     * [Nobody, Nobody's Friend]
     * 1.4
     *
     * Process finished with exit code 0
     */
}


