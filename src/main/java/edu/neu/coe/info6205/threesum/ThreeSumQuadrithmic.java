package edu.neu.coe.info6205.threesum;

import edu.neu.coe.info6205.util.Stopwatch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

/**
 * Implementation of ThreeSum which follows the simple optimization of
 * requiring a sorted array, then using binary search to find an element x where
 * -x the sum of a pair of elements.
 * <p>
 * The array provided in the constructor MUST be ordered.
 * <p>
 * This algorithm runs in O(N^2 log N) time.
 */
class ThreeSumQuadrithmic implements ThreeSum {
    /**
     * Construct a ThreeSumQuadrithmic on a.
     * @param a a sorted array.
     */
    public ThreeSumQuadrithmic(int[] a) {
        this.a = a;
        length = a.length;
    }

    public Triple[] getTriples() {
        List<Triple> triples = new ArrayList<>();
        for (int i = 0; i < length; i++)
            for (int j = i + 1; j < length; j++) {
                Triple triple = getTriple(i, j);
                if (triple != null) triples.add(triple);
            }
        Collections.sort(triples);
        return triples.stream().distinct().toArray(Triple[]::new);
    }

    public Triple getTriple(int i, int j) {
        int index = Arrays.binarySearch(a, -a[i] - a[j]);
        if (index >= 0 && index > j) return new Triple(a[i], a[j], a[index]);
        else return null;
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
                ThreeSum target = new ThreeSumQuadrithmic(arr);
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

        // Print the results for the Three Sum Quadrithmic
        System.out.println("Three Sum Quadrithmic:");
        for (int i = 0; i < nValues.size(); i++) {
            // Get the integer value
            int xval = nValues.get(i);
            // Get the time taken
            double yval = timeTaken.get(i);
            // Print the integer value and the time taken
            System.out.println(" x : " + xval + " y : " + yval);
        }

        // Print the results for the Three Sum Quadrithmic in logarithmic form
        System.out.println("Three Sum Quadrithmic (Log-Log):");
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