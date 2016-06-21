import org.junit.Test;
import java.util.ArrayList;
import static org.junit.Assert.assertEquals;
/**
 * Single test to make sure repeated nested patterns work properly
 * @author Joseph Minieri
 * @version 1.1
 */
public class KMPTest {
    @Test(timeout = 200)
    public void kmpNestedDoubleMatches() {
        PrintingSearchableString p = new PrintingSearchableString("ababab");
        PrintingSearchableString t = new PrintingSearchableString("ababababab");
        System.out.println("\nkmpNestedDoubleMatches");
        p.flag = true;
        t.flag = true;
        assertEquals(make(new int[]{0,2,4}), StringSearching.kmp(p, t));
        p.flag = false;
        t.flag = false;
    }
    public ArrayList<Integer> make(int [] ints) {
        ArrayList<Integer> ans = new ArrayList<Integer>();
        for (int e: ints ) {
            ans.add(e);
        }
        return ans;
    }
    /**
     * Class that prints when the flag is true.
     *
     * @author CS 1332 TAs and Joseph Minieri
     * @version 1.0
     */
    public final class PrintingSearchableString implements CharSequence {
        private String str;
        private int count;
        public boolean flag;
        /**
         * Creates the SearchableString
         * @param s the string for the SearchableString to be created from
         */
        public PrintingSearchableString(String s) {
            flag = false;
            str = s;
            count = 0;
        }

        @Override
        public char charAt(int i) {
            if (flag) {
                String format = "";
                for (int j = 0; j < i; j++) {
                    format += " ";
                }
                format += "^";
                System.out.println(str + "\n" + format);
            }
            count++;
            return str.charAt(i);
        }

        @Override
        public int length() {
            return str.length();
        }

        /**
         * Returns the number of times charAt has been called for this object
         * @return the number of times charAt has been called for this object
         */
        public int getCount() {
            return count;
        }

        /**
         * Do NOT use this. It will not help at all.
         * @param start a parameter that should not be used
         * @param end another parameter that should not be used
         * @return never
         */
        @Override
        public CharSequence subSequence(int start, int end) {
            throw new UnsupportedOperationException("Do not use method "
                    + "subSequence.");
        }

        /**
         * Do NOT use this (with the exception of debugging your code).
         * If you use this method for any other purpose,
         * you WILL get a 0 on the entire assignment.
         * @return debugging messsage
         */
        @Override
        public String toString() {
            return "SearchableString containing: " + str + " (debug use only)";
        }

        /**
         * Do NOT use this. It will not help at all.
         * @param o a parameter that should not be used
         * @return never
         */
        @Override
        public boolean equals(Object o) {
            throw new UnsupportedOperationException(
                "Do not use method equals.");
        }
    }
}
