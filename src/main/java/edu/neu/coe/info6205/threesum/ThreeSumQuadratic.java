package edu.neu.coe.info6205.threesum;

import edu.neu.coe.info6205.util.Stopwatch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

/**
 * Implementation of ThreeSum which follows the approach of dividing the solution-space into
 * N sub-spaces where each sub-space corresponds to a fixed value for the middle index of the three values.
 * Each sub-space is then solved by expanding the scope of the other two indices outwards from the starting point.
 * Since each sub-space can be solved in O(N) time, the overall complexity is O(N^2).
 * <p>
 * NOTE: The array provided in the constructor MUST be ordered.
 */
public class ThreeSumQuadratic implements ThreeSum {
    /**
     * Construct a ThreeSumQuadratic on a.
     *
     * @param a a sorted array.
     */
    public ThreeSumQuadratic(int[] a) {
        this.a = a;
        length = a.length;
    }

    public Triple[] getTriples() {
        List<Triple> triples = new ArrayList<>();
        for (int i = 0; i < length; i++) triples.addAll(getTriples(i));
        Collections.sort(triples);
        return triples.stream().distinct().toArray(Triple[]::new);
    }

    /**
     * Get a list of Triples such that the middle index is the given value j.
     *
     * @param j the index of the middle value.
     * @return a Triple such that
     */
    public List<Triple> getTriples(int j) {
        List<Triple> triples = new ArrayList<>();
        int first = j - 1;
        int last = j + 1;
        while (first >= 0 && last < length) {
            int currentSum = a[first] + a[j] + a[last];
            if (currentSum == 0) {
                triples.add(new Triple(a[first], a[j], a[last]));
                first--;
                last++;
            } else if (currentSum < 0) {
                last++;
            } else {
                first--;
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
                ThreeSum target = new ThreeSumQuadratic(arr);
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

        // Print the results for the Three Sum Quadratic
        System.out.println("Three Sum Quadratic:");
        for (int i = 0; i < nValues.size(); i++) {
            // Get the integer value
            int xval = nValues.get(i);
            // Get the time taken
            double yval = timeTaken.get(i);
            // Print the integer value and the time taken
            System.out.println(" x : " + xval + " y : " + yval);
        }

        // Print the results for the Three Sum Quadratic in logarithmic form
        System.out.println("Three Sum Quadratic (Log-Log):");
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