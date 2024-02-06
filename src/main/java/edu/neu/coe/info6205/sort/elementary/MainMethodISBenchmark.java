package edu.neu.coe.info6205.sort.elementary;

import edu.neu.coe.info6205.util.Timer;

import java.util.Random;

public class MainMethodISBenchmark {
    public static void main(String[] args){
        Timer timer = new Timer();
        System.out.println("Insertion Sort Time Complexity Analysis");
        Integer[] random, ordered, partiallyOrdered, reverseOrdered;

        InsertionSort insertionSort = new InsertionSort();

        int numberOfTrials = 5;
        int n = 2500;


        for (int i = 0; i < numberOfTrials; i++) {
            System.out.println("Sorting Analysis for N: "+n +" items");
            random = constructArray(n,true,false,false,false);
            ordered = constructArray(n,false,true,false,false);
            partiallyOrdered = constructArray(n,false,false,true,false);
            reverseOrdered = constructArray(n,false,false,false,true);

            long startTime = System.currentTimeMillis();
            insertionSort.sort(random, 0, random.length);
            long endTime = System.currentTimeMillis();
            System.out.println("Random Array - Time elapsed: " + (endTime - startTime) + "ms");

            startTime = System.nanoTime();
            insertionSort.sort(ordered, 0, ordered.length);
            endTime = System.nanoTime();
            System.out.println("Ordered Array - Time elapsed: " + (endTime - startTime) + "ns");

            startTime = System.currentTimeMillis();
            insertionSort.sort(partiallyOrdered, 0, partiallyOrdered.length);
            endTime = System.currentTimeMillis();
            System.out.println("Partially ordered array - Time elapsed: " + (endTime - startTime) + "ms");

            startTime = System.currentTimeMillis();
            insertionSort.sort(reverseOrdered, 0, reverseOrdered.length);
            endTime = System.currentTimeMillis();
            System.out.println("Reverse ordered array - Time elapsed: " + (endTime - startTime) + "ms");

            System.out.println();
            n *= 2; // double n for next trial
        }
    }

    private static Integer[] constructArray(int n, boolean Random, boolean Ordered, boolean PartialOrdered, boolean ReverseOrdered) {

        Integer[] array = new Integer[n];
        if(Random) {
            Random random = new Random();

            for (int i = 0; i < n; i++) {
                array[i] = random.nextInt();
            }
        }

        else if(Ordered) {
            for (int i = 0; i < n; i++) {
                array[i] = i;
            }
        }
        else if(PartialOrdered) {
            for (int i = 0; i < n / 2; i++) {
                array[i] = i;
            }
            for (int i = n / 2; i < n; i++) {
                array[i] = n - i - 1;
            }
        }
        else if(ReverseOrdered) {
            for (int i = 0; i < n; i++) {
                array[i] = n - i - 1;
            }
        }
        return array;
    }
}
