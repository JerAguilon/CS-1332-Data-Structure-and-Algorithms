import java.util.*;

public class HashMapExercises {



    /**
     * Counts the number of times each character occurs in the input string
     * and returns a map with the result.
     *
     * Use a HashMap as the Map implementation.
     *
     * @throws java.lang.IllegalArgumentException if input is null
     * @param input the String whose characters to count
     * @return a map mapping each character to the number of times it occurs in
     *         input
     */
    public static Map<Character, Integer> characterCount(String input) {
        HashMap<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < input.length(); i++) {
            Character thisChar = input.charAt(i);

            if (map.containsKey(thisChar)) {
                map.put(thisChar, map.get(thisChar) + 1);
            } else {
                map.put(thisChar, 1);
            }
        }

        return map;
    }


    /**
     * Constructs and returns a new list containing the elements from the
     * input list without duplicate elements.
     * 
     * This method preserves the relative ordering of elements.
     *
     * Use a HashSet.
     *
     * @throws java.lang.IllegalArgumentException if input is null
     * @param input the List to process
     * @param <T> the generic type of the list to process
     * @return the newly constructed list
     */
    public static <T> List<T> removeDuplicates(List<T> input) {
        if (input == null) throw new IllegalArgumentException("Yoooo");

        HashSet<T> output = new HashSet<>();

        for (T i : input) {
            if output.add
            output.add(i);
        }

        input.removeAll(output);
        return input;
    }
}
