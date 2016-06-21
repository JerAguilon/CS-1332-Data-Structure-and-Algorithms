import java.util.Comparator;
import java.util.Random;
import java.util.LinkedList;

/**
 * Your implementation of various sorting algorithms.
 *
 * @author Jeremy Aguilon
 * @version 1.0
 */
public class Sorting {

    /**
     * Implement bubble sort.
     *
     * It should be:
     *  in-place
     *  stable
     *
     * Have a worst case running time of:
     *  O(n^2)
     *
     * And a best case running time of:
     *  O(n)
     *
     * Any duplicates in the array should be in the same relative position after
     * sorting as they were before sorting. (stable).
     *
     * See the PDF for more info on this sort.
     *
     * @throws IllegalArgumentException if the array or comparator is null
     * @param <T> data type to sort
     * @param arr the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     */
    public static <T> void bubbleSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("Cannot pass in null objects");
        }

        boolean flag = true;
        int j = 1;
        while (flag) {
            flag = false;

            for (int i = 0; i < arr.length - j; i++) {
                if (comparator.compare(arr[i], arr[i + 1]) > 0) {
                    T temp = arr[i];
                    arr[i] = arr[i + 1];
                    arr[i + 1] = temp;
                    flag = true;
                }
            }
            j++;
        }

/*        boolean finished = false;

        for (int i = 0; i < arr.length && !finished; i++) {
            finished = true;

            for (int j = arr.length - 1; j > i; j--) {
                if (comparator.compare(arr[j], arr[j - 1]) < 0) {
                    T temp = arr[j];
                    arr[j] = arr[j - 1];
                    arr[j - 1] = temp;
                    finished = false;
                }
            }
        }*/
    }

    /**
     * Implement insertion sort.
     *
     * It should be:
     *  in-place
     *  stable
     *
     * Have a worst case running time of:
     *  O(n^2)
     *
     * And a best case running time of:
     *  O(n)
     *
     * Any duplicates in the array should be in the same relative position after
     * sorting as they were before sorting. (stable).
     *
     * See the PDF for more info on this sort.
     *
     * @throws IllegalArgumentException if the array or comparator is null
     * @param <T> data type to sort
     * @param arr the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     */
    public static <T> void insertionSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("Cannot pass in null objects");
        }

        for (int i = 1; i < arr.length; i++) {
            int j = i;
            while (j > 0 && comparator.compare(arr[j - 1], arr[j]) > 0) {
                T temp = arr[j];
                arr[j] = arr[j - 1];
                arr[j - 1] = temp;

                j--;
            }
        }
    }

    /**
     * Implement selection sort.
     *
     * It should be:
     *  in-place
     *
     * Have a worst case running time of:
     *  O(n^2)
     *
     * And a best case running time of:
     *  O(n^2)
     *
     * Note that there may be duplicates in the array, but they may not
     * necessarily stay in the same relative order.
     *
     * @throws IllegalArgumentException if the array or comparator is null
     * @param <T> data type to sort
     * @param arr the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     */
    public static <T> void selectionSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("Cannot pass in null arguments");
        }

        for (int i = 0; i < arr.length - 1; i++) {
            int lowest = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (comparator.compare(arr[j], arr[lowest]) < 0) {
                    lowest = j;
                }
            }

            T temp = arr[i];
            arr[i] = arr[lowest];
            arr[lowest] = temp;
        }
    }

    /**
     * Implement quick sort.
     *
     * Use the provided random object to select your pivots.
     * For example if you need a pivot between a (inclusive)
     * and b (exclusive) where b > a, use the following code:
     *
     * int pivotIndex = r.nextInt(b - a) + a;
     *
     * It should be:
     *  in-place
     *
     * Have a worst case running time of:
     *  O(n^2)
     *
     * And a best case running time of:
     *  O(n log n)
     *
     * Note that there may be duplicates in the array.
     *
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not get full
     * credit if you do not use the one we have taught you!
     *
     * @throws IllegalArgumentException if the array or comparator or rand is
     * null
     * @param <T> data type to sort
     * @param arr the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @param rand the Random object used to select pivots
     */
    public static <T> void quickSort(T[] arr, Comparator<T> comparator,
                                     Random rand) {
        if (arr == null || comparator == null || rand == null) {
            throw new IllegalArgumentException(
                    "Cannot pass in null array or Random object");
        }

        quickSort(0, arr.length - 1, arr, comparator, rand);
    }

    /**
     * Helper recursive method for the quick sort, paritions
     * the array recursively around the pivot until the arary is sorted
     *
     * @param <T> the generic type being held in arr
     * @param a The left most element of the current partition
     * @param b The right most element of the current partition
     * @param arr THe array being sorted
     * @param comparator the comparator object for the values
     * @param rand the random object that selects the pivot
     */
    private static <T> void quickSort(int a, int b, T[] arr,
                                      Comparator<T> comparator, Random rand) {
        if (a >= b) {
            return;
        }

        int left = a + 1;
        int right = b;


        int pivot = rand.nextInt(b - a) + a;
        T pivotObject = arr[pivot];

        arr[pivot] = arr[a];
        arr[a] = pivotObject;

        T temp;

        while (left <= right) {
            while (left <= right && comparator.compare(arr[left],
                    pivotObject) < 0) {
                left++;
            }

            while (left <= right && comparator.compare(arr[right],
                    pivotObject) > 0) {
                right--;
            }

            if (left <= right) {
                temp = arr[left];
                arr[left] = arr[right];
                arr[right] = temp;
                left++;
                right--;
            }
        }

        temp = arr[right];
        arr[right] = arr[a];
        arr[a] = temp;

        quickSort(a, right - 1, arr, comparator, rand);
        quickSort(right + 1, b, arr, comparator, rand);

    }

    /**
     * Implement merge sort.
     *
     * It should be:
     *  stable
     *
     * Have a worst case running time of:
     *  O(n log n)
     *
     * And a best case running time of:
     *  O(n log n)
     *
     * You can create more arrays to run mergesort, but at the end,
     * everything should be merged back into the original T[]
     * which was passed in.
     *
     * Any duplicates in the array should be in the same relative position after
     * sorting as they were before sorting.
     *
     * @throws IllegalArgumentException if the array or comparator is null
     * @param <T> data type to sort
     * @param arr the array to be sorted
     * @param comparator the Comparator used to compare the data in arr
     */
    public static <T> void mergeSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("Cannot pass in null arguments");
        }

        T[] temp = (T[]) new Object[arr.length];
        mergeSort(arr, temp, 0, arr.length - 1, comparator);

    }

    /**
     * Helper method of mergeSort, recursively calls on the left and
     * right half until the entire array has been split into individual
     * units
     *
     * @param <T> the type held in arr
     * @param arr the array to be sorted
     * @param temp helper array to store elements in the array
     * @param left the index of the left element to be merged
     * @param right the index of the right element to be merged
     * @param comparator the comparator object for the values
     */
    private static <T> void mergeSort(T[] arr, T[] temp, int left,
            int right, Comparator<T> comparator) {
        if (left < right) {
            int center = (left + right) / 2;
            mergeSort(arr, temp, left, center, comparator);
            mergeSort(arr, temp, center + 1, right, comparator);
            merge(arr, temp, left, center + 1, right, comparator);
        }
    }


    /**
     * Merges the portions of the array that have been partitioned
     *
     * @param <T> the type held in arr
     * @param arr the array to be sorted
     * @param temp helper array to store elements in the array
     * @param left the index of the left element to be merged
     * @param right the index of the right element to be merged
     * @param rightLast the index that right is incremeted to
     * @param comparator the comparator object for the values
     */
    private static <T> void merge(T[] arr, T[] temp, int left,
            int right, int rightLast, Comparator<T> comparator) {
        int length = rightLast - left + 1;
        int leftLast = right - 1;

        int tempPointer = left;

        while (right <= rightLast && left <= leftLast) {
            if (comparator.compare(arr[left], arr[right]) <= 0) {
                temp[tempPointer] = arr[left];
                tempPointer++;
                left++;
            } else {
                temp[tempPointer] = arr[right];
                tempPointer++;
                right++;
            }
        }

        while (left <= leftLast) {
            temp[tempPointer] = arr[left];
            tempPointer++;
            left++;
        }
        while (right <= rightLast) {
            temp[tempPointer] = arr[right];
            tempPointer++;
            right++;
        }
        for (int i = 0; i < length; i++, rightLast--) {
            arr[rightLast] = temp[rightLast];
        }
    }


    /**
     * Implement LSD (least significant digit) radix sort.
     *
     * Remember you CANNOT convert the ints to strings at any point in your
     * code!
     *
     * It should be:
     *  stable
     *
     * Have a worst case running time of:
     *  O(kn)
     *
     * And a best case running time of:
     *  O(kn)
     *
     * Any duplicates in the array should be in the same relative position after
     * sorting as they were before sorting. (stable)
     *
     * Do NOT use {@code Math.pow()} in your sort. Instead, if you need to, use
     * the provided {@code pow()} method below.
     *
     * You may use {@code java.util.ArrayList} or {@code java.util.LinkedList}
     * if you wish, but it may only be used inside radix sort and any radix sort
     * helpers. Do NOT use these classes with other sorts.
     *
     * @throws IllegalArgumentException if the array is null
     * @param arr the array to be sorted
     * @return the sorted array
     */
    public static int[] lsdRadixSort(int[] arr) {
        if (arr == null) {
            throw new IllegalArgumentException("Cannot sort null array");
        }

        arr = lsd(arr, 1);

        return arr;

    }

    /**
     * Helper method for lsd sort, puts elements into buckets and
     * re-adds to array
     *
     * @param arr the array to be sorted
     * @param n the digit being compared
     * @return the sorted array
     */
    private static int[] lsd(int[] arr, int n) {
        LinkedList<Integer>[] buckets = new LinkedList[19];
        boolean shouldContinue = false;

        for (int i : arr) {
            if (i / pow(10, n) != 0) {
                shouldContinue = true;
            }

            int index = (i % pow(10, n)) / pow(10, n - 1) + 9;
            if (buckets[index] == null) {
                buckets[index] = new LinkedList<>();
            }

            buckets[index].add(i);
        }

        int arrayIndex = 0;
        for (int i = 0; i < buckets.length; i++) {
            if (buckets[i] != null) {
                for (Integer element : buckets[i]) {
                    arr[arrayIndex] = element;
                    arrayIndex++;
                }
            }
        }

        if (shouldContinue) {
            arr = lsd(arr, n + 1);
        }

        return arr;
    }
    /**
     * Implement MSD (most significant digit) radix sort.
     *
     * Remember you CANNOT convert the ints to strings at any point in your
     * code!
     *
     * It should:
     *
     * Have a worst case running time of:
     *  O(kn)
     *
     * And a best case running time of:
     *  O(kn)
     *
     * Do NOT use {@code Math.pow()} in your sort. Instead, if you need to, use
     * the provided {@code pow()} method below.
     *
     * You may use {@code java.util.ArrayList} or {@code java.util.LinkedList}
     * if you wish, but it may only be used inside radix sort and any radix sort
     * helpers. Do NOT use these classes with other sorts.
     *
     * @throws IllegalArgumentException if the array is null
     * @param arr the array to be sorted
     * @return the sorted array
     */
    public static int[] msdRadixSort(int[] arr) {
        if (arr == null) {
            throw new IllegalArgumentException("Cannot sort a null array");
        }
        int largest = Math.abs(arr[0]);
        for (int i = 1; i < arr.length; i++) {
            if (Math.abs(arr[i]) > largest) {
                largest = Math.abs(arr[i]);
            }
        }

        int maxDigits = 0;

        while (largest != 0) {
            largest /= 10;
            maxDigits++;
        }

        arr = msd(arr, maxDigits);

        return arr;
    }

    /**
     * Helper method for msd sort, puts elements into buckets and
     * sorts each bucket
     *
     * @param arr the array to be sorted
     * @param n the digit being compared
     * @return the sorted array
     */
    private static int[] msd(int[] arr, int n) {

        LinkedList<Integer>[] buckets = new LinkedList[19];
        for (int i : arr) {
            int index = i % pow(10, n) / pow(10, n - 1) + 9;

            if (buckets[index] == null) {
                buckets[index] = new LinkedList<>();
            }

            buckets[index].add(i);
        }

        for (int i = 0; i < buckets.length; i++) {
            if (buckets[i] != null) {
                buckets[i] = rearrangeBucket(buckets[i], n - 1);
            }
        }


        int curr = 0;
        for (LinkedList<Integer> ll : buckets) {
            if (ll != null) {
                for (Integer i : ll) {
                    arr[curr] = i;
                    curr++;
                }
            }
        }
        return arr;
    }

    /**
     * Helper method for msd sort, recursively sorts a bucket
     *
     * @param bucket the bucket being sorted
     * @param n the digit being compared
     * @return the sorted bucket
     */
    private static LinkedList<Integer> rearrangeBucket(
            LinkedList<Integer> bucket, int n) {
        //end case: Bucket is empty or entire bucket has been sorted
        if (bucket.size() == 1 || n == 0) {
            return bucket;
        }

        LinkedList<Integer>[] buckets = new LinkedList[19];

        for (Integer i : bucket) {
            int index = i % pow(10, n) / pow(10, n - 1) + 9;

            if (buckets[index] == null) {
                buckets[index] = new LinkedList<>();
            }

            buckets[index].add(i);
        }

        for (int i = 0; i < buckets.length; i++) {
            if (buckets[i] != null) {
                buckets[i] = rearrangeBucket(buckets[i], n - 1);
            }
        }

        bucket = new LinkedList<>();

        for (LinkedList<Integer> ll : buckets) {
            if (ll != null) {
                for (Integer i : ll) {

                    bucket.add(i);
                }
            }
        }

        return bucket;

    }

    /**
     * Calculate the result of a number raised to a power. Use this method in
     * your radix sorts instead of {@code Math.pow()}.digit
     *
     * DO NOT MODIFY THIS METHOD.
     *
     * @throws IllegalArgumentException if both {@code base} and {@code exp} are
     * 0
     * @throws IllegalArgumentException if {@code exp} is negative
     * @param base base of the number
     * @param exp power to raise the base to. Must be 0 or greater.
     * @return result of the base raised to that power
     */
    private static int pow(int base, int exp) {
        if (exp < 0) {
            throw new IllegalArgumentException("Exponent cannot be negative.");
        } else if (base == 0 && exp == 0) {
            throw new IllegalArgumentException(
                    "Both base and exponent cannot be 0.");
        } else if (exp == 0) {
            return 1;
        } else if (exp == 1) {
            return base;
        }
        int halfPow = pow(base, exp / 2);
        if (exp % 2 == 0) {
            return halfPow * halfPow;
        } else {
            return halfPow * pow(base, (exp / 2) + 1);
        }
    }
}
