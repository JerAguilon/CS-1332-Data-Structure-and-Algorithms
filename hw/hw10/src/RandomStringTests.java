import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertEquals;

/**
 * Random tests for Rabin Karp, Boyer Moore, and KMP.
 *
 * These tests use random patterns of size {@link #MIN_QUERY_LENGTH} to
 * {@link #MAX_QUERY_LENGTH}, and random search strings of size
 * {@link #MIN_TEXT_LENGTH} to {@link #MAX_TEXT_LENGTH}.
 *
 * Because these strings are randomly generated, it becomes less and less
 * probable that a pattern will appear in the text as its length increases, so
 * you may need to increase {@link #TEST_COUNT} for these tests to become more
 * comprehensive.
 *
 * These tests DO NOT check your methods' efficiency.
 *
 * @author Andrew Bailey
 * @version 1.1
 */
public class RandomStringTests {

    private static final int TEST_COUNT = 1000;
    private static final int TIMEOUT = TEST_COUNT * 200;
    private static final int MIN_QUERY_LENGTH = 1;
    private static final int MAX_QUERY_LENGTH = 12;
    private static final int MAX_TEXT_LENGTH = 100;
    private static final int MIN_TEXT_LENGTH = 1;

    @Test(timeout = TIMEOUT)
    public void testRandomKmp() {
        testStringSearch("KMP", StringSearching::kmp);
    }

    @Test(timeout = TIMEOUT)
    public void testRandomBoyerMoore() {
        testStringSearch("Boyer Moore", StringSearching::boyerMoore);
    }

    @Test(timeout = TIMEOUT)
    public void testRandomRabinKarp() {
        testStringSearch("Rabin Karp", StringSearching::rabinKarp);
    }

    /**
     * Allows the searching methods to be treated as method references, which
     * keeps the code shorter.
     */
    private interface StringSearchMethod {
        List<Integer> search(CharSequence pattern, CharSequence text);
    }

    /**
     * Runs {@link #TEST_COUNT} random tests of a given search method
     * @param methodName The name of the method being tested to improve error
     *                   outputs
     * @param method The method to test its correctness
     */
    private static void testStringSearch(String methodName,
                                         StringSearchMethod method) {
        for (int i = 0; i < TEST_COUNT; i++) {
            String query = randomString(MIN_QUERY_LENGTH, MAX_QUERY_LENGTH);
            String text = randomString(MIN_TEXT_LENGTH, MAX_TEXT_LENGTH);

            List<Integer> actual = method.search(query, text);
            verifySearchResults(methodName, text, query, actual);
        }
    }

    /**
     * Compares actual results against those returned by the Java API
     * @param methodName The name of the method being tested
     * @param text The text being searched
     * @param pattern The pattern being searched for
     * @param actual The result returned by the implementation of
     *               {@link StringSearching}
     */
    private static void verifySearchResults(String methodName, String text,
                                            String pattern,
                                            List<Integer> actual) {
        List<Integer> expected = new ArrayList<>();

        int start = 0;
        while (start != -1) {
            start = text.indexOf(pattern, start);
            if (start != -1) {
                expected.add(start);
                start++;
            }
        }

        assertEquals(methodName + ": Result returned incorrect indices"
                        + "\ntext: " + text
                        + "\npattern: " + pattern,
                expected, actual);
    }

    /**
     * Generates a random string of a certain size with characters 'a' - 'z'
     * @param minLength The minimum length of the string
     * @param maxLength The maximum length of the string
     * @return The random string
     */
    private static String randomString(int minLength, int maxLength) {
        Random rand = new Random();
        int size = rand.nextInt(maxLength - minLength) + minLength;

        char[] buffer = new char[size];
        for (int i = 0; i < size; i++) {
            buffer[i] = (char) (rand.nextInt('z' - 'a' + 1) + 'a');
        }

        return new String(buffer);
    }
}
