import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Queue;
import java.util.LinkedList;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;

/**
 * Your implementation of various different graph algorithms.
 *
 * @author robin luo
 * @version 1.0
 * @userid hluo76
 * @GTID 903391803
 *
 * Collaborators: null
 *
 * Resources: null
 */
public class GraphAlgorithms {

    /**
     * Performs a breadth first search (bfs) on the input graph, starting at
     * the parameterized starting vertex.
     *
     * When exploring a vertex, explore in the order of neighbors returned by
     * the adjacency list. Failure to do so may cause you to lose points.
     *
     * You may import/use java.util.Set, java.util.List, java.util.Queue, and
     * any classes that implement the aforementioned interfaces, as long as they
     * are efficient.
     *
     * The only instance of java.util.Map that you may use is the
     * adjacency list from graph. DO NOT create new instances of Map
     * for BFS (storing the adjacency list in a variable is fine).
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the bfs on
     * @param graph the graph to search through
     * @return list of vertices in visited order
     * @throws IllegalArgumentException if any input
     *                                  is null, or if start doesn't exist in
     *                                  the graph
     */
    public static <T> List<Vertex<T>> bfs(Vertex<T> start, Graph<T> graph) {
        if (start == null || graph == null) {
            throw new IllegalArgumentException("The input is null");
        }
        Map<Vertex<T>, List<VertexDistance<T>>> map = graph.getAdjList();
        if (!map.containsKey(start)) {
            throw new IllegalArgumentException("Start is not in graph");
        }
        Queue<Vertex<T>> queue = new LinkedList<>();
        Set<Vertex<T>> visited = new HashSet<>();
        List<Vertex<T>> result = new ArrayList<>();
        visited.add(start);
        queue.add(start);
        while (!queue.isEmpty()) {
            Vertex<T> temp = queue.remove();
            result.add(temp);
            for (VertexDistance<T> vertex: map.get(temp)) {
                if (!visited.contains(vertex.getVertex())) {
                    queue.add(vertex.getVertex());
                    visited.add(vertex.getVertex());
                }
            }
        }
        return result;
    }


    /**
     * Performs a depth first search (dfs) on the input graph, starting at
     * the parameterized starting vertex.
     *
     * When exploring a vertex, explore in the order of neighbors returned by
     * the adjacency list. Failure to do so may cause you to lose points.
     *
     * *NOTE* You MUST implement this method recursively, or else you will lose
     * all points for this method.
     *
     * You may import/use java.util.Set, java.util.List, and
     * any classes that implement the aforementioned interfaces, as long as they
     * are efficient.
     *
     * The only instance of java.util.Map that you may use is the
     * adjacency list from graph. DO NOT create new instances of Map
     * for DFS (storing the adjacency list in a variable is fine).
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the dfs on
     * @param graph the graph to search through
     * @return list of vertices in visited order
     * @throws IllegalArgumentException if any input
     *                                  is null, or if start doesn't exist in
     *                                  the graph
     */
    public static <T> List<Vertex<T>> dfs(Vertex<T> start, Graph<T> graph) {
        if (start == null || graph == null) {
            throw new IllegalArgumentException("The input is null");
        }
        Map<Vertex<T>, List<VertexDistance<T>>> map = graph.getAdjList();
        if (!map.containsKey(start)) {
            throw new IllegalArgumentException("Start is not in graph");
        }
        List<Vertex<T>> result = new ArrayList<>();
        Set<Vertex<T>> visited = new HashSet<>();
        dfs(start, visited, result, graph);
        return result;
    }

    /**
     * helper function for dfs
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the dfs on
     * @param graph the graph to search through
     * @param visited the set of vertex of visited
     * @param result the list of the result output
     */
    private static <T> void dfs(Vertex<T> start, Set<Vertex<T>> visited,
                                List<Vertex<T>> result, Graph<T> graph) {
        Map<Vertex<T>, List<VertexDistance<T>>> map = graph.getAdjList();
        if (!visited.contains(start)) {
            result.add(start);
            visited.add(start);
            for (VertexDistance<T> vertex : map.get(start)) {
                if (!visited.contains(vertex.getVertex())) {
                    dfs(vertex.getVertex(), visited,
                            result, graph);
                }
            }
        }
    }


    /**
     * Finds the single-source shortest distance between the start vertex and
     * all vertices given a weighted graph (you may assume non-negative edge
     * weights).
     *
     * Return a map of the shortest distances such that the key of each entry
     * is a node in the graph and the value for the key is the shortest distance
     * to that node from start, or Integer.MAX_VALUE (representing
     * infinity) if no path exists.
     *
     * You may import/use java.util.PriorityQueue,
     * java.util.Map, and java.util.Set and any class that
     * implements the aforementioned interfaces, as long as your use of it
     * is efficient as possible.
     *
     * You should implement the version of Dijkstra's where you use two
     * termination conditions in conjunction.
     *
     * 1) Check if all of the vertices have been visited.
     * 2) Check if the PQ is empty yet.
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the Dijkstra's on (source)
     * @param graph the graph we are applying Dijkstra's to
     * @return a map of the shortest distances from start to every
     * other node in the graph
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph.
     */
    public static <T> Map<Vertex<T>, Integer> dijkstras(Vertex<T> start,
                                                        Graph<T> graph) {
        if (start == null || graph == null) {
            throw new IllegalArgumentException("The input is null");
        }
        Map<Vertex<T>, List<VertexDistance<T>>> map = graph.getAdjList();
        if (!map.containsKey(start)) {
            throw new IllegalArgumentException("Start is not in graph");
        }
        Map<Vertex<T>, Integer> result = new HashMap<>();
        Queue<VertexDistance<T>> queue = new PriorityQueue<>();
        Set<VertexDistance<T>> visited = new HashSet<>();
        for (Vertex<T> vertex: map.keySet()) {
            if (vertex.equals(start)) {
                result.put(vertex, 0);
            } else {
                result.put(vertex, Integer.MAX_VALUE);
            }
        }
        queue.add(new VertexDistance<T>(start, 0));
        while (!queue.isEmpty()) {
            VertexDistance<T> temp = queue.remove();
            visited.add(temp);
            for (VertexDistance<T> ver : map.get(temp.getVertex())) {
                if (!visited.contains(ver)) {
                    if (result.get(ver.getVertex()) > temp.getDistance()
                            + ver.getDistance()) {
                        result.put(ver.getVertex(), temp.getDistance()
                                + ver.getDistance());
                        queue.add(new VertexDistance<T>(ver.getVertex(),
                                temp.getDistance()
                                        + ver.getDistance()));
                    }
                }
            }
        }
        return result;
    }

    /**
     * Runs Kruskal's algorithm on the given graph and returns the Minimal
     * Spanning Tree (MST) in the form of a set of Edges. If the graph is
     * disconnected and therefore no valid MST exists, return null.
     *
     * You may assume that the passed in graph is undirected. In this framework,
     * this means that if (u, v, 3) is in the graph, then the opposite edge
     * (v, u, 3) will also be in the graph, though as a separate Edge object.
     *
     * The returned set of edges should form an undirected graph. This means
     * that every time you add an edge to your return set, you should add the
     * reverse edge to the set as well. This is for testing purposes. This
     * reverse edge does not need to be the one from the graph itself; you can
     * just make a new edge object representing the reverse edge.
     *
     * You may assume that there will only be one valid MST that can be formed.
     *
     * Kruskal's will also require you to use a Disjoint Set which has been
     * provided for you. A Disjoint Set will keep track of which vertices are
     * connected given the edges in your current MST, allowing you to easily
     * figure out whether adding an edge will create a cycle. Refer
     * to the DisjointSet and DisjointSetNode classes that
     * have been provided to you for more information.
     *
     * You should NOT allow self-loops or parallel edges into the MST.
     *
     * You may import/use java.util.PriorityQueue,
     * java.util.Set, and any class that implements the aforementioned
     * interface.
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param graph the graph we are applying Kruskals to
     * @return the MST of the graph or null if there is no valid MST
     * @throws IllegalArgumentException if any input is null
     */
    public static <T> Set<Edge<T>> kruskals(Graph<T> graph) {
        if (graph == null) {
            throw new IllegalArgumentException("graph is null");
        }
        Queue<Edge<T>> queue = new PriorityQueue<>(graph.getEdges());
        Set<Edge<T>> result = new HashSet<>();
        Set<Vertex<T>> vertices = graph.getVertices();
        DisjointSet<Vertex<T>> disjointSet = new DisjointSet<>(vertices);
        while (queue.size() > 0 && result.size() < (2 * vertices.size() - 1)) {
            Edge<T>  temp = queue.poll();
            Vertex<T> u = temp.getU();
            Vertex<T> v = temp.getV();
            if (disjointSet.find(u)
                    != disjointSet.find(v)) {
                disjointSet.union(disjointSet.find(u), disjointSet.find(v));
                result.add(temp);
                result.add(new Edge(v, u, temp.getWeight()));
            }
        }
        if (result.size() == (2 * vertices.size() - 2) || (result
                .size() == 0 && vertices.size() == 0)) {
            return result;
        } else {
            return null;
        }
    }
}
