import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Rabin-Karp test cases.
 *
 * @author Jonathan Jiang
 * @version 1.0
 */
public class RabinKarpTests {
    private SearchableString jesse;
    private SearchableString jesseText;
    private SearchableString jesseTextNotThere;
    List<Integer> jesseAnswer;

    private SearchableString lmao;
    private SearchableString alpha;
    private SearchableString missing;
    List<Integer> alphaAnswer;

    private SearchableString repeat;
    private SearchableString hasRepeating;
    private SearchableString noRepeat;
    List<Integer> repeatAnswer;

    private SearchableString begin;
    private SearchableString hasBegin;
    private SearchableString noBegin;
    List<Integer> beginAnswer;

    private SearchableString end;
    private SearchableString hasEnd;
    private SearchableString noEnd;
    List<Integer> endAnswer;

    private SearchableString sequential;
    private SearchableString hasSequence;
    private SearchableString noSequence;
    List<Integer> sequenceAnswer;


    private SearchableString identical;
    private SearchableString isIdentical;
    private SearchableString notIdentical;
    List<Integer> identicalAnswer;

    List<Integer> emptyList;

    public static final int TIMEOUT = 200;

    @Before
    public void setUp() {
        jesse = new SearchableString("jesse");
        jesseText = new SearchableString("who the heck is jesse?");
        jesseTextNotThere = new SearchableString("i dunno");
        jesseAnswer = new ArrayList<Integer>();
        jesseAnswer.add(16);

        lmao = new SearchableString("l");
        alpha = new SearchableString("the quick brown fox jumps over the lazy dog");
        missing = new SearchableString("this sentence contains characters that aren't needed");
        alphaAnswer = new ArrayList<Integer>();
        alphaAnswer.add(35);

        repeat = new SearchableString("aba");
        hasRepeating = new SearchableString("abababababa");
        noRepeat = new SearchableString("yes");
        repeatAnswer = new ArrayList<Integer>();
        for(int i = 0; i <= 8; i += 2) {
            repeatAnswer.add(i);
        }

        begin = new SearchableString("x");
        hasBegin = new SearchableString("xasdfasdfasdfasdfasdf");
        noBegin = new SearchableString("asdfasdfasdf");
        beginAnswer = new ArrayList<Integer>();
        beginAnswer.add(0);

        end = new SearchableString("x");
        hasEnd = new SearchableString("asdfasdfasdfasdfasdfx");
        noEnd = new SearchableString("asdfasdfasdf");
        endAnswer = new ArrayList<Integer>();
        endAnswer.add(20);

        sequential = new SearchableString("aa");
        hasSequence = new SearchableString("aaabaaa");
        noSequence = new SearchableString("xxxxx");
        sequenceAnswer = new ArrayList<Integer>();
        sequenceAnswer.add(0);
        sequenceAnswer.add(1);
        sequenceAnswer.add(4);
        sequenceAnswer.add(5);

        identical = new SearchableString("I like cheese");
        isIdentical = new SearchableString("I like cheese");
        notIdentical = new SearchableString("I don't like cheese");
        identicalAnswer = new ArrayList<Integer>();
        identicalAnswer.add(0);

        emptyList = new ArrayList<Integer>();
    }

    /**
     * Tests Rabin-Karp algorithm for basic functionality.
     */
    @Test(timeout = TIMEOUT)
    public void testRabinKarpBasic() {
        assertEquals(jesseAnswer, StringSearching.rabinKarp(jesse, jesseText));
        assertEquals(emptyList, StringSearching.rabinKarp(jesse, jesseTextNotThere));
    }

    /**
     * Tests Rabin-Karp algorithm for searching for a single character.
     */
    @Test(timeout = TIMEOUT)
    public void testRabinKarpSingleChar() {
        assertEquals(alphaAnswer, StringSearching.rabinKarp(lmao, alpha));
        assertEquals(emptyList, StringSearching.rabinKarp(lmao, missing));
    }

    /**
     * Tests Rabin-Karp algorithm for searching for more than one instance
     * of a matching subsequence within the text.
     */
    @Test(timeout = TIMEOUT)
    public void testRabinKarpRepeat() {
        assertEquals(repeatAnswer, StringSearching.rabinKarp(repeat, hasRepeating));
        assertEquals(emptyList, StringSearching.rabinKarp(repeat, noRepeat));
    }

    /**
     * Test Rabin-Karp algorithm for finding matching character in
     * the beginning of the text.
     */
    @Test(timeout = TIMEOUT)
    public void testRabinKarpBeginningMatch() {
        assertEquals(beginAnswer, StringSearching.rabinKarp(begin, hasBegin));
        assertEquals(emptyList, StringSearching.rabinKarp(begin, noBegin));
    }

    /**
     * Tests Rabin-Karp algorithm for finding matching character in
     * the end of the text.
     */
    @Test(timeout = TIMEOUT)
    public void testRabinKarpEndMatch() {
        assertEquals(endAnswer, StringSearching.rabinKarp(end, hasEnd));
        assertEquals(emptyList, StringSearching.rabinKarp(end, noEnd));
    }

    /**
     * Tests Rabin-Karp algorithm for finding matching sequences where one
     * is a substring of another (e.g. finding "aa" in "aaabaaa")
     */
    @Test(timeout = TIMEOUT)
    public void testRabinKarpSequentialMatch() {
        assertEquals(sequenceAnswer, StringSearching.rabinKarp(sequential, hasSequence));
        assertEquals(emptyList, StringSearching.rabinKarp(sequential, noSequence));
    }

    /**
     * Tests Rabin-Karp algorithm when the pattern and the text are the exact
     * same.
     */
    @Test(timeout = TIMEOUT)
    public void testRabinKarpWholeText() {
        assertEquals(identicalAnswer, StringSearching.rabinKarp(identical, isIdentical));
        isIdentical = identical;
        assertEquals(identicalAnswer, StringSearching.rabinKarp(identical, isIdentical));
        assertEquals(emptyList, StringSearching.rabinKarp(identical, notIdentical));
    }
}
