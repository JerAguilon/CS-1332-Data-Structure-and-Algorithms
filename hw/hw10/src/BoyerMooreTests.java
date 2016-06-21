import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;


import static org.junit.Assert.assertEquals;

/**
 * Search Boyer-Moore edge cases
 * 
 * 
 * @author Rania Glass
 * @version 1.0
 */
public class BoyerMooreTests {
    private SearchableString kittycat;
    private SearchableString kittyText;
    private List<Integer> kittyAnswer;

    private SearchableString puppydog;
    private SearchableString puppyText;
    private List<Integer> puppyAnswer;

    private SearchableString parrotbird;
    private SearchableString parrotText;
    private List<Integer> parrotAnswer;
        
    public static final int TIMEOUT = 200;

    @Before
    public void setUp() {
        kittycat = new SearchableString("itt");
        kittyText = new SearchableString("She's a pretty kitty kitty cat!");
        kittyAnswer = new ArrayList<>();
        kittyAnswer.add(16);
        kittyAnswer.add(22);

        parrotbird = new SearchableString(" ");
        parrotText = new SearchableString("Parrot bird can say words!");
        parrotAnswer = new ArrayList<>();
        parrotAnswer.add(6);
        parrotAnswer.add(11);
        parrotAnswer.add(15);
        parrotAnswer.add(19);

        puppydog = new SearchableString("puppydog");
        puppyText = new SearchableString("puppydog");
        puppyAnswer = new ArrayList<>();
        puppyAnswer.add(0);
    }
    
    @Test(timeout = TIMEOUT)
    public void testBoyerMooreRepeatedPattern() {
        assertEquals(kittyAnswer, StringSearching.boyerMoore(kittycat,
                kittyText));
    }

    @Test(timeout = TIMEOUT)
    public void testBoyerMooreSingleCharacter() {
        assertEquals(parrotAnswer, StringSearching.boyerMoore(parrotbird,
                parrotText));
    }

    @Test(timeout = TIMEOUT)
    public void testBoyerMooreWholeText() {
        assertEquals(puppyAnswer, StringSearching.boyerMoore(puppydog,
                puppyText));
    }
    
}