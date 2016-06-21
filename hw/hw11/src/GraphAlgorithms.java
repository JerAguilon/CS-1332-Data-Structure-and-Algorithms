import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Queue;
import java.util.LinkedList;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.HashMap;

/**
 * Your implementations of various graph algorithms.
 *
 * @author JEREMY AGUILON
 * @version 1.0
 */
public class GraphAlgorithms {

    /**
     * Perform breadth first search on the given graph, starting at the start
     * Vertex.  You will return a List of the vertices in the order that
     * you visited them.  Make sure to include the starting vertex at the
     * beginning of the list.
     *
     * When exploring a Vertex, make sure you explore in the order that the
     * adjacency list returns the neighbors to you.  Failure to do so may
     * cause you to lose points.
     *
     * You may import/use {@code java.util.Queue}, {@code java.util.Set},
     * {@code java.util.Map}, {@code java.util.List}, and any classes
     * that implement the aforementioned interfaces.
     *
     * @throws IllegalArgumentException if any input is null, or if
     *         {@code start} doesn't exist in the graph
     * @param start the Vertex you are starting at
     * @param graph the Graph we are searching
     * @param <T> the data type representing the vertices in the graph.
     * @return a List of vertices in the order that you visited them
     */
    public static <T> List<Vertex<T>> breadthFirstSearch(Vertex<T> start,
            Graph<T> graph) {
        if (start == null || graph == null) {
            throw new IllegalArgumentException(
                    "Start and graph cannot be null");
        }

        Map<Vertex<T>, List<VertexDistancePair<T>>> vertexList =
                graph.getAdjacencyList();

        if (!graph.getAdjacencyList().containsKey(start)) {
            throw new IllegalArgumentException(
                    "Start must be in adjacency list");
        }

        List<Vertex<T>> output = new ArrayList<>();

        Queue<Vertex<T>> queue = new LinkedList<>();
        Set<Vertex<T>> visited = new HashSet<>();

        queue.add(start);
        visited.add(start);

        while (!queue.isEmpty()) {

            Vertex<T> current = queue.remove();
            output.add(current);

            for (VertexDistancePair<T> vertex : vertexList.get(current)) {
                if (!visited.contains(vertex.getVertex())) {
                    queue.add(vertex.getVertex());
                    visited.add(vertex.getVertex());
                    //output.add(vertex.getVertex());
                }
            }

        }

        return output;
    }

    /**
     * Perform depth first search on the given graph, starting at the start
     * Vertex.  You will return a List of the vertices in the order that
     * you visited them.  Make sure to include the starting vertex at the
     * beginning of the list.
     *
     * When exploring a Vertex, make sure you explore in the order that the
     * adjacency list returns the neighbors to you.  Failure to do so may
     * cause you to lose points.
     *
     * You MUST implement this method recursively.
     * Do not use any data structure as a stack to avoid recursion.
     * Implementing it any other way WILL cause you to lose points!
     *
     * You may import/use {@code java.util.Set}, {@code java.util.Map},
     * {@code java.util.List}, and any classes that implement the
     * aforementioned interfaces.
     *
     * @throws IllegalArgumentException if any input is null, or if
     *         {@code start} doesn't exist in the graph
     * @param start the Vertex you are starting at
     * @param graph the Graph we are searching
     * @param <T> the data type representing the vertices in the graph.
     * @return a List of vertices in the order that you visited them
     */
    public static <T> List<Vertex<T>> depthFirstSearch(Vertex<T> start,
            Graph<T> graph) {
        if (start == null || graph == null) {
            throw new IllegalArgumentException(
                    "Start and graph cannot be null");
        }

        Map<Vertex<T>, List<VertexDistancePair<T>>> vertexList =
                graph.getAdjacencyList();

        if (!graph.getAdjacencyList().containsKey(start)) {
            throw new IllegalArgumentException(
                    "Start must be in adjacency list");
        }

        List<Vertex<T>> output = new ArrayList<>();

        Set<Vertex<T>> visited = new HashSet<>();

        depthFirstSearch(start, visited, output, vertexList);

        return output;
    }

    /**
     * Helper method for the dfs algorithm, recurses to imitate a stack
     * by touching far elements first
     *
     * @param start the Vertex you are starting at
     * @param visited set of visited vertices
     * @param output the list of vertices to be returned in calling method
     * @param vertexList map of vertices to their vertex distance pairs
     * @param <T> the data type representing the vertices in the graph.
     */
    private static <T> void depthFirstSearch(Vertex<T> start,
                                             Set<Vertex<T>> visited,
                                             List<Vertex<T>> output,
                                             Map<Vertex<T>,
                                                List<VertexDistancePair<T>>>
                                                vertexList) {
        output.add(start);
        visited.add(start);

        for (VertexDistancePair<T> vp : vertexList.get(start)) {
            Vertex<T> current = vp.getVertex();
            if (!visited.contains(current)) {
                depthFirstSearch(current, visited, output, vertexList);

            }
        }


    }

    /**
     * Find the shortest distance between the start vertex and all other
     * vertices given a weighted graph where the edges only have positive
     * weights.
     *
     * Return a map of the shortest distances such that the key of each entry is
     * a node in the graph and the value for the key is the shortest distance
     * to that node from start, or Integer.MAX_VALUE (representing infinity)
     * if no path exists. You may assume that going from a vertex to itself
     * has a distance of 0.
     *
     * There are guaranteed to be no negative edge weights in the graph.
     *
     * You may import/use {@code java.util.PriorityQueue},
     * {@code java.util.Map}, and any class that implements the aforementioned
     * interface.
     *
     * @throws IllegalArgumentException if any input is null, or if
     *         {@code start} doesn't exist in the graph
     * @param start the Vertex you are starting at
     * @param graph the Graph we are searching
     * @param <T> the data type representing the vertices in the graph.
     * @return a map of the shortest distances from start to every other node
     *         in the graph.
     */
    public static <T> Map<Vertex<T>, Integer> dijkstras(Vertex<T> start,
            Graph<T> graph) {
        if (start == null || graph == null) {
            throw new IllegalArgumentException(
                    "Start and graph cannot be null");
        }

        Map<Vertex<T>, List<VertexDistancePair<T>>> vertexList =
                graph.getAdjacencyList();

        if (!graph.getAdjacencyList().containsKey(start)) {
            throw new IllegalArgumentException(
                    "Start must be in adjacency list");
        }

        Queue<VertexDistancePair<T>> queue = new PriorityQueue<>();
        Map<Vertex<T>, Integer> output = new HashMap<>();

        for (Vertex<T> vertex : vertexList.keySet()) {

            if (vertex.equals(start)) {
                output.put(vertex, 0);
            } else {
                output.put(vertex, Integer.MAX_VALUE);
            }
        }

        queue.add(new VertexDistancePair<>(start, 0));


        while (!queue.isEmpty()) {
            VertexDistancePair<T> vp = queue.remove();

            for (VertexDistancePair<T> add
                    : vertexList.get(vp.getVertex())) {

                int newD = add.getDistance() +  vp.getDistance();
                if (newD < output.get(add.getVertex())) {
                    VertexDistancePair<T> newPair =
                            new VertexDistancePair<>(add.getVertex(), newD);
                    queue.add(newPair);
                    output.put(add.getVertex(), newD);
                }

            }
        }

        return output;
    }

    /**
     * Run Prim's algorithm on the given graph and return the minimum spanning
     * tree in the form of a set of Edges.  If the graph is disconnected, and
     * therefore there is no valid MST, return null.
     *
     * When exploring a Vertex, make sure you explore in the order that the
     * adjacency list returns the neighbors to you.  Failure to do so may
     * cause you to lose points.
     *
     * You may assume that for a given starting vertex, there will only be
     * one valid MST that can be formed. In addition, only an undirected graph
     * will be passed in.
     *
     * You may import/use {@code java.util.PriorityQueue},
     * {@code java.util.Set}, and any class that implements the aforementioned
     * interface.
     *
     * @throws IllegalArgumentException if any input is null, or if
     *         {@code start} doesn't exist in the graph
     * @param start the Vertex you are starting at
     * @param graph the Graph we are searching
     * @param <T> the data type representing the vertices in the graph.
     * @return the MST of the graph; null if no valid MST exists.
     */
    public static <T> Set<Edge<T>> prims(Vertex<T> start, Graph<T> graph) {
        if (start == null || graph == null) {
            throw new IllegalArgumentException(
                    "Start and graph cannot be null");
        }

        Map<Vertex<T>, List<VertexDistancePair<T>>> vertexList =
                graph.getAdjacencyList();

        if (!graph.getAdjacencyList().containsKey(start)) {
            throw new IllegalArgumentException(
                    "Start must be in adjacency list");
        }

        Set<Vertex<T>> reached = new HashSet<>();
        Set<Edge<T>> output = new HashSet<>();
        Queue<Edge<T>> queue = new PriorityQueue<>();

        for (VertexDistancePair<T> vp
                : vertexList.get(start)) {
            queue.add(new Edge<>(
                    start, vp.getVertex(), vp.getDistance(), false));
        }
        reached.add(start);

        while (!queue.isEmpty()) {
            Edge<T> current = queue.remove();

            if (!(reached.contains(current.getV()))) {
                output.add(current);
                reached.add(current.getV());

                for (VertexDistancePair<T> vp
                        : vertexList.get(current.getV())) {
                    queue.add(new Edge<>(
                            current.getV(), vp.getVertex(),
                            vp.getDistance(), false));
                }

            }
        }

        if (reached.size() < vertexList.size() - 1) {
            return null;
        }
        return output;

    }

}
