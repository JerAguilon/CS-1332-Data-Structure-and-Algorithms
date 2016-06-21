import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Created by Rania on 4/12/2016.
 */
public class RGTests {

    private static final int TIMEOUT = 200;
    private Graph<String> simpleGraph;
    private Graph<String> treeGraph;
    private Graph<String> cycleGraph;
    private Graph<String> completeGraph;
    private Graph<String> branchNodeGraph;

    private Vertex<String> a = new Vertex<>("A");
    private Vertex<String> b = new Vertex<>("B");
    private Vertex<String> c = new Vertex<>("C");
    private Vertex<String> d = new Vertex<>("D");
    private Vertex<String> e = new Vertex<>("E");
    private Vertex<String> f = new Vertex<>("F");
    private Vertex<String> g = new Vertex<>("G");


    LinkedHashSet<Edge<String>> treeEdges = new LinkedHashSet<>();


    @Before
    public void setUp() {

        LinkedHashSet<Edge<String>> simpleEdges = new LinkedHashSet<>();
        simpleEdges.add(new Edge<String>(a, b, 1, false));
        simpleGraph = new Graph<>(simpleEdges);

        treeEdges.add(new Edge<String>(a, b, 1, false));
        treeEdges.add(new Edge<String>(a, c, 2, false));
        treeEdges.add(new Edge<String>(b, d, 3, false));
        treeEdges.add(new Edge<String>(b, e, 1, false));
        treeEdges.add(new Edge<String>(c, f, 1, false));
        treeEdges.add(new Edge<String>(c, g, 3, false));
        treeGraph = new Graph<>(treeEdges);


        LinkedHashSet<Edge<String>> cycleEdges = new LinkedHashSet<>();
        cycleEdges.add(new Edge<String>(a, b, 1, false));
        cycleEdges.add(new Edge<String>(b, c, 2, false));
        cycleEdges.add(new Edge<String>(c, d, 3, false));
        cycleEdges.add(new Edge<String>(d, e, 4, false));
        cycleEdges.add(new Edge<String>(e, a, 5, false));
        cycleGraph = new Graph<>(cycleEdges);


        LinkedHashSet<Edge<String>> completeEdges = new LinkedHashSet<>();
        completeEdges.add(new Edge<String>(a, b, 1, false));
        completeEdges.add(new Edge<String>(b, c, 1, false));
        completeEdges.add(new Edge<String>(c, d, 1, false));
        completeEdges.add(new Edge<String>(a, d, 1, false));
        completeEdges.add(new Edge<String>(a, c, 3, false));
        completeEdges.add(new Edge<String>(d, b, 3, false));
        completeGraph = new Graph<>(completeEdges);

        LinkedHashSet<Edge<String>> branchNodeEdges = new LinkedHashSet<>();
        branchNodeEdges.add(new Edge<String>(a, b, 1, false));
        branchNodeEdges.add(new Edge<String>(a, c, 2, false));
        branchNodeEdges.add(new Edge<String>(b, c, 1, false));
        branchNodeEdges.add(new Edge<String>(c, d, 2, false));
        branchNodeGraph = new Graph<>(branchNodeEdges);
    }

    /**Prim's Algorithm for finding an MST
     * The graphs depicted above the test are the entire graph, not the MST**/


    /** [A]- 1 -[B] **/

    @Test(timeout = TIMEOUT)
    public void testPrimsSimple() {
        Set<Edge<String>> primsAnswer = GraphAlgorithms.prims(a, simpleGraph);
        Set<Edge<String>> primsProper = new HashSet<Edge<String>>();
        primsProper.add(new Edge(a, b, 1, false));
        assertEquals(primsProper, primsAnswer);
    }

    /**        [A]
     *        /   \
     *        1    2
     *      /       \
     *     [B]     [C]
     *     / \     | \
     *    3   1    1  3
     *   /     \   |   \
     *  [D]   [E] [F] [G]
     *
     * **/
    @Test (timeout = TIMEOUT)
    public void testPrimsTree() {
        Set<Edge<String>> primsTreeAnswer = GraphAlgorithms.prims(a, treeGraph);
        Set<Edge<String>> primsTreeProper = new HashSet<Edge<String>>();
        primsTreeProper.addAll(treeEdges);
        assertEquals(primsTreeProper, primsTreeAnswer);
    }

    /**
     *         _[A]_
     *       /      \
     *      1        5
     *     /          \
     *   [B]          [E]
     *     \           /
     *      2         4
     *       \       /
     *       [C]-3-[D]
     */
    @Test (timeout = TIMEOUT)
    public void testPrimsCycle() {
        Set<Edge<String>> primsCycleAnswer = GraphAlgorithms.prims(a,
                cycleGraph);
        Set<Edge<String>> primsCycleProper = new HashSet<Edge<String>>();
        primsCycleProper.add(new Edge(a, b, 1, false));
        primsCycleProper.add(new Edge(b, c, 2, false));
        primsCycleProper.add(new Edge(c, d, 3, false));
        primsCycleProper.add(new Edge(d, e, 4, false));
        assertEquals(primsCycleProper, primsCycleAnswer);
    }

    /**
     *      [A]
     *     /|  \
     *    1 |   1
     *   /  |    \
     * [D]--|- 3-[B]
     *  \   |    /
     *   1  3   1
     *    \ |  /
     *     [C]
     */
    @Test (timeout = TIMEOUT)
    public void testPrimsComplete() {
        Set<Edge<String>> primsCompleteAnswer
                = GraphAlgorithms.prims(a, completeGraph);
        Set<Edge<String>> primsCompleteProper = new HashSet<Edge<String>>();
        primsCompleteProper.add(new Edge(a, b, 1, false));
        primsCompleteProper.add(new Edge(b, c, 1, false));
        primsCompleteProper.add(new Edge(a, d, 1, false));
        assertEquals(primsCompleteProper, primsCompleteAnswer);
    }

    /**
     *         [A]
     *        /   \
     *       1     2
     *      /       \
     *    [B]-- 1 --[C]- 2 -[D]
     *
     */
    @Test (timeout = TIMEOUT)
    public void testPrimsBranch() {
        Set<Edge<String>> primsBranchAnswer
                = GraphAlgorithms.prims(a, branchNodeGraph);
        Set<Edge<String>> primsBranchProper = new HashSet<Edge<String>>();
        primsBranchProper.add(new Edge(a, b, 1, false));
        primsBranchProper.add(new Edge(b, c, 1, false));
        primsBranchProper.add(new Edge(c, d, 2, false));
        assertEquals(primsBranchProper, primsBranchAnswer);
    }
}