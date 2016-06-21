import org.junit.Test;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

public class HashMapExercisesTests {

    private static final long TIMEOUT = 200;

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testCharacterCountException() {
        HashMapExercises.characterCount(null);
    }

    @Test(timeout = TIMEOUT)
    public void testCharacterCountSpecialCases() {
        Map<Character, Integer> zeroChar = HashMapExercises.characterCount("");
        assertTrue(zeroChar.isEmpty());

        Map<Character, Integer> oneChar = HashMapExercises.characterCount("a");
        assertEquals(1, oneChar.size());
        assertEquals((Integer) 1, oneChar.get('a'));
    }

    @Test(timeout = TIMEOUT)
    public void testCharacterCountGeneral() {
        // one repeated character
        Map<Character, Integer> chars =
                HashMapExercises.characterCount("aaaaaa");
        assertEquals(1, chars.size());
        assertEquals((Integer) 6, chars.get('a'));

        // alternating characters
        chars = HashMapExercises.characterCount("abababab");
        assertEquals(2, chars.size());
        assertEquals((Integer) 4, chars.get('a'));
        assertEquals((Integer) 4, chars.get('b'));

        // many different characters
        chars = HashMapExercises.characterCount("HeLlo, World!");
        assertEquals(11, chars.size());
        assertEquals((Integer) 1, chars.get('H'));
        assertEquals((Integer) 1, chars.get('e'));
        assertEquals((Integer) 1, chars.get('L'));
        assertEquals((Integer) 2, chars.get('l'));
        assertEquals((Integer) 2, chars.get('o'));
        assertEquals((Integer) 1, chars.get(','));
        assertEquals((Integer) 1, chars.get(' '));
        assertEquals((Integer) 1, chars.get('W'));
        assertEquals((Integer) 1, chars.get('r'));
        assertEquals((Integer) 1, chars.get('d'));
        assertEquals((Integer) 1, chars.get('!'));

        // unicode
        chars = HashMapExercises.characterCount("☑ Test");
        assertEquals(6, chars.size());
        assertEquals((Integer) 1, chars.get('☑'));
        assertEquals((Integer) 1, chars.get(' '));
        assertEquals((Integer) 1, chars.get('T'));
        assertEquals((Integer) 1, chars.get('e'));
        assertEquals((Integer) 1, chars.get('s'));
        assertEquals((Integer) 1, chars.get('t'));
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testRemoveDuplicatesException() {
        HashMapExercises.removeDuplicates(null);
    }

    @Test(timeout = TIMEOUT)
    public void testRemoveDuplicatesSpecialCases() {
        List<Integer> list =
                HashMapExercises.removeDuplicates(new LinkedList<>());
        assertEquals(0, list.size());
    }

    @Test(timeout = TIMEOUT)
    public void testRemoveDuplicatesGeneral() {
        // no duplicates
        List<Integer> result;
        List<Integer> list = new LinkedList<>();
        list.add(6);
        list.add(7);
        list.add(8);
        list.add(9);
        result = HashMapExercises.removeDuplicates(list);
        assertNotSame(list, result);
        assertEquals(4, list.size());
        assertEquals(list, result);

        // many duplicated items
        list.clear();
        list.add(6);
        list.add(7);
        list.add(8);
        list.add(9);
        list.add(8);
        list.add(7);
        list.add(6);
        list.add(10);
        list.add(2);
        list.add(7);
        list.add(4);
        list.add(8);
        list.add(6);
        result = HashMapExercises.removeDuplicates(list);
        assertNotSame(list, result);
        assertEquals(13, list.size());
        list.clear();
        list.add(6);
        list.add(7);
        list.add(8);
        list.add(9);
        list.add(10);
        list.add(2);
        list.add(4);
        assertEquals(list, result);
    }
}
