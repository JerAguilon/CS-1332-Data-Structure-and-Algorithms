import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * @author Joshua Dwire + Hayden Flinner
 * @version 1.0
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class HaydenTests {

    private HashMap<HackedString, String> directory;
    private static final int TIMEOUT = 200;

    @Before
    public void setUp() {
        directory = new HashMap<>();
    }

    @Test(timeout = TIMEOUT)
    public void test1FullContains() {
        directory.add(new HackedString("Hash8", 8), "a");
        directory.add(new HackedString("Hash7", 7), "b");
        directory.add(new HackedString("Hash4", 4), "c");
        directory.resizeBackingTable(3);

        assertFalse(directory.contains(new HackedString("NotHash8", 8)));
        assertFalse(directory.contains(new HackedString("NotHash8", 0)));
        assertTrue(directory.contains(new HackedString("Hash8", 8)));
        // You will fail here if your looparound stopping logic is bad.
        // I have no strong feelings on PHP, so here's a song instead.
        // https://www.youtube.com/watch?v=dKW6eqlPcBY
    }

    @Test(timeout = TIMEOUT)
    public void test2GeneralAbuse() {
        directory.add(new HackedString("Hash8", 8), "a");
        directory.add(new HackedString("Hash7", 7), "b");
        directory.resizeBackingTable(2);

        assertFalse(directory.contains(new HackedString("NotHash8", 8)));
        assertFalse(directory.contains(new HackedString("NotHash8", 0)));
        assertTrue(directory.contains(new HackedString("Hash8", 8)));

        directory.add(new HackedString("Hash7", 7), "b");
        directory.remove(new HackedString("Hash7", 7));

        assertTrue(directory.contains(new HackedString("Hash8", 8)));
        assertEquals("a", directory.add(new HackedString("Hash8", 8), "a"));
        assertTrue(directory.contains(new HackedString("Hash8", 8)));
    }

    private static class HackedString implements Comparable<HackedString> {
        private String s;
        private int hashcode;

        /**
         * Create a wrapper object around a String object, for the purposes
         * of controlling the hash code.
         *
         * @param s        string to store in this object
         * @param hashcode the hashcode to return
         */
        public HackedString(String s, int hashcode) {
            this.s = s;
            this.hashcode = hashcode;
        }

        @Override
        public int hashCode() {
            return this.hashcode;
        }

        @Override
        public boolean equals(Object o) {
            if (o instanceof HackedString) {
                return s.equals(((HackedString) o).s);
            }
            if (o instanceof String) {
                return s.equals(o);
            }
            return false;
        }

        /**
         * Stop highlighting "public" in read
         *
         * @param o The object to compare
         * @return Same thing a compareTo normally does
         */
        public int compareTo(HackedString o) {
            return s.compareTo(o.toString());
        }

        @Override
        public String toString() {
            return s;
        }
    }
}