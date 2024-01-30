package edu.neu.coe.info6205.threesum;

import edu.neu.coe.info6205.util.Stopwatch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Implementation of ThreeSum which follows the approach of dividing the solution-space into
 * N sub-spaces where each sub-space corresponds to a fixed value for the middle index of the three values.
 * Each sub-space is then solved by expanding the scope of the other two indices outwards from the starting point.
 * Since each sub-space can be solved in O(N) time, the overall complexity is O(N^2).
 * <p>
 * The array provided in the constructor MUST be ordered.
 */
public class ThreeSumQuadraticWithCalipers implements ThreeSum {
    /**
     * Construct a ThreeSumQuadratic on a.
     *
     * @param a a sorted array.
     */
    public ThreeSumQuadraticWithCalipers(int[] a) {
        this.a = a;
        length = a.length;
    }

    /**
     * Get an array or Triple containing all of those triples for which sum is zero.
     *
     * @return a Triple[].
     */
    public Triple[] getTriples() {
        List<Triple> triples = new ArrayList<>();
        Collections.sort(triples); // ???
        for (int i = 0; i < length - 2; i++)
            triples.addAll(calipers(a, i, Triple::sum));
        return triples.stream().distinct().toArray(Triple[]::new);
    }

    /**
     * Get a set of candidate Triples such that the first index is the given value i.
     * Any candidate triple is added to the result if it yields zero when passed into function.
     *
     * @param a        a sorted array of ints.
     * @param i        the index of the first element of resulting triples.
     * @param function a function which takes a triple and returns a value which will be compared with zero.
     * @return a List of Triples.
     */
    public static List<Triple> calipers(int[] a, int i, Function<Triple, Integer> function) {
        List<Triple> triples = new ArrayList<>();
        Arrays.sort(a);
        if (i == 0 || (i > 0 && a[i] != a[i - 1])) {
            int low = i + 1, high = a.length - 1, target = 0 - a[i];
            while (low < high) {
                if (a[low] + a[high] == target) {
                    Triple temp = new Triple(a[i], a[low], a[high]);
                    triples.add(temp);
                    while (low < high && a[low] == a[low + 1]) {
                        low++;
                    }
                    while (low < high && a[high] == a[high - 1]) {
                        high--;
                    }
                    low++;
                    high--;
                } else if (a[low] + a[high] < target) {
                    low++;
                } else {
                    high--;
                }
            }
        }
        return triples;
    }

    private final int[] a;
    private final int length;

    public static void main(String[] args){
        // Initialize a Supplier for integer arrays
        Supplier<int[]> numberSupplier = null;
        // Initialize a List to store integer arrays
        List<int[]> listOfArrays = new ArrayList<>();
        // Initialize a List to store integer values
        List<Integer> nValues = new ArrayList<>();
        // Initialize a List to store double values
        List<Double> nValuesDouble = new ArrayList<>();
        // Initialize a List to store time taken for operations
        List<Double> timeTaken = new ArrayList<>();
        // Initialize a List to store time taken for operations in double format
        List<Double> timeTakenDoubleArray = new ArrayList<>();

        // Call the calculateValues method with the initialized variables
        benchmarkAnalysis(numberSupplier, listOfArrays, nValues, nValuesDouble, timeTaken, timeTakenDoubleArray);
    }

    // Method to calculate values based on the 3SUM problem
    public static void benchmarkAnalysis(Supplier<int[]>numberSupplier, List<int[]> listOfArrays, List<Integer> nValues, List<Double> nValuesDouble,	List<Double> timeTaken, List<Double> timeTakenDoubleArray) {
        // Initialize a factor for calculations
        int factor = (int)1.2;
        // Initialize an integer array
        int[] arrInt = null;

        // Loop to generate integer arrays and store them
        for (int i = 1; i <= 8; i++) {
            // Multiply the factor by 2
            factor *= 2;
            // Generate a new integer array
            numberSupplier = new Source(100 * factor, 10).intsSupplier(10);
            // Get the generated array
            arrInt = numberSupplier.get();
            // Add the array to the list of arrays
            listOfArrays.add(arrInt);
            // Add the factor to the list of integer values
            nValues.add(factor * 100);
            // Add the logarithm of the factor to the list of double values
            nValuesDouble.add(Math.log(factor * 100));
        }

        // Loop to sort each array and calculate the time taken
        listOfArrays.forEach(arr -> {
            // Initialize a Stopwatch to measure time
            try (Stopwatch watch = new Stopwatch()) {
                // Sort the array
                Arrays.sort(arr);
                // Initialize a ThreeSum object with the sorted array
                ThreeSum target = new ThreeSumQuadraticWithCalipers(arr);
                // Get the triples from the ThreeSum object
                Triple[] triples = target.getTriples();
                // Get the elapsed time
                double timeEnd = watch.lap();
                // Add the elapsed time to the list of times
                timeTaken.add(timeEnd);
                // Add the logarithm of the elapsed time to the list of double times
                timeTakenDoubleArray.add(Math.log(timeEnd));
            }
        });

        // Print the results for the Three Sum Quadratic with Callipers
        System.out.println("Three Sum Quadratic With Callipers:");
        for (int i = 0; i < nValues.size(); i++) {
            // Get the integer value
            int xval = nValues.get(i);
            // Get the time taken
            double yval = timeTaken.get(i);
            // Print the integer value and the time taken
            System.out.println(" x : " + xval + " y : " + yval);
        }

        // Print the results for the Three Sum Quadratic with Callipers in logarithmic form
        System.out.println("Three Sum Quadratic with Callipers (Log-Log):");
        for (int i = 0; i < nValuesDouble.size(); i++) {
            // Get the double value
            double xval = nValuesDouble.get(i);
            // Get the double time taken
            double yval = timeTakenDoubleArray.get(i);
            // If the time taken is not infinite, print the double value and the time taken
            if (!Double.isInfinite(yval)) {
                System.out.println(" x : " + xval + " y : " + yval);
            }
        }
    }
}