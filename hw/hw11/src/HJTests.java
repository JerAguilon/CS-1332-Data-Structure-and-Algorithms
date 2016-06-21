import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;
/**
 * https://github.gatech.edu/gist/jminieri6/8a095820d76ba6cd993e
 * @author Hayden Flinner and Joseph Minieri
 * @version 3.92
 *
 * Please Note in order for these tests to work the final must be removed from
 * VertexDistancePair.
 *
 * so instead of
 *    public final class VertexDistancePair<T>
 * make it
 *    public class VertexDistancePair<T>
 *
 * This allows for the counting inside of the new version of the
 * VertexDistancePair to detect the number of times accessed.
 */
public class HJTests {
    private static final int TIMEOUT = 200;
    private CounterGraph<String> graph;
    private CounterGraph<String> alreadyGraph;
    private Vertex<String> a = new Vertex<>("a");
    private Vertex<String> b = new Vertex<>("b");
    private Vertex<String> c = new Vertex<>("c");
    private Vertex<String> d = new Vertex<>("d");
    private Vertex<String> e = new Vertex<>("e");
    private Vertex<String> f = new Vertex<>("f");
    @Before
    public void setUp() {
        LinkedHashSet<Edge<String>> edges = new LinkedHashSet<>();

        edges.add(new Edge<String>(a, b, 3, false));
        edges.add(new Edge<String>(a, c, 5, false));
        edges.add(new Edge<String>(a, d, 4, false));
        edges.add(new Edge<String>(b, e, 3, false));
        edges.add(new Edge<String>(b, f, 5, false));
        edges.add(new Edge<String>(c, d, 2, false));
        edges.add(new Edge<String>(d, e, 1, false));
        edges.add(new Edge<String>(e, f, 2, false));
        LinkedHashSet<Edge<String>> already = new LinkedHashSet<>();

        already.add(new Edge<String>(a, b, 3, false));
        already.add(new Edge<String>(a, d, 5, false));
        already.add(new Edge<String>(b, f, 5, false));
        already.add(new Edge<String>(a, f, 1, false));
        already.add(new Edge<String>(a, b, 1, false));
        already.add(new Edge<String>(d, f, 1, false));

        alreadyGraph = new CounterGraph<>(already);
        graph = new CounterGraph<>(edges);
    }
    @Test(timeout = TIMEOUT)
    public void TestDikstroptimization() {
        Map<Vertex<String>, Integer> dijkstrasAnswer =
                GraphAlgorithms.dijkstras(c, graph);
        assertEquals(5, dijkstrasAnswer.get(a).intValue());
        assertEquals(6, dijkstrasAnswer.get(b).intValue());
        assertEquals(0, dijkstrasAnswer.get(c).intValue());
        assertEquals(2, dijkstrasAnswer.get(d).intValue());
        assertEquals(3, dijkstrasAnswer.get(e).intValue());
        assertEquals(5, dijkstrasAnswer.get(f).intValue());
        int count = graph.getCounter();
        assertTrue(String.format(
            "Comparisons allowed: %d Actual: %d", 6, count),
            6 >= graph.getCounter());
    }
    @Test(timeout = TIMEOUT)
    public void DJCloudOptimization() {
        Map<Vertex<String>, Integer> dijkstrasAnswer =
                GraphAlgorithms.dijkstras(a, alreadyGraph);
        int count = alreadyGraph.getCounter();
        assertTrue(String.format(
            "Comparisons allowed: %d Actual: %d", 4, count),
            4 >= alreadyGraph.getCounter());
    }
    @Test(timeout = TIMEOUT)
    public void TestPrimsProper() {
        GraphAlgorithms.prims(c, graph);
        int count = alreadyGraph.getVertexCounter();
        // This is a round about way to determining how many times things are
        // added to the PriorityQueue
        assertTrue(String.format(
            "Called getDistance() Allowed: %d Actual: %d", 0, count),
            0 == count);
    }
    @Test(timeout = TIMEOUT)
    public void Distanceigration() {
        GraphAlgorithms.prims(a, alreadyGraph);
        int count = alreadyGraph.getVertexCounter();
        // This is a round about way to determining how many times things are
        // added to the PriorityQueue
        assertTrue(String.format(
            "Called getDistance() Allowed: %d Actual: %d", 10, count),
            10 >= count);
    }
/*
                                                +my//:`
                                                :hhsdsh+``.``   `/s+
            `::::..-:.            `.:/+:....`    sss/hddh+yhy/. -yhs
             .hhddhmddy.        /oooo+osyo++yhyyhNNmhmNNNhyyhdd+:hs
       -os/yhhdsoohssyyso/.`   +MMNNNNNmhhyhmddddmmmNNmdmdhyyyhdsy`
        ``/smmh:/smmmmdhhyss+/+NMMNNmmNysNMmdhyosdsmmdy+::hdhyhhyy/
          mdmh+`  -oyhdmNNNmmdmMMMmmmmdmmNNdmyoosyyhddmds::+/-ohyos/
          d+-`     .ossyyhdNNNNNNmddhs/--ymdhhaydendmmNMNmhys/:oy::y
       `.+/         `ohys+shdmmdyo/-.``.--smNmmmmmmmmmmmNNNNNNmmmysd
       :/+`  `..---.``+hdddddhs:-....-+oy:hmmmNNNNNNNMMNNNNmdNNNNmhy.
         ./+shhhhhddhy+osohds:``.--..yhysosyhdmmNNNNNNmddNmmyNMMMd+.
       :oyydmNmdhddddhhhhdo:-.-+hh+::::+oshddmmmmdmmmhhyyNmmmNMMNds:.
      :ddhhhhdmNNdo-/--/+h``hyshhhhoy++oshdmmNNNmdhhyoy+hhdhhhmNNNNNy:`
      :dddddhhyhmdso:-:/:+./hso+sdNdmNddddmmNNNddysosyN+yddhmNMmNNNNNmh:
      smNmdhddsshho/o/+.:/+/+oo+/ym:yNmNNNNNNNhmsoo+odmysdmhmmmohmhdmNNm+`
     `dmNMh/+s+yodhhhhhh+o:oosysshNyddhdNNNNNdydsooomNmhhomddNhs/yosdmNmd+
     .mNNMsoo.:sdmmmddmmdd+yysyhdmmhhhdmNNmmmhymhhydNNy+osymmmNN+:o+ydmNds-
     `dmNMd+oysyNNNmmmmmmNmhshdhddNdddmNMNmmdhhmddhNNm:+ys/hd+hMm/:ysydNmy+
      odNNMds/oy:yhmmNNNNNNmdminierimdhdNNNhyyhmdddNNy-ss+-soyhMNh-:+sdmmyo
      `hmNNNmhyo :mmNNNNmddhddhyhhhhhdhhyyhhyyydmmmNNNm/-ohsymNmmh-:.ydmmho
       .ymmNNddhsdmNNNNd: ``.--::::/+osyhhyssyyydNmdNNNd-/:sdmms+/-sshmmmd/
        `+dmmmmmmmNNNmo.                     -::/+:`yNmNd/..+sy:-/o/+dmmmy`
        ``./shdmmmmho-..``````                      `ymmNNds../:-.ysdmmmy.
         ```...---:-------............````````        +mmmNmdyyhhdmmmmmo`
                ``````````````.....................````.+hNNmNmmNNNmdo.
`````                                     ```````...---:::+syhhhhy+-`
*/
    public class CounterGraph<T> extends Graph {
        CounterHashMap<Vertex<T>, List<CounterPair<T>>>
            trackingAdjacency;
        private Map<Vertex<T>, List<CounterPair<T>>> adjacencyList;
        public CounterGraph(LinkedHashSet<Edge<T>> edges) {
            super(edges);
            this.adjacencyList = new HashMap<>();
            for (Edge<T> e : edges) {
                adjacencyList.putIfAbsent(e.getU(),
                        new ArrayList<CounterPair<T>>());
                adjacencyList.putIfAbsent(e.getV(),
                        new ArrayList<CounterPair<T>>());
                adjacencyList.get(e.getU()).add(
                        new CounterPair<T>(e.getV(), e.getWeight()));
                if (!e.isDirected()) {
                    adjacencyList.get(e.getV()).add(
                            new CounterPair<T>(e.getU(), e.getWeight()));
                }
            }
            trackingAdjacency = new CounterHashMap<
                Vertex<T>, List<CounterPair<T>>>();
            trackingAdjacency.putAll(adjacencyList);
        }
        public int getCounter() {
            return trackingAdjacency.counter;
        }
        public int getVertexCounter() {
            int num = 0;
            HashSet<CounterPair<T>> added = new HashSet<>();
            for (Object vertex: adjacencyList.keySet()) {
                for (CounterPair<T> vdp
                    : adjacencyList.get(((Vertex<T>) vertex))) {
                    if (!added.contains(vdp)) {
                        num += vdp.getCounter();
                        added.add(vdp);
                    }
                }
            }
            return num;
        }
        @Override
        public Map<Vertex<T>, List<VertexDistancePair<T>>> getAdjacencyList() {
            return trackingAdjacency;
        }

        class CounterHashMap<T, V> extends java.util.HashMap {
            public int counter;

            public CounterHashMap() {
                super();
                counter = 0;
            }

            @Override
            public T get(Object key) {
                counter++;
                return (T) super.get(key);
            }
        }
    }
    public class CounterPair<T> extends VertexDistancePair {
        private int counter;
        public CounterPair(Vertex<T> v, int d) {
            super(v, d);
        }
        @Override
        public int getDistance() {
            counter++;
            return super.getDistance();
        }
        public int getCounter() {
            return counter;
        }
    }
    /*
                                __...------------._
                             ,-'                   `-.
                          ,-'                         `.
                        ,'                            ,-`.
                       ;                              `-' `.
                      ;                                 .-. \
                     ;                           .-.    `-'  \
                    ;                            `-'          \
                   ;                                          `.
                   ;                                           :
                  ;                                            |
                 ;                                             ;
                ;                            ___              ;
               ;                        ,-;-','.`.__          |
           _..;                      ,-' ;`,'.`,'.--`.        |
          ///;           ,-'   `. ,-'   ;` ;`,','_.--=:      /
         |'':          ,'        :     ;` ;,;,,-'_.-._`.   ,'
         '  :         ;_.-.      `.    :' ;;;'.ee.    \|  /
          \.'    _..-'/8o. `.     :    :! ' ':8888)   || /
  _______  ||`-''    \\88o\ :     :    :! :  :`""'    ;;/
/  IT'S  \ ||         \"88o\;     `.    \ `. `.      ;,'
|  A     | /)   ___    `."'/(--.._ `.    `.`.  `-..-' ;--.
\ TRAP  /  \(.="""""==.. `'-'     `.|      `-`-..__.-' `. `.
    \       |          `"==.__      )                    )  ;
     \      |   ||           `"=== '                   .'  .'
      ---  /\,,||||  | |           \                .'   .'
            | |||'|' |'|'           \|             .'   _.' \
            | |\' |  |           || ||           .'    .'    \
            ' | \ ' |'  .   ``-- `| ||         .'    .'       \
              '  |  ' |  .    ``-.._ |  ;    .'    .'          `.
           _.--,;`.       .  --  ...._,'   .'    .'              `.__
         ,'  ,';   `.     .   --..__..--'.'    .'                __/_\
       ,'   ; ;     |    .   --..__.._.'     .'                ,'     `.
      /    ; :     ;     .    -.. _.'     _.'                 /         `
     /     :  `-._ |    .    _.--'     _.'                   |
    /       `.    `--....--''       _.'                      |
              `._              _..-'                         |
                 `-..____...-''                              |
                                                             |
                                                             |
*/
}
