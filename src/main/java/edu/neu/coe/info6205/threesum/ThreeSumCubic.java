package edu.neu.coe.info6205.threesum;

import edu.neu.coe.info6205.util.Stopwatch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

/**
 * Implementation of ThreeSum which follows the brute-force approach of
 * testing every candidate in the solution-space.
 * The array provided in the constructor may be randomly ordered.
 * <p>
 * This algorithm runs in O(N^3) time.
 */
class ThreeSumCubic implements ThreeSum {
    /**
     * Construct a ThreeSumCubic on a.
     * @param a an array.
     */
    public ThreeSumCubic(int[] a) {
        this.a = a;
        length = a.length;
    }

    public Triple[] getTriples() {
        List<Triple> triples = new ArrayList<>();
        for (int i = 0; i < length; i++)
            for (int j = i + 1; j < length; j++) {
                for (int k = j + 1; k < length; k++) {
                    if (a[i] + a[j] + a[k] == 0)
                        triples.add(new Triple(a[i], a[j], a[k]));
                }
            }
        Collections.sort(triples);
        return triples.stream().distinct().toArray(Triple[]::new);
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

        // Print the results for the Three Sum Cubic
        System.out.println("Three Sum Cubic:");
        for (int i = 0; i < nValues.size(); i++) {
            // Get the integer value
            int xval = nValues.get(i);
            // Get the time taken
            double yval = timeTaken.get(i);
            // Print the integer value and the time taken
            System.out.println(" x : " + xval + " y : " + yval);
        }

        // Print the results for the Three Sum Cubic in logarithmic form
        System.out.println("Three Sum Cubic (Log-Log):");
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